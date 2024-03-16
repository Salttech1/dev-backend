package kraheja.enggsys.lcsystem.payload.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.constant.ApiResponseMessage;
import kraheja.enggsys.lcsystem.annotations.CheckMasterynMasterNo;
import kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse;
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
@JsonInclude(Include.NON_DEFAULT)
//@CheckMasterynMasterNo
public class AuthorizationRequest extends GenericResponse{
	@Builder.Default private String tranType = "N";
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String preparedBy;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
    @PositiveOrZero(message = ApiResponseMessage.GREATER_THAN_OR_EQUAL_ZERO)
	@Digits(integer = 3, fraction = 0, message =  ApiResponseMessage.ONLY_ALLOWED_3_DIGITS)
	private Integer noOfDays;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String uom;
	private LocalDate authDate;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String payMode;
	
	private String masterAuthNo;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String matGroup;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String currency;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private Integer quantity;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	@Digits(integer = 10, fraction = 2, message = ApiResponseMessage.ONLY_ALLOWED_DIGITS)
	private Double authAmount;
	
	@Digits(integer = 10, fraction = 2, message = ApiResponseMessage.ONLY_ALLOWED_DIGITS)
	private Double bankCharges;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	@Digits(integer = 10, fraction = 2, message = ApiResponseMessage.ONLY_ALLOWED_DIGITS)
	private Double payAmount;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String documentNo;
	
	private LocalDate documentDate;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String category;
	
//	@Max(value = 10, message = ApiResponseMessage.ONLY_ALLOWED_10_CHARACTERS)
	private String lcNo;
	private String fileNo;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String countryOfOrigin;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	private String cityOfOrigin;
	
	@Size(max = 110, message = ApiResponseMessage.ONLY_ALLOWED_110_CHARACTERS)
	private String remarks;
	
	@NotNull(message = ApiResponseMessage.THE_FIELD_IS_REQUIRED)
	@Size(max = 110, message = ApiResponseMessage.ONLY_ALLOWED_110_CHARACTERS)
	private String purpose;
	
	private String debitParty;
	private String debitCurrency;
	private Double debitAmount;
	private Double convRate;
	private String debitReason;
	private LocalDate lcOpenDate;
	private LocalDate lcExpiryDate;
	private LocalDate lastShipmntDate;
	private String epcgNo;
	private String dutyFreeNo;
	private LocalDate dutyFreeDate;
	private String epcgDutyfree ;
	private LocalDate epcgDate;
	
	@Pattern(regexp = "^Y|N|", message = ApiResponseMessage.ONLY_ALLOWED_Y_OR_N) 
	private String masterAuthYN;
	private String status;
	private SupplierDBResponse supplierResponse;
	
	private Integer bldgAuthNo;
	
	@Valid
	private List<LcDetails> lcDetailsList;
	
}
