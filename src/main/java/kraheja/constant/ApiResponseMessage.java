package kraheja.constant;

/**
 * <p>application response constant.</p>
 * 
 * @author sazzad.alom
 * @since 1.0.0
 */
public class ApiResponseMessage {

	private ApiResponseMessage() {}

	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String SUCCESSFULLY_PERSIST = "successfully saved into database";
	public static final String DATA_FETCH_SUCCESSFULLY = "details fetch successfully."; 
	public static final String AUTHORIZATION_FAILED = "message authentication failed.";
	public static final String INVALID_HEADER_FIELDS = "invalid request header fields.";
	public static final String BAD_REQUEST = "bad request, check request fields and re-try.";
	public static final String TECHNICAL_ERROR = "technical error occured.";
	public static final String INETNAL_SERVER_ERROR = "internal server error";
	public static final String UNRECOGNIZED_PROPERTY = "un-recognized property in request fields.";
	public static final String MANDATORY_FIELDS_MISSING = "mandatory fields missing in request.";
	public static final String INCHEQUE_SAVE_SUCCESS = "incheque details save successfully."; 
	//400 - BAD REQUEST
	public static final String MESSAGE_NOT_REDABLE = "message not readable.";
	public static final String JSON_PARSE_ERROR = "json parse error.";
	public static final String JSON_MAPPING_ERROR = "json mapping error. Check data type for this specific field.";
	public static final String UN_SUPPORTED_MEDIA_TYPE = "un-supported media type.";//415 - UNSUPPORTED MEDIA TYPE
	public static final String METHOD_NOT_ALLLOWED = "method not allowed.";//405 - METHOD NOT ALLOWED

	public static final String EXCEPTION_OCCURE = "exception occure.";
	
	public static final String INCHEQ_DETAIL_FAILED_TO_SAVE = "incheq detail failed to save.";
	public static final String CHEQUE_ALREADY_IN_USED = "cheque number already in used. please check the cheque number.";
	public static final String ADMIN_AND_MAINTANACE_RATE_ZERO = "both admin and maintanace rate are zero. please enter required rate.";
	public static final String CHECK_ADMIN_OR_MAINTANACE_RATE_ZERO = "admin or maintanace rate may be zero. please enter required rate.";
	
	public static final String FETCH_SUCCESSFULLY = "fetch successfully.";
	public static final String BILL_CALCULATED_SUCCESSFULLY = "bill calculated successfully.";
	public static final String FLAT_OWNER_ID_NOT_AVAILABLE = "flat owner id is not registered.";
	public static final String BILL_ALREADY_PASSED="bill already passed.";
	public static final String BILL_DOES_NOT_EXIST="bill does not exist.";
	
	public static final String INTERCOMPANY_FAILED_TO_UPDATE = "intercompany detail was failed to update.";
	
	public static final String RECORD_NOT_FOUNDED = "record is not founded.";
	
	//LC CERTIFICATE 
	public static final String LC_CERTIFICATE_FAILED_SAVED = "LC Certificate failed save.";
	public static final String LC_CERTIFICATE_SUCCESSFULLY_SAVED = "LC Certificate save successfully.";
	public static final String LC_CERTIFICATE_SUCCESSFULLY_UPDATED = "LC Certificate updated successfully.";
	public static final String ONLY_ALLOWED_110_CHARACTERS = "the field must not exceed 110 characters.";
	public static final String THE_FIELD_IS_REQUIRED = "the field is required.";
	public static final String GREATER_THAN_OR_EQUAL_ZERO = "the field must be greater than or equal to zero.";
	public static final String ONLY_ALLOWED_3_DIGITS = "the field must be a number with up to 3 digits.";
	public static final String ONLY_ALLOWED_10_CHARACTERS = "The field must be less than or equal 10 charachters.";
	public static final String ONLY_ALLOWED_5_CHARACTERS = "the field must be less than or equal 5 charachters.";
	public static final String ONLY_ALLOWED_DIGITS = "the field must be digit only.";
	public static final String ONLY_ALLOWED_Y_OR_N = "only allowed value Y as Yes, N as No.";
	public static final String ONLY_ONE_ALLOWED_BOTH_OF_THEM_MASTERAUTHYN_OR_MASTERAUTHNO = "allowed only one either masterAuthYN or masterAuthNo.";
	public static final String CANNOT_MODIFY_THIS_REOCRD = "Cannot Modify this reocrd.Final Bill Generated for this Logic Note.";
	public static final String ONLY_ONE_ALLOWED_BOTH_OF_THEM_MASTERCERTYN_OR_MASTERCERTNO = "allowed only one either masterCertificateYN or masterCertificateNo.";
	public static final String  LC_CERTIFICATE_IS_ALREADY_PASSED = "LC Certificate is already passed / paid.";
	public static final String  LC_AUTHORISATION_IS_ALREADY_PASSED = "LC Authorisation is already passed / paid.";

}