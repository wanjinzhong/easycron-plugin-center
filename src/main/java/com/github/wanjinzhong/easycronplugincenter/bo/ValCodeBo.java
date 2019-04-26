package com.github.wanjinzhong.easycronplugincenter.bo;
import java.util.Objects;

import com.github.wanjinzhong.easycronplugincenter.constant.enums.ValCodeType;

public class ValCodeBo {
    private String email;
    private Long startTime;
    private Long expireTime;
    private ValCodeType type;
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ValCodeType getType() {
        return type;
    }

    public void setType(ValCodeType type) {
        this.type = type;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValCodeBo valCodeBo = (ValCodeBo) o;
        return Objects.equals(email, valCodeBo.email) &&
               type == valCodeBo.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, type);
    }
}
