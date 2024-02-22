package kraheja.commons.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MinorsBean {

//	private String minorsname ;
//
//	private LocalDate opendate ;
//
//	private String site ;
//
//	private LocalDateTime today ;
//
//	private String userid ;
//
//	private String validminor ;
//	
//	private String minorscode ; 
//
//	private String minorstype ; 

	private LocalDate closedate ; 
}
