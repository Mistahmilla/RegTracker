package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.Church;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ChurchValidator implements Validator {

    @Override
    public boolean supports(Class aClass) {
        return Church.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "churchName", "churchName.empty", "Church name cannot be blank.");
        Church c = (Church)o;
        if(c.getChurchName() != null && c.getChurchName().length()>45){
            errors.rejectValue("churchName", "churchName.length", "The maximum size for the church's name is 45 characters.");
        }
        if(c.getPhoneNumber() !=null && c.getPhoneNumber().length()>14){
            errors.rejectValue("phoneNumber", "phoneNumber.length", "The phone number can not exceed 14 characters.");
        }
        if(c.getEmailAddress() !=null && c.getEmailAddress().length()>45){
            errors.rejectValue("emailAddress", "emailAddress.length", "The email address can not exceed 45 characters.");
        }
    }

}
