alter table GCSPLATFORM_INVOICE add constraint FK_GCSPLATFORM_INVOICE_ON_XLSX_FILE foreign key (XLSX_FILE_ID) references SYS_FILE(ID);
create index IDX_GCSPLATFORM_INVOICE_ON_XLSX_FILE on GCSPLATFORM_INVOICE (XLSX_FILE_ID);
