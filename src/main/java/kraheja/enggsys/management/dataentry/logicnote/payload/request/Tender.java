package kraheja.enggsys.management.dataentry.logicnote.payload.request;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tender {

	private String partyType;
	private String partyCode;
	private String venderName;
	private String brand;
	private String yesNo;
	private String groupLogicNote;
	private String currType;
	private Double currRate;
	private Double currAmt;
	private LocalDate quoteDate;
	private LocalDate orderDate;
	private LocalDate deliveryDate;
	private String deliveryWeeks;
	private TenderAddress tenderAddress;
}
