package kraheja.enggsys.lcsystem.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.payload.GenericResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
@JsonInclude(Include.NON_DEFAULT)
public class AuthorizationResponse extends GenericResponse {
	private String authNum;
}
