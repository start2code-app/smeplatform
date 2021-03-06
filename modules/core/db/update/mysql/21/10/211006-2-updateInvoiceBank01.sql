update GCSPLATFORM_INVOICE_BANK set LOCATION_ID = '2f069ce385c542ffb582bcbb370f33b4' where LOCATION_ID = 'LON';
update GCSPLATFORM_INVOICE_BANK set LOCATION_ID = 'e0558c71389d439baf7aeef981a2951e' where LOCATION_ID = 'HK';

alter table GCSPLATFORM_INVOICE_BANK add constraint FK_GCSPLATFORM_INVOICE_BANK_ON_LOCATION foreign key (LOCATION_ID) references GCSPLATFORM_LOCATION(ID);
create index IDX_GCSPLATFORM_INVOICE_BANK_ON_LOCATION on GCSPLATFORM_INVOICE_BANK (LOCATION_ID);

create unique index IDX_GCSPLATFORM_INVOICE_BANK_UNQ on GCSPLATFORM_INVOICE_BANK (CURRENCY_ID, LOCATION_ID) ;