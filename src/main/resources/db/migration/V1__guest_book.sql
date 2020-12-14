CREATE TABLE IF NOT EXISTS `gb_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(48) NOT NULL,
  `last_name` VARCHAR(16) NOT NULL,
  `email` VARCHAR(48) NOT NULL,
  `gsm` VARCHAR(10) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `is_admin` TINYINT(1) default 0,
  `create_dt` DATETIME NOT NULL,
  `update_dt` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `gsm_UNIQUE` (`gsm` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `gb_invitation` (
  `invite_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `message` VARCHAR(512) NULL,
  `card` VARCHAR(48) NULL,
  `invite_create_dt` DATETIME NOT NULL,
  `invite_update_dt` DATETIME NOT NULL,
  PRIMARY KEY (`invite_id`),
  UNIQUE INDEX `invite_id_UNIQUE` (`invite_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `uid`
    FOREIGN KEY (`user_id`)
    REFERENCES `gb_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;