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
