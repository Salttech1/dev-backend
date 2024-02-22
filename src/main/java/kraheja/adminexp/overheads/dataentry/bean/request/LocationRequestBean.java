package kraheja.adminexp.overheads.dataentry.bean.request;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestBean {
	private String bldgcode ;
	private String code ;
	private String duefromsociety ;
	private String name ;
	private String site ;
	private LocalDateTime today ;
	private String userid ;
}
