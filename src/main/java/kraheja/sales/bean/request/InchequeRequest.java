package kraheja.sales.bean.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import kraheja.sales.bean.response.GridResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NotEmpty
@AllArgsConstructor
@ToString
public class InchequeRequest {
	
	private double cgstAmt;
	private double sgstAmt;
	private double igstAmt;
	private double tdsAmt;
	private double ogAmt;
	private double admin;
	private double intPaid;
	private double igst;
	private String startMonth;
	private String endMonth;
	private double totalMonth;
	private double transactionAmt;
	private LocalDateTime receiptDate;
	private String recDate;
	private List<ChequeRequest> cheques;
	private List<GridResponse> gridRequest;
}