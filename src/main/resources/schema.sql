DROP table IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts (
  account BIGINT NOT NULL,
  name VARCHAR(254) NOT NULL,
  value DOUBLE PRECISION NOT NULL,
  CONSTRAINT pk_accounts PRIMARY KEY (account),
  CONSTRAINT uq_accounts_account UNIQUE (account)
);