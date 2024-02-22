package kraheja.purch.bean.response;


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

public class WorkMatNarrationResponseBean {

	private String bldgType ;
	private String matCertType ;
	private String matCertCode ;
	private String matCertName ;
	private String matItemDesc ;
}