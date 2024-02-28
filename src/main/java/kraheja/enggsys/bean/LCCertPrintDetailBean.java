package kraheja.enggsys.bean;


import java.time.LocalDate;

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

public class LCCertPrintDetailBean {
	private String autdsuppbillno;    
	private LocalDate autdsuppbilldt;
	private Double autdauthqty;
	private Double autdauthamount; 
	private Double autdauthtdsamt;
	private Double autdadvadj;
	private Double autdrelretamt;
	private Double autdretentionadj;  
	private String autdauthnum;
	private String autdauthtype;
	private Double autdretainamt;
	private Double pblhretainos;
	private String pblhser;
	private String pblhpartycode;
	private String pblhauthnum;
	private String pbldceser;
	private String authauthnum;
	private String authpartycode;
	
	  }