DROP TABLE METADATA IF EXISTS;
CREATE TABLE METADATA(
METADATA_ID              NUMERIC,
TRANSACTION_TYPE         VARCHAR(100),
TRANSACTION_SUB_TYPE     VARCHAR(100),
MESSAGE_TYPE             VARCHAR(50),
PAYLOAD                  VARCHAR(255),
CREATE_ON                TIMESTAMP,
UPDATED_ON               TIMESTAMP
);