<<<<<<< HEAD
package com.tdtu.logistics_inventory_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<ValidateProductPrice, Double> {
	@Override
	public void initialize(ValidateProductPrice constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Double productPrice,
	                       ConstraintValidatorContext constraintValidatorContext) {
		return productPrice >= 0;
	}
}
=======
package com.tdtu.logistics_inventory_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<ValidateProductPrice, Double> {
	@Override
	public void initialize(ValidateProductPrice constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Double productPrice,
	                       ConstraintValidatorContext constraintValidatorContext) {
		return productPrice >= 0;
	}
}
>>>>>>> develop
