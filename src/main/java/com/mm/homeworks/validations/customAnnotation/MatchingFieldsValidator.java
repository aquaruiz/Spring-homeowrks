package com.mm.homeworks.validations.customAnnotation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.mm.homeworks.constants.ErrorMessages;

@Component
public class MatchingFieldsValidator implements ConstraintValidator<MatchingFieldsConstraint, Object>  {

    private String[] fields;

    @Override
    public void initialize(MatchingFieldsConstraint constraintAnnotation) {
        fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        List<Field> classFields = extractFieldsFromObject(obj);
        boolean areMatching = allFieldsAreMatching(classFields, obj);

        if (!areMatching) {
            addConstraintViolations(context);
        }

        return areMatching;
    }

    private void addConstraintViolations(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        for (String field : fields) {
            context
                    .buildConstraintViolationWithTemplate(ErrorMessages.FIELDS_ARE_NOT_MATCHING)
                    .addPropertyNode(field).addConstraintViolation();
        }
    }

    private boolean allFieldsAreMatching(List<Field> fields, Object obj) {
        for (Field field : fields) {
            for (Field field1 : fields) {
                try {
                    Object f1 = field.get(obj);
                    Object f2 = field1.get(obj);

                    if ((f1 == null || f2 == null) || !f1.equals(f2)) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    private List<Field> extractFieldsFromObject(Object obj) {
        Class<?> aClass = obj.getClass();
        List<Field> classFields = new ArrayList<>();

        for (String field : fields) {
            try {
                Field declaredField = aClass.getDeclaredField(field);
                declaredField.setAccessible(true);
                classFields.add(declaredField);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return classFields;
    }
}
