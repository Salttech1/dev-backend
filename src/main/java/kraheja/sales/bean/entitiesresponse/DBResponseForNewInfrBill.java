package kraheja.sales.bean.entitiesresponse;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//kraheja.sales.bean.entitiesresponse.DBResponseForNewInfrBill()
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DBResponseForNewInfrBill {

	private LocalDate billDate;
	private Double balance;
	private Double arrears;
	private Double interest;
	private Double intarrears;
	
}
