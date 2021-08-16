create table GCSPLATFORM_FX (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    CURRENCY_ID varchar(32) not null,
    FX_VALUE decimal(10, 4) not null,
    BILLING_DATE datetime(3) not null,
    --
    primary key (ID)
);