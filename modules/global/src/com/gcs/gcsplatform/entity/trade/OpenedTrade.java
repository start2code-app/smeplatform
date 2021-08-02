package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "GCSPLATFORM_OPENED_TRADE")
@Entity(name = "gcsplatform_OpenedTrade")
public class OpenedTrade extends TradeContainer {

    private static final long serialVersionUID = -5484023914577305005L;
}
