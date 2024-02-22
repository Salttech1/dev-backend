package kraheja.commons.bean.request;

import java.util.Date;

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
public class AddressRequestBean {

	private String owner;
	
	private String adtype;
	
	private String ser;
	
	private String topser;
	
	private String title;
	
	private String fname;
	
	private String mname;
	
	private String lname;
	
	private String ad1;
	
	private String ad2;
	
	private String ad3;
	
	private String ad4;
	
	private String ad5;
	
	private String town;
	
	private String rcnum;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String pincode;
	
	private String phoneoff;
	
	private String adsegment;
	
	private String phoneres;
	
	private String fax;
	
	private String email;
	
	private Date birthday;
	
	private String site;
	
	private String userid;
	
	private String today;
	
	private String origsite;
	
	private String phonemobile;
	
	private String insupd;
}
