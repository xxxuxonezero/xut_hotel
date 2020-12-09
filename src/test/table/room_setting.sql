DROP TABLE IN EXISTS `xut_room_setting`;
CREATE TABLE `xut_room_setting`(
  xut_id INT AUTO_INCREMENT PRIMARY KEY,
  xut_type_id INT NOT NULL,
  xut_wifi VARCHAR(100),
  xut_food VARCHAR(100),
  xut_air_conditioner VARCHAR(50),
  xut_spoc VARCHAR(100) COMMENT '酒店周边的名胜',
  xut_facilities VARCHAR(200)
);