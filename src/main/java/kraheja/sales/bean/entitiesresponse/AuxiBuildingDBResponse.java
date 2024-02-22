package kraheja.sales.bean.entitiesresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//kraheja.sales.bean.entitiesresponse.AuxiBuildingDBResponse(b.bldgProp, b.bldgProperty, b.bldgProject, b.bldgCoy)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuxiBuildingDBResponse {
	private String bldgProp ; 
	private String bldgProperty ;
	private String bldgProject ;
	private String bldgCoy ;
	private String bldgMaintcoy ;
}