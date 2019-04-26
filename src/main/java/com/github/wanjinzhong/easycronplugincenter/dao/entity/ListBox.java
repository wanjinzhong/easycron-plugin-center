package com.github.wanjinzhong.easycronplugincenter.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.wanjinzhong.easycronplugincenter.constant.enums.ListCatalog;

@Entity
@Table(name = "list_box")
public class ListBox implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "list_catalog")
    @Enumerated(EnumType.STRING)
    private ListCatalog catalog;

    @Column(name = "code")
    private String code;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "detail")
    private String detail;

    public Integer getId() {
        return id;
    }

    public ListCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(ListCatalog catalog) {
        this.catalog = catalog;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
