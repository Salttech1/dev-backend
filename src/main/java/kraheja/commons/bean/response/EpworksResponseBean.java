package kraheja.commons.bean.response;

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

public class EpworksResponseBean {

	private String acmajor ;
	private String acname ;
	private String allowdupdur ;
	private String aworkcode ;
	private String aworkname ;
	private String closedate ;
	private String costclass ;
	private String linkreqyn ;
	private String maingroup ;
	private String matcode ;
	private String opendate ;
	private String paymode ;
	private String printgroup ;
	private String saccode ;
	private String site ;
	private String tdsacmajor ;
	private LocalDateTime today ;
	private String userid ;
	private String code ;
	private String workgroup ;
	private String name ;
}