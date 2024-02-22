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

public class PreviousAuthDetailBean {
	private Double authamt ;
	private Double authquanty ;
	private Double relret ;
	private Double retamt ;
	private Double retadj ;
}