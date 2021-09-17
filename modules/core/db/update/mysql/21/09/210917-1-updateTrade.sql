alter table GCSPLATFORM_OPENED_TRADE add column BROKERAGE_TYPE varchar(50) ;
update GCSPLATFORM_OPENED_TRADE set BROKERAGE_TYPE = 'GEN' where GC = true ;
update GCSPLATFORM_OPENED_TRADE set BROKERAGE_TYPE = 'SPE' where SPECIAL = true ;
update GCSPLATFORM_OPENED_TRADE set BROKERAGE_TYPE = 'M30D' where MORE_THAN_THIRTY = true ;
update GCSPLATFORM_OPENED_TRADE set BROKERAGE_TYPE = 'S30D' where SUB_THIRTY = true ;
alter table GCSPLATFORM_OPENED_TRADE drop column GC ;
alter table GCSPLATFORM_OPENED_TRADE drop column SPECIAL ;
alter table GCSPLATFORM_OPENED_TRADE drop column SUB_THIRTY ;
alter table GCSPLATFORM_OPENED_TRADE drop column MORE_THAN_THIRTY ;

alter table GCSPLATFORM_CLOSED_TRADE add column BROKERAGE_TYPE varchar(50) ;
update GCSPLATFORM_CLOSED_TRADE set BROKERAGE_TYPE = 'GEN' where GC = true ;
update GCSPLATFORM_CLOSED_TRADE set BROKERAGE_TYPE = 'SPE' where SPECIAL = true ;
update GCSPLATFORM_CLOSED_TRADE set BROKERAGE_TYPE = 'M30D' where MORE_THAN_THIRTY = true ;
update GCSPLATFORM_CLOSED_TRADE set BROKERAGE_TYPE = 'S30D' where SUB_THIRTY = true ;
alter table GCSPLATFORM_CLOSED_TRADE drop column GC ;
alter table GCSPLATFORM_CLOSED_TRADE drop column SPECIAL ;
alter table GCSPLATFORM_CLOSED_TRADE drop column SUB_THIRTY ;
alter table GCSPLATFORM_CLOSED_TRADE drop column MORE_THAN_THIRTY ;

alter table GCSPLATFORM_LIVE_TRADE add column BROKERAGE_TYPE varchar(50) ;
update GCSPLATFORM_LIVE_TRADE set BROKERAGE_TYPE = 'GEN' where GC = true ;
update GCSPLATFORM_LIVE_TRADE set BROKERAGE_TYPE = 'SPE' where SPECIAL = true ;
update GCSPLATFORM_LIVE_TRADE set BROKERAGE_TYPE = 'M30D' where MORE_THAN_THIRTY = true ;
update GCSPLATFORM_LIVE_TRADE set BROKERAGE_TYPE = 'S30D' where SUB_THIRTY = true ;
alter table GCSPLATFORM_LIVE_TRADE drop column GC ;
alter table GCSPLATFORM_LIVE_TRADE drop column SPECIAL ;
alter table GCSPLATFORM_LIVE_TRADE drop column SUB_THIRTY ;
alter table GCSPLATFORM_LIVE_TRADE drop column MORE_THAN_THIRTY ;

alter table GCSPLATFORM_CLOSED_LIVE_TRADE add column BROKERAGE_TYPE varchar(50) ;
update GCSPLATFORM_CLOSED_LIVE_TRADE set BROKERAGE_TYPE = 'GEN' where GC = true ;
update GCSPLATFORM_CLOSED_LIVE_TRADE set BROKERAGE_TYPE = 'SPE' where SPECIAL = true ;
update GCSPLATFORM_CLOSED_LIVE_TRADE set BROKERAGE_TYPE = 'M30D' where MORE_THAN_THIRTY = true ;
update GCSPLATFORM_CLOSED_LIVE_TRADE set BROKERAGE_TYPE = 'S30D' where SUB_THIRTY = true ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE drop column GC ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE drop column SPECIAL ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE drop column SUB_THIRTY ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE drop column MORE_THAN_THIRTY ;

alter table GCSPLATFORM_CALL_OPTION_TRADE add column BROKERAGE_TYPE varchar(50) ;
update GCSPLATFORM_CALL_OPTION_TRADE set BROKERAGE_TYPE = 'GEN' where GC = true ;
update GCSPLATFORM_CALL_OPTION_TRADE set BROKERAGE_TYPE = 'SPE' where SPECIAL = true ;
update GCSPLATFORM_CALL_OPTION_TRADE set BROKERAGE_TYPE = 'M30D' where MORE_THAN_THIRTY = true ;
update GCSPLATFORM_CALL_OPTION_TRADE set BROKERAGE_TYPE = 'S30D' where SUB_THIRTY = true ;
alter table GCSPLATFORM_CALL_OPTION_TRADE drop column GC ;
alter table GCSPLATFORM_CALL_OPTION_TRADE drop column SPECIAL ;
alter table GCSPLATFORM_CALL_OPTION_TRADE drop column SUB_THIRTY ;
alter table GCSPLATFORM_CALL_OPTION_TRADE drop column MORE_THAN_THIRTY ;