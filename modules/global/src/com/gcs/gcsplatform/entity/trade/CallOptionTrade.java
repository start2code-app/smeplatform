package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "calloptiontrade") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_CallOptionTrade")
public class CallOptionTrade extends Trade {
}
