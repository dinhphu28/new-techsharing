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
    col_username VARCHAR(30),
    col_password VARCHAR(100),
    col_active BIT DEFAULT 1 NOT NULL,

    PRIMARY KEY(col_username)
)
GO

-- User Info
CREATE TABLE tbl_user_info (
    col_username VARCHAR(30) REFERENCES tbl_user(col_username),
    col_avatar VARCHAR(500) DEFAULT 'https://www.apple.com/ac/structured-data/images/open_graph_logo.png?201809210816',

    PRIMARY KEY (col_username)
)
GO

-- Role
CREATE TABLE tbl_role (
    col_id INT IDENTITY(1,1),
    col_rolename NVARCHAR(50),

    PRIMARY KEY(col_id)
)
GO

-- Article
CREATE TABLE tbl_article (
    col_id INT IDENTITY(1,1),
    col_title NVARCHAR(500),
    col_description NTEXT,
    col_content NTEXT,
    col_date_created DATE DEFAULT GETDATE(),
    col_time_created TIME DEFAULT GETDATE(),
    col_author VARCHAR(30) REFERENCES tbl_user(col_username),
    col_url NVARCHAR(500) UNIQUE,
    col_category NVARCHAR(50),
    col_thumbnail_url NVARCHAR(500),

    PRIMARY KEY(col_id)
)
GO

-- Article Evaluation
CREATE TABLE tbl_article_evaluation (
    col_article_id INT REFERENCES tbl_article(col_id),
    col_upvote INT DEFAULT 0,
    col_downvote INT DEFAULT 0,

    PRIMARY KEY(col_article_id)
)
GO

-- Comment
CREATE TABLE tbl_comment (
    col_id INT IDENTITY(1,1),
    col_author VARCHAR(30) REFERENCES tbl_user(col_username),
    col_article_id INT REFERENCES tbl_article(col_id),
    col_date DATE DEFAULT GETDATE(),
    col_time TIME DEFAULT GETDATE(),
    col_content NTEXT,

    PRIMARY KEY(col_id)
)
GO

-- User Vote State
CREATE TABLE tbl_user_vote_state (
    col_id INT IDENTITY(1,1),
    col_article_id INT REFERENCES tbl_article(col_id),
    col_username VARCHAR(30) REFERENCES tbl_user(col_username),
    col_vote_state INT, -- 0: none | 1: up | 2: down
    
    PRIMARY KEY(col_id)
)
GO
