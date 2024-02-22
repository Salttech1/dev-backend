package kraheja.enggsys.lcsystem.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;

import kraheja.enggsys.lcsystem.annotations.impl.EPCGOrDutyFreeNoValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EPCGOrDutyFreeNoValidator.class)
@Documented
public @interface CheckEPCGOrDutyFreeNo {
    String message() default "EPCG or DutyFree No is not entered...";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

