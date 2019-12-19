-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`venue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`venue` (
  `venue_id` INT(11) NOT NULL AUTO_INCREMENT,
  `sport_type` VARCHAR(45) NULL DEFAULT NULL,
  `site_count` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`venue_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`course` (
  `course_id` INT(11) NOT NULL,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `time_slot_id` INT(11) NULL DEFAULT NULL,
  `venue_id` INT(11) NULL DEFAULT NULL,
  `weekday` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  INDEX `time_slot_id_idx` (`time_slot_id` ASC) VISIBLE,
  INDEX `venue_id_idx` (`venue_id` ASC) VISIBLE,
  CONSTRAINT `venue_id2`
    FOREIGN KEY (`venue_id`)
    REFERENCES `mydb`.`venue` (`venue_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`site`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`site` (
  `site_id` INT(11) NOT NULL,
  `venue_id` INT(11) NOT NULL,
  `fee` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`site_id`, `venue_id`),
  INDEX `venue_id_idx` (`venue_id` ASC) VISIBLE,
  CONSTRAINT `venue_id`
    FOREIGN KEY (`venue_id`)
    REFERENCES `mydb`.`venue` (`venue_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`time_slot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`time_slot` (
  `time_slot_id` INT(11) NOT NULL AUTO_INCREMENT,
  `start_time` INT(11) NOT NULL,
  PRIMARY KEY (`time_slot_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `user_id` VARCHAR(7) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `phone_number` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`rent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`rent` (
  `r_id` INT(11) NOT NULL AUTO_INCREMENT,
  `v_id` INT(11) NOT NULL,
  `s_id` INT(11) NOT NULL,
  `t_id` INT(11) NOT NULL,
  `u_id` VARCHAR(7) NULL DEFAULT NULL,
  `date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`r_id`),
  INDEX `time_slot_id_idx` (`t_id` ASC) VISIBLE,
  INDEX `site_id_idx` (`s_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`u_id` ASC) VISIBLE,
  INDEX `venue_id1` (`v_id` ASC) VISIBLE,
  CONSTRAINT `site_id`
    FOREIGN KEY (`s_id`)
    REFERENCES `mydb`.`site` (`site_id`),
  CONSTRAINT `time_slot_id`
    FOREIGN KEY (`t_id`)
    REFERENCES `mydb`.`time_slot` (`time_slot_id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`u_id`)
    REFERENCES `mydb`.`user` (`user_id`),
  CONSTRAINT `venue_id1`
    FOREIGN KEY (`v_id`)
    REFERENCES `mydb`.`venue` (`venue_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 111
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`userlog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`userlog` (
  `log_id` INT(11) NOT NULL AUTO_INCREMENT,
  `register_date` DATE NULL DEFAULT NULL,
  `user_id` VARCHAR(45) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`worklog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`worklog` (
  `log_id` INT(11) NOT NULL AUTO_INCREMENT,
  `operate_date` DATE NULL DEFAULT NULL,
  `user_id` VARCHAR(45) NULL DEFAULT NULL,
  `operate` VARCHAR(45) NULL DEFAULT NULL,
  `venue_id` INT(11) NOT NULL,
  `site_id` INT(11) NOT NULL,
  `book_date` DATE NULL DEFAULT NULL,
  `book_time` INT(11) NULL DEFAULT NULL,
  `fee` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;

USE `mydb` ;

-- -----------------------------------------------------
-- function book_site
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` FUNCTION `book_site`(vid int,tid int,uid varchar(45),d date) RETURNS int(11)
    READS SQL DATA
    DETERMINISTIC
begin
	declare sid int;
	create temporary table if not exists cur_sid(s_id int);
    if (select count(u_id) from rent where u_id=uid and date=d and tid=t_id)>=1 
    then return -1;
    end if;
	insert into cur_sid
		select distinct site_id from site where venue_id=vid and site_id not in 
		(select distinct s_id from rent where s_id=site_id and v_id=vid and t_id=tid);
        
    select * into sid from cur_sid order by s_id asc limit 0,1;
	insert into rent values(null,vid,sid,tid,uid,d);
    return sid;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure booker_info
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` PROCEDURE `booker_info`(vid int,sid int,t_id int,d datetime)
begin
	select u_id,name,phone_number
    from rent natural join user
    where rent.v_id=vid and rent.s_id=sid and rent.t_id=tid and rent.date=d;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure booker_record
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` PROCEDURE `booker_record`(uid varchar(45))
begin
	select user_id,name,rent.v_id,sport_type,s_id,t_id,date
    from (user join rent on user.user_id=rent.u_id) join venue on rent.v_id=venue.venue_id
    where uid=user.user_id;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure cancel_book
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` PROCEDURE `cancel_book`(vid int,d date,tid int,uid varchar(45))
begin
	delete from rent where rent.v_id=vid and rent.date=d and rent.t_id=tid and rent.u_id=uid;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure register
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` PROCEDURE `register`(id varchar(45),un varchar(45),pn varchar(45),pw varchar(45))
begin
	insert into user values(id,un,pn,pw);
end$$

DELIMITER ;

-- -----------------------------------------------------
-- function site_fee
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` FUNCTION `site_fee`(vid int,sid int) RETURNS int(11)
    READS SQL DATA
    DETERMINISTIC
begin
	declare f int;
	select fee into f from site where site_id = sid and venue_id=vid;
    return f;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure site_info
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` PROCEDURE `site_info`(s_type varchar(45),d date)
begin
	declare wd int;
    declare vid int;
    declare sc int;
    declare i int;
    declare a int;
	create temporary table if not exists temp(time_slot_id int,cur_site_count int);
    truncate TABLE temp;
    select venue_id into vid from venue where sport_type=s_type;
    select dayofweek(d) into wd;
    select site_count into sc from venue where venue_id=vid;
    set a=0;
    set  i=1;
    while i<=12 do
    

        
	insert into temp
		select time_slot_id,0
		from course 
		where venue_id=vid and course.weekday= wd and time_slot_id=i;
    
	insert into temp
		select t_id,sc-count(s_id)
		from rent where rent.v_id=vid and t_id=rent.t_id and date =d and rent.t_id=i
		group by t_id;
	
   if (select count(time_slot_id) from temp) !=i then
		insert into temp select i,sc;
	end if;
	
    set i = i+1;
	end while;
    select * from temp order by time_slot_id;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure user_info
-- -----------------------------------------------------

DELIMITER $$
USE `mydb`$$
CREATE DEFINER=`ruby`@`%` PROCEDURE `user_info`(uid varchar(45))
begin
	select user_id,name,phone_number 
    from user
    where user.user_id=uid;
end$$

DELIMITER ;
USE `mydb`;

DELIMITER $$
USE `mydb`$$
CREATE
DEFINER=`ruby`@`%`
TRIGGER `mydb`.`register_log`
AFTER INSERT ON `mydb`.`user`
FOR EACH ROW
begin
	insert into userlog values(null,curdate(),new.user_id,new.name);
end$$

USE `mydb`$$
CREATE
DEFINER=`ruby`@`%`
TRIGGER `mydb`.`cancel_log`
AFTER DELETE ON `mydb`.`rent`
FOR EACH ROW
begin
	declare f int;
    select fee into f from site where old.v_id=site.venue_id and old.s_id=site.site_id;
    insert into worklog values(null,curdate(),old.u_id,'canceled book',old.v_id,old.s_id,old.date,old.t_id,-f);
end$$

USE `mydb`$$
CREATE
DEFINER=`ruby`@`%`
TRIGGER `mydb`.`rent_log`
AFTER INSERT ON `mydb`.`rent`
FOR EACH ROW
begin
	declare f int;
    select fee into f from site where new.v_id=site.venue_id and new.s_id=site.site_id;
	insert into worklog values(null,curdate(),new.u_id,'success rent',new.v_id,new.s_id,new.date,new.t_id,f);
end$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
