package agilepuppers.cleanwater.test;

import agilepuppers.cleanwater.controller.RegisterScreen;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test by Gabor Siffel
 * <p>
 * Testing method: agilepuppers.cleanwater.controller.RegisterScreen->validRegistrationInfo(un, pw)
 */
public class ValidRegistrationTest {

    @Test
    public void testInvalidCharacters() {

        Assert.assertTrue("valid info was incorrectly returned false", RegisterScreen.validRegistrationInfo("validUsername", "validPass"));
        Assert.assertFalse("invalid username was incorrectly accepted", RegisterScreen.validRegistrationInfo("in-valid username", "validPass"));
        Assert.assertFalse("invalid password was incorrectly accepted", RegisterScreen.validRegistrationInfo("validUsername", "invalid Pass#@&$"));

    }

    @Test
    public void testFieldLengths() {

        Assert.assertTrue("valid info was incorrectly rejected", RegisterScreen.validRegistrationInfo("validUsername", "validPass"));
        Assert.assertFalse("1 character username should not be accepted", RegisterScreen.validRegistrationInfo("u", "validPass"));
        Assert.assertFalse("1 character password should not be accepted", RegisterScreen.validRegistrationInfo("validUsername", "p"));
        Assert.assertFalse("too many character username should not be accepted", RegisterScreen.validRegistrationInfo("01234567890123456789toolong", "validPass"));
        Assert.assertFalse("too many character password should not be accepted", RegisterScreen.validRegistrationInfo("validUsername", "01234567890123456789toolong"));

    }

}
