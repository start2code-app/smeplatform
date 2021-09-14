package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
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
        List<InvoiceLine> invoiceLines = trades.stream()
                .map(closedTrade -> invoiceLineService.splitTrade(closedTrade))
                .flatMap(Collection::stream)
                .peek(commitContext::addInstanceToCommit)
                .collect(Collectors.toList());
        invoiceService.createInvoices(invoiceLines)
                .forEach(commitContext::addInstanceToCommit);
        dataManager.commit(commitContext);
    }
}