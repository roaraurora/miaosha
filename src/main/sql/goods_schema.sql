CREATE TABLE goods (
  id           BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '商品id',
  goods_name   VARCHAR(16)         DEFAULT NULL
  COMMENT '商品名称',
  goods_title  VARCHAR(64)         DEFAULT NULL
  COMMENT '商品标题',
  goods_detail LONGTEXT COMMENT '商品的详情介绍',
  goods_img    VARCHAR(64)         DEFAULT NULL
  COMMENT '商品的图片',
  goods_price  DECIMAL(10, 2)      DEFAULT '0.00'
  COMMENT '商品单价',
  goods_stock  INT(11)             DEFAULT '0'
  COMMENT '秒杀商品库存, -1代表没有限制',
  PRIMARY KEY (id)
)
  ENGINE = innodb
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

# 插入数据
INSERT INTO goods VALUES (1, 'iPhoneX', 'Apple iPhone X(A1856) 64GB 银色 移动联通电信4G手机', '/img/iphonex.png',
                          'Apple iPhone X(A1856) 64GB 银色 移动联通电信4G手机', 8765.00, 10000),
  (2, '华为Mate9', '华为 Mate9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', '/img/mate9.png', '华为 Mate9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待',
   3212.00, -1);