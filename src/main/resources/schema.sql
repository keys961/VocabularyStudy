DROP DATABASE IF EXISTS VocabularyStudy;
CREATE DATABASE VocabularyStudy;
ALTER DATABASE VocabularyStudy CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE VocabularyStudy;

# User table
CREATE TABLE `User`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(20) UNIQUE NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) UNIQUE NOT NULL
);
CREATE INDEX UserIndex ON User(username, email);

# Category table: CET-4, CET-6, GRE etc..
CREATE TABLE `Category`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `category` VARCHAR(16) UNIQUE
);

# Vocabulary table
CREATE TABLE `Vocabulary`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `word` VARCHAR(128) NOT NULL,
  `translation` VARCHAR(256),
  `phonetic` VARCHAR(256),
  `tag` VARCHAR(16),
  `category` BIGINT REFERENCES Category ON DELETE NO ACTION
);
CREATE INDEX VocabularyIndex ON Vocabulary(id, category);

# Collections of vocabulary collected by user
CREATE TABLE `UserCollection`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user` BIGINT REFERENCES User ON DELETE CASCADE,
  `word` BIGINT REFERENCES Vocabulary ON DELETE CASCADE
);
CREATE INDEX UserCollectionIndex ON UserCollection(user);

# Learn plan: if set (user chose the word set), user must create the plan
CREATE TABLE `LearnPlan`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user` BIGINT REFERENCES User ON DELETE CASCADE,
  `category` BIGINT REFERENCES Category ON DELETE CASCADE,
  `start_time` DATE,
  `end_time` DATE # Use this to determine how many words he/she will learn today
);

# Learn task for each day..
CREATE TABLE `LearnTask`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user` BIGINT REFERENCES User ON DELETE CASCADE,
  `category` BIGINT REFERENCES Category ON DELETE CASCADE,
  `learn_plan` BIGINT REFERENCES LearnPlan ON DELETE CASCADE,
  `learn_time` DATE
);

# Task item (each word)
CREATE TABLE `LearnTaskItem`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `task_id` BIGINT REFERENCES LearnTask ON DELETE CASCADE,
  `word` BIGINT REFERENCES Vocabulary ON DELETE CASCADE
);
CREATE INDEX LearnTaskItem ON LearnTaskItem(id, task_id);

# Words user has learned (To get revision task & progress)
CREATE TABLE `LearnWordHistory`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user` BIGINT REFERENCES User ON DELETE CASCADE,
  `category` BIGINT REFERENCES Category ON DELETE CASCADE,
  `word` BIGINT REFERENCES Vocabulary ON DELETE CASCADE,
  `learn_time` DATE
);
CREATE INDEX LearnWordHistoryIndex ON LearnWordHistory(user, category, learn_time);

# Word sets user has learned
CREATE TABLE `LearnCategoryHistory`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user` BIGINT REFERENCES User ON DELETE CASCADE,
  `category` BIGINT REFERENCES Category ON DELETE CASCADE,
  `learn_time` DATE
);

CREATE TABLE `TestHistory`
(
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user` BIGINT REFERENCES User ON DELETE CASCADE,
  `category` BIGINT REFERENCES Category ON DELETE NO ACTION,
  `total_score` INT NOT NULL,
  `score` INT NOT NULL
);

# Then,
# please RUN vocabularystudy.ImportVocabularyHandler.main(String[])
# to import vocabulary data!