alter table GCSPLATFORM_CALL_OPTION_TRADE add column BUY_SPLIT boolean ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column SELL_SPLIT boolean ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column BUYER_LOCATION varchar(10) ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column BUY_SPLIT_BROKER varchar(10) ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column SELL_SPLIT_BROKER varchar(10) ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column SELLER_LOCATION varchar(10) ;
