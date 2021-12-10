-- USE master
-- GO

-- ALTER DATABASE techsharing SET SINGLE_USER WITH ROLLBACK IMMEDIATE
-- GO
-- DROP DATABASE techsharing
-- GO

CREATE DATABASE techsharing
GO

USE techsharing
GO

-- User
CREATE TABLE tbl_user (
    col_username VARCHAR(30) NOT NULL,
    col_password VARCHAR(100) NOT NULL,
    col_active BIT DEFAULT 1 NOT NULL,

    PRIMARY KEY(col_username)
)
GO

-- User Info
CREATE TABLE tbl_user_info (
    col_username VARCHAR(30) REFERENCES tbl_user(col_username),
    col_avatar VARCHAR(500) NOT NULL DEFAULT 'https://www.apple.com/ac/structured-data/images/open_graph_logo.png?201809210816',
    col_email VARCHAR(100) NULL,

    PRIMARY KEY (col_username)
)
GO

-- Role
CREATE TABLE tbl_role (
    col_id INT IDENTITY(1,1),
    col_role_name NVARCHAR(50) NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- User Role
CREATE TABLE tbl_user_role (
    col_id INT IDENTITY(1,1),
    col_username VARCHAR(30) REFERENCES tbl_user(col_username) UNIQUE NOT NULL,
    col_role_id INT REFERENCES tbl_role(col_id) NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- Article
CREATE TABLE tbl_article (
    col_id INT IDENTITY(1,1),
    col_title NVARCHAR(500) NOT NULL,
    col_description NTEXT,
    col_content NTEXT NOT NULL,
    col_date_created DATE NOT NULL DEFAULT GETDATE(),
    col_time_created TIME NOT NULL DEFAULT GETDATE(),
    col_author VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_url NVARCHAR(500) UNIQUE NOT NULL,
    col_category NVARCHAR(50) NOT NULL,
    col_thumbnail_url NVARCHAR(500) DEFAULT 'https://www.apple.com/ac/structured-data/images/open_graph_logo.png?201809210816' NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- Article Evaluation
CREATE TABLE tbl_article_evaluation (
    col_article_id INT REFERENCES tbl_article(col_id),
    col_upvote INT DEFAULT 0 NOT NULL,
    col_downvote INT DEFAULT 0 NOT NULL,

    PRIMARY KEY(col_article_id)
)
GO

-- Comment
CREATE TABLE tbl_comment (
    col_id INT IDENTITY(1,1),
    col_author VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_article_id INT REFERENCES tbl_article(col_id) NOT NULL,
    col_date DATE DEFAULT GETDATE() NOT NULL,
    col_time TIME DEFAULT GETDATE() NOT NULL,
    col_content NTEXT NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- User Vote State
CREATE TABLE tbl_user_vote_state (
    col_id INT IDENTITY(1,1),
    col_article_id INT REFERENCES tbl_article(col_id) NOT NULL,
    col_username VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_vote_state INT NOT NULL, -- 0: none | 1: up | -1: down
    
    PRIMARY KEY(col_id)
)
GO


-- Init data
INSERT INTO tbl_user (col_username, col_password, col_active) VALUES ('admin', '$2a$10$Oq2/BUGgxCaeKjcWEnpiyOyh0aGOeltqUM9Db44cRJvlfX9npXmzy', 1)
GO

INSERT INTO tbl_user_role (col_username, col_role_id) VALUES ('admin', 1)
GO
