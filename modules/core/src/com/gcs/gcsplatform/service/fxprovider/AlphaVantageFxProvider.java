package com.gcs.gcsplatform.service.fxprovider;

import java.io.IOException;
import java.math.BigDecimal;
import javax.inject.Inject;

import com.gcs.gcsplatform.config.FxConfig;
import com.gcs.gcsplatform.exception.FxProviderException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component(FxProviderAPI.NAME)
public class AlphaVantageFxProvider implements FxProviderAPI {

    private static final String API_METHOD_URI = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=%s&apikey=%s";

    @Inject
    private FxConfig fxConfig;

    @Override
    public BigDecimal getFx(String fromCurrency, String toCurrency) {
        String apiKey = fxConfig.getAlphaVantageApiKey();
        if (apiKey == null) {
            throw new FxProviderException("Alpha Vantage API key property is not set");
        }
        String jsonString = getJsonString(fromCurrency, toCurrency, apiKey);
        try {
            JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
            JsonObject root = jsonObject.getAsJsonObject("Realtime Currency Exchange Rate");
            return root.get("5. Exchange Rate").getAsBigDecimal();
        } catch (Exception e) {
            throw new FxProviderException("An error occurred while parsing response from Alpha Vantage", e);
        }
    }

    private String getJsonString(String fromCurrency, String toCurrency, String apiKey) {
        String json;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(String.format(API_METHOD_URI, fromCurrency, toCurrency, apiKey));
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            json = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new FxProviderException("Cannot get fx from Alpha Vantage", e);
        }
        return json;
    }
}