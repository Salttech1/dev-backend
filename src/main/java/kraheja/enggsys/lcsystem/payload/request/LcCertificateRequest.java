package kraheja.enggsys.lcsystem.payload.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.constant.ApiResponseMessage;
import kraheja.enggsys.lcsystem.annotations.CheckMasterCertynMasterCertNo;
import kraheja.payload.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@CheckMasterCertynMasterCertNo
@JsonInclude(Include.NON_DEFAULT)
public class LcCertificateRequest extends GenericResponse{
	@Builder.Default private String tranType = "N";
	private String preparedBy;
	private LocalDate certificateDate;
	private Integer revNum;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
    @PositiveOrZero(message = ApiResponseMessage.GREATER_THAN_OR_EQUAL_ZERO)
	@Digits(integer = 3, fraction = 0, message =  ApiResponseMessage.ONLY_ALLOWED_3_DIGITS)
	private Integer noOfDays;
	private LocalDate durationFrom;
	private LocalDate durationUpto;
	private String payMode;
	private Double quantity;
	private String uom;
	private String currency;
	private Double amount;
	private Double bankCharges;
	private Double payAmount;
	private String documentNo;
	private LocalDate documentDate;
	private String masterCertificateNo;
	private String category;
	private String lcNo;
	
	@Size(max = 5, message = ApiResponseMessage.ONLY_ALLOWED_5_CHARACTERS)
	private String fileNo;
	
	@Size(max = 110, message = ApiResponseMessage.ONLY_ALLOWED_110_CHARACTERS)
	private String remarks;
	
	@Size(max = 110, message = ApiResponseMessage.ONLY_ALLOWED_110_CHARACTERS)
	private String purpose;
	private String masterCertificateYN;
//	private ContractResponse contractRequest;
	@Valid private List<LcDetails> lcDetailsList;
}
