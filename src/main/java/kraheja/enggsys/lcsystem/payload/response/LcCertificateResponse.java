package kraheja.enggsys.lcsystem.payload.response;

import kraheja.payload.GenericResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@ToString
public class LcCertificateResponse extends GenericResponse{
	private String lccertNumber;
}
