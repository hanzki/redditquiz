# Test schema

# --- !Ups

DROP TABLE IF EXISTS subreddits;
CREATE TABLE subreddits (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=INNODB;

DROP TABLE IF EXISTS reddit_images;
CREATE TABLE reddit_images (
    id INT NOT NULL AUTO_INCREMENT,
    srd_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    src VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (srd_id) REFERENCES subreddits(id)
) ENGINE=INNODB;

DROP TABLE IF EXISTS image_quizs;
CREATE TABLE image_quizs (
    id INT NOT NULL AUTO_INCREMENT,
    img_id INT NOT NULL,
    choice_1 INT NOT NULL,
    choice_2 INT NOT NULL,
    choice_3 INT NOT NULL,
    choice_4 INT NOT NULL,
    choice_5 INT NOT NULL,
    choice_6 INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (img_id) REFERENCES reddit_images(id),
    FOREIGN KEY (choice_1) REFERENCES subreddits(id),
    FOREIGN KEY (choice_2) REFERENCES subreddits(id),
    FOREIGN KEY (choice_3) REFERENCES subreddits(id),
    FOREIGN KEY (choice_4) REFERENCES subreddits(id),
    FOREIGN KEY (choice_5) REFERENCES subreddits(id),
    FOREIGN KEY (choice_6) REFERENCES subreddits(id)
) ENGINE=INNODB;

# --- !Downs

DROP TABLE image_quizs;
DROP TABLE reddit_images;
DROP TABLE subreddits;
