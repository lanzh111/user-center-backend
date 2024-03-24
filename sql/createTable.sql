create table user
(
    id           bigint auto_increment comment '编号'
        primary key,
    userName     varchar(256)                           not null comment '名称',
    userAccount  varchar(256)                           null comment '账号',
    userPassword varchar(256)                           not null comment '密码',
    phone        char(18)                               null comment '手机号',
    sex          char(2)      default '男'               not null comment '性别',
    avatarUrl    varchar(1025)                          null comment '头像URL',
    createTime   datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 null comment '1启用 0禁用',
    userRole     tinyint(255) default 0                 not null comment '0 -普通用户 1-管理员',
    planetCode   varchar(255)                           not null comment '星球编号'
);

