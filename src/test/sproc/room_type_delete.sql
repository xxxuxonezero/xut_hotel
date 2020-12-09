CREATE PROCEDURE IF NOT EXISTS `room_type_delete`;
DELIMITER $$
CREATE PROCEDURE `room_type_delete`(
  IN in_ids MEDIUMTEXT
)
BEGIN
  DELETE FROM `xut_room_type` WHERE FIND_IN_SET(xut_id, in_ids);
END $$
DELIMITER ;