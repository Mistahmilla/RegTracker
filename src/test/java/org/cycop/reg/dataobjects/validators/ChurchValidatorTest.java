package org.cycop.reg.dataobjects.validators;

import org.cycop.reg.dataobjects.Church;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ChurchValidatorTest {

    private Validator v;

    @Before
    public void setup(){
        v = new ChurchValidator();
    }

    @Test
    public void supports() {
        assertTrue(v.supports(Church.class));
        assertFalse(v.supports(Object.class));
    }

    @Test
    public void emailAddressTest(){
        Church c = new Church();
        c.setChurchName("church");
        c.setEmailAddress("sjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjslssskljsfjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        BindException errors = new BindException(c, "emailAddress");
        ValidationUtils.invokeValidator(v, c, errors);
        Assert.assertTrue(errors.hasErrors());
    }

    @Test
    public void phoneNumberTest(){
        Church c = new Church();
        c.setChurchName("church");
        c.setPhoneNumber("sjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjslssskljsfjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        BindException errors = new BindException(c, "phoneNumber");
        ValidationUtils.invokeValidator(v, c, errors);
        Assert.assertTrue(errors.hasErrors());
    }

    @Test
    public void churchNameTest(){
        Church c = new Church();
        c.setChurchName("test");
        BindException errors = new BindException(c, "churchName");
        ValidationUtils.invokeValidator(v, c, errors);
        assertEquals(0, errors.getFieldErrorCount("churchName"));
        c.setChurchName("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
        errors = new BindException(c, "churchName");
        ValidationUtils.invokeValidator(v, c, errors);
        assertEquals("churchName.length", errors.getFieldError("churchName").getCode());
    }
}
