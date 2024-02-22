package kraheja.adminexp.billing.dataentry.intercompany.bean.request;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InterCompanyRequest {
	private String companyCode;
	private String projectCode;
	private LocalDateTime billDate;
	private LocalDateTime billToDate;
	private LocalDateTime billFromDate;
	private String transactionDate;
	private String transactionToDate;
	private String transactionFromDate;
	private Map<String, Double> locPartyMap;
	private String remarks;
	private String invoiceNumber;
}
