create table if not exists orders(
  id bigint not null primary key auto_increment,
  platform_order_id varchar(85) not null ,
  currency varchar(10) not null comment '币种',
  amount int not null comment '对应人民币是分',
  product_id bigint not null ,
  product_num bigint not null,
  state tinyint(5) comment '状态: 0 :未支付，1：已支付，2：已过期',

  create_time datetime not null,
  update_time datetime not null
);