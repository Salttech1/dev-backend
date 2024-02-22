package kraheja.adminexp.billing.dataentry.debitNote.bean.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdbnotehRequestBean {

	private Double amount ;
	private String billtype ;
	private String bldgcode ;
	private String coy ;
	private String date ;
	private String dbnoteser ;
	private String description1 ;
	private Double fotoamt ;
	private LocalDate invbilldt ;
	private String invbillno ;
	private String narration ;
	private String partycode ;
	private String partytype ;
	private String passedby ;
	private String prepby ;
	private String project ;
	private String prop ;
	private String site ;
	private Double tdsamount ;
	private Integer tdsperc ;
	private LocalDateTime today ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}
