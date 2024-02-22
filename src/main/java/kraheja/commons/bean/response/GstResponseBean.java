package kraheja.commons.bean.response;

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

public class GstResponseBean {

	private Double cgstperc ;
	private Double igstperc ;
	private Double sgstperc ;
	private Double ugstperc ;
	private String description;
}