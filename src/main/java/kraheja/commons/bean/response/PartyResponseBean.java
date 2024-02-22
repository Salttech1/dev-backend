package kraheja.commons.bean.response;

import java.io.Serializable;

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
public class PartyResponseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String partycode;

	private String partytype;

	private String closedate;

	private String aadharno;

	private String cino;

	private String city;

	private String esicno;

	private String gstno;

	private String lastintpaid;

	private String ltdcoyn;

	private String oStaxregncen;

	private String oStaxregnst;

	private String oldrefdate;

	private String opendate;

	private String partyconstt;

	private String partyname;

	private String payeeacnum1;

	private String payeeacnum2;

	private String payeebankcode1;

	private String payeebankcode2;

	private String payeebankname1;

	private String payeebankname2;

	private String payeebranch1;

	private String payeebranch2;

	private String payeeifsc1;

	private String payeeifsc2;

	private String pfno;

	private String pmtacnum;

	private String professiontaxno;

	private String rcnum;

	private String servicetaxnum;

	private String site;

	private String staxregncen;

	private String staxregnst;

	private String supptype;

	private String tanno;

	private String tinnum;

	private String title;

	private String today;

	private String userid;

	private String validminor;

	private String validparty;

	private String vatcanceldt;

	private String vateffectivefrom;

	private String vatnum;
}