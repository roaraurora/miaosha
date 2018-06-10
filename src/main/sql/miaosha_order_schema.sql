CREATE TABLE miaosha_order (
  id       BIGINT(20) NOT NULL AUTO_INCREMENT,
  user_id  BIGINT(20)          DEFAULT NULL
  COMMENT '用户ID',
  order_id BIGINT(20)          DEFAULT NULL
  COMMENT '订单ID',
  goods_id BIGINT(20)          DEFAULT NULL
  COMMENT '商品ID',
  PRIMARY KEY (id)
)
  ENGINE = innodb
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;