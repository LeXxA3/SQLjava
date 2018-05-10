-- MySQL Script generated by MySQL Workbench
-- Tue Apr  3 20:59:31 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema compshop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema compshop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `compshop` DEFAULT CHARACTER SET utf8 ;
USE `compshop` ;

-- -----------------------------------------------------
-- Table `compshop`.`parameters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compshop`.`parameters` (
  `paramID` INT NOT NULL AUTO_INCREMENT,
  `parameterName` TEXT NULL,
  `ed` INT NULL,
  PRIMARY KEY (`paramID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `compshop`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compshop`.`company` (
  `companyID` INT NOT NULL AUTO_INCREMENT,
  `companyName` TEXT NULL,
  PRIMARY KEY (`companyID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `compshop`.`components`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compshop`.`components` (
  `componentID` INT NOT NULL AUTO_INCREMENT,
  `companyID` INT NOT NULL,
  `componentName` TEXT NULL,
  `cost` INT NULL,
  `type` TEXT NULL,
  PRIMARY KEY (`componentID`, `companyID`),
  INDEX `fk_components_company1_idx` (`companyID` ASC),
  CONSTRAINT `fk_components_company1`
    FOREIGN KEY (`companyID`)
    REFERENCES `compshop`.`company` (`companyID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `compshop`.`paramComponents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compshop`.`paramComponents` (
  `paramID` INT NOT NULL,
  `ComponentID` INT NOT NULL,
  `value` INT NULL,
  PRIMARY KEY (`paramID`, `ComponentID`),
  INDEX `fk_paramComponents_components1_idx` (`ComponentID` ASC),
  CONSTRAINT `fk_paramComponents_parameters`
    FOREIGN KEY (`paramID`)
    REFERENCES `compshop`.`parameters` (`paramID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_paramComponents_components1`
    FOREIGN KEY (`ComponentID`)
    REFERENCES `compshop`.`components` (`componentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `compshop`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compshop`.`clients` (
  `clientID` INT NOT NULL AUTO_INCREMENT,
  `clientName` TEXT NULL,
  `Email` TEXT NULL,
  `age` INT NULL,
  PRIMARY KEY (`clientID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `compshop`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `compshop`.`orders` (
  `orderID` INT NOT NULL AUTO_INCREMENT,
  `clientID` INT NOT NULL,
  `orderDate` DATE NULL,
  `completeDate` DATE NULL,
  `componentID` INT NOT NULL,
  PRIMARY KEY (`orderID`, `clientID`, `componentID`),
  INDEX `fk_orders_components1_idx` (`componentID` ASC),
  INDEX `fk_orders_clients1_idx` (`clientID` ASC),
  CONSTRAINT `fk_orders_components1`
    FOREIGN KEY (`componentID`)
    REFERENCES `compshop`.`components` (`componentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_clients1`
    FOREIGN KEY (`clientID`)
    REFERENCES `compshop`.`clients` (`clientID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;