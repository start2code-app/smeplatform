-- begin GCSPLATFORM_COUNTERPARTY_BROKERAGE
alter table GCSPLATFORM_COUNTERPARTY_BROKERAGE add constraint FK_GCSPLATFORM_COUNTERPARTY_BROKERAGE_ON_COUNTERPARTY foreign key (COUNTERPARTY_ID) references GCSPLATFORM_COUNTERPARTY(ID)^
alter table GCSPLATFORM_COUNTERPARTY_BROKERAGE add constraint FK_GCSPLATFORM_COUNTERPARTY_BROKERAGE_ON_CATEGORY foreign key (CATEGORY_ID) references GCSPLATFORM_CATEGORY(ID)^
create unique index IDX_GCSPLATFORM_COUNTERPARTY_BROKERAGE_UNQ on GCSPLATFORM_COUNTERPARTY_BROKERAGE (COUNTERPARTY_ID, CATEGORY_ID, BROKERAGE_TYPE) ^
create index IDX_GCSPLATFORM_COUNTERPARTY_BROKERAGE_ON_COUNTERPARTY on GCSPLATFORM_COUNTERPARTY_BROKERAGE (COUNTERPARTY_ID)^
create index IDX_GCSPLATFORM_COUNTERPARTY_BROKERAGE_ON_CATEGORY on GCSPLATFORM_COUNTERPARTY_BROKERAGE (CATEGORY_ID)^
-- end GCSPLATFORM_COUNTERPARTY_BROKERAGE
-- begin GCSPLATFORM_CATEGORY
create unique index IDX_GCSPLATFORM_CATEGORY_UNIQ_CATEGORY on GCSPLATFORM_CATEGORY (CATEGORY) ^
-- end GCSPLATFORM_CATEGORY
-- begin GCSPLATFORM_COUNTERPARTY
alter table GCSPLATFORM_COUNTERPARTY add constraint FK_GCSPLATFORM_COUNTERPARTY_ON_LOCATION foreign key (LOCATION_ID) references GCSPLATFORM_LOCATION(ID)^
create unique index IDX_GCSPLATFORM_COUNTERPARTY_UNIQ_COUNTERPARTY on GCSPLATFORM_COUNTERPARTY (COUNTERPARTY) ^
create index IDX_GCSPLATFORM_COUNTERPARTY_ON_LOCATION on GCSPLATFORM_COUNTERPARTY (LOCATION_ID)^
-- end GCSPLATFORM_COUNTERPARTY
-- begin GCSPLATFORM_CURRENCY
create unique index IDX_GCSPLATFORM_CURRENCY_UNIQ_CURRENCY on GCSPLATFORM_CURRENCY (CURRENCY) ^
-- end GCSPLATFORM_CURRENCY
-- begin GCSPLATFORM_FX
alter table GCSPLATFORM_FX add constraint FK_GCSPLATFORM_FX_ON_CURRENCY foreign key (CURRENCY_ID) references GCSPLATFORM_CURRENCY(ID)^
create unique index IDX_GCSPLATFORM_FX_UNQ on GCSPLATFORM_FX (CURRENCY_ID, BILLING_DATE) ^
create index IDX_GCSPLATFORM_FX_ON_CURRENCY on GCSPLATFORM_FX (CURRENCY_ID)^
-- end GCSPLATFORM_FX
-- begin GCSPLATFORM_INVOICE_LINE
alter table GCSPLATFORM_INVOICE_LINE add constraint FK_GCSPLATFORM_INVOICE_LINE_ON_TRADE foreign key (TRADE_ID) references GCSPLATFORM_CLOSED_TRADE(ID)^
create index IDX_GCSPLATFORM_INVOICE_LINE_ON_TRADE on GCSPLATFORM_INVOICE_LINE (TRADE_ID)^
-- end GCSPLATFORM_INVOICE_LINE

-- begin GCSPLATFORM_INVOICE_BANK
alter table GCSPLATFORM_INVOICE_BANK add constraint FK_GCSPLATFORM_INVOICE_BANK_ON_LOCATION foreign key (LOCATION_ID) references GCSPLATFORM_LOCATION(ID)^
alter table GCSPLATFORM_INVOICE_BANK add constraint FK_GCSPLATFORM_INVOICE_BANK_ON_CURRENCY foreign key (CURRENCY_ID) references GCSPLATFORM_CURRENCY(ID)^
alter table GCSPLATFORM_INVOICE_BANK add constraint FK_GCSPLATFORM_INVOICE_BANK_ON_BANK foreign key (BANK_ID) references GCSPLATFORM_BANK(ID)^
create unique index IDX_GCSPLATFORM_INVOICE_BANK_UNQ on GCSPLATFORM_INVOICE_BANK (CURRENCY_ID, LOCATION_ID) ^
create index IDX_GCSPLATFORM_INVOICE_BANK_ON_LOCATION on GCSPLATFORM_INVOICE_BANK (LOCATION_ID)^
create index IDX_GCSPLATFORM_INVOICE_BANK_ON_CURRENCY on GCSPLATFORM_INVOICE_BANK (CURRENCY_ID)^
create index IDX_GCSPLATFORM_INVOICE_BANK_ON_BANK on GCSPLATFORM_INVOICE_BANK (BANK_ID)^
-- end GCSPLATFORM_INVOICE_BANK
-- begin GCSPLATFORM_BROKER
alter table GCSPLATFORM_BROKER add constraint FK_GCSPLATFORM_BROKER_ON_USER foreign key (USER_ID) references SEC_USER(ID)^
create unique index IDX_GCSPLATFORM_BROKER_UNIQ_NAME on GCSPLATFORM_BROKER (NAME) ^
create index IDX_GCSPLATFORM_BROKER_ON_USER on GCSPLATFORM_BROKER (USER_ID)^
-- end GCSPLATFORM_BROKER
-- begin GCSPLATFORM_TRADER
alter table GCSPLATFORM_TRADER add constraint FK_GCSPLATFORM_TRADER_ON_COUNTERPARTY foreign key (COUNTERPARTY_ID) references GCSPLATFORM_COUNTERPARTY(ID)^
create unique index IDX_GCSPLATFORM_TRADER_UNIQ_NAME on GCSPLATFORM_TRADER (NAME) ^
create index IDX_GCSPLATFORM_TRADER_ON_COUNTERPARTY on GCSPLATFORM_TRADER (COUNTERPARTY_ID)^
-- end GCSPLATFORM_TRADER
-- begin GCSPLATFORM_INVOICE
alter table GCSPLATFORM_INVOICE add constraint FK_GCSPLATFORM_INVOICE_ON_XLSX_FILE foreign key (XLSX_FILE_ID) references SYS_FILE(ID)^
alter table GCSPLATFORM_INVOICE add constraint FK_GCSPLATFORM_INVOICE_ON_PDF_FILE foreign key (PDF_FILE_ID) references SYS_FILE(ID)^
create index IDX_GCSPLATFORM_INVOICE_ON_XLSX_FILE on GCSPLATFORM_INVOICE (XLSX_FILE_ID)^
create index IDX_GCSPLATFORM_INVOICE_ON_PDF_FILE on GCSPLATFORM_INVOICE (PDF_FILE_ID)^
-- end GCSPLATFORM_INVOICE
-- begin GCSPLATFORM_INVOICE_COMPANY
alter table GCSPLATFORM_INVOICE_COMPANY add constraint FK_GCSPLATFORM_INVOICE_COMPANY_ON_LOCATION foreign key (LOCATION_ID) references GCSPLATFORM_LOCATION(ID)^
alter table GCSPLATFORM_INVOICE_COMPANY add constraint FK_GCSPLATFORM_INVOICE_COMPANY_ON_COMPANY foreign key (COMPANY_ID) references GCSPLATFORM_COMPANY(ID)^
create unique index IDX_GCSPLATFORM_INVOICE_COMPANY_UNIQ_COMPANY_ID on GCSPLATFORM_INVOICE_COMPANY (COMPANY_ID) ^
create unique index IDX_GCSPLATFORM_INVOICE_COMPANY_UNIQ_LOCATION_ID on GCSPLATFORM_INVOICE_COMPANY (LOCATION_ID) ^
create index IDX_GCSPLATFORM_INVOICE_COMPANY_ON_LOCATION on GCSPLATFORM_INVOICE_COMPANY (LOCATION_ID)^
create index IDX_GCSPLATFORM_INVOICE_COMPANY_ON_COMPANY on GCSPLATFORM_INVOICE_COMPANY (COMPANY_ID)^
-- end GCSPLATFORM_INVOICE_COMPANY
-- begin GCSPLATFORM_LOCATION
create unique index IDX_GCSPLATFORM_LOCATION_UNIQ_NAME on GCSPLATFORM_LOCATION (NAME) ^
-- end GCSPLATFORM_LOCATION
