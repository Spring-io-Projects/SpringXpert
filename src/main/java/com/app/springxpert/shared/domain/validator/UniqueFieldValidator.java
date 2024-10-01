package com.app.springxpert.shared.domain.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {
    @PersistenceContext
    private EntityManager entityManager;
    private String fieldName;
    private Class<?> entityClass;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        else {
            String query = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value", entityClass.getSimpleName(), fieldName);

            try {
                Long count = entityManager.createQuery(query, Long.class).setParameter("value", value).getSingleResult();
                return count == 0L;
            }
            catch (Exception e) {
                throw new RuntimeException("Error while validating the field. Error: " + e.getMessage());
            }
        }
    }
}
