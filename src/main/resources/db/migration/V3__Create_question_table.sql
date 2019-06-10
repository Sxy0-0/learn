CREATE TABLE question(
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255),
  `description` text,
  `gmt_create` bigint,
  `gmt_modified` bigint,
  `creator` int,
  `comment_count` int DEFAULT 0,
  `view_count` int DEFAULT 0,
  `like_count` int DEFAULT 0,
  `tag` varchar(255),
  PRIMARY KEY (`id`)
);