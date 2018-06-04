CREATE TABLE miaosha_user (
  id              BIGINT(20)   NOT NULL
  COMMENT '用户ID, 手机号', /*todo 增加自增主键 索引*/
  nickname        VARCHAR(255) NOT NULL
  COMMENT '用户昵称',
  password        VARCHAR(32)  DEFAULT NULL
  COMMENT 'MD5(MD5(pass明文+固定salt) + 随机salt)',
  salt            VARCHAR(10)  DEFAULT NULL
  COMMENT '随机salt',
  head            VARCHAR(128) DEFAULT NULL
  COMMENT '头像, 云注册的ID',
  register_date   DATETIME     DEFAULT NULL
  COMMENT '注册时间',
  last_login_date DATETIME     DEFAULT NULL
  COMMENT '上次登录时间',
  login_count     INT(11)      DEFAULT '0'
  COMMENT '登录次数',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4