package com.github.wanjinzhong.easycronplugincenter.cache;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.github.wanjinzhong.easycronplugincenter.bo.ValCodeBo;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.ValCodeType;

public class ValCodeCache {
    private static Set<ValCodeBo> valCodes = new HashSet<>();

    public static void put(ValCodeBo valCode) {
        valCodes.add(valCode);
    }

    public static void remove(ValCodeBo code) {
        valCodes.remove(code);
    }

    public static ValCodeBo get(String email, ValCodeType type) {
        ValCodeBo need = new ValCodeBo();
        need.setEmail(email);
        need.setType(type);
        ValCodeBo res = null;
        for (ValCodeBo code : valCodes) {
            if (code.equals(need)) {
                res = code;
                break;
            }
        }
        if (isValCodeExpired(res)) {
            remove(res);
            return null;
        } else {
            return res;
        }
    }

    public static boolean isValCodeExpired(ValCodeBo code) {
        return code == null || code.getExpireTime() < Calendar.getInstance().getTimeInMillis();
    }
}
