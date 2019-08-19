  drop table if exists clientdetails;
  create table clientdetails (
    clientId VARCHAR(256) PRIMARY KEY,
    clientSecret VARCHAR(256),
    scope VARCHAR(256)
  );
--
--   drop table if exists oauth_client_token;
--   create table oauth_client_token (
--     token_id VARCHAR(256),
--     token LONGVARBINARY,
--     authentication_id VARCHAR(256) PRIMARY KEY,
--     user_name VARCHAR(256),
--     client_id VARCHAR(256)
--   );
--
--   drop table if exists oauth_access_token;
--   create table oauth_access_token (
--     token_id VARCHAR(256),
--     token LONGVARBINARY,
--     authentication_id VARCHAR(256) PRIMARY KEY,
--     user_name VARCHAR(256),
--     client_id VARCHAR(256),
--     authentication LONGVARBINARY,
--     refresh_token VARCHAR(256)
--   );
--
--   drop table if exists oauth_refresh_token;
--   create table oauth_refresh_token (
--    token_id VARCHAR(256),
--      token LONGVARBINARY,
--     authentication LONGVARBINARY
--   );
--   drop table if exists oauth_code;
--   create table oauth_code (
--     code VARCHAR(256), authentication LONGVARBINARY
--  );
--
--   drop table if exists oauth_approvals;
--   create table oauth_approvals (
--   	userId VARCHAR(256),
--   	clientId VARCHAR(256),
--   	scope VARCHAR(256),
--   	status VARCHAR(10),
--   	expiresAt TIMESTAMP,
--   	lastModifiedAt TIMESTAMP
--  );
