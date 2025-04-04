package com.musungare.BackendForReact.paypalConfig;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalConfig {

    private String clientId = "AUVBBDr3CiV8Ar2_ujBwIkWib9UMWmpJnFCCrIIMDsKrVUVNvUuapNK_rs9zR9xqshKs1Wk-yEfM4ok4";
    private String clientSecret = "EG17rHXl_5O9AKYgCktKOkBlTTLvHi_xGxsqXRpahKxxufhHtJmQYkiayFLIXxBiBvierzobJQUbIB4j";
    private String mode = "sandbox"; // or "live" depending on your setup

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
}
