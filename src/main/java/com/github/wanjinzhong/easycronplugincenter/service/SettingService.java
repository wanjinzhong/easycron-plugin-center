package com.github.wanjinzhong.easycronplugincenter.service;
import com.github.wanjinzhong.easycronplugincenter.bo.SettingBo;

public interface SettingService {
    SettingBo getSettings();

    boolean saveSettings(SettingBo settingBo);
}
