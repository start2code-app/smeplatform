alter table GCSPLATFORM_CALL_OPTION_TRADE change column PNL PNL__U93166 decimal(10, 4)^
alter table GCSPLATFORM_CALL_OPTION_TRADE change column GBP_EQUIVALENT GBP_EQUIVALENT__U42705 decimal(10, 4)^
alter table GCSPLATFORM_CALL_OPTION_TRADE add column SELL_GBP_EQUIVALENT decimal(10, 4) ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column BUY_GBP_EQUIVALENT decimal(10, 4) ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column BUY_PNL decimal(10, 4) ;
alter table GCSPLATFORM_CALL_OPTION_TRADE add column SELL_PNL decimal(10, 4) ;
