package kraheja.sales.infra.bean.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.payload.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class BillResponse extends GenericResponse{
	private String billNumber;
	private String ownerId;
	private String month;
	private LocalDate billDate;
	private LocalDate billFromDate;
	private LocalDate billToDate;
	private double billAmount;
	private double billArrears;
	private double interest;
	private double interestArrears;
	private double admin;
	private double cgst;
	private double sgst;
	private double igst;
	private double cgstPerc;
	private double sgstPerc;
	private double igstPerc;
	private String invoiceNumber;
	private String irnno;
	private String sessionId;
	private String buildingCode;
	private String companyName;
	private String billMode;
	private double infrRate;
}
