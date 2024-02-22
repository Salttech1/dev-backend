package kraheja.commons.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class HsnsacmasterResponseBean {

	private Double cgstperc ;
	private String closedate ;
	private String code ;
	private String desc ;
	private Double igstperc ;
	private String opendate ;
	private String origsite ;
	private Double perc ;
	private Double sgstperc ;
	private String site ;
	private LocalDateTime today ;
	private String type ;
	private Double ugstperc ;
	private String userid ;
}