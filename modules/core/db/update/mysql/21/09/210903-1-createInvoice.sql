create table GCSPLATFORM_INVOICE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    ISSUE integer,
    CURRENCY varchar(10),
    COUNTERPARTY_CODE varchar(10),
    LOCATION varchar(50),
    START_DATE datetime(3),
    END_DATE datetime(3),
    AMOUNT decimal(10, 4),
    FX decimal(10, 4),
    FX_USD decimal(10, 4),
    GBP_AMOUNT decimal(10, 4),
    POSTED boolean,
    PRINTED boolean,
    --
    primary key (ID)
);