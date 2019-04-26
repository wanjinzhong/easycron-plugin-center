package com.github.wanjinzhong.easycronplugincenter.config;
import static com.google.common.collect.Sets.newHashSet;

import com.github.wanjinzhong.easycronplugincenter.constant.enums.UserStatus;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.User;
import com.github.wanjinzhong.easycronplugincenter.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class EasyCronRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String email = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = userService.findByEmail(email);
        validateUser(user);
        info.setRoles(newHashSet(user.getRole().getCode()));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        EasyCronToken token = (EasyCronToken) authenticationToken;
        String email = (String) token.getPrincipal();
        User user = userService.findByEmail(email);
        validateUser(user);
        return new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }

    private void validateUser(User user) {
        if (user != null) {
            if (UserStatus.DISABLED.equals(user.getStatus().getCode())) {
                throw new DisabledAccountException("账户已被禁用");
            }
        } else {
            throw new UnknownAccountException();
        }
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof EasyCronToken;
    }
}
