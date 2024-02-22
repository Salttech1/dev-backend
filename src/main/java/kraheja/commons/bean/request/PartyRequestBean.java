package kraheja.commons.bean.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.annotaions.AadhaarNumberCheck;
import kraheja.commons.annotaions.PanNumberCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class PartyRequestBean {

	private String partycode;
	
	private String partytype;
	
	private String title;
	
	private String partyname;
	
	private String constt;
	
	private String city;
	
	private Date opendate;
	
	@PanNumberCheck
	private String pmtacnum;
	
	private String staxregncen;
	
	private String staxregnst;
	
	private String ostaxregncen;
	
	private String ostaxregnst;
	
	private String validparty;
	
	private String validminor;
	
	private String site;
	
	private String userid;
	
	private String today;
	
	private Date closedate;
	
	private String supptype;
	
	private String ltdcoyn;
	
	private Date lastintpaid;
	
	private Date vatEffectiveFrom;
	
	private String vatnum;
	
	private String tinnum;
	
	private String servicetaxnum;
	
	private String gstno;
	
	private String payeebankcode1;
	
	private String payeebankname1;
	
	private String payeeBranch1;
	
	private String payeeacNum1;
	
	private String payeeIfsc1;
	
	private String payeebankcode2;
	
	private String payeebankname2;
	
	private String payeeBranch2;
	
	private String payeeacNum2;
	
	private String payeeIfsc2;
	
	private String tanNo;
	
	private String professionTaxno;
	
	private String pfNo;
	
	private String esicno;
	
	private String cino;
	
	@AadhaarNumberCheck
	private String aadharno;
	
	private String insupd;

	private String pannum1;

	private Date oldrefdate;
	
	private String rcnum;
	
	private Date vatcanceldt;

	private String aadharpanlinkedyn;
}
