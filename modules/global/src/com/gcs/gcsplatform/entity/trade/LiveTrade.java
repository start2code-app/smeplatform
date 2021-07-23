package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "livetrade") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_LiveTrade")
public class LiveTrade extends Trade {
}
