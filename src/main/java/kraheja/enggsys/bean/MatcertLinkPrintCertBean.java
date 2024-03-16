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

public class MatcertLinkPrintCertBean {
	private String tmpCode;    
	private String tmpGroupcode;    
	private String tmpGroupdesc;    
	private String tmpCodedesc;    
	private Double tmpTotamt; 
	  }
