drop table if exists oauth_client_details;
create table oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER
);
drop table if exists oauth_client_token;
create table oauth_client_token (
    token_id VARCHAR(256),
    token bytea,
    authentication_id VARCHAR(256),
    user_name VARCHAR(256),
    client_id VARCHAR(256)
);
drop table if exists oauth_access_token;
create table oauth_access_token (
    token_id VARCHAR(256),
    token bytea,
    authentication_id VARCHAR(256),
    user_name VARCHAR(256),
    client_id VARCHAR(256),
    authentication bytea,
    refresh_token VARCHAR(256)
);
drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
    token_id VARCHAR(256),
    token bytea,
    authentication bytea
);
drop table if exists oauth_code;
create table oauth_code (
    code VARCHAR(256), authentication bytea
);
drop table if exists oauth_approvals;
create table oauth_approvals (
    userId VARCHAR(256),
    clientId VARCHAR(256),
    scope VARCHAR(256),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

drop table if exists users CASCADE;
create table users (
    id SERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(120),
    firstName VARCHAR(40),
    lastName VARCHAR(40),
    nickName VARCHAR(20),
    password VARCHAR(12),
    userType VARCHAR(10),
    locationId INTEGER
);

drop table if exists posts;
create table posts (
    id SERIAL PRIMARY KEY NOT NULL ,
    description VARCHAR(1000),
    title VARCHAR(120),
    locationId INTEGER,
    userId INTEGER
);

drop table if exists locations;
create table locations (
    id SERIAL PRIMARY KEY not null ,
    city VARCHAR(120),
    country VARCHAR(120),
    latitude REAL,
    longitude REAL
);


