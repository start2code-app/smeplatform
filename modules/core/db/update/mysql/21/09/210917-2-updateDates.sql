-- closed trade
alter table GCSPLATFORM_CLOSED_TRADE modify column INVOICE_DATE date ;
alter table GCSPLATFORM_CLOSED_TRADE modify column TRADE_DATE date ;
alter table GCSPLATFORM_CLOSED_TRADE modify column MATURITY_DATE date ;
alter table GCSPLATFORM_CLOSED_TRADE modify column VALUE_DATE date ;

-- opened trade
alter table GCSPLATFORM_OPENED_TRADE modify column INVOICE_DATE date ;
alter table GCSPLATFORM_OPENED_TRADE modify column TRADE_DATE date ;
alter table GCSPLATFORM_OPENED_TRADE modify column MATURITY_DATE date ;
alter table GCSPLATFORM_OPENED_TRADE modify column VALUE_DATE date ;

-- live trade
alter table GCSPLATFORM_LIVE_TRADE modify column INVOICE_DATE date ;
alter table GCSPLATFORM_LIVE_TRADE modify column TRADE_DATE date ;
alter table GCSPLATFORM_LIVE_TRADE modify column MATURITY_DATE date ;
alter table GCSPLATFORM_LIVE_TRADE modify column VALUE_DATE date ;

-- closed live trade
alter table GCSPLATFORM_CLOSED_LIVE_TRADE modify column INVOICE_DATE date ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE modify column TRADE_DATE date ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE modify column MATURITY_DATE date ;
alter table GCSPLATFORM_CLOSED_LIVE_TRADE modify column VALUE_DATE date ;

-- call option trade
alter table GCSPLATFORM_CALL_OPTION_TRADE modify column INVOICE_DATE date ;
alter table GCSPLATFORM_CALL_OPTION_TRADE modify column TRADE_DATE date ;
alter table GCSPLATFORM_CALL_OPTION_TRADE modify column MATURITY_DATE date ;
alter table GCSPLATFORM_CALL_OPTION_TRADE modify column VALUE_DATE date ;

-- fx
alter table GCSPLATFORM_FX modify column BILLING_DATE date ;

-- invoice
alter table GCSPLATFORM_INVOICE modify column START_DATE date ;
alter table GCSPLATFORM_INVOICE modify column END_DATE date ;

-- invoice line
alter table GCSPLATFORM_INVOICE_LINE modify column START_DATE date ;
alter table GCSPLATFORM_INVOICE_LINE modify column END_DATE date ;
alter table GCSPLATFORM_INVOICE_LINE modify column TRADE_DATE date ;
alter table GCSPLATFORM_INVOICE_LINE modify column MATURITY_DATE date ;
alter table GCSPLATFORM_INVOICE_LINE modify column VALUE_DATE date ;