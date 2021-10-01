alter table GCSPLATFORM_INVOICE change column PRINTED POSTED_TO_WORK_DOCS boolean ;
alter table GCSPLATFORM_INVOICE change column POSTED POSTED_TO_QB boolean ;
alter table GCSPLATFORM_INVOICE add column PDF_FILE_ID varchar(32) ;
alter table GCSPLATFORM_INVOICE add column XLSX_FILE_ID varchar(32) ;
