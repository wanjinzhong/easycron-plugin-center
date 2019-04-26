package com.github.wanjinzhong.easycronplugincenter.service.impl;

import java.util.List;

import com.github.wanjinzhong.easycronplugincenter.bo.SettingBo;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.Setting;
import com.github.wanjinzhong.easycronplugincenter.dao.repository.SettingRepository;
import com.github.wanjinzhong.easycronplugincenter.exception.BizException;
import com.github.wanjinzhong.easycronplugincenter.service.MailService;
import com.github.wanjinzhong.easycronplugincenter.service.SettingService;
import com.github.wanjinzhong.easycronplugincenter.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingRepository userProfileRepository;

    @Autowired
    private MailService mailService;

    @Override
    public SettingBo getSettings() {
        List<Setting> settings = userProfileRepository.findAll();
        SettingBo settingBo = new SettingBo();
        for (Setting setting : settings) {
            switch (setting.getKey()) {
                case EMAIL_USERNAME:
                    settingBo.setEmail(setting.getValue());
                    break;
                case EMAIL_PASSWORD:
                    settingBo.setEmailPwd(setting.getValue());
                    break;
                case EMAIL_HOST:
                    settingBo.setEmailHost(setting.getValue());
                    break;
                case EMAIL_PORT:
                    settingBo.setEmailPort(setting.getValue());
                    break;
            }
        }
        return settingBo;
    }

    @Override
    public boolean saveSettings(SettingBo settingBo) {
        validateSetting(settingBo);
        List<Setting> settings = userProfileRepository.findAll();
        for (Setting setting : settings) {
            switch (setting.getKey()) {
                case EMAIL_USERNAME:
                    setting.setValue(settingBo.getEmail());
                    break;
                case EMAIL_PASSWORD:
                    setting.setValue(settingBo.getEmailPwd());
                    break;
                case EMAIL_HOST:
                    setting.setValue(settingBo.getEmailHost());
                    break;
                case EMAIL_PORT:
                    setting.setValue(settingBo.getEmailPort());
                    break;
            }
        }
        userProfileRepository.saveAll(settings);
        mailService.flush();
        return mailService.testConnection();
    }

    private void validateSetting(SettingBo settingBo) {
        if (StringUtils.isBlank(settingBo.getEmail())) {
            throw new BizException("邮箱用户名不能为空");
        }
        if (StringUtils.isBlank(settingBo.getEmailPwd())) {
            throw new BizException("邮箱密码不能为空");
        }
        if (StringUtils.isBlank(settingBo.getEmailHost())) {
            throw new BizException("邮件服务器不能为空");
        }
        if (StringUtils.isBlank(settingBo.getEmailPort())) {
            throw new BizException("邮件服务器端口不能为空");
        }
        if (!ValidatorUtil.isEmail(settingBo.getEmail())) {
            throw new BizException("邮箱格式不正确");
        }
    }
}
