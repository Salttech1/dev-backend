package kraheja.enggsys.lcsystem.annotations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import kraheja.enggsys.lcsystem.annotations.CheckMasterCertynMasterCertNo;
import kraheja.enggsys.lcsystem.payload.request.LcCertificateRequest;

public class CheckMasterCertynMasterCertNoValidator implements ConstraintValidator<CheckMasterCertynMasterCertNo, LcCertificateRequest> {
    @Override
    public void initialize(CheckMasterCertynMasterCertNo constraintAnnotation) {
    }

    @Override
    public boolean isValid(LcCertificateRequest value, ConstraintValidatorContext context) {
    	
    	if (!ObjectUtils.isEmpty(value)) {
			if (!StringUtils.isEmpty(value.getMasterCertificateNo()) || value.getMasterCertificateYN().equals("Y")) {
				return true;
			}
		}
    	return false;
    }
}
