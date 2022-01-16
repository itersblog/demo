DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'User id',
  `email` varchar(80) NOT NULL COMMENT 'Email address',
  `name` varchar(50) NOT NULL COMMENT 'User name',
  `status` tinyint(4) NOT NULL COMMENT 'User status, 0 is valid, -1 is invalid(deleted)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Modify time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;