package kraheja.constant;

/**
 * <p>Application response code constant.</p>
 * 
 * @author sazzad.alom
 * @since 1.0.0
 */
public class ApiResponseCode {
	private ApiResponseCode() {}

	public static final String SUCCESS = "00";
	
	public static final String AUTHORIZATION_FAILED = "100";
	public static final String INVALID_HEADER_FIELDS = "101";
	public static final String BAD_REQUEST = "102";
	public static final String TECHNICAL_ERROR = "103";
	public static final String INETNAL_SERVER_ERROR = "104";
	
	public static final String UNRECOGNIZED_PROPERTY = "105";
	public static final String MANDATORY_FIELDS_MISSING = "106";
	public static final String COMMUNICATION_ERROR = "107";
	public static final String JSON_MAPPING_ERROR = "108";
	
	public static final String FAILED = "109";
	
	public static final String CHEQUE_NUMBER_USED = "110";
	
	public static final String DUPLICATE_DATA_CONFLICT="111";
	public static final String DATA_DOES_NOT_EXIST="";
}