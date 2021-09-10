package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

@Service(InvoiceSnapshotService.NAME)
public class InvoiceSnapshotServiceBean implements InvoiceSnapshotService {

    @Inject
    private DataManager dataManager;
    @Inject
    private InvoiceLineService invoiceLineService;
    @Inject
    private InvoiceService invoiceService;

    @Override
    public void makeSnapshot(Collection<ClosedTrade> trades) {
        CommitContext commitContext = new CommitContext();
        trades.stream()
                .map(closedTrade -> invoiceLineService.splitTrade(closedTrade))
                .peek(invoiceLines -> addInstancesToCommit(invoiceLines, commitContext))
                .map(invoiceLines -> invoiceService.createInvoices(invoiceLines))
                .forEach(invoices -> addInstancesToCommit(invoices, commitContext));
        dataManager.commit(commitContext);
    }

    private <E extends Entity> void addInstancesToCommit(Collection<E> entities, CommitContext commitContext) {
        for (E entity : entities) {
            commitContext.addInstanceToCommit(entity);
        }
    }
}