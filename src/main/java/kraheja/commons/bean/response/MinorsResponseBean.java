package kraheja.commons.bean.response;

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

public class MinorsResponseBean {

	private String closedate ;
	private String minorscode ;
	private String minorsname ;
	private String minorstype ;
	private String opendate ;
	private String site ;
	private LocalDateTime today ;
	private String userid ;
	private String validminor ;
}