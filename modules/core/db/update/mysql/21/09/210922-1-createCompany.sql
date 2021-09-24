create table GCSPLATFORM_COMPANY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(50) not null,
    LOCATION varchar(5) not null,
    ADDRESS1 varchar(50),
    ADDRESS2 varchar(50),
    TEL varchar(50),
    ADDITIONAL_COMPANY_NAME_LINE varchar(50),
    --
    primary key (ID)
);