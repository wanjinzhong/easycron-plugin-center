package com.github.wanjinzhong.easycronplugincenter.service.impl;
import java.util.Calendar;
import java.util.UUID;

import javax.transaction.Transactional;

import com.github.wanjinzhong.easycronplugincenter.bo.ValCodeBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.ChangePwdBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.LoginRequestBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.RegisterRequestBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.RegisterResultBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.UserInfo;
import com.github.wanjinzhong.easycronplugincenter.cache.ValCodeCache;
import com.github.wanjinzhong.easycronplugincenter.config.EasyCronToken;
import com.github.wanjinzhong.easycronplugincenter.constant.Constant;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.ListCatalog;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.UserRole;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.UserStatus;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.ValCodeType;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.ListBox;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.User;
import com.github.wanjinzhong.easycronplugincenter.dao.repository.ListBoxRepository;
import com.github.wanjinzhong.easycronplugincenter.dao.repository.UserRepository;
import com.github.wanjinzhong.easycronplugincenter.exception.BizException;
import com.github.wanjinzhong.easycronplugincenter.service.MailService;
import com.github.wanjinzhong.easycronplugincenter.service.UserService;
import com.github.wanjinzhong.easycronplugincenter.utils.UuidUtil;
import com.github.wanjinzhong.easycronplugincenter.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Autowired
    private MailService mailService;

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getId() == 1) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public String login(LoginRequestBo requestBo) {
        EasyCronToken token = new EasyCronToken(requestBo);
        try {
            SecurityUtils.getSubject().login(token);
            return "OK";
        } catch (IncorrectCredentialsException e) {
            throw new AuthenticationException("密码错误", e);
        }
    }

    @Override
    public RegisterResultBo regist(RegisterRequestBo registerRequestBo) {
        if (StringUtils.isBlank(registerRequestBo.getName())) {
            throw new BizException("用户名不能为空");
        }
        if (StringUtils.isBlank(registerRequestBo.getEmail())) {
            throw new BizException("邮箱不能为空");
        }
        if (!ValidatorUtil.isEmail(registerRequestBo.getEmail())) {
            throw new BizException("邮箱格式不正确");
        }
        if (StringUtils.isBlank(registerRequestBo.getPassword()) || registerRequestBo.getPassword().length() < 6) {
            throw new BizException("密码不能小于6位");
        }
        if (StringUtils.isBlank(registerRequestBo.getValCode())) {
            throw new BizException("验证码不能为空");
        }
        ValCodeBo valCode = ValCodeCache.get(registerRequestBo.getEmail(), ValCodeType.REGISTER);
        if (valCode == null) {
            throw new BizException("请先获取验证码");
        } else if (!valCode.getCode().equalsIgnoreCase(registerRequestBo.getValCode())) {
            throw new BizException("验证码不正确");
        }
        User user = userRepository.findByEmail(registerRequestBo.getEmail());
        if (user != null) {
            throw new BizException("用户已存在");
        }
        String pwd = registerRequestBo.getPassword();
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String securityPwd = new SimpleHash("MD5", pwd, salt, Constant.HASH_ITERATIONS).toHex();
        user = new User();
        user.setEmail(registerRequestBo.getEmail());
        user.setName(registerRequestBo.getName());
        user.setSalt(salt);
        user.setPassword(securityPwd);
        ListBox normal = listBoxRepository.findByCatalogAndCode(ListCatalog.USER_STATUS, UserStatus.NORMAL.name());
        user.setStatus(normal);
        ListBox listBox = listBoxRepository.findByCatalogAndCode(ListCatalog.USER_ROLE, UserRole.NORMAL.name());
        user.setRole(listBox);
        userRepository.save(user);
        RegisterResultBo resultBo = new RegisterResultBo();
        boolean status = mailService.sendNewUserEmail(user.getEmail(), pwd, user.getName());
        resultBo.setEmailSuccess(status);
        resultBo.setEmail(registerRequestBo.getEmail());
        if (!status) {
            resultBo.setPassword(pwd);
        }
        ValCodeCache.remove(valCode);
        return resultBo;
    }

    @Override
    public UserInfo getUserInfo() {
        User user = userRepository.findByEmail((String) SecurityUtils.getSubject().getPrincipal());
        UserInfo userInfo = toUserInfo(user);
        return userInfo;
    }

    private UserInfo toUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setEmail(user.getEmail());
        userInfo.setStatus(user.getStatus().getDisplayName());
        userInfo.setStatusCode(user.getStatus().getCode());

        userInfo.setRole(user.getRole().getCode());
        return userInfo;
    }

    @Override
    public void updateName(String name) {
        User user = userRepository.findById(getUserInfo().getId()).orElse(null);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        if (!user.getId().equals(userInfo.getId())) {
            throw new UnauthorizedException();
        }
        user.setName(name);
        userRepository.save(user);
    }

    @Override
    public void getValCode(String email, ValCodeType type) {
        String code = UuidUtil.getUuid().substring(0, 8);
        String userName = "新用户";
        if (ValCodeType.PWD_RESET.equals(type)) {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new BizException("用户不存在");
            } else {
                userName = user.getName();
            }
        }
        ValCodeBo valCodeBo = new ValCodeBo();
        valCodeBo.setEmail(email);
        valCodeBo.setCode(code);
        System.out.println(code);
        valCodeBo.setStartTime(Calendar.getInstance().getTimeInMillis());
        Calendar expireTime = Calendar.getInstance();
        expireTime.add(Calendar.MINUTE, 30);
        valCodeBo.setExpireTime(expireTime.getTimeInMillis());
        valCodeBo.setType(type);
        ValCodeCache.put(valCodeBo);
        if (ValCodeType.REGISTER.equals(type)) {
            mailService.sendRegisterValCodeEmail(email, userName, code);
        } else {
            mailService.sendChangePwdValCodeEmail(email, userName, code);
        }
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    @Override
    public void changePwd(ChangePwdBo changePwdBo) {
        if (changePwdBo.getPwd().length() < 6) {
            throw new BizException("密码不能小于6位");
        }
        UserInfo userInfo = getUserInfo();
        ValCodeBo valCodeBo = ValCodeCache.get(userInfo.getEmail(), ValCodeType.PWD_RESET);
        if (valCodeBo == null) {
            throw new BizException("请先获取验证码");
        }
        if (ValCodeCache.isValCodeExpired(valCodeBo)) {
            ValCodeCache.remove(valCodeBo);
            throw new BizException("验证码已过期，请重新获取验证码");
        }
        if (!valCodeBo.getCode().equalsIgnoreCase(changePwdBo.getValCode())) {
            throw new BizException("验证码不正确");
        }
        User user = userRepository.findById(userInfo.getId()).orElse(null);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String securityPwd = new SimpleHash("MD5", changePwdBo.getPwd(), salt, Constant.HASH_ITERATIONS).toHex();
        user.setSalt(salt);
        user.setPassword(securityPwd);
        userRepository.save(user);
        ValCodeCache.remove(valCodeBo);
    }
}
