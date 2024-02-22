package kraheja.enggsys.lcsystem.annotations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kraheja.enggsys.lcsystem.annotations.CheckMasterynMasterNo;
import kraheja.enggsys.lcsystem.payload.request.AuthorizationRequest;

public class CheckMasterynMasterNoValidator implements ConstraintValidator<CheckMasterynMasterNo, AuthorizationRequest> {
    @Override
    public void initialize(CheckMasterynMasterNo constraintAnnotation) {
    }

    @Override
    public boolean isValid(AuthorizationRequest value, ConstraintValidatorContext context) {
        if (value != null && value.getMasterAuthNo() == null || value.getMasterAuthYN() == null ) {
			return true;
		}
    	return false;
    }
}