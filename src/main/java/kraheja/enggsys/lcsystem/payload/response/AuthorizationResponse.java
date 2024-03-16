package kraheja.enggsys.lcsystem.payload.response;

import kraheja.payload.GenericResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@SuperBuilder
public class AuthorizationResponse extends GenericResponse {
	private String authNum;
}
