package io.pivotal.config;

import io.pivotal.service.IDataSeattleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Configuration
public class CrimeConfig {
    public static final String DATASEATTLE_ENDPOINT = "https://data.seattle.gov/resource";
    public static final String DATASEATTLE_KEY = "5SrzVINmberri7ZBf0QytGtY5";

    @Bean
    public IDataSeattleService getCrimeService() {
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(DATASEATTLE_ENDPOINT);
        builder.setClient(new OkClient());
        builder.setRequestInterceptor(request -> request.addHeader("X-App-Token", DATASEATTLE_KEY));
        RestAdapter adapter = builder.build();
        return adapter.create(IDataSeattleService.class);
    }

}
