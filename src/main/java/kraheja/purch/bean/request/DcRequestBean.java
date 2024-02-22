package kraheja.purch.bean.request;

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

public class DcRequestBean {

	private String billdt ;
	private String dcdate ;
	private String dcno ;
	private String entryno ;
	private String origsite ;
	private Character pukka ;
	private String site ;
	private String suppbill ;
	private String suppcode ;
	private LocalDateTime today ;
	private String userid ;
}