package io.pivotal.service;

import io.pivotal.input_response.CrimeResponse;
import org.springframework.stereotype.Component;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

@Component
public interface IDataSeattleService {
    @GET("/y7pv-r3kh.json")
    List<CrimeResponse> getCrimeResponse(@Query("$where") String filter);
}
