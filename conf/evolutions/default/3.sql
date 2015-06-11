# Add unique constraints

# --- !Ups
ALTER TABLE subreddits
    ADD CONSTRAINT uc_Name UNIQUE (name);

ALTER TABLE reddit_images
    ADD CONSTRAINT uc_SubredditSrc UNIQUE (srd_id, src);

# --- !Downs

ALTER TABLE subreddits
DROP INDEX uc_name;

ALTER TABLE reddit_images
DROP INDEX uc_SubredditSrc;