package kraheja.commons.annotations.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kraheja.commons.annotaions.AadhaarNumberCheck;


public class AadhaarNumberCheckValidation implements ConstraintValidator<AadhaarNumberCheck, String> {

	private boolean mandatory;

	@Override
	public void initialize(AadhaarNumberCheck aadharCardCheck) {
		this.mandatory = aadharCardCheck.mandatory();
	}

	@Override
	public boolean isValid(String aadhaarNumber, ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();

		if(aadhaarNumber != null && !aadhaarNumber.isEmpty()) {
			return validationCondition(aadhaarNumber.trim(), constraintValidatorContext);
		}

		if(this.mandatory) {
			return validationCondition(aadhaarNumber, constraintValidatorContext);
		}
		return Boolean.TRUE;
	}

	private Boolean validationCondition(String aadhaarCard, ConstraintValidatorContext constraintValidatorContext) {
		Boolean valid = Boolean.FALSE;
		if(aadhaarCard.length() == 12) 
			valid = Boolean.TRUE;
		else
			constraintValidatorContext.buildConstraintViolationWithTemplate("Kindly verify Adhaar Number as it's not valid format.").addConstraintViolation();
		return valid;
	}
}
