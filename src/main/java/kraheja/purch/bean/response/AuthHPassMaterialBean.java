package kraheja.purch.bean.response;

import java.time.LocalDate;

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

public class AuthHPassMaterialBean {

	private String authBldgcode ;
	private String authPartytype ;
	private String authPartycode ;
	private String authAuthnum ;
	private String authAuthtype ;
	private LocalDate authAuthdate ;
	private LocalDate authPassedon ;
	private String authAuthstatus ;
	private String authCoy ;
	private String authProp ;
	private String authProject ;
	private String authProperty ;
	private Double authAuthamount ;
	private String authMatgroup ;
	private String authTranser ;
	private Double authTdsamount ;
	private Double authAdvadjust ;
	private Double authRetained ;
	private Double authRelretain ;
	private Double authPayamount ;
	private String partyName;

}