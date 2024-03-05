package kraheja.enggsys.lcsystem.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import kraheja.constant.ApiResponseMessage;
import kraheja.enggsys.lcsystem.annotations.impl.CheckMasterCertynMasterCertNoValidator;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckMasterCertynMasterCertNoValidator.class)
@Documented
public @interface CheckMasterCertynMasterCertNo {
    String message() default ApiResponseMessage.ONLY_ONE_ALLOWED_BOTH_OF_THEM_MASTERCERTYN_OR_MASTERCERTNO;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
