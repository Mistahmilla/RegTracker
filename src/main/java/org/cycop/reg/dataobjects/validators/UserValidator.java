package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean supports(Class aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User u = (User)o;
        ValidationUtils.rejectIfEmpty(errors, "emailAddress", "emailAddress.empty", "Email address can not be blank.");
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty", "Password can not be blank.");
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(u.getEmailAddress());
        if (!matcher.matches()) {
            errors.rejectValue("emailAddress", "emailAddress.incorrect","A valid email address must be entered.");
        }
    }
}
