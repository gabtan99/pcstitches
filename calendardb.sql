-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema calendardb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema calendardb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `calendardb` DEFAULT CHARACTER SET utf8 ;
USE `calendardb` ;

-- -----------------------------------------------------
-- Table `calendardb`.`myevents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calendardb`.`myevents` (
  `event_id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `start_month` INT(11) NOT NULL,
  `start_day` INT(11) NOT NULL,
  `start_year` INT(11) NOT NULL,
  `start_hour` INT(11) NOT NULL,
  `start_minutes` INT(11) NOT NULL,
  `end_month` INT(11) NOT NULL,
  `end_day` INT(11) NOT NULL,
  `end_year` INT(11) NOT NULL,
  `end_hour` INT(11) NOT NULL,
  `end_minutes` INT(11) NOT NULL,
  `rgbid` INT(20) NOT NULL,
  PRIMARY KEY (`event_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
