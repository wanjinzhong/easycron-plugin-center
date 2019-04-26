create table list_box
(
    id           int auto_increment
        primary key,
    list_catalog varchar(50)   not null,
    code         varchar(50)   not null,
    detail       varchar(1024) null,
    display_name varchar(100)  null,
    seq          int           not null
)
    collate = utf8_bin;

INSERT INTO EC_PLUGIN_CENTER_TEST.list_box (id, list_catalog, code, detail, display_name, seq) VALUES (1, 'USER_ROLE', 'ADMIN', null, '管理员', 1);
INSERT INTO EC_PLUGIN_CENTER_TEST.list_box (id, list_catalog, code, detail, display_name, seq) VALUES (2, 'USER_ROLE', 'NORMAL', null, '普通用户', 2);
INSERT INTO EC_PLUGIN_CENTER_TEST.list_box (id, list_catalog, code, detail, display_name, seq) VALUES (3, 'USER_STATUS', 'NORMAL', null, '正常', 1);
INSERT INTO EC_PLUGIN_CENTER_TEST.list_box (id, list_catalog, code, detail, display_name, seq) VALUES (4, 'USER_STATUS', 'DISABLED', null, '禁用', 2);
create table plugin
(
    id           int auto_increment,
    name         varchar(200)  not null,
    description  varchar(1024) null,
    bucket_addr  varchar(512)  not null,
    thumb_addr   varchar(1024) null,
    group_id     varchar(512)  not null,
    artifact_id  varchar(128)  not null,
    main_class   varchar(512)  not null,
    version      varchar(45)   not null,
    base_version varchar(45)   not null,
    status       int           not null,
    owner        int           not null,
    constraint id_UNIQUE
        unique (id),
    constraint logic_unique
        unique (group_id, artifact_id)
);

alter table plugin
    add primary key (id);


create table setting
(
    id            int auto_increment
        primary key,
    setting_key   varchar(256) not null,
    setting_value varchar(256) null
)
    collate = utf8_bin;

INSERT INTO EC_PLUGIN_CENTER_TEST.setting (id, setting_key, setting_value) VALUES (14, 'EMAIL_HOST', 'smtp.163.com');
INSERT INTO EC_PLUGIN_CENTER_TEST.setting (id, setting_key, setting_value) VALUES (15, 'EMAIL_PORT', '465');
INSERT INTO EC_PLUGIN_CENTER_TEST.setting (id, setting_key, setting_value) VALUES (16, 'EMAIL_USERNAME', 'wan_neil@163.com');
INSERT INTO EC_PLUGIN_CENTER_TEST.setting (id, setting_key, setting_value) VALUES (17, 'EMAIL_PASSWORD', '');
create table user
(
    id       int auto_increment
        primary key,
    email    varchar(100) not null,
    name     varchar(50)  not null,
    password varchar(50)  not null,
    salt     varchar(20)  not null,
    status   int          null,
    role     int          not null,
    constraint user_I2
        unique (email)
)
    collate = utf8_bin;
