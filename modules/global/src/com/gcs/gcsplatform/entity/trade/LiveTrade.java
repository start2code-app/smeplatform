package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "livetrade") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_LiveTrade")
public class LiveTrade extends TradeContainer {

    private static final long serialVersionUID = 1353322256901457178L;
}
