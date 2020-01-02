CREATE DATABASE auctions;
USE auctions;

CREATE TABLE url_views (
  id INT(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT, 
  email VARCHAR(255),
  click_count INT(10) NOT NULL,
  templatename VARCHAR(36),
  url VARCHAR(255),
  create_time DATE not null
);


CREATE TABLE user_token (
  id INT(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT, 
  email VARCHAR(255),
  currentstatus tinyint(1) unsigned NOT NULL,
  reset_token VARCHAR(36) 
);

CREATE TABLE blog (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(500) NOT NULL,
  content VARCHAR(5000) NOT NULL
);
CREATE TABLE user_role_assignment (
  USER_ROLE_PK_ID INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  USER_NAME VARCHAR(50) NOT NULL,
  ROLE_ID int(10) unsigned NOT NULL,
  IS_ACTIVE tinyint(1) unsigned NOT NULL
);

CREATE TABLE user_product_assignment (
  id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  USER_NAME VARCHAR(50) NOT NULL,
  PRODUCT_TYPE VARCHAR(30) NOT NULL,
  EXPIRE_PERIOD DATE not null,
  CREATION_DATE DATE not null,
  FREE_TRIAL tinyint(1) unsigned NOT NULL,
  EXPIRED_FLAG tinyint(1) unsigned NOT NULL
);
SET GLOBAL event_scheduler = ON;
DELIMITER $$
CREATE EVENT deactivate_freeTrial_service
    ON SCHEDULE
      EVERY 1 HOUR
    ON COMPLETION PRESERVE
DO BEGIN
  update auctions.user_product_assignment up set up.FREE_TRIAL = 1,up.EXPIRED_FLAG = 1  where datediff(now(),up.EXPIRE_PERIOD)>=0;
END;$$
DELIMITER ;
SELECT * FROM INFORMATION_SCHEMA.events;

create table brand(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
brand_id VARCHAR(30) NOT NULL,brand_name VARCHAR(100) NOT NULL);

create table accompliance_details(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
acc_id VARCHAR(30) NOT NULL,designation VARCHAR(30) NOT NULL);

create table comments_given(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
comment_id VARCHAR(20) NOT NULL,comment_desc VARCHAR(100) NOT NULL);

create table doctor_details(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 doctor_id VARCHAR(20) NOT NULL,doctor_name VARCHAR(100) NOT NULL,area_of_expertise VARCHAR(100) NOT NULL);
 
create table feedback_provided(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 feedback_id VARCHAR(20) NOT NULL,feedback_desc VARCHAR(100) NOT NULL);
 
create table highlighted_feature(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 feature_id VARCHAR(20) NOT NULL,feature_desc VARCHAR(100) NOT NULL);
 
create table gift_given(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  gift_id VARCHAR(20) NOT NULL,gift_desc VARCHAR(100) NOT NULL);
  
create table medical_representative(id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
rep_id VARCHAR(20) NOT NULL,doctor_id VARCHAR(20) NOT NULL,rep_desc VARCHAR(100) NOT NULL);