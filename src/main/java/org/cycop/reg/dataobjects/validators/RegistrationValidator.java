package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.Registration;
import org.springframework.validation.*;

public class RegistrationValidator implements Validator {

    @Override
    public boolean supports(Class aClass) {
        return Registration.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "registrationDate", "registrationDate.empty", "Registration date is a required field.");
        ValidationUtils.rejectIfEmpty(errors, "program", "program.empty", "Program is a required field.");
        ValidationUtils.rejectIfEmpty(errors, "person", "person.empty", "Person is a required field.");
        Registration r = (Registration)o;
        Person p = r.getPerson();

        if(p != null) {
            DataBinder db = new DataBinder(p);
            db.setValidator(new PersonValidator());
            db.bind(null);
            db.validate();
            BindingResult result = db.getBindingResult();
            if (result.hasErrors()) {
                errors.rejectValue("person", "person.invalid", "Person is invalid.");
            }
        }
    }

}
