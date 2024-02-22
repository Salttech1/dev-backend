package kraheja.arch.projbldg.dataentry.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class MailinfoResponseBean {

	private String amenity1 ;
	private String amenity2 ;
	private String amenity3 ;
	private String amenity4 ;
	private String amenity5 ;
	private String area1 ;
	private String area2 ;
	private String area3 ;
	private String bldgcode ;
	private String city ;
	private String config1 ;
	private String config2 ;
	private String config3 ;
	private String config4 ;
	private String coy ;
	private String origsite ;
	private String otherex1 ;
	private String otherex2 ;
	private String possdate ;
	private String project ;
	private String rate1 ;
	private String rate2 ;
	private String rate3 ;
	private String remark1 ;
	private String remark2 ;
	private String remark3 ;
	private String site ;
	private String tenure ;
	private String term1 ;
	private String term2 ;
	private String term3 ;
	private String term4 ;
	private String term5 ;
	private String term6 ;
	private LocalDateTime today ;
	private String township ;
	private String userid ;
	private String validity1 ;
	private String validity2 ;
}