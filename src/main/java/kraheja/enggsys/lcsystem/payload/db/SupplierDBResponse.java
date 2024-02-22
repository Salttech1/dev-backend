package kraheja.enggsys.lcsystem.payload.db;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class SupplierDBResponse {
	private String bldgCoy;
	private String bldgProp;
	private String bldgProject;
	private String bldgMisproject;
	private String bldgProperty;
	private String bldgMisbldg;
}
