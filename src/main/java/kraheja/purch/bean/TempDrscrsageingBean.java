package kraheja.purch.bean;


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

public class TempDrscrsageingBean {

	private String trantype ;
	private String project ;
	private String party ;
	private String partyName;
	private String billNo ;
	private LocalDate billDate ;
	private Double billamt ;
	private Double drnoteamt ;
	private Double paidamt ;
	private Double osamt ;
}