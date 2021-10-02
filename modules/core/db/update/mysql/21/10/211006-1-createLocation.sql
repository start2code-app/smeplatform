create table GCSPLATFORM_LOCATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(5) not null,
    --
    primary key (ID)
);

INSERT INTO `GCSPLATFORM_LOCATION` VALUES ('2f069ce385c542ffb582bcbb370f33b4',1,'2021-10-06 09:45:26.276','admin','2021-08-09 09:45:26.276',NULL,NULL,NULL,'LON'),
('e0558c71389d439baf7aeef981a2951e',1,'2021-10-06 09:45:14.129','admin','2021-10-06 09:45:14.129',NULL,NULL,NULL,'HK');