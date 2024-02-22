package kraheja.payroll.bean;

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
public class CoySchemeBean {
	private String schemecode ;
	private String schemecodedesc ;
	private Character autoyn ;
	private String empschemeno ;
	private Double schemepercentage ;
	private String schemecentre ;
	private Character applicableyn ;
}