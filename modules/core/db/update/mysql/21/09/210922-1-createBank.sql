create table GCSPLATFORM_BANK (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    BANK_NAME varchar(50) not null,
    ACCOUNT_NAME varchar(50) not null,
    ACCOUNT_NUMBER varchar(50) not null,
    SORT_CODE varchar(50) not null,
    SWIFT_BIC varchar(50) not null,
    ADDITIONAL_BANK_LINE_CAPTION varchar(50),
    ADDITIONAL_BANK_LINE varchar(50),
    ADDITIONAL_LINE1 varchar(100),
    ADDITIONAL_LINE2 varchar(100),
    ADDITIONAL_LINE3 varchar(100),
    ADDITIONAL_LINE4 varchar(100),
    ADDITIONAL_LINE5 varchar(100),
    --
    primary key (ID)
);