package io.pivotal.response.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrimeDetail {
    private final String type;
    private final int total;
    private final String popular;
    private final List<CrimeDetail> offenses;
}
