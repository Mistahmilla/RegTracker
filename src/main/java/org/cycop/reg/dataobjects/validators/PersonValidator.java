package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.Person;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PersonValidator implements Validator {

    @Override
    public boolean supports(Class aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty", "First name cannot be blank.");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty", "Last name cannot be blank.");
        Person p = (Person) o;
        if (p.getFirstName().length()>45){
            errors.rejectValue("firstName", "firstName.length", "The first name can not exceed 45 characters.");
        }
        if(p.getLastName().length()>45){
            errors.rejectValue("lastName", "firstName.length", "The last name can not exceed 45 characters.");
        }
    }
}
