package com.imooc.miaosha.validator;

import com.imooc.miaosha.util.ValidatorUtil;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    /* *
     * ConstraintValidator<A,T> A: annotation T:Type of being annotated object
     */

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            // if parameter is required
            return ValidatorUtil.isMobile(s);
        }else {
            // if parameter be null is permitted
            return StringUtils.isEmpty(s) || ValidatorUtil.isMobile(s);
        }
    }


}
