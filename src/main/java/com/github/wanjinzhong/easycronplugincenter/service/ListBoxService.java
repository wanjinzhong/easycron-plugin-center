package com.github.wanjinzhong.easycronplugincenter.service;
import com.github.wanjinzhong.easycronplugincenter.constant.enums.ListCatalog;

public interface ListBoxService {
    String getNotNullDisplayName(ListCatalog catalog, String configCode);
}
