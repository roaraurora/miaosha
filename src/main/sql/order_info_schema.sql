CREATE TABLE order_info (
  id               BIGINT(20) NOT NULL AUTO_INCREMENT,
  user_id          BIGINT(20)          DEFAULT NULL
  COMMENT '用户id',
  goods_id         BIGINT(20)          DEFAULT NULL
  COMMENT '商品id',
  delivery_addr_id BIGINT(20)          DEFAULT NULL
  COMMENT '收获地址id',
  goods_name       VARCHAR(16)         DEFAULT NULL
  COMMENT '冗余过来的商品名称',
  goods_count      INT(11)             DEFAULT '0'
  COMMENT '商品数量',
  goods_price      DECIMAL(10, 2)      DEFAULT '0.00'
  COMMENT '商品单价',
  order_channel    TINYINT(4)          DEFAULT '0'
  COMMENT '1.pc, 2.android, 3.ios',
  status           TINYINT(4)          DEFAULT '0'
  COMMENT '订单状态: 0.新建未支付, 1.已支付, 2.已发货, 3.已收获, 4.已退款, 5.已完成',
  crate_date       DATETIME            DEFAULT NULL
  COMMENT '订单的创建时间',
  pay_date         DATETIME            DEFAULT NULL
  COMMENT '支付时间',
  PRIMARY KEY (id)
)
  ENGINE = innodb
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4