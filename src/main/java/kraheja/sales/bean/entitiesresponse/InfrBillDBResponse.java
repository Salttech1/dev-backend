package kraheja.sales.bean.entitiesresponse;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfrBillDBResponse {
	private String infrBillnum;
	private String infrInvoiceNo;
	private String infrIrnNo;
	private String infrOwnerId;
	private String infrBldgCode;
	private String infrWing;
	private String infrFlatnum;
	private String infrMonth;
	private Double infrRate;
	private Double infrBillamt;
	private LocalDate infrBilldate;
	private LocalDate infrFromdate;
	private LocalDate infrTodate;
	private Double infrAmtos;
	
	private Double infrArrears;
	private Double infrIntarrears;
	private Double infrInterest;
	private Double infrAdmincharges;
	private Double infrCgst;
	private Double infrSgst;
	private Double infrIgst;
	private Double infrCgstperc;
	private Double infrSgstperc;
	private Double infrIgstperc;
}
