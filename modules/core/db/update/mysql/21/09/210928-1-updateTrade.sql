alter table GCSPLATFORM_OPENED_TRADE change column CURRENCY BOND_CURRENCY varchar(5);
alter table GCSPLATFORM_OPENED_TRADE change column TRADE_CURRENCY REPO_CURRENCY varchar(5);
alter table GCSPLATFORM_OPENED_TRADE change column XRATE1 FX decimal(10, 4);
alter table GCSPLATFORM_OPENED_TRADE change column XRATE2 XRATE decimal(10, 4);
alter table GCSPLATFORM_OPENED_TRADE change column XRATE3 FX_USD decimal(10, 4);

alter table GCSPLATFORM_CLOSED_TRADE change column CURRENCY BOND_CURRENCY varchar(5);
alter table GCSPLATFORM_CLOSED_TRADE change column TRADE_CURRENCY REPO_CURRENCY varchar(5);
alter table GCSPLATFORM_CLOSED_TRADE change column XRATE1 FX decimal(10, 4);
alter table GCSPLATFORM_CLOSED_TRADE change column XRATE2 XRATE decimal(10, 4);
alter table GCSPLATFORM_CLOSED_TRADE change column XRATE3 FX_USD decimal(10, 4);

alter table GCSPLATFORM_CLOSED_LIVE_TRADE change column CURRENCY BOND_CURRENCY varchar(5);
alter table GCSPLATFORM_CLOSED_LIVE_TRADE change column TRADE_CURRENCY REPO_CURRENCY varchar(5);
alter table GCSPLATFORM_CLOSED_LIVE_TRADE change column XRATE1 FX decimal(10, 4);
alter table GCSPLATFORM_CLOSED_LIVE_TRADE change column XRATE2 XRATE decimal(10, 4);
alter table GCSPLATFORM_CLOSED_LIVE_TRADE change column XRATE3 FX_USD decimal(10, 4);

alter table GCSPLATFORM_CALL_OPTION_TRADE change column CURRENCY BOND_CURRENCY varchar(5);
alter table GCSPLATFORM_CALL_OPTION_TRADE change column TRADE_CURRENCY REPO_CURRENCY varchar(5);
alter table GCSPLATFORM_CALL_OPTION_TRADE change column XRATE1 FX decimal(10, 4);
alter table GCSPLATFORM_CALL_OPTION_TRADE change column XRATE2 XRATE decimal(10, 4);
alter table GCSPLATFORM_CALL_OPTION_TRADE change column XRATE3 FX_USD decimal(10, 4);

alter table GCSPLATFORM_LIVE_TRADE change column CURRENCY BOND_CURRENCY varchar(5);
alter table GCSPLATFORM_LIVE_TRADE change column TRADE_CURRENCY REPO_CURRENCY varchar(5);
alter table GCSPLATFORM_LIVE_TRADE change column XRATE1 FX decimal(10, 4);
alter table GCSPLATFORM_LIVE_TRADE change column XRATE2 XRATE decimal(10, 4);
alter table GCSPLATFORM_LIVE_TRADE change column XRATE3 FX_USD decimal(10, 4);