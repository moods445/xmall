create table if not exists user(
  id bigint not null primary key auto_increment,
  gender tinyint  default '-1' comment '性别: -1: 未知;0: 男; 1 : 女',
  nick_name varchar(30) not null comment '昵称',
  phone varchar(30) default '' ,

  create_time datetime not null,
  update_time datetime not null
);