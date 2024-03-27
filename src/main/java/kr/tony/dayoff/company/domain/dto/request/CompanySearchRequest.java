package kr.tony.dayoff.company.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanySearchRequest {

    @JsonProperty("b_no")
    private String businessNumber;

    @JsonProperty("start_dt")
    private String startDate;

    @JsonProperty("p_nm")
    private String primaryRepresentName1;

    @JsonProperty("b_nm")
    private String brandName;
}
