package kraheja.purch.bean.response;


import java.time.LocalDateTime;

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

public class MaterialResponseBean {

	private String acmajor ;
	private String acmatgroup ;
	private String acminor ;
	private String blockdate ;
	private Double brqconv ;
	private String brqtytype ;
	private String closedate ;
	private Double cumamount ;
	private Double cumquantity ;
	private String dsftcdm ;
	private String dsftcdw ;
	private String dworktype ;
	private Double entries ;
	private String estgroupcd ;
	private String execcode ;
	private String groupcode ;
	private Double gstpercent ;
	private String hsftcdm ;
	private String hsftcdw ;
	private String hsncode ;
	private String indentable ;
	private String itemcode ;
	private String length ;
	private String level ;
	private String code ;
	private String matgroup ;
	private String name ;
	private String mulitem ;
	private String mulsmat ;
	private String number ;
	private String opendate ;
	private String origsite ;
	private String pjmatcode ;
	private String pjmatname ;
	private String pjsku ;
	private String purcmat ;
	private Double rate ;
	private String site ;
	private String sku ;
	private LocalDateTime today ;
	private String userid ;
	private Double vatpercent ;
	private String workcode ;
}