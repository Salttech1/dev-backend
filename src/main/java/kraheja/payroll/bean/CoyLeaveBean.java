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
public class CoyLeaveBean {
	private String leavecode ;
	private String acyear ;
	private Double daysentitled ;
	private Double maxdaysenc  ;
	private Double maxdayscf  ;
	private Double daysbf  ;
	private Double daysavailed  ;
	private Double daysencashed  ;
	private Double dayexcessadj  ;
	private Double compoffearned  ;
}