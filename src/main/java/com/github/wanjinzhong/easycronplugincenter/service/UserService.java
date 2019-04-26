package com.github.wanjinzhong.easycronplugincenter.service;
import com.github.wanjinzhong.easycronplugincenter.bo.user.ChangePwdBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.LoginRequestBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.RegisterRequestBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.RegisterResultBo;
import com.github.wanjinzhong.easycronplugincenter.bo.user.UserInfo;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.ValCodeType;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.User;

public interface UserService {
    User findByEmail(String email);

    String login(LoginRequestBo requestBo);

    RegisterResultBo regist(RegisterRequestBo registerRequestBo);

    UserInfo getUserInfo();

    void updateName(String name);

    void getValCode(String email, ValCodeType type);

    void logout();

    void changePwd(ChangePwdBo changePwdBo);
}
