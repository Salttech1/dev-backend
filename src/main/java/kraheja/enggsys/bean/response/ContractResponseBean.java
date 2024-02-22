package kraheja.enggsys.bean.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.commons.bean.response.PartyResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseBean {

	private Double advtax ;
	private Double advtaxca ;
	private String advtaxyr ;
	private String bldgcode ;
	private String bldgname ;
	private String closedate ;
	private String contract ;
	private String contractorname;
	private String city ;
	private String conttor ;
	private String coy ;
	private String coyname ;
	private Double estval ;
	private String exmpref ;
	private Integer krishicessperc ;
	private String matcode ;
	private String matgroup ;
	private String name ;
	private Integer nextadv ;
	private Integer nextfin ;
	private Integer nextint ;
	private Integer nextmis ;
	private Integer nextrret ;
	private Integer nextrun ;
	private Integer nextset ;
	private String origsite ;
	private Double origval ;
	private Double paidval ;
	private String partycode ;
	private String partytype ;
	private String partyTypeDesc ;
	private Double prevtds ;
	private String project ;
	private String property ;
	private String proprietor ;
	private Double retainval ;
	private Integer servtaxperc ;
	private String site ;
	private Integer sptdsper ;
	private Integer swachhcessperc ;
	private String tdscode ;
	private String tdsfrom ;
	private Integer tdsper ;
	private String tdsptype ;
	private String tdsupto ;
	private LocalDateTime today ;
	private String userid ;
	private Integer vatperc ;
	private Integer wctaxper ;
	private String wing ;
	private String workcode ;
	private String workname ;
	private String workgroup ;
	
	private PartyResponseBean partyResponseBean;
	private AddressResponseBean addressResponseBean;
}