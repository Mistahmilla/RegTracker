package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.Program;
import org.cycop.reg.dataobjects.Registration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegistrationValidatorTest {

    private Validator v;

    @Before
    public void setup(){
        v = new RegistrationValidator();
    }

    @Test
    public void supports() {
        assertTrue(v.supports(Registration.class));
        assertFalse(v.supports(Object.class));

    }

    @Test
    public void invalidRegistration(){
        Registration r = new Registration();
        BindException errors = new BindException(r, "program");
        ValidationUtils.invokeValidator(v, r, errors);
        assertTrue(errors.hasFieldErrors("program"));
        assertTrue(errors.hasFieldErrors("person"));
        assertTrue(errors.hasFieldErrors("registrationDate"));

        r.setPerson(new Person());

        errors = new BindException(r, "person");
        ValidationUtils.invokeValidator(v, r, errors);
        assertTrue(errors.hasFieldErrors("person"));
    }

    @Test
    public void validRegistration(){
        Registration r = new Registration();
        r.setRegistrationDate(LocalDate.now());
        Program p = new Program();
        r.setProgram(p);
        Person per = new Person();
        per.setFirstName("John");
        per.setLastName("Doe");
        r.setPerson(per);
        BindException errors = new BindException(r, "program");
        ValidationUtils.invokeValidator(v, r, errors);
        assertTrue(!errors.hasFieldErrors("program"));
        assertTrue(!errors.hasFieldErrors("person"));
        assertTrue(!errors.hasFieldErrors("registrationDate"));
    }
}
