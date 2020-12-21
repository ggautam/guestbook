INSERT INTO `guestdb`.`gb_user` (`uid`, `name`, `email`, `gsm`, `password`, `is_admin`, `create_dt`) 
VALUES ('1', 'Admin', 'admin@gbtest.com', '9876543210', '$2a$10$oSQ8t2rd1bCdV9wwEANnuO66S8ycPbacVq/AT6VZ8v/wh/5OovKju', 1, CURDATE());
INSERT INTO `guestdb`.`gb_user` (`uid`, `name`, `email`, `gsm`, `password`, `is_admin`, `create_dt`) 
VALUES ('2', 'Guest', 'guest@gbtest.com', '9876543211', '$2a$10$oSQ8t2rd1bCdV9wwEANnuO66S8ycPbacVq/AT6VZ8v/wh/5OovKju', 0, CURDATE());