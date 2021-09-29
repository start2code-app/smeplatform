package com.gcs.gcsplatform.service.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.CurrencyConfig;
import com.gcs.gcsplatform.entity.invoice.Invoice;
import com.gcs.gcsplatform.entity.invoice.InvoiceLine;
import com.gcs.gcsplatform.entity.masterdata.Company;
import com.gcs.gcsplatform.entity.masterdata.Counterparty;
import com.gcs.gcsplatform.entity.masterdata.InvoiceBank;
import com.gcs.gcsplatform.service.BankService;
import com.gcs.gcsplatform.service.CompanyService;
import com.gcs.gcsplatform.service.CounterpartyService;
import com.gcs.gcsplatform.service.fx.FxCalculationService;
import com.gcs.gcsplatform.service.invoice.InvoiceLineService;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import org.springframework.stereotype.Service;

import static com.gcs.gcsplatform.util.DateUtils.getCurrentDate;

@Service(InvoiceReportService.NAME)
public class InvoiceReportServiceBean implements InvoiceReportService {

    @Inject
    private FxCalculationService fxCalculationService;
    @Inject
    private CurrencyConfig currencyConfig;
    @Inject
    private CounterpartyService counterpartyService;
    @Inject
    private CompanyService companyService;
    @Inject
    private BankService bankService;
    @Inject
    private InvoiceLineService invoiceLineService;
    @Inject
    private ReportDataConversionService reportDataConversionService;

    @Override
    public List<Map<String, Object>> getHeader(Invoice invoice) {
        Map<String, Object> headerMap = reportDataConversionService.entityToMap(invoice);
        headerMap.putAll(getCounterparty(invoice));
        headerMap.putAll(getBank(invoice));
        headerMap.putAll(getCompany(invoice));
        return Collections.singletonList(headerMap);
    }

    private Map<String, Object> getCounterparty(Invoice invoice) {
        Counterparty counterparty = counterpartyService.findCounterparty(invoice.getCounterpartyCode(),
                ViewBuilder.of(Counterparty.class)
                        .addAll("billingCompanyName", "contactName", "address1", "address2", "address3", "address4")
                        .build());
        return reportDataConversionService.entityToMap(counterparty);
    }

    private Map<String, Object> getBank(Invoice invoice) {
        View view = ViewBuilder.of(InvoiceBank.class)
                .addView(View.LOCAL)
                .add("bank", View.LOCAL)
                .build();
        InvoiceBank invoiceBank = bankService.findInvoiceBank(invoice.getLocation(), invoice.getCurrency(), view);
        if (invoiceBank == null) {
            String usdCurrency = currencyConfig.getUsdCurrency().getCurrency();
            InvoiceBank usdBank = bankService.findInvoiceBank(invoice.getLocation(), usdCurrency, view);
            if (usdBank == null) {
                return Collections.emptyMap();
            }
            Map<String, Object> bankMap = reportDataConversionService.entityToMap(usdBank.getBank());
            BigDecimal crossRate = fxCalculationService.calculateCrossRate(invoice.getFx(), invoice.getFxUsd());
            bankMap.put("totalDueUsd", String.format("Total Due USD: %.2f , FX : %.4f", invoice.getUsdAmount(),
                    crossRate));
            bankMap.put("bankCurrency", usdCurrency);
            return bankMap;
        } else {
            Map<String, Object> bankMap = reportDataConversionService.entityToMap(invoiceBank.getBank());
            bankMap.put("bankCurrency", invoice.getCurrency());
            return bankMap;
        }
    }

    private Map<String, Object> getCompany(Invoice invoice) {
        Company company = companyService.findCompany(invoice.getLocation(),
                ViewBuilder.of(Company.class)
                        .addView(View.LOCAL)
                        .build());
        return reportDataConversionService.entityToMap(company);
    }

    @Override
    public List<Map<String, Object>> getInvoiceLines(Invoice invoice) {
        Collection<InvoiceLine> invoiceLines = invoiceLineService.getInvoiceLines(invoice,
                ViewBuilder.of(InvoiceLine.class)
                        .addView(View.LOCAL)
                        .build());
        List<Map<String, Object>> mapList = reportDataConversionService.entityCollectionToMapList(invoiceLines);
        for (int i = 0; i < mapList.size(); i++) {
            mapList.get(i).put("rowNum", i + 1);
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getBottom(Invoice invoice) {
        Map<String, Object> bottomMap = new HashMap<>();
        bottomMap.put("amount", invoice.getAmount());
        bottomMap.put("currency", invoice.getCurrency());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy h:mm a");
        bottomMap.put("printDate", dateFormat.format(getCurrentDate()));
        return Collections.singletonList(bottomMap);
    }
}