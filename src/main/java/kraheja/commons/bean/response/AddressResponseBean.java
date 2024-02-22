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
public class AddressResponseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String adowner;

	private String adsegment;

	private String adtype;

	private String adser;
	
	private String adfname;

	private String adline1;

	private String adline2;

	private String adline3;

	private String adline4;

	private String adline5;

	private String adlname;

	private String admname;

	private String adtitle;

	private String adtopser;

	private String birthday;

	private String city;

	private String country;

	private String email;

	private String fax;

	private String origsite;

	private String phonemobile;

	private String phoneoff;

	private String phoneres;

	private String pincode;

	private String site;

	private String state;

	private String today;

	private String township;

	private String userid;
}