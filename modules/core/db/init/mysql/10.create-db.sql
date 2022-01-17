-- begin GCSPLATFORM_LOCATION
create table GCSPLATFORM_LOCATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    NAME varchar(5) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_LOCATION
-- begin GCSPLATFORM_COUNTERPARTY
create table GCSPLATFORM_COUNTERPARTY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    COUNTERPARTY varchar(30) not null,
    ADDRESS1 varchar(50),
    ADDRESS2 varchar(50),
    ADDRESS3 varchar(50),
    ADDRESS4 varchar(50),
    BILLING_NAME varchar(50),
    BILLING_COMPANY_NAME varchar(50),
    BILLING_COUNTRY varchar(5),
    BILLING_INFO varchar(50),
    CODE varchar(10),
    LOCATION_ID varchar(32) not null,
    CONTACT_NAME varchar(50),
    DESCRIPTION varchar(50),
    EMAIL varchar(50),
    EMAIL1 varchar(50),
    EMAIL2 varchar(50),
    CASH boolean,
    COMMISSION_OVERRIDE boolean,
    ACTIVE boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_COUNTERPARTY

-- begin GCSPLATFORM_CATEGORY
create table GCSPLATFORM_CATEGORY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    CATEGORY varchar(50) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_CATEGORY

-- begin GCSPLATFORM_COUNTERPARTY_BROKERAGE
create table GCSPLATFORM_COUNTERPARTY_BROKERAGE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    COUNTERPARTY_ID varchar(32) not null,
    CATEGORY_ID varchar(32) not null,
    BROKERAGE_VALUE decimal(10, 4) not null,
    BROKERAGE_TYPE varchar(50) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_COUNTERPARTY_BROKERAGE
-- begin GCSPLATFORM_LIVE_TRADE
create table GCSPLATFORM_LIVE_TRADE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    BOND_DESCRIPTION varchar(50),
    BROOVERIDE boolean,
    BUYBROKER varchar(10),
    BUYBROKERAGE decimal(10, 4),
    BUYER varchar(30),
    BUYER_TRADER varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    BOND_CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE date,
    NOMINAL decimal(10, 4),
    NOTES varchar(200),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_TRADER varchar(30),
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    REPO_CURRENCY varchar(5),
    TRADE_DATE date,
    INVOICE_DATE date,
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE date,
    FX decimal(10, 4),
    FX_USD decimal(10, 4),
    XRATE decimal(10, 4),
    GM_SLA boolean,
    BUYER_LOCATION varchar(5),
    SELLER_LOCATION varchar(5),
    BUY_SPLIT boolean,
    BUY_SPLIT_BROKER varchar(10),
    SELL_SPLIT boolean,
    SELL_SPLIT_BROKER varchar(10),
    BUY_PNL decimal(10, 4),
    SELL_PNL decimal(10, 4),
    BUYER_CASH boolean,
    SELLER_CASH boolean,
    BUYER_CODE varchar(50),
    SELLER_CODE varchar(50),
    BROKERAGE_TYPE varchar(50),
    BUY_COMMISSION_OVERRIDE boolean,
    SELL_COMISSION_OVERRIDE boolean,
    BUY_PUT_ON_INVOICE boolean,
    SELL_PUT_ON_INVOICE boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_LIVE_TRADE
-- begin GCSPLATFORM_CALL_OPTION_TRADE
create table GCSPLATFORM_CALL_OPTION_TRADE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    BOND_DESCRIPTION varchar(50),
    BROOVERIDE boolean,
    BUYBROKER varchar(10),
    BUYBROKERAGE decimal(10, 4),
    BUYER varchar(30),
    BUYER_TRADER varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    BOND_CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE date,
    NOMINAL decimal(10, 4),
    NOTES varchar(200),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_TRADER varchar(30),
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    REPO_CURRENCY varchar(5),
    TRADE_DATE date,
    INVOICE_DATE date,
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE date,
    FX decimal(10, 4),
    FX_USD decimal(10, 4),
    XRATE decimal(10, 4),
    GM_SLA boolean,
    BUYER_LOCATION varchar(5),
    SELLER_LOCATION varchar(5),
    BUY_SPLIT boolean,
    BUY_SPLIT_BROKER varchar(10),
    SELL_SPLIT boolean,
    SELL_SPLIT_BROKER varchar(10),
    BUY_PNL decimal(10, 4),
    SELL_PNL decimal(10, 4),
    BUYER_CASH boolean,
    SELLER_CASH boolean,
    BUYER_CODE varchar(50),
    SELLER_CODE varchar(50),
    BROKERAGE_TYPE varchar(50),
    BUY_COMMISSION_OVERRIDE boolean,
    SELL_COMISSION_OVERRIDE boolean,
    BUY_PUT_ON_INVOICE boolean,
    SELL_PUT_ON_INVOICE boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_CALL_OPTION_TRADE
-- begin GCSPLATFORM_CLOSED_TRADE
create table GCSPLATFORM_CLOSED_TRADE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    BOND_DESCRIPTION varchar(50),
    BROOVERIDE boolean,
    BUYBROKER varchar(10),
    BUYBROKERAGE decimal(10, 4),
    BUYER varchar(30),
    BUYER_TRADER varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    BOND_CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE date,
    NOMINAL decimal(10, 4),
    NOTES varchar(200),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_TRADER varchar(30),
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    REPO_CURRENCY varchar(5),
    TRADE_DATE date,
    INVOICE_DATE date,
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE date,
    FX decimal(10, 4),
    FX_USD decimal(10, 4),
    XRATE decimal(10, 4),
    GM_SLA boolean,
    BUYER_LOCATION varchar(5),
    SELLER_LOCATION varchar(5),
    BUY_SPLIT boolean,
    BUY_SPLIT_BROKER varchar(10),
    SELL_SPLIT boolean,
    SELL_SPLIT_BROKER varchar(10),
    BUY_PNL decimal(10, 4),
    SELL_PNL decimal(10, 4),
    BUYER_CASH boolean,
    SELLER_CASH boolean,
    BUYER_CODE varchar(50),
    SELLER_CODE varchar(50),
    BROKERAGE_TYPE varchar(50),
    BUY_COMMISSION_OVERRIDE boolean,
    SELL_COMISSION_OVERRIDE boolean,
    BUY_PUT_ON_INVOICE boolean,
    SELL_PUT_ON_INVOICE boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_CLOSED_TRADE
-- begin GCSPLATFORM_OPENED_TRADE
create table GCSPLATFORM_OPENED_TRADE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    BOND_DESCRIPTION varchar(50),
    BROOVERIDE boolean,
    BUYBROKER varchar(10),
    BUYBROKERAGE decimal(10, 4),
    BUYER varchar(30),
    BUYER_TRADER varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    BOND_CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE date,
    NOMINAL decimal(10, 4),
    NOTES varchar(200),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_TRADER varchar(30),
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    REPO_CURRENCY varchar(5),
    TRADE_DATE date,
    INVOICE_DATE date,
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE date,
    FX decimal(10, 4),
    FX_USD decimal(10, 4),
    XRATE decimal(10, 4),
    GM_SLA boolean,
    BUYER_LOCATION varchar(5),
    SELLER_LOCATION varchar(5),
    BUY_SPLIT boolean,
    BUY_SPLIT_BROKER varchar(10),
    SELL_SPLIT boolean,
    SELL_SPLIT_BROKER varchar(10),
    BUY_PNL decimal(10, 4),
    SELL_PNL decimal(10, 4),
    BUYER_CASH boolean,
    SELLER_CASH boolean,
    BUYER_CODE varchar(50),
    SELLER_CODE varchar(50),
    BROKERAGE_TYPE varchar(50),
    BUY_COMMISSION_OVERRIDE boolean,
    SELL_COMISSION_OVERRIDE boolean,
    BUY_PUT_ON_INVOICE boolean,
    SELL_PUT_ON_INVOICE boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_OPENED_TRADE
-- begin GCSPLATFORM_CLOSED_LIVE_TRADE
create table GCSPLATFORM_CLOSED_LIVE_TRADE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    BOND_DESCRIPTION varchar(50),
    BROOVERIDE boolean,
    BUYBROKER varchar(10),
    BUYBROKERAGE decimal(10, 4),
    BUYER varchar(30),
    BUYER_TRADER varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    BOND_CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE date,
    NOMINAL decimal(10, 4),
    NOTES varchar(200),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_TRADER varchar(30),
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    REPO_CURRENCY varchar(5),
    TRADE_DATE date,
    INVOICE_DATE date,
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE date,
    FX decimal(10, 4),
    FX_USD decimal(10, 4),
    XRATE decimal(10, 4),
    GM_SLA boolean,
    BUYER_LOCATION varchar(5),
    SELLER_LOCATION varchar(5),
    BUY_SPLIT boolean,
    BUY_SPLIT_BROKER varchar(10),
    SELL_SPLIT boolean,
    SELL_SPLIT_BROKER varchar(10),
    BUY_PNL decimal(10, 4),
    SELL_PNL decimal(10, 4),
    BUYER_CASH boolean,
    SELLER_CASH boolean,
    BUYER_CODE varchar(50),
    SELLER_CODE varchar(50),
    BROKERAGE_TYPE varchar(50),
    BUY_COMMISSION_OVERRIDE boolean,
    SELL_COMISSION_OVERRIDE boolean,
    BUY_PUT_ON_INVOICE boolean,
    SELL_PUT_ON_INVOICE boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_CLOSED_LIVE_TRADE
-- begin GCSPLATFORM_CURRENCY
create table GCSPLATFORM_CURRENCY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000' ,
    --
    CURRENCY varchar(5) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_CURRENCY
-- begin GCSPLATFORM_FX
create table GCSPLATFORM_FX (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    CURRENCY_ID varchar(32) not null,
    FX_VALUE decimal(10, 4) not null,
    BILLING_DATE date not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_FX
-- begin GCSPLATFORM_INVOICE
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
    XLSX_FILE_ID varchar(32),
    PDF_FILE_ID varchar(32),
    ISSUE integer,
    CURRENCY varchar(10),
    COUNTERPARTY_CODE varchar(10),
    LOCATION varchar(5),
    START_DATE date,
    END_DATE date,
    AMOUNT decimal(10, 4),
    FX decimal(10, 4),
    FX_USD decimal(10, 4),
    GBP_AMOUNT decimal(10, 4),
    USD_AMOUNT decimal(10, 4),
    USD_CROSS_RATE decimal(10, 4),
    SHOW_TOTAL_USD boolean,
    POSTED_TO_WORK_DOCS boolean,
    POSTED_TO_QB boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_INVOICE
-- begin GCSPLATFORM_INVOICE_LINE
create table GCSPLATFORM_INVOICE_LINE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    CONTRACT_NUMBER varchar(100),
    START_DATE date,
    END_DATE date,
    TRADE_DATE date,
    BOND_DESCRIPTION varchar(50),
    BUYER varchar(30),
    SELLER varchar(30),
    COUNTERPARTY varchar(30),
    COUNTERPARTY_CODE varchar(10),
    BROKER varchar(10),
    NOTES varchar(200),
    ISIN varchar(20),
    LOCATION varchar(5),
    CURRENCY varchar(10),
    BASE_CURRENCY varchar(10),
    CROSS_RATE varchar(50),
    VALUE_DATE date,
    MATURITY_DATE date,
    NUMDAYS bigint,
    NOMINAL decimal(10, 4),
    HAIR_CUT decimal(10, 4),
    REPO_RATE decimal(10, 4),
    XRATE decimal(10, 4),
    BROKERAGE decimal(10, 4),
    START_PRICE decimal(10, 4),
    PNL decimal(10, 4),
    FX decimal(10, 4),
    GBP_EQUIVALENT decimal(10, 4),
    CASH boolean,
    TRADE_SIDE varchar(50) not null,
    TRADE_ID varchar(32),
    --
    primary key (ID)
)^
-- end GCSPLATFORM_INVOICE_LINE
-- begin GCSPLATFORM_COMPANY
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
    ADDRESS1 varchar(50),
    ADDRESS2 varchar(50),
    TEL varchar(50),
    ADDITIONAL_COMPANY_NAME_LINE varchar(50),
    --
    primary key (ID)
)^
-- end GCSPLATFORM_COMPANY
-- begin GCSPLATFORM_INVOICE_BANK
create table GCSPLATFORM_INVOICE_BANK (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    LOCATION_ID varchar(32) not null,
    CURRENCY_ID varchar(32) not null,
    BANK_ID varchar(32) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_INVOICE_BANK
-- begin GCSPLATFORM_BANK
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
)^
-- end GCSPLATFORM_BANK
-- begin GCSPLATFORM_BROKER
create table GCSPLATFORM_BROKER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    NAME varchar(10) not null,
    USER_ID varchar(32) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_BROKER
-- begin GCSPLATFORM_TRADER
create table GCSPLATFORM_TRADER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    COUNTERPARTY_ID varchar(32) not null,
    NAME varchar(30) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_TRADER
-- begin GCSPLATFORM_QUICK_BOOKS_TOKEN
create table GCSPLATFORM_QUICK_BOOKS_TOKEN (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    ACCESS_TOKEN varchar(1000) not null,
    REFRESH_TOKEN varchar(1000) not null,
    REALM_ID varchar(255) not null,
    EXPIRE_TS datetime(3) not null,
    REFRESH_TOKEN_EXPIRE_TS datetime(3) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_QUICK_BOOKS_TOKEN
-- begin GCSPLATFORM_QUICK_BOOKS_CSRF
create table GCSPLATFORM_QUICK_BOOKS_CSRF (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    CSRF varchar(255) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_QUICK_BOOKS_CSRF
-- begin GCSPLATFORM_INVOICE_COMPANY
create table GCSPLATFORM_INVOICE_COMPANY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000',
    --
    LOCATION_ID varchar(32) not null,
    COMPANY_ID varchar(32) not null,
    WORK_DOCS_FOLDER_ID varchar(255),
    QB_REALM_ID varchar(255),
    --
    primary key (ID)
)^
-- end GCSPLATFORM_INVOICE_COMPANY
