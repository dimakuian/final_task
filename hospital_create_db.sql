DROP DATABASE IF EXISTS hospital;
CREATE DATABASE `hospital`;
USE `hospital`;

CREATE TABLE `language`(
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `short_name` VARCHAR(2) NOT NULL,
                           `full_name` VARCHAR(45),
                           PRIMARY KEY (`id`)
);

CREATE TABLE `user` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `login` VARCHAR(45) NOT NULL ,
                        `password` VARCHAR (45) NOT NULL,
                        `role` ENUM ('administrator', 'doctor', 'nurse') NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `login_UNIQUE` (`login`)
);

CREATE TABLE `staff`(
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `user_id` INT NOT NULL,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `fk_staff_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `doctor_specialty`(
                                   `id` INT NOT NULL AUTO_INCREMENT,
                                   `title` VARCHAR(255)UNIQUE,
                                   `language` INT NOT NULL,
                                   PRIMARY KEY (`id`),
                                   CONSTRAINT `fk_specialty_language` FOREIGN KEY (language) REFERENCES `language` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `doctor`(
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `first_name` VARCHAR (45) NOT NULL,
                         `last_name` VARCHAR (45) NOT NULL,
                         `age` DATE NOT NULL,
                         `gender` ENUM ('male','female') NOT NULL,
                         `staff_id` INT NOT NULL,
                         `specialty_id` INT DEFAULT NULL,
                         UNIQUE KEY `staff_id_UNIQUE` (`staff_id`),
                         KEY `fk_doctor_staff1_idx` (`staff_id`),
                         PRIMARY KEY (`id`),
                         CONSTRAINT `fk_doctor_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT `fk_doctor_specialty` FOREIGN KEY (`specialty_id`) REFERENCES `doctor_specialty` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `nurse`(
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `first_name` VARCHAR (45) NOT NULL,
                        `last_name` VARCHAR (45) NOT NULL,
                        `age` DATE NOT NULL,
                        `gender` ENUM ('male','female') NOT NULL,
                        `staff_id` INT,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `fk_nurse_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `patient`(
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `first_name` VARCHAR (50) NOT NULL,
                          `last_name` VARCHAR (50) NOT NULL,
                          `age` DATE NOT NULL,
                          `gender` enum('male','female') NOT NULL,
                          `status` VARCHAR (45) NOT NULL,
                          `doctor_id` INT DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          CONSTRAINT `fk_patient_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `medical_file` (
                                `id` INT NOT NULL AUTO_INCREMENT,
                                `diagnosis` VARCHAR(255) DEFAULT 'Unknown',
                                `patient_id` INT NOT NULL,
                                PRIMARY KEY (`id`),
                                CONSTRAINT `fk_card_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `therapy` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `title` varchar(255) NOT NULL,
                           `type` enum('drug','procedure','operation') NOT NULL,
                           `status` enum('done','in progress') NOT NULL,
                           `medical_file_id` int NOT NULL,
                           `staff_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           CONSTRAINT `fk_therapy_card` FOREIGN KEY (`medical_file_id`) REFERENCES `medical_file` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           CONSTRAINT `fk_therapy_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);