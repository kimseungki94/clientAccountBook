CREATE TABLE IF NOT EXISTS user (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(80) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`user_id`))
    ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS accountbook (
   `accountbook_id` BIGINT NOT NULL AUTO_INCREMENT,
   `transaction_type` VARCHAR(20) NOT NULL,
   `amount` BIGINT NOT NULL,
   `description` VARCHAR(100) NOT NULL,
   `expense_category` VARCHAR(80),
   `payment_type` VARCHAR(80),
   PRIMARY KEY (`accountbook_id`))
    ENGINE = InnoDB DEFAULT CHARSET=utf8;