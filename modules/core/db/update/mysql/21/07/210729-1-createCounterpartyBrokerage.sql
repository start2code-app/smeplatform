create table GCSPLATFORM_COUNTERPARTY_BROKERAGE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    COUNTERPARTY_ID varchar(32) not null,
    CATEGORY_ID varchar(32) not null,
    BROKERAGE_VALUE decimal(19, 2) not null,
    BROKERAGE_TYPE varchar(50) not null,
    --
    primary key (ID)
);