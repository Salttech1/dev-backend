package kraheja.adminexp.vehicleexp.dataentry.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class EquipResponseBean {

	private String allottedto ;
	private String authorisationvalidtill ;
	private Double avglast ;
	private String batterychanged ;
	private String batteryexpiry ;
	private String bmctaxexp ;
	private Double bookvalue ;
	private String chasisno ;
	private String coy ;
	private String dispaadhaar ;
	private String dispaddress ;
	private String dispchequedate ;
	private String dispchequeno ;
	private String dispcontactno ;
	private String dispdate ;
	private String dispname ;
	private String disppan ;
	private Double dispvalue ;
	private Double emiamount ;
	private String emienddate ;
	private String emistartdate ;
	private String enginecc ;
	private String engineno ;
	private String eqpnum ;
	private String eqptype ;
	private String fitnessvalidtill ;
	private String hpacancelledon ;
	private String hpawith ;
	private String inscompany ;
	private String insexpon ;
	private String inspolicyno ;
	private Double kmslimit ;
	private String loanacno ;
	private Double loanamount ;
	private String loanpapersignedby ;
	private Double lymexp ;
	private Double lyrexp ;
	private String nextmaint ;
	private String origsite ;
	private String permitvalidtill ;
	private String prop ;
	private String purcdate ;
	private String rcbooksignedby ;
	private String registrationauth ;
	private String regvalidtill ;
	private String rtotaxexp ;
	private String rtotaxpaidtill ;
	private String runon ;
	private String servexpiry ;
	private String servicedone ;
	private String servstatus ;
	private String site ;
	private String sitebldg ;
	private Double todatemexp ;
	private Double todaterexp ;
	private LocalDateTime today ;
	private String tuneexpiry ;
	private String tuneup ;
	private Double tymexp ;
	private Double tyrexp ;
	private String userid ;
	private String vehmake ;
	private String vehtype ;
}