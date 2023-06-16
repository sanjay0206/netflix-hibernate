CREATE TABLE subscribers (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
subscription_plan ENUM('basic', 'standard', 'premium') NOT NULL,
account_status ENUM('active', 'suspended', 'cancelled') NOT NULL.
region ENUM('North America', 'South America', 'Europe', 'Africa', 'Asia', 'Oceania') NOT NULL DEFAULT 'North America';
UNIQUE (email)
);

CREATE TABLE content (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
release_year INT NOT NULL,
content_type ENUM('movie', 'tv_show') NOT NULL,
genre ENUM('action', 'comedy', 'drama', 'thriller', 'horror') NOT NULL,
description TEXT NOT NULL,
duration INT NOT NULL,
rating INT NOT NULL,
cast TEXT NOT NULL,
director VARCHAR(255) NOT NULL,
studio VARCHAR(255) NOT NULL,
trailer_url VARCHAR(255) NOT NULL
);

CREATE TABLE watchlist (
id INT AUTO_INCREMENT PRIMARY KEY,
subscriber_id INT NOT NULL,
content_id INT NOT NULL,
favorite ENUM('yes', 'no') DEFAULT 'no',
FOREIGN KEY (subscriber_id) REFERENCES subscribers(id),
FOREIGN KEY (content_id) REFERENCES content(id)
);

