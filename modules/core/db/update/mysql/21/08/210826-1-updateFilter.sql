DELETE FROM `SEC_FILTER` WHERE update_ts > '2021-08-20';

INSERT INTO `SEC_FILTER` VALUES ('f2d43a9b8082fe83357767cd70c7de74','2021-08-20 12:20:34.010','admin',1,'2021-08-20 14:23:39.845','admin',NULL,NULL,NULL,'[gcsplatform_OpenedTrade.browse].filter',NULL,NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"traderef\" class=\"java.lang.String\" caption=\"Contract No\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.traderef like :component$filter.trade_traderef91095 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_traderef91095\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"bondDescription\" class=\"java.lang.String\" caption=\"Description\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.bondDescription like :component$filter.trade_bondDescription60803 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_bondDescription60803\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"isin\" class=\"java.lang.String\" caption=\"Isin\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.isin like :component$filter.trade_isin19143 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_isin19143\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"subs\" class=\"java.lang.Boolean\" caption=\"Subs\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.subs = :component$filter.trade_subs14571]]>\n      <param name=\"component$filter.trade_subs14571\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"special\" class=\"java.lang.Boolean\" caption=\"Special\" operatorType=\"EQUAL\" width=\"2\" type=\"PROPERTY\"><![CDATA[e.trade.special = :component$filter.trade_special27188]]>\n      <param name=\"component$filter.trade_special27188\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"gc\" class=\"java.lang.Boolean\" caption=\"Gc\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.gc = :component$filter.trade_gc80798]]>\n      <param name=\"component$filter.trade_gc80798\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"qqeYLORMKh\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.qqeYLORMKh15191 and {E}.trade.buyer = cpy.counterparty]]>\n      <param name=\"component$filter.qqeYLORMKh15191\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"ecvwubcuLz\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.ecvwubcuLz04571 and {E}.trade.seller = cpy.counterparty]]>\n      <param name=\"component$filter.ecvwubcuLz04571\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"GOaqEMIZat\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.GOaqEMIZat05747 and {E}.trade.buyerAgent = ag.agent]]>\n      <param name=\"component$filter.GOaqEMIZat05747\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"uIgFBKQOzq\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.uIgFBKQOzq87630 and {E}.trade.sellerAgent = ag.agent]]>\n      <param name=\"component$filter.uIgFBKQOzq87630\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"BlDJmOceWC\" class=\"com.gcs.gcsplatform.entity.masterdata.Currency\" width=\"1\" type=\"CUSTOM\" locCaption=\"Currency\" entityAlias=\"e\"><![CDATA[cur.id = :component$filter.BlDJmOceWC40743 and {E}.trade.tradeCurrency = cur.currency]]>\n      <param name=\"component$filter.BlDJmOceWC40743\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Currency\">NULL</param>\n      <join><![CDATA[, gcsplatform_Currency cur]]></join>\n    </c>\n    <c name=\"TfXNIJTDKJ\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Trade date from\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate >= :component$filter.TfXNIJTDKJ10759]]>\n      <param name=\"component$filter.TfXNIJTDKJ10759\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"mRGTfIesJq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Trade date to\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate <= :component$filter.mRGTfIesJq87358]]>\n      <param name=\"component$filter.mRGTfIesJq87358\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"AAMFVbXvgD\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Maturity date from\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate >= :component$filter.AAMFVbXvgD64515]]>\n      <param name=\"component$filter.AAMFVbXvgD64515\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"QcuhzKAiVq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Maturity date to\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate <= :component$filter.QcuhzKAiVq83129]]>\n      <param name=\"component$filter.QcuhzKAiVq83129\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"KpTTiFgwpG\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date from\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate >= :component$filter.KpTTiFgwpG33161]]>\n      <param name=\"component$filter.KpTTiFgwpG33161\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"RnhlJlRjXN\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date to\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate <= :component$filter.RnhlJlRjXN49982]]>\n      <param name=\"component$filter.RnhlJlRjXN49982\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n  </and>\n</filter>\n',NULL,1),
('41cd65bd0d0a478f8e844ce0bcc5bbf0','2021-08-20 12:20:34.010','admin',1,'2021-08-20 14:23:39.845','admin',NULL,NULL,NULL,'[gcsplatform_ClosedTrade.browse].filter',NULL,NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"traderef\" class=\"java.lang.String\" caption=\"Contract No\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.traderef like :component$filter.trade_traderef91095 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_traderef91095\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"bondDescription\" class=\"java.lang.String\" caption=\"Description\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.bondDescription like :component$filter.trade_bondDescription60803 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_bondDescription60803\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"isin\" class=\"java.lang.String\" caption=\"Isin\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.isin like :component$filter.trade_isin19143 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_isin19143\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"subs\" class=\"java.lang.Boolean\" caption=\"Subs\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.subs = :component$filter.trade_subs14571]]>\n      <param name=\"component$filter.trade_subs14571\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"special\" class=\"java.lang.Boolean\" caption=\"Special\" operatorType=\"EQUAL\" width=\"2\" type=\"PROPERTY\"><![CDATA[e.trade.special = :component$filter.trade_special27188]]>\n      <param name=\"component$filter.trade_special27188\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"gc\" class=\"java.lang.Boolean\" caption=\"Gc\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.gc = :component$filter.trade_gc80798]]>\n      <param name=\"component$filter.trade_gc80798\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"qqeYLORMKh\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.qqeYLORMKh15191 and {E}.trade.buyer = cpy.counterparty]]>\n      <param name=\"component$filter.qqeYLORMKh15191\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"ecvwubcuLz\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.ecvwubcuLz04571 and {E}.trade.seller = cpy.counterparty]]>\n      <param name=\"component$filter.ecvwubcuLz04571\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"GOaqEMIZat\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.GOaqEMIZat05747 and {E}.trade.buyerAgent = ag.agent]]>\n      <param name=\"component$filter.GOaqEMIZat05747\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"uIgFBKQOzq\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.uIgFBKQOzq87630 and {E}.trade.sellerAgent = ag.agent]]>\n      <param name=\"component$filter.uIgFBKQOzq87630\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"BlDJmOceWC\" class=\"com.gcs.gcsplatform.entity.masterdata.Currency\" width=\"1\" type=\"CUSTOM\" locCaption=\"Currency\" entityAlias=\"e\"><![CDATA[cur.id = :component$filter.BlDJmOceWC40743 and {E}.trade.tradeCurrency = cur.currency]]>\n      <param name=\"component$filter.BlDJmOceWC40743\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Currency\">NULL</param>\n      <join><![CDATA[, gcsplatform_Currency cur]]></join>\n    </c>\n    <c name=\"TfXNIJTDKJ\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Trade date from\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate >= :component$filter.TfXNIJTDKJ10759]]>\n      <param name=\"component$filter.TfXNIJTDKJ10759\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"mRGTfIesJq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Trade date to\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate <= :component$filter.mRGTfIesJq87358]]>\n      <param name=\"component$filter.mRGTfIesJq87358\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"AAMFVbXvgD\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Maturity date from\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate >= :component$filter.AAMFVbXvgD64515]]>\n      <param name=\"component$filter.AAMFVbXvgD64515\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"QcuhzKAiVq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Maturity date to\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate <= :component$filter.QcuhzKAiVq83129]]>\n      <param name=\"component$filter.QcuhzKAiVq83129\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"KpTTiFgwpG\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date from\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate >= :component$filter.KpTTiFgwpG33161]]>\n      <param name=\"component$filter.KpTTiFgwpG33161\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"RnhlJlRjXN\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date to\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate <= :component$filter.RnhlJlRjXN49982]]>\n      <param name=\"component$filter.RnhlJlRjXN49982\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n  </and>\n</filter>\n',NULL,1),
('b2a936337a5e4d28821f7098c958c37f','2021-08-20 12:20:34.010','admin',1,'2021-08-20 14:23:39.845','admin',NULL,NULL,NULL,'[gcsplatform_DailyBlotter.browse].filter',NULL,NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"traderef\" class=\"java.lang.String\" caption=\"Contract No\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.traderef like :component$filter.trade_traderef91095 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_traderef91095\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"bondDescription\" class=\"java.lang.String\" caption=\"Description\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.bondDescription like :component$filter.trade_bondDescription60803 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_bondDescription60803\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"isin\" class=\"java.lang.String\" caption=\"Isin\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.isin like :component$filter.trade_isin19143 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_isin19143\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"subs\" class=\"java.lang.Boolean\" caption=\"Subs\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.subs = :component$filter.trade_subs14571]]>\n      <param name=\"component$filter.trade_subs14571\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"special\" class=\"java.lang.Boolean\" caption=\"Special\" operatorType=\"EQUAL\" width=\"2\" type=\"PROPERTY\"><![CDATA[e.trade.special = :component$filter.trade_special27188]]>\n      <param name=\"component$filter.trade_special27188\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"gc\" class=\"java.lang.Boolean\" caption=\"Gc\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.gc = :component$filter.trade_gc80798]]>\n      <param name=\"component$filter.trade_gc80798\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"qqeYLORMKh\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.qqeYLORMKh15191 and {E}.trade.buyer = cpy.counterparty]]>\n      <param name=\"component$filter.qqeYLORMKh15191\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"ecvwubcuLz\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.ecvwubcuLz04571 and {E}.trade.seller = cpy.counterparty]]>\n      <param name=\"component$filter.ecvwubcuLz04571\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"GOaqEMIZat\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.GOaqEMIZat05747 and {E}.trade.buyerAgent = ag.agent]]>\n      <param name=\"component$filter.GOaqEMIZat05747\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"uIgFBKQOzq\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.uIgFBKQOzq87630 and {E}.trade.sellerAgent = ag.agent]]>\n      <param name=\"component$filter.uIgFBKQOzq87630\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"BlDJmOceWC\" class=\"com.gcs.gcsplatform.entity.masterdata.Currency\" width=\"1\" type=\"CUSTOM\" locCaption=\"Currency\" entityAlias=\"e\"><![CDATA[cur.id = :component$filter.BlDJmOceWC40743 and {E}.trade.tradeCurrency = cur.currency]]>\n      <param name=\"component$filter.BlDJmOceWC40743\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Currency\">NULL</param>\n      <join><![CDATA[, gcsplatform_Currency cur]]></join>\n    </c>\n    <c name=\"TfXNIJTDKJ\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Trade date from\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate >= :component$filter.TfXNIJTDKJ10759]]>\n      <param name=\"component$filter.TfXNIJTDKJ10759\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"mRGTfIesJq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Trade date to\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate <= :component$filter.mRGTfIesJq87358]]>\n      <param name=\"component$filter.mRGTfIesJq87358\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"AAMFVbXvgD\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Maturity date from\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate >= :component$filter.AAMFVbXvgD64515]]>\n      <param name=\"component$filter.AAMFVbXvgD64515\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"QcuhzKAiVq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Maturity date to\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate <= :component$filter.QcuhzKAiVq83129]]>\n      <param name=\"component$filter.QcuhzKAiVq83129\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"KpTTiFgwpG\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date from\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate >= :component$filter.KpTTiFgwpG33161]]>\n      <param name=\"component$filter.KpTTiFgwpG33161\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"RnhlJlRjXN\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date to\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate <= :component$filter.RnhlJlRjXN49982]]>\n      <param name=\"component$filter.RnhlJlRjXN49982\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n  </and>\n</filter>\n',NULL,1),
('5843d8f456574a7e966ad056927bc6f6','2021-08-20 12:20:34.010','admin',1,'2021-08-20 14:23:39.845','admin',NULL,NULL,NULL,'[gcsplatform_CallOptionTrade.browse].filter',NULL,NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"traderef\" class=\"java.lang.String\" caption=\"Contract No\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.traderef like :component$filter.trade_traderef91095 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_traderef91095\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"bondDescription\" class=\"java.lang.String\" caption=\"Description\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.bondDescription like :component$filter.trade_bondDescription60803 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_bondDescription60803\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"isin\" class=\"java.lang.String\" caption=\"Isin\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.isin like :component$filter.trade_isin19143 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_isin19143\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"subs\" class=\"java.lang.Boolean\" caption=\"Subs\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.subs = :component$filter.trade_subs14571]]>\n      <param name=\"component$filter.trade_subs14571\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"special\" class=\"java.lang.Boolean\" caption=\"Special\" operatorType=\"EQUAL\" width=\"2\" type=\"PROPERTY\"><![CDATA[e.trade.special = :component$filter.trade_special27188]]>\n      <param name=\"component$filter.trade_special27188\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"gc\" class=\"java.lang.Boolean\" caption=\"Gc\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.gc = :component$filter.trade_gc80798]]>\n      <param name=\"component$filter.trade_gc80798\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"qqeYLORMKh\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.qqeYLORMKh15191 and {E}.trade.buyer = cpy.counterparty]]>\n      <param name=\"component$filter.qqeYLORMKh15191\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"ecvwubcuLz\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.ecvwubcuLz04571 and {E}.trade.seller = cpy.counterparty]]>\n      <param name=\"component$filter.ecvwubcuLz04571\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"GOaqEMIZat\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.GOaqEMIZat05747 and {E}.trade.buyerAgent = ag.agent]]>\n      <param name=\"component$filter.GOaqEMIZat05747\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"uIgFBKQOzq\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.uIgFBKQOzq87630 and {E}.trade.sellerAgent = ag.agent]]>\n      <param name=\"component$filter.uIgFBKQOzq87630\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"BlDJmOceWC\" class=\"com.gcs.gcsplatform.entity.masterdata.Currency\" width=\"1\" type=\"CUSTOM\" locCaption=\"Currency\" entityAlias=\"e\"><![CDATA[cur.id = :component$filter.BlDJmOceWC40743 and {E}.trade.tradeCurrency = cur.currency]]>\n      <param name=\"component$filter.BlDJmOceWC40743\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Currency\">NULL</param>\n      <join><![CDATA[, gcsplatform_Currency cur]]></join>\n    </c>\n    <c name=\"TfXNIJTDKJ\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Trade date from\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate >= :component$filter.TfXNIJTDKJ10759]]>\n      <param name=\"component$filter.TfXNIJTDKJ10759\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"mRGTfIesJq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Trade date to\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate <= :component$filter.mRGTfIesJq87358]]>\n      <param name=\"component$filter.mRGTfIesJq87358\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"AAMFVbXvgD\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Maturity date from\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate >= :component$filter.AAMFVbXvgD64515]]>\n      <param name=\"component$filter.AAMFVbXvgD64515\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"QcuhzKAiVq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Maturity date to\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate <= :component$filter.QcuhzKAiVq83129]]>\n      <param name=\"component$filter.QcuhzKAiVq83129\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"KpTTiFgwpG\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date from\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate >= :component$filter.KpTTiFgwpG33161]]>\n      <param name=\"component$filter.KpTTiFgwpG33161\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"RnhlJlRjXN\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date to\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate <= :component$filter.RnhlJlRjXN49982]]>\n      <param name=\"component$filter.RnhlJlRjXN49982\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n  </and>\n</filter>\n',NULL,1),
('2aee2475d1c6496cb4f82fa1b0993a6f','2021-08-20 12:20:34.010','admin',1,'2021-08-20 14:23:39.845','admin',NULL,NULL,NULL,'[gcsplatform_LiveTrade.browse].filter',NULL,NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"traderef\" class=\"java.lang.String\" caption=\"Contract No\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.traderef like :component$filter.trade_traderef91095 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_traderef91095\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"bondDescription\" class=\"java.lang.String\" caption=\"Description\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.bondDescription like :component$filter.trade_bondDescription60803 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_bondDescription60803\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"isin\" class=\"java.lang.String\" caption=\"Isin\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.isin like :component$filter.trade_isin19143 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_isin19143\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"subs\" class=\"java.lang.Boolean\" caption=\"Subs\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.subs = :component$filter.trade_subs14571]]>\n      <param name=\"component$filter.trade_subs14571\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"special\" class=\"java.lang.Boolean\" caption=\"Special\" operatorType=\"EQUAL\" width=\"2\" type=\"PROPERTY\"><![CDATA[e.trade.special = :component$filter.trade_special27188]]>\n      <param name=\"component$filter.trade_special27188\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"gc\" class=\"java.lang.Boolean\" caption=\"Gc\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.gc = :component$filter.trade_gc80798]]>\n      <param name=\"component$filter.trade_gc80798\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"qqeYLORMKh\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.qqeYLORMKh15191 and {E}.trade.buyer = cpy.counterparty]]>\n      <param name=\"component$filter.qqeYLORMKh15191\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"ecvwubcuLz\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.ecvwubcuLz04571 and {E}.trade.seller = cpy.counterparty]]>\n      <param name=\"component$filter.ecvwubcuLz04571\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"GOaqEMIZat\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.GOaqEMIZat05747 and {E}.trade.buyerAgent = ag.agent]]>\n      <param name=\"component$filter.GOaqEMIZat05747\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"uIgFBKQOzq\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.uIgFBKQOzq87630 and {E}.trade.sellerAgent = ag.agent]]>\n      <param name=\"component$filter.uIgFBKQOzq87630\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"BlDJmOceWC\" class=\"com.gcs.gcsplatform.entity.masterdata.Currency\" width=\"1\" type=\"CUSTOM\" locCaption=\"Currency\" entityAlias=\"e\"><![CDATA[cur.id = :component$filter.BlDJmOceWC40743 and {E}.trade.tradeCurrency = cur.currency]]>\n      <param name=\"component$filter.BlDJmOceWC40743\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Currency\">NULL</param>\n      <join><![CDATA[, gcsplatform_Currency cur]]></join>\n    </c>\n    <c name=\"TfXNIJTDKJ\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Trade date from\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate >= :component$filter.TfXNIJTDKJ10759]]>\n      <param name=\"component$filter.TfXNIJTDKJ10759\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"mRGTfIesJq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Trade date to\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate <= :component$filter.mRGTfIesJq87358]]>\n      <param name=\"component$filter.mRGTfIesJq87358\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"AAMFVbXvgD\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Maturity date from\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate >= :component$filter.AAMFVbXvgD64515]]>\n      <param name=\"component$filter.AAMFVbXvgD64515\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"QcuhzKAiVq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Maturity date to\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate <= :component$filter.QcuhzKAiVq83129]]>\n      <param name=\"component$filter.QcuhzKAiVq83129\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"KpTTiFgwpG\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date from\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate >= :component$filter.KpTTiFgwpG33161]]>\n      <param name=\"component$filter.KpTTiFgwpG33161\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"RnhlJlRjXN\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date to\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate <= :component$filter.RnhlJlRjXN49982]]>\n      <param name=\"component$filter.RnhlJlRjXN49982\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n  </and>\n</filter>\n',NULL,1),
('e770b57fe2884b1e8bbb2c08e04fef87','2021-08-20 12:20:34.010','admin',1,'2021-08-20 14:23:39.845','admin',NULL,NULL,NULL,'[gcsplatform_ClosedLiveTrade.browse].filter',NULL,NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"traderef\" class=\"java.lang.String\" caption=\"Contract No\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.traderef like :component$filter.trade_traderef91095 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_traderef91095\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"bondDescription\" class=\"java.lang.String\" caption=\"Description\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.bondDescription like :component$filter.trade_bondDescription60803 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_bondDescription60803\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"isin\" class=\"java.lang.String\" caption=\"Isin\" operatorType=\"CONTAINS\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.isin like :component$filter.trade_isin19143 ESCAPE \'\\\' ]]>\n      <param name=\"component$filter.trade_isin19143\" javaClass=\"java.lang.String\">NULL</param>\n    </c>\n    <c name=\"subs\" class=\"java.lang.Boolean\" caption=\"Subs\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.subs = :component$filter.trade_subs14571]]>\n      <param name=\"component$filter.trade_subs14571\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"special\" class=\"java.lang.Boolean\" caption=\"Special\" operatorType=\"EQUAL\" width=\"2\" type=\"PROPERTY\"><![CDATA[e.trade.special = :component$filter.trade_special27188]]>\n      <param name=\"component$filter.trade_special27188\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"gc\" class=\"java.lang.Boolean\" caption=\"Gc\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.trade.gc = :component$filter.trade_gc80798]]>\n      <param name=\"component$filter.trade_gc80798\" javaClass=\"java.lang.Boolean\">NULL</param>\n    </c>\n    <c name=\"qqeYLORMKh\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.qqeYLORMKh15191 and {E}.trade.buyer = cpy.counterparty]]>\n      <param name=\"component$filter.qqeYLORMKh15191\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"ecvwubcuLz\" class=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller\" entityAlias=\"e\"><![CDATA[cpy.id = :component$filter.ecvwubcuLz04571 and {E}.trade.seller = cpy.counterparty]]>\n      <param name=\"component$filter.ecvwubcuLz04571\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Counterparty\">NULL</param>\n      <join><![CDATA[, gcsplatform_Counterparty cpy]]></join>\n    </c>\n    <c name=\"GOaqEMIZat\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Buyer agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.GOaqEMIZat05747 and {E}.trade.buyerAgent = ag.agent]]>\n      <param name=\"component$filter.GOaqEMIZat05747\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"uIgFBKQOzq\" class=\"com.gcs.gcsplatform.entity.masterdata.Agent\" width=\"1\" type=\"CUSTOM\" locCaption=\"Seller agent\" entityAlias=\"e\"><![CDATA[ag.id = :component$filter.uIgFBKQOzq87630 and {E}.trade.sellerAgent = ag.agent]]>\n      <param name=\"component$filter.uIgFBKQOzq87630\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Agent\">NULL</param>\n      <join><![CDATA[, gcsplatform_Agent ag]]></join>\n    </c>\n    <c name=\"BlDJmOceWC\" class=\"com.gcs.gcsplatform.entity.masterdata.Currency\" width=\"1\" type=\"CUSTOM\" locCaption=\"Currency\" entityAlias=\"e\"><![CDATA[cur.id = :component$filter.BlDJmOceWC40743 and {E}.trade.tradeCurrency = cur.currency]]>\n      <param name=\"component$filter.BlDJmOceWC40743\" javaClass=\"com.gcs.gcsplatform.entity.masterdata.Currency\">NULL</param>\n      <join><![CDATA[, gcsplatform_Currency cur]]></join>\n    </c>\n    <c name=\"TfXNIJTDKJ\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Trade date from\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate >= :component$filter.TfXNIJTDKJ10759]]>\n      <param name=\"component$filter.TfXNIJTDKJ10759\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"mRGTfIesJq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Trade date to\" entityAlias=\"e\"><![CDATA[{E}.trade.tradeDate <= :component$filter.mRGTfIesJq87358]]>\n      <param name=\"component$filter.mRGTfIesJq87358\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"AAMFVbXvgD\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Maturity date from\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate >= :component$filter.AAMFVbXvgD64515]]>\n      <param name=\"component$filter.AAMFVbXvgD64515\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"QcuhzKAiVq\" class=\"java.sql.Date\" width=\"2\" type=\"CUSTOM\" locCaption=\"Maturity date to\" entityAlias=\"e\"><![CDATA[{E}.trade.maturityDate <= :component$filter.QcuhzKAiVq83129]]>\n      <param name=\"component$filter.QcuhzKAiVq83129\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"KpTTiFgwpG\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date from\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate >= :component$filter.KpTTiFgwpG33161]]>\n      <param name=\"component$filter.KpTTiFgwpG33161\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n    <c name=\"RnhlJlRjXN\" class=\"java.sql.Date\" width=\"1\" type=\"CUSTOM\" locCaption=\"Value date to\" entityAlias=\"e\"><![CDATA[{E}.trade.valueDate <= :component$filter.RnhlJlRjXN49982]]>\n      <param name=\"component$filter.RnhlJlRjXN49982\" javaClass=\"java.sql.Date\">NULL</param>\n    </c>\n  </and>\n</filter>\n',NULL,1);
