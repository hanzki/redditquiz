# Add columns

# --- !Ups
ALTER TABLE subreddits
    ADD (subscribers INT,
         nsfw BOOLEAN,
         reddit_name VARCHAR(255),
         updated DATETIME);

ALTER TABLE reddit_images
    ADD (nsfw BOOLEAN,
         reddit_name VARCHAR(255));

# --- !Downs

ALTER TABLE subreddits
DROP subscribers,
DROP nsfw,
DROP reddit_name,
DROP updated;

ALTER TABLE reddit_images
DROP nsfw,
DROP reddit_name;