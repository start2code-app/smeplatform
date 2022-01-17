package com.gcs.gcsplatform.core.role;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.Bank;
import com.gcs.gcsplatform.entity.masterdata.Company;
import com.gcs.gcsplatform.entity.masterdata.InvoiceBank;
import com.gcs.gcsplatform.entity.masterdata.InvoiceCompany;
import com.gcs.gcsplatform.entity.masterdata.Trader;
import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.masterdata.Broker;
import com.gcs.gcsplatform.entity.masterdata.Fx;
import com.gcs.gcsplatform.entity.pnl.Pnl;
import com.gcs.gcsplatform.entity.pnl.chart.BrokerageTypeCount;
import com.gcs.gcsplatform.entity.pnl.chart.CategoryCount;
import com.gcs.gcsplatform.entity.pnl.chart.TotalPnl;
import com.gcs.gcsplatform.entity.qb.QuickBooksCsrf;
import com.gcs.gcsplatform.entity.qb.QuickBooksToken;
import com.gcs.gcsplatform.entity.trade.CallOptionTrade;
import com.gcs.gcsplatform.entity.trade.ClosedLiveTrade;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.gcs.gcsplatform.entity.trade.LiveTrade;
import com.gcs.gcsplatform.entity.trade.OpenedTrade;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.app.role.annotation.ScreenComponentAccess;
import com.haulmont.cuba.security.app.role.annotation.SpecificAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.entity.FilterEntity;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenComponentPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;
import com.haulmont.cuba.security.role.SpecificPermissionsContainer;

@Role(name = "Back Office")
public class BackOfficeRole extends AnnotatedRoleDefinition {

    @ScreenAccess(screenIds = {"application-trades",
            "application-invoicing",
            "application-masterdata",
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
            "gcsplatform_Counterparty.browse",
            "gcsplatform_Counterparty.edit",
            "gcsplatform_Trader.browse",
            "gcsplatform_Trader.edit",
            "gcsplatform_Broker.browse",
            "gcsplatform_Broker.edit",
            "gcsplatform_Category.browse",
            "gcsplatform_Category.edit",
            "gcsplatform_CounterpartyBrokerage.browse",
            "gcsplatform_Currency.browse",
            "gcsplatform_Currency.edit",
            "gcsplatform_Fx.browse",
            "gcsplatform_Fx.edit",
            "gcsplatform_Invoice.browse",
            "gcsplatform_InvoiceLine.browse",
            "gcsplatform_InvoiceLine.edit",
            "gcsplatform_Bank.browse",
            "gcsplatform_Bank.edit",
            "gcsplatform_InvoiceBank.browse",
            "gcsplatform_InvoiceBank.edit",
            "gcsplatform_Company.browse",
            "gcsplatform_Company.edit",
            "gcsplatform_InvoiceCompany.browse",
            "gcsplatform_InvoiceCompany.edit",
            "gcsplatform_PnlChartScreen",
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
    @EntityAccess(entityClass = Counterparty.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Trader.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Broker.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Category.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = CounterpartyBrokerage.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Currency.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Fx.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Invoice.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = InvoiceLine.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Bank.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = InvoiceBank.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Company.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = InvoiceCompany.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = FileDescriptor.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = QuickBooksCsrf.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = QuickBooksToken.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
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

    @SpecificAccess(permissions = {"app.editClosedTradesWhenSnapshotTaken",
            "cuba.gui.filter.edit",
            "cuba.gui.filter.customConditions",
            "cuba.gui.filter.maxResults"})
    @Override
    public SpecificPermissionsContainer specificPermissions() {
        return super.specificPermissions();
    }

    @ScreenComponentAccess(screenId = "gcsplatform_DailyBlotter.browse", deny = "tradesTable<uti>")
    @ScreenComponentAccess(screenId = "gcsplatform_OpenedTrade.browse", deny = "tradesTable<uti>")
    @ScreenComponentAccess(screenId = "gcsplatform_OpenedTrade.browse", deny = "tradesTable<simpleCopy>")
    @ScreenComponentAccess(screenId = "gcsplatform_CallOptionTrade.browse", deny = "tradesTable<uti>")
    @ScreenComponentAccess(screenId = "gcsplatform_ClosedTrade.browse", deny = "tradesTable<uti>")
    @ScreenComponentAccess(screenId = "gcsplatform_LiveTrade.browse", deny = "tradesTable<uti>")
    @ScreenComponentAccess(screenId = "gcsplatform_LiveTrade.browse", deny = "tradesTable<simpleCopy>")
    @ScreenComponentAccess(screenId = "gcsplatform_ClosedLiveTrade.browse", deny = "tradesTable<uti>")
    @ScreenComponentAccess(screenId = "gcsplatform_Invoice.browse", deny = "invoicesTable<uti>")
    @ScreenComponentAccess(screenId = "gcsplatform_InvoiceLine.browse", deny = "invoiceLinesTable<uti>")

    @Override
    public ScreenComponentPermissionsContainer screenComponentPermissions() {
        return super.screenComponentPermissions();
    }
}
