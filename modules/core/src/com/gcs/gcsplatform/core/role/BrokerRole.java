package com.gcs.gcsplatform.core.role;

import com.gcs.gcsplatform.entity.masterdata.Broker;
import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.masterdata.Fx;
import com.gcs.gcsplatform.entity.masterdata.Trader;
import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.pnl.chart.BrokerageTypeCount;
import com.gcs.gcsplatform.entity.pnl.chart.CategoryCount;
import com.gcs.gcsplatform.entity.pnl.chart.TotalPnl;
import com.gcs.gcsplatform.entity.trade.CallOptionTrade;
import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.app.role.annotation.ScreenComponentAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.entity.FilterEntity;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenComponentPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

@Role(name = "Broker")
public class BrokerRole extends AnnotatedRoleDefinition {

    @ScreenAccess(screenIds = {"application-trades",
            "gcsplatform_DailyBlotter.browse",
            "gcsplatform_OpenedTrade.browse",
            "gcsplatform_OpenedTrade.edit",
            "gcsplatform_CallOptionTrade.browse",
            "gcsplatform_CallOptionTrade.edit",
            "gcsplatform_ClosedTrade.browse",
            "gcsplatform_ClosedTrade.edit",
            "gcsplatform_LiveTrade.browse",
            "gcsplatform_LiveTrade.edit",
            "gcsplatform_ClosedLiveTrade.browse",
            "gcsplatform_ClosedLiveTrade.edit",
            "gcsplatform_PnlChartScreen",
            "gcsplatform_UtiScreen",
            "gcsplatform_SimpleCopyScreen",
            "help",
            "aboutWindow",
            "settings"})
    @Override
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }

    @EntityAccess(entityClass = OpenedTrade.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = ClosedTrade.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = ClosedLiveTrade.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = CallOptionTrade.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = LiveTrade.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Counterparty.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Trader.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Broker.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Category.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = CounterpartyBrokerage.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Currency.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Fx.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Pnl.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = BrokerageTypeCount.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = CategoryCount.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = TotalPnl.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = FilterEntity.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @Override
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }

    @EntityAttributeAccess(entityName = "*", modify = "*")
    @Override
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }

    @ScreenComponentAccess(screenId = "gcsplatform_DailyBlotter.browse", deny = "tradesTable<excel>")
    @ScreenComponentAccess(screenId = "gcsplatform_OpenedTrade.browse", deny = "tradesTable<excel>")
    @ScreenComponentAccess(screenId = "gcsplatform_CallOptionTrade.browse", deny = "tradesTable<excel>")
    @ScreenComponentAccess(screenId = "gcsplatform_ClosedTrade.browse", deny = "tradesTable<excel>")
    @ScreenComponentAccess(screenId = "gcsplatform_LiveTrade.browse", deny = "tradesTable<excel>")
    @ScreenComponentAccess(screenId = "gcsplatform_ClosedLiveTrade.browse", deny = "tradesTable<excel>")
    @ScreenComponentAccess(screenId = "gcsplatform_Invoice.browse", deny = "invoicesTable<excel>")
    @ScreenComponentAccess(screenId = "gcsplatform_InvoiceLine.browse", deny = "invoiceLinesTable<excel>")
    @Override
    public ScreenComponentPermissionsContainer screenComponentPermissions() {
        return super.screenComponentPermissions();
    }
}
