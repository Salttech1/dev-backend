package kraheja.fd.deposit.annotations.constraints;

import java.math.BigInteger;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kraheja.fd.deposit.annotaions.DepositorType;
import kraheja.fd.deposit.enums.InterestCalculationTypeEnum;

public class DepositorTypeValidation implements ConstraintValidator<DepositorType, String> {

	private boolean mandatory;

	@Override
	public void initialize(DepositorType depositorType) {
		this.mandatory = depositorType.mandatory();
	}

	@Override
	public boolean isValid(String depositorType, ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();
		constraintValidatorContext.buildConstraintViolationWithTemplate("Depositor type Status must be any one of these " + InterestCalculationTypeEnum.values()).addConstraintViolation();

		if(this.mandatory)
			return validationCondition(depositorType, constraintValidatorContext);

		if(depositorType != null)
			return validationCondition(depositorType, constraintValidatorContext);
		return Boolean.TRUE;
	}

	private Boolean validationCondition(String depositorType, ConstraintValidatorContext constraintValidatorContext) {
		return InterestCalculationTypeEnum.getAllValues().stream().filter(data -> data.equalsIgnoreCase(depositorType)).collect(Collectors.toSet()).size() > BigInteger.ZERO.intValue() ? InterestCalculationTypeEnum.getAllValues().stream().anyMatch(data -> data.equalsIgnoreCase(depositorType)) ? Boolean.TRUE : Boolean.FALSE : Boolean.FALSE;
	}

}
