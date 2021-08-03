-- update GCSPLATFORM_AGENT set AGENT = <default_value> where AGENT is null ;
alter table GCSPLATFORM_AGENT modify column AGENT varchar(30) not null ;
