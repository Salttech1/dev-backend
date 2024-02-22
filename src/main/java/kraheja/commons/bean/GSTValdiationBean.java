package kraheja.commons.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.annotaions.GSTNumberCheck;
import kraheja.commons.annotaions.PanNumberCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@GSTNumberCheck
public class GSTValdiationBean {

	public String gstNumber;

	@PanNumberCheck
	public String panCardNumber;

	public String state;
}
