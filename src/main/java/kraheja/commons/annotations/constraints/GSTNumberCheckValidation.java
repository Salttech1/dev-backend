package kraheja.commons.annotations.constraints;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kraheja.commons.annotaions.GSTNumberCheck;
import kraheja.commons.bean.GSTValdiationBean;
import kraheja.commons.utils.CommonResultsetGenerator;


public class GSTNumberCheckValidation implements ConstraintValidator<GSTNumberCheck, GSTValdiationBean> {

	private boolean mandatory;

	@Override
	public void initialize(GSTNumberCheck gstNumberCheck) {
		this.mandatory = gstNumberCheck.mandatory();
	}

	@Override
	public boolean isValid(GSTValdiationBean gstValdiationBean, ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();

		if(Objects.nonNull(gstValdiationBean)) {
			return validationCondition(gstValdiationBean, constraintValidatorContext);
		}

		if(this.mandatory) {
			return validationCondition(gstValdiationBean, constraintValidatorContext);
		}
		return Boolean.TRUE;
	}

	private Boolean validationCondition(GSTValdiationBean gstValdiationBean, ConstraintValidatorContext constraintValidatorContext) {

		Boolean valid = Boolean.FALSE;
		if(gstValdiationBean.gstNumber.length() == 15) {

//			Pattern p = Pattern.compile(CommonConstraints.INSTANCE.gstNumberValidationRegex);
//			Matcher m = p.matcher(gstValdiationBean.getGstNumber());
//			if(!m.matches()) {
//				constraintValidatorContext.buildConstraintViolationWithTemplate("Kindly verify GST number as it's not valid format.").addConstraintViolation();
//				return valid;
//			}
			String stateCode = gstValdiationBean.getGstNumber().substring(0, 2);
			System.out.println("state Code" + stateCode);

			String panNumberStrFromgstNumber = gstValdiationBean.getGstNumber().substring(2, 12);
			System.out.println("PanNumber String From GstNUmber" + panNumberStrFromgstNumber);

			String forteenthCharacter = gstValdiationBean.getGstNumber().substring(13, 14);
			System.out.println("Forteenth Character" + forteenthCharacter);

			List<Map<String,Object>> stateCodeValidCount = CommonResultsetGenerator.queryToResultSetBuilder("select COUNT(*) from entity where ent_class= 'STATE'  and ent_id  = '".concat(gstValdiationBean.getState().trim()).concat("'").concat("and ent_char2 = '").concat(stateCode).concat("'"));
			System.out.println("result set" + stateCodeValidCount);

			Integer countForStateCode = ((BigDecimal) stateCodeValidCount.get(0).get("COUNT(*)")).intValue();
			if(countForStateCode == 0) {
				constraintValidatorContext.buildConstraintViolationWithTemplate("First two character are not valid for GST number.").addConstraintViolation();
				return valid;
			}

			if(!gstValdiationBean.panCardNumber.trim().equals(panNumberStrFromgstNumber)) {
				constraintValidatorContext.buildConstraintViolationWithTemplate("PanCard number doesn't match with GST number").addConstraintViolation();
				return valid;
			}

			List<Map<String,Object>> forteenthCharacterValidCount = CommonResultsetGenerator.queryToResultSetBuilder("select COUNT(*) from entity where ent_class='GSTFM' and ent_char1 = '".concat(forteenthCharacter).concat("'"));
			System.out.println("forteenthCharacterValidCount" + forteenthCharacterValidCount);

			Integer countForforteenthCharacter = ((BigDecimal) forteenthCharacterValidCount.get(0).get("COUNT(*)")).intValue();
			if(countForforteenthCharacter == 0) {
				constraintValidatorContext.buildConstraintViolationWithTemplate("Fourteenth character is not valid for GST number").addConstraintViolation();
				return valid;
			}
			return Boolean.TRUE;
		}else
			constraintValidatorContext.buildConstraintViolationWithTemplate("GST number should be 15 character.").addConstraintViolation();
		return valid;
	}
}
