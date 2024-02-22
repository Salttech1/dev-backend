package kraheja.purch.bean.response;

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

public class PaidBillResponseBean {

	private String pblhSuppbillno ;
	private LocalDate pblhSuppbilldt ;
	private Double pblhAmount ;
	private String pblhAuthnum ;
	private String pblhChequeno ;
	private Double pblhDebitamt ;
	private Double pblhTdsamount ;
	private Double pblhPaidamt ;

}