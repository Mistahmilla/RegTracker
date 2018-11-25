package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {

    private Validator v;

    @Before
    public void setup(){
        v = new UserValidator();
    }

    @Test
    public void supports() {
        assertTrue(v.supports(User.class));
        assertFalse(v.supports(Object.class));
    }

    @Test
    public void emailAddressIsNull(){
        User u = new User();
        u.setEmailAddress("");
        BindException errors = new BindException(u, "emailAddress");
        ValidationUtils.invokeValidator(v, u, errors);
        assertTrue(errors.hasFieldErrors("emailAddress"));
    }

    @Test
    public void passwordIsNull(){
        User u = new User();
        u.setEmailAddress("john@doe.com");
        BindException errors = new BindException(u, "password");
        ValidationUtils.invokeValidator(v, u, errors);
        assertTrue(errors.hasFieldErrors("password"));
    }

    @Test
    public void emailIsRightFormat(){
        User u = new User();
        u.setEmailAddress("john@doe.com");
        u.setPassword("password");
        BindException errors = new BindException(u, "emailAddress");
        ValidationUtils.invokeValidator(v, u, errors);
        assertFalse(errors.hasFieldErrors("emailAddress"));
    }

    @Test
    public void emailIsWrongFormat(){
        User u = new User();
        u.setEmailAddress("test");
        u.setPassword("password");
        BindException errors = new BindException(u, "emailAddress");
        ValidationUtils.invokeValidator(v, u, errors);
        assertTrue(errors.hasFieldErrors("emailAddress"));
    }

}
