package io.pivotal.service;

import io.pivotal.response.input.CrimeResponse;
import org.springframework.stereotype.Component;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

@Component
public interface IDataSeattleService {
    @GET("/y7pv-r3kh.json")
    List<CrimeResponse> getCrimeResponses(@Query("$where") String filter);
}
