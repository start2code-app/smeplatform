insert into SYS_SCHEDULED_TASK (ID, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, DEFINED_BY, SYS_TENANT_ID, BEAN_NAME, METHOD_NAME, CLASS_NAME, SCRIPT_NAME, USER_NAME, IS_SINGLETON, IS_ACTIVE, PERIOD_, TIMEOUT, START_DATE, CRON, SCHEDULING_TYPE, TIME_FRAME, START_DELAY, PERMITTED_SERVERS, LOG_START, LOG_FINISH, LAST_START_TIME, LAST_START_SERVER, METHOD_PARAMS, DESCRIPTION)
values ('4f73644290032f839c0bcee4b8936fb9', '2021-09-29 15:30:46', 'admin', '2021-09-29 16:23:40', 'admin', null, null, 'B', null, 'gcsplatform_FxScheduledUpdateService', 'updateFxForAllCurrencies', null, null, null, null, false, null, null, null, '0 0 0 1,25 * *', 'C', null, null, null, null, null, null, null, '<?xml version="1.0" encoding="UTF-8"?><params/>', null);