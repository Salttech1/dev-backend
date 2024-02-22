package kraheja.commons.bean;

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

public class ActrandxBean {

	private String acmajor ;
	private String acminor ;
	private String bldgcode ;
	private Double bunum ;
	private String cfcode ;
	private String cfgroup ;
	private String contract ;
	private String coy ;
	private String docdate ;
	private String docnum ;
	private String doctype ;
	private String flat ;
	private String ledgcode ;
	private String matcode ;
	private String matgroup ;
	private String mincode ;
	private String mintype ;
	private String narrative ;
	private String partycode ;
	private String partytype ;
	private String period ;
	private String project ;
	private String proprietor ;
	private String reffrom ;
	private String refupto ;
	private String revision ;
	private String site ;
	private Double tranamt ;
	private String trandate ;
	private String transer ;
	private String trantype ;
	private String userid ;
	private String voudate ;
	private String vounum ;
	private String wing ;
	private String workcode ;
	private String workgroup ;
	private String xacmajor ;
	private String xacminor ;
	private String xmintype ;
	private String xpartycode ;
	private String xpartytype ;
	private String xproject ;
	private Double xref_bunum ;
	private String xref_trandate ;
	private String xref_transer ;
}