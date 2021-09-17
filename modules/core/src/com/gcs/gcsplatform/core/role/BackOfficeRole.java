package com.gcs.gcsplatform.core.role;

import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.Agent;
import com.gcs.gcsplatform.entity.masterdata.Category;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.CounterpartyBrokerage;
import com.gcs.gcsplatform.entity.masterdata.Currency;
import com.gcs.gcsplatform.entity.masterdata.Dealer;
import com.gcs.gcsplatform.entity.masterdata.Fx;
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
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenComponentPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

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
            "gcsplatform_Agent.browse",
            "gcsplatform_Agent.edit",
            "gcsplatform_Dealer.browse",
            "gcsplatform_Dealer.edit",
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
    @EntityAccess(entityClass = Agent.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Dealer.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Category.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = CounterpartyBrokerage.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Currency.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Fx.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Invoice.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = InvoiceLine.class, operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @Override
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }

    @EntityAttributeAccess(entityName = "*", modify = "*")
    @Override
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }
}
