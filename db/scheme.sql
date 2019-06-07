CREATE TABLE `Ticket` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`dateTime` TIMESTAMP NOT NULL,
	`seat` INT NOT NULL,
	`user` INT,
	`event` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `User` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`firstName` varchar(255) NOT NULL,
	`lastName` varchar(255) NOT NULL,
	`email` varchar(255) NOT NULL UNIQUE,
	`birthday` DATE NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Event` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`basePrice` FLOAT NOT NULL,
	`rating` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `CounterAspect` (
	`event` INT NOT NULL,
	`byName` INT,
	`byPrice` INT,
	`byBooked` INT,
	PRIMARY KEY (`event`)
);

CREATE TABLE `DiscountAspect` (
	`discount` VARCHAR(255) NOT NULL,
	`amount` DOUBLE,
	PRIMARY KEY (`discount`)
);

ALTER TABLE `Ticket` ADD CONSTRAINT `Ticket_fk0` FOREIGN KEY (`user`) REFERENCES `User`(`id`);

ALTER TABLE `Ticket` ADD CONSTRAINT `Ticket_fk1` FOREIGN KEY (`event`) REFERENCES `Event`(`id`);

ALTER TABLE `CounterAspect` ADD CONSTRAINT `CounterAspect_fk0` FOREIGN KEY (`event`) REFERENCES `Event`(`id`);
