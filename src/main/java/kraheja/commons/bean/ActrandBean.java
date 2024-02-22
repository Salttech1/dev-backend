package kraheja.commons.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ActrandBean {
	
	private String coy;
	
	private String transer;

	private Integer bunum;
	
	private String bbookyn;

	private String proprietor;

	private String trandate;

	private String partycode;

	private String acmajor;

	private String acminor;

	private String bldgcode;

	private String cfcode;

	private String cfgroup;

	private Integer contrabunum;

	private String contract;

	private String docdate;

	private String docnum;

	private String docparcode;

	private String docpartype;

	private String doctype;

	private String domain;

	private String flat;

	private Double itemqty;

	private String ledgcode;

	private String matcode;

	private String matgroup;

	private String mincode;

	private String mintype;

	private String narrative;

	private Integer oldbunum;

	private String partytype;

	private String paymode;

	private String period;

	private String project;

	private String property;

	private String reffrom;

	private String refupto;

	private String site;

	private String sku;

	private LocalDateTime today;

	private Double tranamt;

	private String trantype;

	private String userid;

	private String voudate;

	private String vounum;

	private String wing;

	private String workcode;

	private String workgroup;

	private String xacmajor;

	private String xacminor;

	private String xmintype;

	private String xpartycode;

	private String xpartytype;

	private String xproject;

	private Integer xrefbunum;

	private String xreftrandate;

	private String xreftranser;
}
