package kraheja.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kraheja.commons.bean.ClientConfig;
import kraheja.commons.entity.Dynapop;
import kraheja.commons.filter.GenericAuditContextHolder;

public enum CommonUtils {

	INSTANCE;

	private static final String COLUMN_ALIAS = "_COL2";
	

public static final String[] units = { "", "ONE", "TWO", "THREE", "FOUR",
"FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE",
"THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN",
"EIGHTEEN", "NINETEEN" };

public static final String[] tens = { 
        "",         // 0
        "",     // 1
        "TWENTY",   // 2
        "THIRTY",   // 3
        "FORTY",    // 4
        "FIFTY",    // 5
        "SIXTY",    // 6
        "SEVENTY",  // 7
        "EIGHTY",   // 8
        "NINETY"    // 9
};

public static String convert(final int n) {
    if (n < 0) {
        return "MINUS " + convert(-n);
    }

    if (n < 20) {
        return units[n];
    }

    if (n < 100) {
        return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
    }

    if (n < 1000) {
        return units[n / 100] + " HUNDRED" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
    }

    if (n < 100000) {
        return convert(n / 1000) + " THOUSAND" + ((n % 10000 != 0) ? " " : "") + convert(n % 1000);
    }

    if (n < 10000000) {
        return convert(n / 100000) + " LACS" + ((n % 100000 != 0) ? " " : "") + convert(n % 100000);
    }

    return convert(n / 10000000) + " CRORE" + ((n % 10000000 != 0) ? " " : "") + convert(n % 10000000);
}

	public String encrypt(String str) {
		StringBuilder pass = new StringBuilder();
		for (int x = 0; (x < str.length()); x++) {
			pass.insert(0, (char) (((str.substring(x, x + 1).charAt(0)) * 2) + 3));
		}
		return pass.toString();
	}

	public String convertLocalDateTimeToString(LocalDateTime date) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(dateTimeFormatter);
	}

	public String decrypt(String str) {
		StringBuilder pass = new StringBuilder();
		for (int x = 0; (x < str.length()); x++) {
			pass.insert(0, (char) (((str.substring(x, x + 1).charAt(0)) - 3) / 2));
		}
		return pass.toString();
	}

	public Date closeDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2050);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 01);
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			return formatter.parse(formatter.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date currentDate() {
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = new Date();
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String stringToDateTimeFormatterConverter(String date) {
		DateTimeFormatter inSDF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		DateTimeFormatter outSDF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			return outSDF.format(inSDF.parse(date));
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public String stringDateFormatter(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	public String localDateTimeDDMMYYYYFormatter(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formatDateTime = date.format(formatter);
		return formatDateTime;
	}

	public String addDaysInString(String date, Integer days) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(date, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		localDate = localDate.plusDays(days);
		return localDate.format(formatter).toString();
	}

	public String stringDateTimeFormatter(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public Long dateToEpoch(Date date) {
		return date.getTime();
	}

	public StringBuilder dynaPop(Dynapop dynapopEntity, String fromRequest) {
		StringBuilder query = new StringBuilder();
		String substitutedString = null;
		if (!DynapopUtils.INSTANCE.validateDynaPop(dynapopEntity)) {
			if(StringUtils.isNotBlank(dynapopEntity.getDynSitefilter())) {
			 substitutedString = StringSubstitutor.replace(dynapopEntity.getDynSitefilter(), new HashMap<String, Object>(){
					private static final long serialVersionUID = 1L;
					{
						put("region", " '"+GenericAuditContextHolder.getContext().getSite()+"' ");
					}});
				
			}
			query.append("SELECT ");
			if(StringUtils.isNotBlank(dynapopEntity.getDynDistinct()) && dynapopEntity.getDynDistinct().equals("Y")) {
				query.append(" DISTINCT ");
			}
			checkDynapaopColunnName(query, dynapopEntity);
			query.append(" FROM ");
			query.append(dynapopEntity.getDynTable());
			if (StringUtils.isNotBlank(dynapopEntity.getDynCondition())) {
				query.append(DynapopUtils.INSTANCE.createWhereFields(dynapopEntity));
//				query.append(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString));
				if (StringUtils.isNotBlank(fromRequest)) {
					query.append(" AND ");
					query.append(fromRequest);
					query.append(StringUtils.isNotBlank(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString)) ? " AND "+DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString) : CommonConstraints.INSTANCE.SPACE_STRING);
				}
				if (StringUtils.isNotBlank(dynapopEntity.getDynOrderby()))
					query.append(DynapopUtils.INSTANCE.createOrderByFields(dynapopEntity));
			} else {
				if (StringUtils.isNotBlank(fromRequest)) {
					query.append(" WHERE ");
					query.append(fromRequest);
					query.append(StringUtils.isNotBlank(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString)) ? " AND "+DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString) : CommonConstraints.INSTANCE.SPACE_STRING);
				}
				else {
					query.append(StringUtils.isNotBlank(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString)) ? " WHERE " + DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString) : CommonConstraints.INSTANCE.SPACE_STRING);
				}
				if (StringUtils.isNotBlank(dynapopEntity.getDynOrderby()))
					query.append(DynapopUtils.INSTANCE.createOrderByFields(dynapopEntity));
			}
		}
		return query;
	}

	public String convertStringFormat(String date) throws ParseException {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
		return myFormat.format(fromUser.parse(date));
	}

	public LocalDateTime convertStringToDateFormat(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(date, formatter).atStartOfDay();
	}

	public StringBuilder dynaPopFindBy(Dynapop dynapopEntity, String fromRequest, String searchText) {
		StringBuilder query = new StringBuilder();
		String substitutedString = null;
		if (!DynapopUtils.INSTANCE.validateDynaPop(dynapopEntity)) {
			if(StringUtils.isNotBlank(dynapopEntity.getDynSitefilter())) {
				 substitutedString = StringSubstitutor.replace(dynapopEntity.getDynSitefilter(), new HashMap<String, Object>(){
						private static final long serialVersionUID = 1L;
						{
							put("region", " '"+GenericAuditContextHolder.getContext().getSite()+"' ");
						}});
				}
			query.append("SELECT ");
			if(StringUtils.isNotBlank(dynapopEntity.getDynDistinct()) && dynapopEntity.getDynDistinct().equals("Y")) {
				query.append(" DISTINCT ");
			}
			checkDynapaopColunnName(query, dynapopEntity);
			query.append(" FROM ");
			query.append(dynapopEntity.getDynTable());
			if (StringUtils.isNotBlank(dynapopEntity.getDynCondition())) {
				query.append(DynapopUtils.INSTANCE.createWhereFields(dynapopEntity));
//				query.append(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString));
				if (StringUtils.isNotBlank(fromRequest)) {
					query.append(" AND ");
					DynapopUtils.INSTANCE.appendRequestQuery(query, fromRequest);
					DynapopUtils.INSTANCE.bbcResultSet(dynapopEntity, query, searchText);
					query.append(StringUtils.isNotBlank(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString)) ? " AND "+DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString) : CommonConstraints.INSTANCE.SPACE_STRING);
					
				} else {
					query.append(" AND ");
					DynapopUtils.INSTANCE.bbcResultSet(dynapopEntity, query, searchText);
					query.append(StringUtils.isNotBlank(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString)) ? " AND "+DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString) : CommonConstraints.INSTANCE.SPACE_STRING);
				}
			} else if (StringUtils.isNotBlank(fromRequest)) {
				query.append(" WHERE ");
				DynapopUtils.INSTANCE.appendRequestQuery(query, fromRequest);
				DynapopUtils.INSTANCE.bbcResultSet(dynapopEntity, query, searchText);
				query.append(StringUtils.isNotBlank(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString)) ? " AND "+DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString) : CommonConstraints.INSTANCE.SPACE_STRING);
			} else if (!StringUtils.isEmpty(searchText)) {
				query.append(" WHERE ");
				DynapopUtils.INSTANCE.bbcResultSet(dynapopEntity, query, searchText);
				query.append(StringUtils.isNotBlank(DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString)) ? " AND "+DynapopUtils.INSTANCE.createSiteFilter(dynapopEntity, substitutedString) : CommonConstraints.INSTANCE.SPACE_STRING);
			}
			if (StringUtils.isNotBlank(dynapopEntity.getDynOrderby()))
				query.append(DynapopUtils.INSTANCE.createOrderByFields(dynapopEntity));
		}
		return query;
	}

	public void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
		// append = false
		FileOutputStream outputStream = null;

		try {
			outputStream = new FileOutputStream(file, false);
			int read;
			byte[] bytes = new byte[Byte.MAX_VALUE];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObjectMapper objectMapper = fetchObjectMapperInstance();

	public ObjectMapper fetchObjectMapperInstance() {
		ObjectMapper objectMapperClass = new ObjectMapper();
		objectMapperClass.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapperClass.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapperClass.findAndRegisterModules();
		objectMapperClass.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
		return objectMapperClass;
	}

	public Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	public void checkDynapaopColunnName(StringBuilder query, Dynapop dynapopEntity) {
		query.append(DynapopUtils.INSTANCE.validateString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn1(), dynapopEntity.getDynColtype1())));
		query.append(dynapopEntity.getDynColumn1().trim().equals(dynapopEntity.getDynColumn2().trim())
				? DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn2(), dynapopEntity.getDynColtype2())) + " AS "
						+ dynapopEntity.getDynColumn2().trim() + COLUMN_ALIAS
				: DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn2(), dynapopEntity.getDynColtype2())));
		if (dynapopEntity.getDynColumn3() != null) {
			query.append(dynapopEntity.getDynColumn2().trim().equals(dynapopEntity.getDynColumn3().trim())
					? DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn3(), dynapopEntity.getDynColtype3())) + " AS "
							+ dynapopEntity.getDynColumn3().trim() + COLUMN_ALIAS
					: DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn3(), dynapopEntity.getDynColtype3())));
		}
		if (dynapopEntity.getDynColumn4() != null) {
			query.append(dynapopEntity.getDynColumn3().trim().equals(dynapopEntity.getDynColumn4().trim())
					? DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn4(), dynapopEntity.getDynColtype4())) + " AS "
							+ dynapopEntity.getDynColumn4().trim() + COLUMN_ALIAS
					: DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn4(), dynapopEntity.getDynColtype4())));
		}
		if (dynapopEntity.getDynColumn5() != null) {
			query.append(dynapopEntity.getDynColumn4().trim().equals(dynapopEntity.getDynColumn5().trim())
					? DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn5(), dynapopEntity.getDynColtype5())) + " AS "
							+ dynapopEntity.getDynColumn5().trim() + COLUMN_ALIAS
					: DynapopUtils.INSTANCE.validateColumnString(DynapopUtils.INSTANCE.dateColumnFormatter(dynapopEntity.getDynColumn5(), dynapopEntity.getDynColtype5())));
		}
	}

	public Date stringToDateFormatter(String date) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1;
	}

	public Date stringToDateTimeFormatter(String date) {
		Date dateFormat = new Date();
		try {
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFormat;
	}

	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public LocalDateTime closeDateInLocalDateTime() {
		return LocalDateTime.of(2050, Month.JANUARY, 01, 00, 00, 00);
	}

	public long toEpochMilli(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public Boolean isDateInBetweenIncludingEndPoints(String date) {
		Integer day = Integer.valueOf(date.substring(0, 2));
		Integer month = Integer.valueOf(date.substring(3, 5));
		if (month == 4)
			if (day >= 1 && day <= 29)
				return Boolean.TRUE;
		return Boolean.FALSE;
	}

	public Boolean checkIfDateInBetween(LocalDate fromDate, LocalDate toDate, LocalDate date, Long period,
			Boolean plusOrMinusMonths) {
		if (plusOrMinusMonths)
			toDate = Objects.nonNull(toDate) ? toDate : fromDate.plusMonths(period);
		else
			toDate = Objects.nonNull(toDate) ? toDate : fromDate.minusMonths(period);

		if (date.isAfter(fromDate) && date.isBefore(toDate) || date.isEqual(fromDate))
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	public Boolean isDateInBetweenIncludingEndPoints(String date, Integer checkMonth, Integer startDate, Integer endDate) {
		Integer day = Integer.valueOf(date.substring(0, 2));
		Integer month = Integer.valueOf(date.substring(3, 5));
		if (month == checkMonth)
			if (day >= startDate && day <= endDate)
				return Boolean.TRUE;
		return Boolean.FALSE;
	}

	public String dateToFinancialYear(String date) {
		LocalDate parsedDate = LocalDate.parse(date, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		int year = parsedDate.getMonthValue() < 3 ? parsedDate.getYear() - 1 : parsedDate.getYear();

		return year + "-" + (year+ 1);
	}
	
	public String convertToCapitalizeCase(String input) {
		return StringUtils.isNotBlank(input) ? Arrays.stream(input.trim().split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(CommonConstraints.INSTANCE.SPACE_STRING)) : CommonConstraints.INSTANCE.BLANK_STRING;
	}
	
	public ClientConfig getClientConfig() {
		 String remoteAddress = GenericAuditContextHolder.getContext().getIpAddress();
		    String computerName;
		    try{
		    InetAddress inetAddress = InetAddress.getByName(remoteAddress);
	        computerName = inetAddress.getHostName();
			if (computerName.equalsIgnoreCase("localhost")) {
	            computerName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
	        } 
			computerName = StringUtils.isNotBlank(computerName) && computerName.trim().length() > 10 ? InetAddress.getLocalHost().getHostName() : computerName.trim();
			InetAddress localhost = InetAddress.getLocalHost();
			String ipaddress = GenericAuditContextHolder.getContext().getIpAddress().startsWith("0:0:0:0") ? localhost.getHostAddress().trim() : GenericAuditContextHolder.getContext().getIpAddress();
		   
			return ClientConfig.builder().ipAddress(ipaddress).machineName(computerName).build();
		    }catch(Exception e) {
		    	e.getMessage();
		    }
			return null;
	}
	
	public String uniqueIdentifier(String... prefix) {
		return (prefix.length > BigInteger.ZERO.intValue() ? prefix[BigInteger.ZERO.intValue()] : CommonConstraints.INSTANCE.BLANK_STRING).concat(UUID.randomUUID().toString().replace(CommonConstraints.INSTANCE.HYPHEN_STRING, CommonConstraints.INSTANCE.BLANK_STRING));
	}
}
