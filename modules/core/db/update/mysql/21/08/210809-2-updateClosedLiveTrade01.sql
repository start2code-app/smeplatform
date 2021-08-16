alter table GCSPLATFORM_CLOSED_LIVE_TRADE change column PNL PNL__U45065 decimal(10, 4)^
alter table GCSPLATFORM_CLOSED_LIVE_TRADE change column GBP_EQUIVALENT GBP_EQUIVALENT__U60460 decimal(10, 4)^
alter table GCSPLATFORM_CLOSED_LIVE_TRADE add column SELL_GBP_EQUIVALENT decimal(10, 4) ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE add column BUY_GBP_EQUIVALENT decimal(10, 4) ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE add column BUY_PNL decimal(10, 4) ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE add column SELL_PNL decimal(10, 4) ;
