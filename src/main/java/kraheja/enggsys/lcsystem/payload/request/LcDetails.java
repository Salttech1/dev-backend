package kraheja.enggsys.lcsystem.payload.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.enggsys.lcsystem.annotations.CheckEPCGOrDutyFreeNo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@CheckEPCGOrDutyFreeNo
@JsonInclude(Include.NON_NULL)
public class LcDetails {
	private String lcabAuthnum ; //ck 
	private Integer srlNo; //ck
	private String bldgCode ;
	private LocalDate boeDate ;
	private String boeNo ;
	private String coy ;
	private LocalDate docDate ;
	private String docNo ;
	private LocalDate dutyFreeDate ;
	private String dutyFreeNo ;
	private LocalDate epcgDate ;
	private String epcgNo ;
	private LocalDate inspectionDate ;
	private LocalDate instCertSubmdt ;
	private LocalDate shipDate ;
	@NotEmpty(message = "LC/TT No is not entered...")
	private String lcNo ;
	private String origSite ;
	private Integer pendingItems ;
	private String project ;
	
	@NotNull(message = "Document received date is not entered...")
	private LocalDate docsRecdDate ;
	private String site ;
	private LocalDateTime today ;
	private String userId ;
}
