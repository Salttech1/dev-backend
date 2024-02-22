package kraheja.enggsys.lcsystem.annotations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kraheja.enggsys.lcsystem.annotations.CheckEPCGOrDutyFreeNo;
import kraheja.enggsys.lcsystem.payload.request.LcDetails;

public class EPCGOrDutyFreeNoValidator implements ConstraintValidator<CheckEPCGOrDutyFreeNo, LcDetails> {
    @Override
    public void initialize(CheckEPCGOrDutyFreeNo constraintAnnotation) {
    }

    @Override
    public boolean isValid(LcDetails value, ConstraintValidatorContext context) {
        return value != null && (value.getEpcgNo() != null && !value.getEpcgNo().isEmpty()) ||
               (value.getDutyFreeNo() != null && !value.getDutyFreeNo().isEmpty());
    }
}

