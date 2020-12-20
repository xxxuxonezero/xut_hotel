CREATE TABLE client(
  xut_id INT AUTO_INCREMENT PRIMARY KEY,
  xut_order_id INT NOT NULL,
  xut_realname VARCHAR(20) NOT NULL,
  xut_identification_id CHAR(18) NOT NULL,
  xut_phone CHAR(11) NOT NULL
)