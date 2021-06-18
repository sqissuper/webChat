drop database if exists java_chatroom;
create database java_chatroom character set utf8mb4;

use java_chatroom;

create table user (userId int primary key auto_increment,
                   name varchar(50) unique,
                   password varchar(50),
                   nickName varchar(50),   -- 昵称
                   iconPath varchar(2048), -- 头像路径
                   signature varchar(100),
                   lastLogout DateTime -- 上次登录时间
); -- 个性签名

insert into user values(null, 'test', '123', '巧克力', '', '我好吃又甜', now());
insert into user values(null, 'test2', '123', '巧乐兹', '', '我好吃', now());
insert into user values(null, 'test3', '123', '红玫瑰', '', '我很美', now());
insert into user values(null, 'test4', '123', '棒棒糖', '', '我很甜', now());



create table channel (channelId int primary key auto_increment,
                      channelName varchar(50)
);
insert into channel values(null, '体坛赛事');
insert into channel values(null, '娱乐八卦');
insert into channel values(null, '时事新闻');
insert into channel values(null, '午夜情感');



create table message (messageId int primary key auto_increment,
                      userId int, -- 谁发的
                      channelId int, -- 发到哪个频道中
                      content text, -- 消息内容是啥
                      sendTime DateTime default now()    -- 发送时间
);

insert into message values (null, 1, 1, 'hehe1', now());
insert into message values (null, 1, 1, 'hehe2', now());
insert into message values (null, 1, 1, 'hehe3', now());