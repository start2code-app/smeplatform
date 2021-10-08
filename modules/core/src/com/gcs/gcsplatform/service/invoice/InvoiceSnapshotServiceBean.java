package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.TemporalType;

import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.trade.ClosedTrade;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;

@Service(InvoiceSnapshotService.NAME)
public class InvoiceSnapshotServiceBean implements InvoiceSnapshotService {

    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private DataManager dataManager;
    @Inject
    private Persistence persistence;
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

    @Override
    public boolean snapshotIsTaken(Date startDate, Date endDate) {
        return dataManager.loadValue("select 1 from gcsplatform_InvoiceLine e "
                + "where e.startDate >= :startDate "
                + "and e.endDate <= :endDate", Integer.class)
                .parameter("startDate", startDate, TemporalType.DATE)
                .parameter("endDate", endDate, TemporalType.DATE)
                .optional()
                .isPresent();
    }

    @Override
    public void clearSnapshot(Date startDate, Date endDate) {
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            String userLogin = userSessionSource.getUserSession().getUser().getLoginLowerCase();
            em.createQuery("update gcsplatform_Invoice e "
                    + "set e.deleteTs = :deleteTs, e.deletedBy = :deletedBy "
                    + "where e.startDate >= :startDate "
                    + "and e.endDate <= :endDate")
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate", endDate, TemporalType.DATE)
                    .setParameter("deleteTs", getCurrentDate())
                    .setParameter("deletedBy", userLogin)
                    .executeUpdate();
            em.createQuery("update gcsplatform_InvoiceLine e "
                    + "set e.deleteTs = :deleteTs, e.deletedBy = :deletedBy "
                    + "where e.startDate >= :startDate "
                    + "and e.endDate <= :endDate")
                    .setParameter("startDate", startDate, TemporalType.DATE)
                    .setParameter("endDate", endDate, TemporalType.DATE)
                    .setParameter("deleteTs", getCurrentDate())
                    .setParameter("deletedBy", userLogin)
                    .executeUpdate();
            tx.commit();
        } finally {
            tx.end();
        }
    }
}