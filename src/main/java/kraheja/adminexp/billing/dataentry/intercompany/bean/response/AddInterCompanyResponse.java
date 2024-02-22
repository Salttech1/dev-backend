package kraheja.adminexp.billing.dataentry.intercompany.bean.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddInterCompanyResponse extends GenericResponse{
	private String companyCode;
	private String projectCode;
	private LocalDateTime billDate;
	private LocalDateTime billToDate;
	private LocalDateTime billFromDate;
	private String transactionDate;
	private String transactionToDate;
	private String transactionFromDate;
	private String remarks;
	private String groupInvoiceNumber;
	private String isPosted;
	private List<AddInterCompanyData> interCompanyData;
	
	
}
