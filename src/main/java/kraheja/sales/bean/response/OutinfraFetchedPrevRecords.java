package kraheja.sales.bean.response;

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
public class OutinfraFetchedPrevRecords {
	private Double amtpaid ;
	private Double amtint ;
	private Double cgst ;
	private Double admincharges ;
	private String month ;
	private String ownerid ;
	private Double sgst ;
	private Double igst ;
	private Double tds ;
}
