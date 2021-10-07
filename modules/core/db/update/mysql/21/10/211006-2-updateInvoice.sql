alter table GCSPLATFORM_INVOICE add column USD_CROSS_RATE decimal(10, 4) ;
alter table GCSPLATFORM_INVOICE add column SHOW_TOTAL_USD boolean ;
update GCSPLATFORM_INVOICE set SHOW_TOTAL_USD = true, USD_CROSS_RATE = FX_USD / FX where CURRENCY not in ('GBP', 'EUR', 'USD') ;