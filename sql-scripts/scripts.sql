CREATE DATABASE  IF NOT EXISTS `employee_directory`;
USE `employee_directory`;

--
-- `employee` tablosu
--

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- `employee` tablosu verileri
--

INSERT INTO `employee` VALUES 
	(1,'Deniz','Demir','deniz@gmail.com'),
	(2,'Ahmet','Yılmaz','ahmet@gmail.com'),
	(3,'Esra','Gezer','esra@gmail.com'),
	(4,'Yusuf','Sezer','yusuf@gmail.com'),
	(5,'Elif','Yener','elif@gmail.com');

--
-- `user` tablosu
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- `user` tablosu verileri
--
-- Parolalar BCrypt kullanılarak şifrelendi
--
-- Şifreleme aracı: https://www.bcryptcalculator.com/
--
-- Varsayılan parolalar: 123
--

INSERT INTO `user` (username,password,first_name,last_name,email)
VALUES 
('john','$2a$10$zV1dRVSPX/bUmMkIxybQDeuV22PNL3j/iXJ8.Lt/x0JPesFPvDKkq.5PM0K','Hasan','Mesut','hasan@gmail.com'),
('mary','$2a$10$zV1dRVSPX/bUmMkIxybQDeuV22PNL3j/iXJ8.Lt/x0JPesFPvDKkq.5PM0K','Rıza','Selim','riza@gmail.com'),
('susan','$2a$10$zV1dRVSPX/bUmMkIxybQDeuV22PNL3j/iXJ8.Lt/x0JPesFPvDKkq.5PM0K','Eylül','Akman','eylul@gmail.com');


--
-- `role` tablosu
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- `role` tablosu verileri
--

INSERT INTO `role` (name)
VALUES 
('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN');

--
-- `users_roles` tablosu
--

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

--
-- `users_roles` tablosu verileri
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);

--
-- `files` tablosu
--

DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` varchar(50) NOT NULL,
  `data` blob,
  `name` varchar(68),
  `type` varchar(68)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- `files` tablosu verileri
--

INSERT INTO `files` 
VALUES 
(1,"ROLE_MANAGER"),
(2,"ROLE_MANAGER","ROLE_EMPLOYEE"),
(3,"ROLE_MANAGER","ROLE_ADMIN","ROLE_ADMIN");


DROP TABLE IF EXISTS `review`;

--
-- `review` tablosu
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(256) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),

  KEY `FK_USER_ID_idx` (`user_id`),

  CONSTRAINT `FK_USER` 
  FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 

  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


SET FOREIGN_KEY_CHECKS = 1;

