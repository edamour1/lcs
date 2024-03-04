USE lawncare_service;

CREATE TABLE `units`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `unit` VARCHAR(255) NULL
);

CREATE TABLE `ZIPCODES`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `zipcode` BIGINT NOT NULL,
	`city_id` BIGINT UNSIGNED NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `ADDITIONAL_SERVICES_LIST`(
    `id` BIGINT UNSIGNED NOT NULL,
    `additional_service_id` BIGINT UNSIGNED NOT NULL,
    `quantity` DECIMAL(8, 2) UNSIGNED NULL,
	`unit_id` BIGINT UNSIGNED NULL,
    PRIMARY KEY (`id`, `additional_service_id`) -- Composite key
);

CREATE TABLE `ADMIN`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NULL,
    `password` VARCHAR(255) NULL,
    `role` VARCHAR(255) NULL,
    `hint` VARCHAR(255) NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `ADDITIONAL_SERVICES`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `treatment_name` VARCHAR(255) NOT NULL,
    `treatment_description` VARCHAR(255) NOT NULL,
    `price` DECIMAL(8, 2) NOT NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `BUSINESS`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL,
    `phone` VARCHAR(255) NULL,
    `address_id` BIGINT UNSIGNED NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `TREATMENT_LIST`(
    `id` BIGINT UNSIGNED NOT NULL,
    `treatment_id` BIGINT UNSIGNED NOT NULL,
    `quantity` DECIMAL(8, 2) UNSIGNED NULL,
	`unit_id` BIGINT UNSIGNED NULL,
    PRIMARY KEY (`id`, `treatment_id`) -- Composite key
);

CREATE TABLE `ADDRESSES`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `client_id` BIGINT UNSIGNED NULL,
    `street` VARCHAR(255) NULL,
    `city_id` BIGINT UNSIGNED NULL,
    `state_id` BIGINT UNSIGNED NULL,
    `zipcode_id` BIGINT UNSIGNED NULL,
	`is_billing` tinyint(1) NULL,
	`is_active` tinyint(1) NULL,
	`quantity` DECIMAL(8, 2) NULL,
    `unit_id` BIGINT UNSIGNED NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `TREATMENTS`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `treatment_name` VARCHAR(255) NOT NULL,
    `treatment_description` VARCHAR(255) NOT NULL,
    `price` DECIMAL(8, 2) NOT NULL,
	
    PRIMARY KEY (`id`)
);

CREATE TABLE `STATES`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `state` VARCHAR(255) NOT NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `CITIES`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `city` VARCHAR(255) NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `CLIENTS`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NOT NULL,
    `middle_name` VARCHAR(255) NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NULL,
    `phone_number` VARCHAR(255) NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `INVOICE_INFORMATION`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `payment_due_date` DATE NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `client_id` BIGINT UNSIGNED NOT NULL,
    `notes` VARCHAR(255) NOT NULL,
    `additional_services_list_id` BIGINT UNSIGNED NULL,
    `treatment_list_id` BIGINT UNSIGNED NULL,
    `lm_user_id` VARCHAR(255) NOT NULL,
    `lm_date` DATETIME NOT NULL,
	`address_id` BIGINT UNSIGNED NULL,
	`invoice_no` VARCHAR(255) NOT NULL,
     PRIMARY KEY (`id`)
);

ALTER TABLE
    `INVOICE_INFORMATION` ADD CONSTRAINT `invoice_information_additional_services_list_id_foreign` FOREIGN KEY(`additional_services_list_id`) REFERENCES `ADDITIONAL_SERVICES_LIST`(`id`);
ALTER TABLE
    `ADDITIONAL_SERVICES_LIST` ADD CONSTRAINT `additional_services_list_additional_service_id_foreign` FOREIGN KEY(`additional_service_id`) REFERENCES `ADDITIONAL_SERVICES`(`id`);
ALTER TABLE
    `INVOICE_INFORMATION` ADD CONSTRAINT `invoice_information_treatment_list_id_foreign` FOREIGN KEY(`treatment_list_id`) REFERENCES `TREATMENT_LIST`(`id`);
ALTER TABLE
    `TREATMENT_LIST` ADD CONSTRAINT `treatment_list_treatment_id_foreign` FOREIGN KEY(`treatment_id`) REFERENCES `TREATMENTS`(`id`);
ALTER TABLE
    `ADDRESSES` ADD CONSTRAINT `addresses_zipcode_id_foreign` FOREIGN KEY(`zipcode_id`) REFERENCES `ZIPCODES`(`id`);
ALTER TABLE
    `ADDRESSES` ADD CONSTRAINT `addresses_unit_id_foreign` FOREIGN KEY(`unit_id`) REFERENCES `units`(`id`);
ALTER TABLE
    `ADDITIONAL_SERVICES_LIST` ADD CONSTRAINT `additional_services_list_unit_id_foreign` FOREIGN KEY(`unit_id`) REFERENCES `units`(`id`);
ALTER TABLE
    `TREATMENT_LIST` ADD CONSTRAINT `treatment_list_unit_id_foreign` FOREIGN KEY(`unit_id`) REFERENCES `units`(`id`);
ALTER TABLE
    `BUSINESS` ADD CONSTRAINT `business_address_id_foreign` FOREIGN KEY(`address_id`) REFERENCES `ADDRESSES`(`id`);
ALTER TABLE
    `INVOICE_INFORMATION` ADD CONSTRAINT `invoice_information_client_id_foreign` FOREIGN KEY(`client_id`) REFERENCES `CLIENTS`(`id`);
ALTER TABLE
    `ADDRESSES` ADD CONSTRAINT `addresses_client_id_foreign` FOREIGN KEY(`client_id`) REFERENCES `CLIENTS`(`id`);
ALTER TABLE
`INVOICE_INFORMATION` ADD CONSTRAINT `invoice_information_address_id_foreign` FOREIGN KEY(`address_id`) REFERENCES `ADDRESSES`(`id`);
ALTER TABLE
    `ADDRESSES` ADD CONSTRAINT `addresses_city_id_foreign` FOREIGN KEY(`city_id`) REFERENCES `CITIES`(`id`);
ALTER TABLE
    `ZIPCODES` ADD CONSTRAINT `zipcodes_city_id_foreign` FOREIGN KEY(`city_id`) REFERENCES `CITIES`(`id`);
ALTER TABLE
    `ADDRESSES` ADD CONSTRAINT `addresses_state_id_foreign` FOREIGN KEY(`state_id`) REFERENCES `STATES`(`id`);
    
    commit;