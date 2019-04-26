package com.github.wanjinzhong.easycronplugincenter.dao.repository;

import java.util.List;

import com.github.wanjinzhong.easycronplugincenter.constant.enums.ListCatalog;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.ListBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListBoxRepository extends JpaRepository<ListBox, Integer> {

    List<ListBox> findByCatalog(ListCatalog catalog);

    ListBox findByCatalogAndCode(ListCatalog catalog, String code);
}
