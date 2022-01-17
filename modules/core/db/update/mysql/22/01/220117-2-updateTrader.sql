alter table GCSPLATFORM_TRADER add column DELETE_TS_NN datetime(3) ^
update GCSPLATFORM_TRADER set DELETE_TS_NN = '1000-01-01 00:00:00.000' where DELETE_TS_NN is null ;
alter table GCSPLATFORM_TRADER modify column DELETE_TS_NN datetime(3) not null default '1000-01-01 00:00:00.000' ;
