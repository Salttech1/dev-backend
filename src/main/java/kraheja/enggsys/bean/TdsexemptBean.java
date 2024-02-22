package kraheja.enggsys.bean;

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

public class TdsexemptBean {

	private String contract ;
	private String exmpref ;
	private String origsite ;
	private String site ;
	private String tdsfrom ;
	private Integer tdsper ;
	private String tdsupto ;
	private LocalDateTime today ;
	private String userid ;
}