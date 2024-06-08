package bg.softuni.cardealerexercise.util;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidationUtil {

    <E> boolean isValid(E entity);

    <E>Set<ConstraintViolation<E>> getViolations(E entity);


}
