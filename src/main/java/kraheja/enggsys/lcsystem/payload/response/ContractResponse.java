package kraheja.enggsys.lcsystem.payload.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class ContractResponse {
	private String prvCertNum;
	private String prvCertType;
	private Integer prvRunSer;
	private LocalDate prvCertDate;
	private String proprietor;
	private String company;
	private String project;
	private String proprty;
	private String building;
	private String workCode;
	private String partyCode;
	private String partyType;
	private Double prvCertAmt;
	private Double totalAmtCertified;
	private Double prvTotTwoptc;
	private LocalDate durationFrom;
	private LocalDate durationUpto;
}
