alter table GCSPLATFORM_TRADER add constraint FK_GCSPLATFORM_TRADER_ON_COUNTERPARTY foreign key (COUNTERPARTY_ID) references GCSPLATFORM_COUNTERPARTY(ID);
create unique index IDX_GCSPLATFORM_TRADER_UNIQ_NAME on GCSPLATFORM_TRADER (NAME) ;
create index IDX_GCSPLATFORM_TRADER_ON_COUNTERPARTY on GCSPLATFORM_TRADER (COUNTERPARTY_ID);
