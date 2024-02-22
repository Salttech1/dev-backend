package kraheja.commons.utils;

import java.time.format.DateTimeFormatter;

public enum CommonConstraints {

	INSTANCE;

	public final String BLANK_STRING = "";

	public final String HYPHEN_STRING = "-";

	public final String UNDERSCORE_STRING = "_";

	public final String SPACE_STRING = " ";

	public final String PIPE_STRING = "|";

	public final String BACKQUOTE_STRING = "`";

	public final String SINGLEQUOTE_STRING = "'";

	public final String SEMI_COLON_STRING = ";";

	public final String EQUAL_TO_STRING = "=";

	public final String DOT_STRING = ".";

	public final String ATTHERATE_STRING = "@";

	public final String OPEN_CURLY_BRACKET_STRING = "{";

	public final String CLOSE_CURLY_BRACKET_STRING = "}";
	
	public final String OPEN_ROUND_BRACKET_STRING = "(";
	
	public final String CLOSE_ROUND_BRACKET_STRING = ")";

	public final String COLON_STRING = ":";

	public final String FORWARD_SLASH_STRING = "/";

	public final char FORWARD_SLASH_CHAR = '/';

	public final String DOUBLE_FORWARD_SLASH_STRING = "//";

	public final String PLUS_STRING = "+";

	public final String HASH_STRING = "#";

	public final String DOLLAR_STRING = "$";

	public final String COMMA_STRING = ",";

	public final String DOUBLE_QUOTE_STRING = "\"";

	public final String ASTERISK_STRING = "*";

	public final String NEWLINE_STRING = "\n";

	public final String LESSTHAN_STRING = "<";

	public final String GREATERTHAN_STRING = ">";

	public final String PERCENTAGE_STRING = "%";

	public final String contractors = "E";

	public final String SUPPLIERS = "S";

	public final String employees = "W";

	public final String flatOwners = "F";

	public final String brokers = "B";

	public final String DEPOSITORS = "D";

	public final String banks = "Q";

	public final String companies = "C";

	public final String miscellaneousParty = "Z";

	public final String closeDate = "01-JAN-2050";
	
	public final String closeDateDDMMYYYY = "01/01/2050";

	public final String addresstype = "LOC";

	public final String adrAdsegment = "PARTY";

	public final String adrAdser = "01";
	
	public final String adrAdserZero = "00";

	public final String panNumberValidationRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

	public final String gstNumberValidationRegex = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}[0-9][0-9A-Z]{1}$";

	public final String validParty = "Y";
	
	public final String validMinor = "Y";

	public final String SITE = "MUM";
	
	public final String COMPUTER_HOST_NAME = "COMPUTERNAME";
	
	public final String ENTITY_SITE = "SITE";
	
	public final String ENTITY_CHAR1 = "CURR";
	
	public final String LETTER_Y = "Y";
	
	public final Integer TWO = 2;

	public final Integer THREE = 3;
	
	public final Integer FOUR = 4;

	public final Integer FIVE = 5;
	
	public final Integer SIX = 6;
	
	public final Integer SEVEN = 7;
	
	public final Integer EIGHT = 8;
	
	public final Integer NINE = 9;
	
	public final Integer TEN = 10;
	
	public final String CHEQUE = "Q";
	
	public final String ORIGSYS = "FD";
	
	public final String LoanY = "Y";
	
	public final String LoanN = "N";
	
	public final String XLSX_EXTENSION = ".xlsx";
	
	public final String XLS_EXTENSION = ".xls";
	
	public final String ATTACHMENT_FILENAME_STRING = "attachment; filename=";
	
	public final String PRINCIPLE_AMOUNT = "Principle Amount";

	public final String INTEREST_AMOUNT = "Interest Amount";
	
	public final String RECOVERY_AMOUNT = "Recovery Amount";
	
	public final String TDS_AMOUNT = "TDS Amount";
	
	public final String DD_CHARGES = "DD Charges";
	
	public final String BROKERAGE_AMOUNT = "Brokerage Amount";
	
	public final String NODATA_STRING = "NODATA";
	
	/* Date formatters */
	public final DateTimeFormatter DDMMYYYY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public final DateTimeFormatter YYYYMMDD_HYPHEN_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public final DateTimeFormatter YYYYMMDD_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	public final DateTimeFormatter DDMMMYYYY_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	
	public final DateTimeFormatter YYYYMM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

	public final DateTimeFormatter DDMMMYYYY_FORMATTER_WITH_DOT = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
	
	public final DateTimeFormatter DD_MM_YYYY_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public final DateTimeFormatter DDMMYYYY_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	public final DateTimeFormatter HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public final String EMPPHOTOPATH =  "////prodsrv//FA.Net//Server//KR-ERP//Graphics//Photo//";
}
