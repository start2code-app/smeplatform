create table GCSPLATFORM_INVOICE_BANK (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    LOCATION varchar(5) not null,
    CURRENCY_ID varchar(32) not null,
    BANK_ID varchar(32) not null,
    --
    primary key (ID)
);