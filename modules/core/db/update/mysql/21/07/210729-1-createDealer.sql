create table GCSPLATFORM_DEALER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    DEALER varchar(10) not null,
    USER_ID varchar(32) not null,
    --
    primary key (ID)
);