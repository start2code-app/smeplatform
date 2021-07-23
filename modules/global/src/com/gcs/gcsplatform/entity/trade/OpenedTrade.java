package com.gcs.gcsplatform.entity.trade;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "opentrade") // TODO: Add prefix to table name
@Entity(name = "gcsplatform_OpenedTrade")
public class OpenedTrade extends Trade {
}
