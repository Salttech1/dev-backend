package kraheja.enggsys.management.dataentry.logicnote.payload.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_DEFAULT)
public class WorkCodeDetailRequest {

	private String certType;
	private String certCode;
	private String subGroup;
	private String groupCode;
	private Double amount;
	List<Commitee> commiteeList;
	List<Vendor> vendorList;
}
