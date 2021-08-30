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
    CASH boolean,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_COUNTERPARTY
-- begin GCSPLATFORM_AGENT
create table GCSPLATFORM_AGENT (
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
    AGENT varchar(30) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_AGENT
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
    --
    CATEGORY varchar(50) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_CATEGORY
-- begin GCSPLATFORM_DEALER
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
)^
-- end GCSPLATFORM_DEALER
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
    BUYER_AGENT varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    GC boolean,
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE datetime(3),
    NOMINAL decimal(10, 2),
    NOTES varchar(100),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_AGENT varchar(30),
    SPECIAL boolean,
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    TRADE_CURRENCY varchar(5),
    TRADE_DATE datetime(3),
    INVOICE_DATE datetime(3),
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE datetime(3),
    XRATE1 decimal(10, 4),
    XRATE2 decimal(10, 4),
    XRATE3 decimal(10, 4),
    SUB_THIRTY boolean,
    MORE_THAN_THIRTY boolean,
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
    BUYER_INVOICE_CODE varchar(50),
    SELLER_INVOICE_CODE varchar(50),
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
    BUYER_AGENT varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    GC boolean,
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE datetime(3),
    NOMINAL decimal(10, 2),
    NOTES varchar(100),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_AGENT varchar(30),
    SPECIAL boolean,
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    TRADE_CURRENCY varchar(5),
    TRADE_DATE datetime(3),
    INVOICE_DATE datetime(3),
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE datetime(3),
    XRATE1 decimal(10, 4),
    XRATE2 decimal(10, 4),
    XRATE3 decimal(10, 4),
    SUB_THIRTY boolean,
    MORE_THAN_THIRTY boolean,
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
    BUYER_INVOICE_CODE varchar(50),
    SELLER_INVOICE_CODE varchar(50),
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
    BUYER_AGENT varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    GC boolean,
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE datetime(3),
    NOMINAL decimal(10, 2),
    NOTES varchar(100),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_AGENT varchar(30),
    SPECIAL boolean,
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    TRADE_CURRENCY varchar(5),
    TRADE_DATE datetime(3),
    INVOICE_DATE datetime(3),
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE datetime(3),
    XRATE1 decimal(10, 4),
    XRATE2 decimal(10, 4),
    XRATE3 decimal(10, 4),
    SUB_THIRTY boolean,
    MORE_THAN_THIRTY boolean,
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
    BUYER_INVOICE_CODE varchar(50),
    SELLER_INVOICE_CODE varchar(50),
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
    BUYER_AGENT varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    GC boolean,
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE datetime(3),
    NOMINAL decimal(10, 2),
    NOTES varchar(100),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_AGENT varchar(30),
    SPECIAL boolean,
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    TRADE_CURRENCY varchar(5),
    TRADE_DATE datetime(3),
    INVOICE_DATE datetime(3),
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE datetime(3),
    XRATE1 decimal(10, 4),
    XRATE2 decimal(10, 4),
    XRATE3 decimal(10, 4),
    SUB_THIRTY boolean,
    MORE_THAN_THIRTY boolean,
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
    BUYER_INVOICE_CODE varchar(50),
    SELLER_INVOICE_CODE varchar(50),
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
    BUYER_AGENT varchar(30),
    CALLOPTION varchar(20),
    CATEGORY varchar(10),
    CPAIR1 varchar(10),
    CPAIR2 varchar(10),
    CPAIR3 varchar(10),
    CURRENCY varchar(5),
    BUY_GBP_EQUIVALENT decimal(10, 4),
    SELL_GBP_EQUIVALENT decimal(10, 4),
    GC boolean,
    HAIR_CUT decimal(10, 4),
    ISIN varchar(20),
    MATURITY_DATE datetime(3),
    NOMINAL decimal(10, 2),
    NOTES varchar(100),
    NUMDAYS bigint,
    ORIGTRADEREF varchar(20),
    REPO_RATE decimal(10, 4),
    SELLBROKER varchar(10),
    SELLBROKERAGE decimal(10, 4),
    SELLER varchar(30),
    SELLER_AGENT varchar(30),
    SPECIAL boolean,
    START_PRICE decimal(10, 4),
    STATUS varchar(10),
    SUBS boolean,
    TRADE_CURRENCY varchar(5),
    TRADE_DATE datetime(3),
    INVOICE_DATE datetime(3),
    TRADEREF varchar(20),
    UTI varchar(50),
    VALUE_DATE datetime(3),
    XRATE1 decimal(10, 4),
    XRATE2 decimal(10, 4),
    XRATE3 decimal(10, 4),
    SUB_THIRTY boolean,
    MORE_THAN_THIRTY boolean,
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
    BUYER_INVOICE_CODE varchar(50),
    SELLER_INVOICE_CODE varchar(50),
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
    --
    CURRENCY_ID varchar(32) not null,
    FX_VALUE decimal(10, 4) not null,
    BILLING_DATE datetime(3) not null,
    --
    primary key (ID)
)^
-- end GCSPLATFORM_FX
