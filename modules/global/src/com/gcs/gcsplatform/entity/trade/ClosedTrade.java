package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "closedtrade") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_ClosedTrade")
public class ClosedTrade extends TradeContainer {

    private static final long serialVersionUID = -5997009543405135818L;
}
