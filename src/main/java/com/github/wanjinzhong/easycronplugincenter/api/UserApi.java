package com.github.wanjinzhong.easycronplugincenter.api;
import java.util.UUID;

import com.github.wanjinzhong.easycronplugincenter.bo.response.JsonEntity;
import com.github.wanjinzhong.easycronplugincenter.bo.user.BasicUserBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.ChangePwdBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.LoginRequestBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.LoginResultBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.RegisterRequestBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.RegisterResultBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.UserInfo;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.ValCodeType;
import com.github.wanjinzhong.easycronplugincenter.service.UserService;
import com.github.wanjinzhong.easycronplugincenter.utils.ResponseHelper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public JsonEntity<LoginResultBo> login(@RequestBody LoginRequestBo loginRequestBo) {
        userService.login(loginRequestBo);
        return ResponseHelper.createInstance(new LoginResultBo(UUID.randomUUID().toString().replace("-", "")));
    }

    @PostMapping("logout")
    @RequiresAuthentication
    public JsonEntity<LoginResultBo> logout() {
        userService.logout();
        return ResponseHelper.ofNothing();
    }

    @PostMapping("user")
    public JsonEntity<RegisterResultBo> register(@RequestBody RegisterRequestBo registerRequestBo) {
        RegisterResultBo res = userService.regist(registerRequestBo);
        return ResponseHelper.createInstance(res);
    }

    @GetMapping("userInfo")
    @RequiresAuthentication
    public JsonEntity<UserInfo> getUserInfo() {
        return ResponseHelper.createInstance(userService.getUserInfo());
    }


    @PutMapping("user/name")
    @RequiresAuthentication
    public JsonEntity changeName(@RequestBody BasicUserBo userBo) {
        userService.updateName(userBo.getName());
        return ResponseHelper.ofNothing();
    }

    @GetMapping("user/valCode")
    public JsonEntity getValCode(@RequestParam("email") String email, @RequestParam("type") ValCodeType type) {
        userService.getValCode(email, type);
        return ResponseHelper.ofNothing();
    }

    @PutMapping("user/password")
    @RequiresAuthentication
    public JsonEntity changePassword(@RequestBody ChangePwdBo changePwdBo) {
        userService.changePwd(changePwdBo);
        return ResponseHelper.ofNothing();
    }
}
