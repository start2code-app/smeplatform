-- begin GCSPLATFORM_AGENT
alter table GCSPLATFORM_AGENT add constraint FK_GCSPLATFORM_AGENT_ON_COUNTERPARTY foreign key (COUNTERPARTY_ID) references GCSPLATFORM_COUNTERPARTY(ID)^
create unique index IDX_GCSPLATFORM_AGENT_UNIQ_AGENT on GCSPLATFORM_AGENT (AGENT) ^
create index IDX_GCSPLATFORM_AGENT_ON_COUNTERPARTY on GCSPLATFORM_AGENT (COUNTERPARTY_ID)^
-- end GCSPLATFORM_AGENT
-- begin GCSPLATFORM_DEALER
alter table GCSPLATFORM_DEALER add constraint FK_GCSPLATFORM_DEALER_ON_USER foreign key (USER_ID) references SEC_USER(ID)^
create unique index IDX_GCSPLATFORM_DEALER_UNIQ_DEALER on GCSPLATFORM_DEALER (DEALER) ^
create index IDX_GCSPLATFORM_DEALER_ON_USER on GCSPLATFORM_DEALER (USER_ID)^
-- end GCSPLATFORM_DEALER
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
create unique index IDX_GCSPLATFORM_COUNTERPARTY_UNIQ_COUNTERPARTY on GCSPLATFORM_COUNTERPARTY (COUNTERPARTY) ^
-- end GCSPLATFORM_COUNTERPARTY
