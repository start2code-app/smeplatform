alter table GCSPLATFORM_LIVE_TRADE change column PNL PNL__U44980 decimal(10, 4)^
alter table GCSPLATFORM_LIVE_TRADE change column GBP_EQUIVALENT GBP_EQUIVALENT__U51937 decimal(10, 4)^
alter table GCSPLATFORM_LIVE_TRADE add column SELL_GBP_EQUIVALENT decimal(10, 4) ;
alter table GCSPLATFORM_LIVE_TRADE add column BUY_GBP_EQUIVALENT decimal(10, 4) ;
alter table GCSPLATFORM_LIVE_TRADE add column BUY_PNL decimal(10, 4) ;
alter table GCSPLATFORM_LIVE_TRADE add column SELL_PNL decimal(10, 4) ;
