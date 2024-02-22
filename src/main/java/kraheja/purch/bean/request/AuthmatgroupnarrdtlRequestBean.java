package kraheja.purch.bean.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class AuthmatgroupnarrdtlRequestBean {
	private Double amount ;
	private String authnum ;
	private String itemdesc ;
	private String matgroup ;
	private Double quantity ;
	private String site ;
	private Double srno ;
	private LocalDateTime today ;
	private String userid ;
}