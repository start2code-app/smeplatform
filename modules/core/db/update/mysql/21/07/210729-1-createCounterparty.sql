create table GCSPLATFORM_COUNTERPARTY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    COUNTERPARTY varchar(30) not null,
    ADDRESS1 varchar(50),
    ADDRESS2 varchar(50),
    ADDRESS3 varchar(50),
    ADDRESS4 varchar(50),
    BILLING_NAME varchar(50),
    BILLING_COMPANY_NAME varchar(50),
    BILLING_COUNTRY varchar(5),
    BILLING_INFO1 varchar(50),
    BILLING_INFO2 varchar(50),
    BILLING_INFO3 varchar(50),
    CONTACT_NAME varchar(50),
    DESCRIPTION varchar(50),
    EMAIL varchar(50),
    EMAIL1 varchar(50),
    EMAIL2 varchar(50),
    GC_ABS varchar(10),
    GC_CORP varchar(10),
    GC_EGB varchar(10),
    GC_EGBPIGS varchar(10),
    GC_EM varchar(10),
    GC_GILT varchar(10),
    GC_SSA varchar(10),
    GC_UST varchar(10),
    SPEC_ABS varchar(10),
    SPEC_CORP varchar(10),
    SPEC_EGB varchar(10),
    SPEC_EGBPIGS varchar(10),
    SPEC_EM varchar(10),
    SPEC_GILT varchar(10),
    SPEC_SSA varchar(10),
    SPEC_UST varchar(10),
    --
    primary key (ID)
);