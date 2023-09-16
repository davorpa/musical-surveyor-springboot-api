-- ------------------------------------------------
-- SCHEMA.SQL - DDL SCRIPT
-- ------------------------------------------------


DROP TABLE IF EXISTS `raffle_prize`;
DROP TABLE IF EXISTS `raffle`;
DROP TABLE IF EXISTS `survey_responsing`;
DROP TABLE IF EXISTS `survey_participation`;
DROP TABLE IF EXISTS `raffle_ticket`;
DROP TABLE IF EXISTS `survey`;
DROP TABLE IF EXISTS `prize`;
DROP TABLE IF EXISTS `radio_listener`;
DROP TABLE IF EXISTS `song`;
DROP TABLE IF EXISTS `artist`;
DROP TABLE IF EXISTS `color`;






CREATE TABLE IF NOT EXISTS `color`(
    -- id columns
    `id`                BIGINT          NOT NULL  AUTO_INCREMENT,
    -- data columns
    `code`              VARCHAR(25)     NOT NULL,
    -- audit columns
    `created_on`        TIMESTAMP(6)    NOT NULL,
    `created_by`        VARCHAR(255)    NOT NULL,
    `last_modified_on`  TIMESTAMP(6)    NULL,
    `last_modified_by`  VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_color_id`
            PRIMARY KEY(`id`),
    CONSTRAINT `UK_color_code`
            UNIQUE (`code`),
    CONSTRAINT `CK_color_code_regex`
            CHECK (regexp_like(`code`, '^(\#[0-9A-F]{6})$|^([a-z]+)$', 'c'))
);



CREATE TABLE IF NOT EXISTS `artist`(
    -- id columns
    `id`                BIGINT          NOT NULL  AUTO_INCREMENT,
    -- data columns
    `name`              VARCHAR(255)    NOT NULL,
    `biography`         VARCHAR(2048)   NULL,
    -- audit columns
    `created_on`        TIMESTAMP(6)    NOT NULL,
    `created_by`        VARCHAR(255)    NOT NULL,
    `last_modified_on`  TIMESTAMP(6)    NULL,
    `last_modified_by`  VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_artist_id`
            PRIMARY KEY(`id`)
);



CREATE TABLE IF NOT EXISTS `song`(
    -- id columns
    `id`                BIGINT          NOT NULL  AUTO_INCREMENT,
    -- data columns
    `title`             VARCHAR(255)    NOT NULL,
    `year`              INT             NULL,
    `duration`          INT             NULL,
    `genre`             VARCHAR(50)     NULL,
    -- foreignal columns
    `artist_id`         BIGINT          NOT NULL,
    -- audit columns
    `created_on`        TIMESTAMP(6)    NOT NULL,
    `created_by`        VARCHAR(255)    NOT NULL,
    `last_modified_on`  TIMESTAMP(6)    NULL,
    `last_modified_by`  VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_song_id`
            PRIMARY KEY(`id`),
    CONSTRAINT `CK_song_year`
            CHECK (`year` IS NULL OR `year` >= 0),
    CONSTRAINT `CK_song_duration`
            CHECK (`duration` IS NULL OR `duration` >= 0),
    CONSTRAINT `FK_song_artist_id`
            FOREIGN KEY (`artist_id`)
            REFERENCES `artist`(`id`)
);



CREATE TABLE IF NOT EXISTS `radio_listener`(
    -- id columns
    `id`                BIGINT          NOT NULL  AUTO_INCREMENT,
    -- data columns
    `name`              VARCHAR(255)    NOT NULL,
    `phone`             VARCHAR(15)     NOT NULL,
    `address`           VARCHAR(500),
    `email`             VARCHAR(255)    NOT NULL,
    -- audit columns
    `created_on`        TIMESTAMP(6)    NOT NULL,
    `created_by`        VARCHAR(255)    NOT NULL,
    `last_modified_on`  TIMESTAMP(6)    NULL,
    `last_modified_by`  VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_radio_listener_id`
            PRIMARY KEY(`id`),
    CONSTRAINT `UK_radio_listener_email`
            UNIQUE (`email`),
    CONSTRAINT `CK_radio_listener_phone_regex`
            CHECK (`phone` REGEXP '^\+[0-9]{9,}$')
);



CREATE TABLE IF NOT EXISTS `prize`(
    -- id columns
    `id`                BIGINT          NOT NULL  AUTO_INCREMENT,
    -- data columns
    `title`             VARCHAR(255)    NOT NULL,
    `description`       VARCHAR(2048)   NULL,
    `monetary_value`    NUMERIC(10, 2)  NOT NULL,
    -- audit columns
    `created_on`        TIMESTAMP(6)    NOT NULL,
    `created_by`        VARCHAR(255)    NOT NULL,
    `last_modified_on`  TIMESTAMP(6)    NULL,
    `last_modified_by`  VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_prize_id`
            PRIMARY KEY(`id`),
    CONSTRAINT `UK_prize_title`
            UNIQUE (`title`),
    CONSTRAINT `CK_prize_monetary_value_ge_zero`
            CHECK (`monetary_value` >= 0)
);



CREATE TABLE IF NOT EXISTS `survey`(
    -- id columns
    `id`                    BIGINT          NOT NULL  AUTO_INCREMENT,
    -- data columns
    `title`                 VARCHAR(255)    NOT NULL,
    `description`           VARCHAR(2048)   NULL,
    `status`                VARCHAR(50)     NOT NULL,
    `start_date`            TIMESTAMP(6)    NOT NULL,
    `end_date`              TIMESTAMP(6)    NOT NULL,
    `num_max_participants`  INT             NOT NULL DEFAULT 50,
    `num_survey_responses`  INT             NOT NULL DEFAULT 3,
    -- audit columns
    `created_on`            TIMESTAMP(6)    NOT NULL,
    `created_by`            VARCHAR(255)    NOT NULL,
    `last_modified_on`      TIMESTAMP(6)    NULL,
    `last_modified_by`      VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_survey_id`
            PRIMARY KEY(`id`),
    CONSTRAINT `CK_survey_start_lt_end_date`
            CHECK (`start_date` < `end_date`),
    CONSTRAINT `CK_survey_status_enum`
            CHECK (`status` IN ('PENDING', 'RUNNING', 'CLOSED')),
    CONSTRAINT `CK_survey_num_max_participants_ge_zero`
            CHECK (`num_max_participants` >= 0),
    CONSTRAINT `CK_survey_num_responses_ge_zero`
            CHECK (`num_survey_responses` >= 0)
);



CREATE TABLE IF NOT EXISTS `raffle_ticket`(
    -- id columns
    `id`                BIGINT          NOT NULL  AUTO_INCREMENT,
    -- data columns
    `number`            VARCHAR(40)     NOT NULL,
    `color_id`          BIGINT          NOT NULL,

    -- named constraints
    CONSTRAINT `PK_raffle_ticket_id`
            PRIMARY KEY(`id`),
    CONSTRAINT `UK_raffle_ticket_number_color`
            UNIQUE (`number`, `color_id`),
    CONSTRAINT `CK_raffle_ticket_number_regex`
            CHECK (regexp_like(`number`, '^[0-9a-z]+$', 'c')),
    CONSTRAINT `FK_raffle_ticket_color_id`
            FOREIGN KEY (`color_id`)
            REFERENCES `color`(`id`)
);



CREATE TABLE IF NOT EXISTS `survey_participation`(
    -- id columns
    `survey_id`             BIGINT          NOT NULL,
    `participant_id`        BIGINT          NOT NULL,
    -- data columns
    `participated_at`       TIMESTAMP(6)    NULL,
    -- foreignal columns
    `raffle_ticket_id`      BIGINT          NULL,
    -- audit columns
    `created_on`            TIMESTAMP(6)    NOT NULL,
    `created_by`            VARCHAR(255)    NOT NULL,
    `last_modified_on`      TIMESTAMP(6)    NULL,
    `last_modified_by`      VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_survey_participation_id`
            PRIMARY KEY(`survey_id`, `participant_id`),
    CONSTRAINT `FK_survey_participation_survey_id`
            FOREIGN KEY (`survey_id`)
            REFERENCES `survey`(`id`),
    CONSTRAINT `FK_survey_participation_participant_id`
            FOREIGN KEY (`participant_id`)
            REFERENCES `radio_listener`(`id`),
    CONSTRAINT `FK_survey_participation_raffle_ticket_id`
            FOREIGN KEY (`raffle_ticket_id`)
            REFERENCES `raffle_ticket`(`id`),
    CONSTRAINT `UK_survey_participation_raffle_ticket_id`
            UNIQUE (`raffle_ticket_id`)
);



CREATE TABLE IF NOT EXISTS `survey_responsing`(
    -- id columns
    `survey_id`         BIGINT      NOT NULL,
    `participant_id`    BIGINT      NOT NULL,
    `song_id`           BIGINT      NOT NULL,

    -- named constraints
    CONSTRAINT `PK_survey_responsing_id`
            PRIMARY KEY(`survey_id`, `participant_id`, `song_id`),
    CONSTRAINT `FK_survey_responsing_participation_id`
            FOREIGN KEY (`survey_id`, `participant_id`)
            REFERENCES `survey_participation`(`survey_id`, `participant_id`),
    CONSTRAINT `FK_survey_responsing_song_id`
            FOREIGN KEY (`song_id`)
            REFERENCES `song`(`id`)
);



CREATE TABLE IF NOT EXISTS `raffle`(
    -- id columns
    `id`                    BIGINT          NOT NULL,
    -- data columns
    `status`                VARCHAR(50)     NOT NULL,
    `resolution_date`       TIMESTAMP(6)    NULL,
    -- audit columns
    `created_on`            TIMESTAMP(6)    NOT NULL,
    `created_by`            VARCHAR(255)    NOT NULL,
    `last_modified_on`      TIMESTAMP(6)    NULL,
    `last_modified_by`      VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_raffle_id`
            PRIMARY KEY(`id`),
    CONSTRAINT `CK_raffle_status_enum`
            CHECK (`status` IN ('PENDING', 'RUNNING', 'RESOLVED')),
    CONSTRAINT `FK_raffle_survey_id`
            FOREIGN KEY (`id`)
            REFERENCES `survey`(`id`)
);



CREATE TABLE IF NOT EXISTS `raffle_prize`(
    -- id columns
    `raffle_id`             BIGINT          NOT NULL,
    `prize_id`              BIGINT          NOT NULL,
    -- foreignal columns
    `winner_ticket_id`      BIGINT          NULL,
    -- audit columns
    `created_on`            TIMESTAMP(6)    NOT NULL,
    `created_by`            VARCHAR(255)    NOT NULL,
    `last_modified_on`      TIMESTAMP(6)    NULL,
    `last_modified_by`      VARCHAR(255)    NULL,

    -- named constraints
    CONSTRAINT `PK_raffle_prize_id`
            PRIMARY KEY(`raffle_id`, `prize_id`),
    CONSTRAINT `FK_raffle_prize_raffle_id`
            FOREIGN KEY (`raffle_id`)
            REFERENCES `raffle`(`id`),
    CONSTRAINT `FK_raffle_prize_prize_id`
            FOREIGN KEY (`prize_id`)
            REFERENCES `prize`(`id`),
    CONSTRAINT `FK_raffle_prize_winner_ticket_id`
            FOREIGN KEY (`winner_ticket_id`)
            REFERENCES `raffle_ticket`(`id`),
    CONSTRAINT `UK_raffle_prize_winner_ticket_id`
            UNIQUE (`winner_ticket_id`)
);
