package kraheja.arch.projbldg.dataentry.bean.request;

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

public class BldgmapRequestBean {

	private String abldgcode ;
	private String abldgname ;
	private String blockcerttype ;
	private String closedate ;
	private String coy1 ;
	private String coy2 ;
	private String coy3 ;
	private String ebldgcode ;
	private String ebldgname ;
	private String origsite ;
	private String site ;
	private LocalDateTime today ;
	private String userid ;
}