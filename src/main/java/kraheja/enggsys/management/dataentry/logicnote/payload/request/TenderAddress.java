package kraheja.enggsys.management.dataentry.logicnote.payload.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenderAddress {

	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String address5;
	private String town;
	private String city;
	private String state;
	private String country;
	private String pin;
	private String phoneRes;
	private String phoneOff;
	private String mobile;
	private String fax;
	private LocalDate dateOfBirth;
	private String headContPhone;
	private String email;
	private String headContName;
	private String coordinator1;
	private String coordinator1Phone;
	private String coordinator2;
	private String coordinator2Phone;
}
