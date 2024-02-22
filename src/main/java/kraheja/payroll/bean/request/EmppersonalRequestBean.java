package kraheja.payroll.bean.request;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class EmppersonalRequestBean {

	private String aadhaarno ;
	private String birthdate ;
	private String bloodgrp ;
	private String effectivefrom ;
	private String effectiveupto ;
	private String empcode ;
	private String fullname ;
	private String gender ;
	private String handicapyn ;
	private Double height ;
	private String hobbies ;
	private String ipaddress ;
	private String machinename ;
	private String maritalstat ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String mothertongue ;
	private String nationality ;
	private Integer noofchildren ;
	private String panno ;
	private String pfuan ;
	private String photopath ;
	private String religion ;
	private String remark ;
	private String site ;
	private String title ;
	private String userid ;
	private String weddingdate ;
	private Double weight ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}