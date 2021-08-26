package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "GCSPLATFORM_CLOSED_LIVE_TRADE")
@Entity(name = "gcsplatform_ClosedLiveTrade")
public class ClosedLiveTrade extends Trade {

    private static final long serialVersionUID = -2334631523029821230L;
}