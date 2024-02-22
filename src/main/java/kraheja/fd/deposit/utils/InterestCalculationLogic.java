package kraheja.fd.deposit.utils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;

public enum InterestCalculationLogic {

	INSTANCE;

	public static Long calculateInterest(Double depAmount, String fromDate, String uptoDate, Double intRate, Integer intFrequency) {
		intFrequency = intFrequency != null ? intFrequency : BigInteger.ZERO.intValue();

		Double totalAmount = BigInteger.ZERO.doubleValue();

		LocalDate fromDateInDateFormat = LocalDate.parse(fromDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		LocalDate uptoDateInDateFormat = LocalDate.parse(uptoDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		Long daysBetween = ChronoUnit.DAYS.between(fromDateInDateFormat, uptoDateInDateFormat) + BigInteger.ONE.intValue();

		if(isLeapYear(uptoDateInDateFormat.getYear())) 
			totalAmount = depAmount * (intRate / 100) * (daysBetween.doubleValue() / 366);
		else
			totalAmount = depAmount * (intRate / 100) * (daysBetween.doubleValue() / 365);
		return Math.round(totalAmount);
	}

	public static Long calculateInterestForStaff(Double depAmount, String fromDate, String uptoDate, Double intRate, String staffYN, Double staffIntRate) {
		staffYN = staffYN != null ? staffYN : "N";

		Double totalAmount = BigInteger.ZERO.doubleValue();
		Double staffAllow = BigInteger.ZERO.doubleValue();

		LocalDate fromDateInDateFormat = LocalDate.parse(fromDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		LocalDate uptoDateInDateFormat = LocalDate.parse(uptoDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		Long daysBetween = ChronoUnit.DAYS.between(fromDateInDateFormat, uptoDateInDateFormat) + BigInteger.ONE.intValue();

		if(staffYN.equals("Y")) {
			if(staffIntRate != BigInteger.ZERO.doubleValue()) {
				if(isLeapYear(uptoDateInDateFormat.getYear())) 
					staffAllow = depAmount * (staffIntRate / 100) * (daysBetween.doubleValue() / 366);
				else
					staffAllow = depAmount * (staffIntRate / 100) * (daysBetween.doubleValue() / 365);
			}
			totalAmount = Double.valueOf(Math.round(staffAllow));
		}
		return Math.round(totalAmount);
	}

	public static Boolean isLeapYear(Integer year) {
		return ((year % 4 == 0) && (year % 100!= 0)) || (year % 400 == 0) ? Boolean.TRUE : Boolean.FALSE;
	}

	public static Boolean isInterestCalculationSaveEnabled() {
		Boolean isValidMarchDate = CommonUtils.INSTANCE.isDateInBetweenIncludingEndPoints(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 3, 11, 31);
		Boolean isValidAprilDate = CommonUtils.INSTANCE.isDateInBetweenIncludingEndPoints(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 4, 11, 30);
		Boolean isValidOctoberDate = CommonUtils.INSTANCE.isDateInBetweenIncludingEndPoints(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 10, 11, 31);

		return (isValidMarchDate || isValidAprilDate || isValidOctoberDate) ? Boolean.TRUE : Boolean.FALSE;
	}

}
