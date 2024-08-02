package com.example.inventory.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.Path;

import java.util.Set;
import java.util.Iterator;

public class ValidatorUtil {
	private static Validator validator;
	private Set<ConstraintViolation<Object>> constraintViolations;
	// private Path path;

	public ValidatorUtil(Object object) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        this.validator = factory.getValidator();
		this.constraintViolations = validator.validate(object);
	}

	public boolean isValid() {
		return constraintViolations.size() == 0;
	}

	public String violation() {
		var violation = constraintViolations.iterator().next();
		Path path = violation.getPropertyPath();
		Path.Node node = path.iterator().next();

		return "Field " + node.getName() + " " + violation.getMessage();
	}

}
