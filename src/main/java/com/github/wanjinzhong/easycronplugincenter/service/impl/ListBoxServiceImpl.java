package com.github.wanjinzhong.easycronplugincenter.service.impl;

import com.github.wanjinzhong.easycronplugincenter.constant.enums.ListCatalog;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.ListBox;
import com.github.wanjinzhong.easycronplugincenter.dao.repository.ListBoxRepository;
import com.github.wanjinzhong.easycronplugincenter.service.ListBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ListBoxServiceImpl implements ListBoxService {

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Override
    public String getNotNullDisplayName(ListCatalog catalog, String configCode) {
        String str = "";
        ListBox listBox = listBoxRepository.findByCatalogAndCode(catalog, configCode);
        if (listBox != null) {
            str = listBox.getDisplayName();
        }
        return str;
    }
}
