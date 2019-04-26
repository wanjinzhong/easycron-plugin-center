package com.github.wanjinzhong.easycronplugincenter.utils;
import java.util.UUID;

public class UuidUtil {
    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
