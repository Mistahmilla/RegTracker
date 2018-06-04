package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonValidatorTest {

    private Validator v;

    @Before
    public void setup(){
        v = new PersonValidator();
    }

    @Test
    public void supports() {
        assertTrue(v.supports(Person.class));
        assertFalse(v.supports(Object.class));
    }

    @Test
    public void lastNameIsNull(){
        Person p = new Person();
        p.setFirstName("John");
        BindException errors = new BindException(p, "firstName");
        ValidationUtils.invokeValidator(v, p, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void firstNameIsNull(){
        Person p = new Person();
        p.setLastName("Smith");
        BindException errors = new BindException(p, "lastName");
        ValidationUtils.invokeValidator(v, p, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void validPerson(){
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Smith");
        BindException errors = new BindException(p, "lastName");
        ValidationUtils.invokeValidator(v, p, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void firstNameTooLong(){
        Person p = new Person();
        p.setFirstName("Johnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
        p.setLastName("Smith");
        BindException errors = new BindException(p, "firstName");
        ValidationUtils.invokeValidator(v, p, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void lastNameTooLong(){
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Smithhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        BindException errors = new BindException(p, "firstName");
        ValidationUtils.invokeValidator(v, p, errors);
        assertTrue(errors.hasErrors());
    }
}
