package kraheja.adminexp.overheads.dataentry.bean.response;

import java.time.LocalDateTime;

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

public class OverheadmeterResponseBean 
{
	private String connocode ;
	private String meterno ;
	private String meternoOld ;
	private String insertUpdateMode;
	private String site ;
	private LocalDateTime today ;
	private String userid ;
}
