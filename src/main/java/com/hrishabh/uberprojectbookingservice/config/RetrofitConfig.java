package com.hrishabh.uberprojectbookingservice.config;

import com.hrishabh.uberprojectbookingservice.apis.LocationServiceApi;
import com.hrishabh.uberprojectbookingservice.apis.UberSocketApi;
import com.netflix.discovery.EurekaClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Autowired
    private EurekaClient eurekaClient;

    private String getServiceUrl(String serviceName) {
        return eurekaClient.getNextServerFromEureka(serviceName, false).getHomePageUrl();
    }

    // ✅ This method builds and returns the Retrofit client WHEN called, not at startup
    public LocationServiceApi getLocationServiceApi() {
        String baseUrl = getServiceUrl("UBERPROJECT-LOCATIONSERVICE");
        System.out.println("Dynamically resolved LocationService URL: " + baseUrl);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(LocationServiceApi.class);
    }
    public UberSocketApi uberSocketApi() {
        String baseUrl = getServiceUrl("CLIENTSOCKETSERVICE");
        System.out.println("Dynamically resolved Client Socket URL: " + baseUrl);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(UberSocketApi.class);
    }
}
