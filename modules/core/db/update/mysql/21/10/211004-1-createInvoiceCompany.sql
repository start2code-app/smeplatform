create table GCSPLATFORM_INVOICE_COMPANY (
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
    COMPANY_ID varchar(32) not null,
    WORK_DOCS_FOLDER_ID varchar(255),
    QB_REALM_ID varchar(255),
    --
    primary key (ID)
);