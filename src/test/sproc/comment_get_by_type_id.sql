DROP PROCEDURE IF EXISTS `comment_get_by_type_id`;
DELIMITER $$
CREATE PROCEDURE `comment_get_by_type_id`(
  IN in_type_id INT,
  IN in_offset INT,
  IN in_page_size INT,
  OUT out_total_count INT
)
BEGIN
    SELECT SQL_CALC_FOUND_ROWS * FROM xut_comment WHERE xut_type_id = in_type_id
    LIMIT in_offset, in_page_size;
    SET out_total_count = FOUND_ROWS();
END $$
DELIMITER ;