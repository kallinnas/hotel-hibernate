package db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;


public class ValidationUtils {

    private static Validator validator = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory().getValidator();

    public static void validate(Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
        constraintViolations.forEach((cv) -> System.out.println(String.format(
                "Constraint violations detect in property: [%s], value: [%s], message: [%s]",
                cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage())));
    }

    public static boolean hasViolation(Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
        return constraintViolations.iterator().hasNext();
    }
}
