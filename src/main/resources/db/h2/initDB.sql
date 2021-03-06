DROP TABLE IF EXISTS url_stats;
DROP TABLE IF EXISTS account_short_urls;
DROP TABLE IF EXISTS short_urls;
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id       INTEGER PRIMARY KEY,
  name     VARCHAR UNIQUE NOT NULL,
  password VARCHAR        NOT NULL
);

CREATE TABLE short_urls (
  id            INTEGER PRIMARY KEY,
  short_url     VARCHAR UNIQUE NOT NULL,
  target_url    VARCHAR        NOT NULL,
  redirect_type VARCHAR        NOT NULL,
  CONSTRAINT unique_target_url_redirect_type UNIQUE (target_url, redirect_type)
);

CREATE TABLE account_short_urls (
  account_id   INTEGER NOT NULL,
  short_url_id INTEGER NOT NULL,
  FOREIGN KEY (account_id) REFERENCES accounts (id),
  FOREIGN KEY (short_url_id) REFERENCES short_urls (id)
);

CREATE TABLE url_stats (
  url_id      INTEGER PRIMARY KEY,
  hit_counter INTEGER NOT NULL,
  FOREIGN KEY (url_id) REFERENCES short_urls (id)
);