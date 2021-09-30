package com.gcs.gcsplatform.service.invoice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.AmazonWorkDocsConfig;
import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.service.WorkDocsUploadService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

@Service(InvoicePublishService.NAME)
public class InvoicePublishServiceBean implements InvoicePublishService {

    @Inject
    private WorkDocsUploadService workDocsUploadService;
    @Inject
    private AmazonWorkDocsConfig amazonWorkDocsConfig;
    @Inject
    private DataManager dataManager;

    @Override
    public void publishToWorkDocs(Collection<Invoice> invoices) {
        Map<String, String> locationFolderMap = getLocationFolderMap();
        CommitContext commitContext = new CommitContext();
        for (Invoice invoice : invoices) {
            if (!invoice.getPrinted()) {
                continue;
            }
            String location = invoice.getLocation();
            if (location == null || !locationFolderMap.containsKey(location.toUpperCase())) {
                throw new IllegalArgumentException("Invoice location must be 'HK' or 'LON'. Specified location: " + location);
            }
            String folderId = locationFolderMap.get(location.toUpperCase());
            workDocsUploadService.uploadFile(invoice.getXlsxFile(), folderId);
            workDocsUploadService.uploadFile(invoice.getPdfFile(), folderId);
            invoice.setPostedToWorkDocs(true);
            commitContext.addInstanceToCommit(invoice);
        }
        dataManager.commit(commitContext);
    }

    private Map<String, String> getLocationFolderMap() {
        Map<String, String> locationFolderMap = new HashMap<>();
        locationFolderMap.put("LON", amazonWorkDocsConfig.getLonInvoiceFolder());
        locationFolderMap.put("HK", amazonWorkDocsConfig.getHkInvoiceFolder());
        return locationFolderMap;
    }
}