package validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation,String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !email.isBlank() && email.contains("@");
    }
}
