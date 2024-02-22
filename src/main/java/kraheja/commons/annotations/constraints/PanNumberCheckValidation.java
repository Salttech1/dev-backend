package kraheja.commons.annotations.constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kraheja.commons.annotaions.PanNumberCheck;
import kraheja.commons.utils.CommonConstraints;

public class PanNumberCheckValidation implements ConstraintValidator<PanNumberCheck, String> {

	private boolean mandatory;

	@Override
	public void initialize(PanNumberCheck panNumberCheck) {
		this.mandatory = panNumberCheck.mandatory();
	}

	@Override
	public boolean isValid(String panNum, ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();

		if(panNum != null && !panNum.isEmpty()) {
			return validationCondition(panNum.trim(), constraintValidatorContext);
		}

		if(this.mandatory) {
			return validationCondition(panNum, constraintValidatorContext);
		}
		return Boolean.TRUE;
	}

	private Boolean validationCondition(String panNum, ConstraintValidatorContext constraintValidatorContext) {
		Boolean valid = Boolean.FALSE;

		if(panNum.equals("PANAPPLIED") || panNum.equals("PANNOTAVBL"))
			return !valid;
		if(panNum.matches(".*[a-z].*"))
			constraintValidatorContext.buildConstraintViolationWithTemplate("Pan Number should be in uppercase").addConstraintViolation();
		if(panNum.length() == 10) {
			Pattern p = Pattern.compile(CommonConstraints.INSTANCE.panNumberValidationRegex);
			Matcher m = p.matcher(panNum);
			return m.matches();
		}else
			constraintValidatorContext.buildConstraintViolationWithTemplate("Kindly verify PAN number as it's not valid format.").addConstraintViolation();
		return valid;
	}
}
