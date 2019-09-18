drop table if exists oauth_client_details;
create table oauth_client_details
(
    client_id              VARCHAR(256) PRIMARY KEY,
    resource_ids           VARCHAR(256),
    client_secret          VARCHAR(256),
    scope                  VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    --authorities VARCHAR(256),
    access_token_validity  INTEGER,
    refresh_token_validity INTEGER
);
drop table if exists oauth_client_token;
create table oauth_client_token
(
    token_id          VARCHAR(256),
    token             bytea,
    authentication_id VARCHAR(256),
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);
drop table if exists oauth_access_token;
create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             bytea,
    authentication_id VARCHAR(256),
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    bytea,
    refresh_token     VARCHAR(256)
);
drop table if exists oauth_refresh_token;
create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          bytea,
    authentication bytea
);
drop table if exists oauth_code;
create table oauth_code
(
    code           VARCHAR(256),
    authentication bytea
);
drop table if exists oauth_approvals;
create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

drop table if exists users CASCADE;
create table users
(
    id          SERIAL PRIMARY KEY NOT NULL,
    email       VARCHAR(120) UNIQUE,
    first_name  VARCHAR(40),
    last_name   VARCHAR(40),
    nickname    VARCHAR(20) UNIQUE,
    password    VARCHAR(12),
    user_type   VARCHAR(10),
    location_id INTEGER,
    enabled     BOOLEAN,
    password_creation_date DATE
);

drop table if exists posts;
create table posts
(
    id          SERIAL PRIMARY KEY NOT NULL,
    description VARCHAR(1000),
    title       VARCHAR(120),
    location_id INTEGER,
    user_id     INTEGER
);

drop table if exists locations;
create table locations
(
    id        SERIAL PRIMARY KEY not null,
    city      VARCHAR(120),
    country   VARCHAR(120),
    latitude  REAL,
    longitude REAL
);

drop table if exists verification_token;
create table verification_token
(
    id           SERIAL PRIMARY KEY not null,
    created_date DATE,
    expiry_date  DATE,
    token        VARCHAR(120),
    user_id      INTEGER
);

drop table if exists password_reset_token;
create table password_reset_token
(
    id           SERIAL PRIMARY KEY not null,
    expiry_date  DATE,
    token        VARCHAR(120),
    user_id      INTEGER
);


