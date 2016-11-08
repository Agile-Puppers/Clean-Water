package agilepuppers.cleanwater.test;

import agilepuppers.cleanwater.model.TextDatabase;
import agilepuppers.cleanwater.model.user.UserAccount;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by ridoymajumdar on 11/7/16.
 */
public class TextDatabaseTest {

    @Test
    public void testPropertyWithoutDelimiter() {
        TextDatabase<UserAccount> db = new TextDatabase<UserAccount>("./db/accounts", UserAccount.USERNAME_KEY, UserAccount.factory);
        String[] list = new String[3];
        list[0] = "TestString";
        list[1] = "TestaAAAAaaAAT string=aaa";
        list[2] = "123ABCabc=aaa";
        HashMap<String, String> map = db.hashMapFromPropertyList(list);

        assertEquals(2, map.size());
    }

    @Test
    public void testPropertyWithDelimiter() {
        TextDatabase<UserAccount> db = new TextDatabase<UserAccount>("./db/accounts", UserAccount.USERNAME_KEY, UserAccount.factory);
        String[] list = new String[3];
        list[0] = "Test=String";
        list[1] = "TestaAAAAaaAAT string=aaa";
        list[2] = "123ABCabc=aaa";
        HashMap<String, String> map = db.hashMapFromPropertyList(list);

        assertEquals(3, map.size());
    }

    @Test
    public void testPairLengthGreaterThanTwo() {
        TextDatabase<UserAccount> db = new TextDatabase<UserAccount>("./db/accounts", UserAccount.USERNAME_KEY, UserAccount.factory);
        String[] list = new String[3];
        list[0] = "Test = String";
        list[1] = "TestaAAAAaaAAT string = aaa";
        list[2] = "123ABCabc=aaa";
        HashMap<String, String> map = db.hashMapFromPropertyList(list);

        assertEquals(3, map.size());
    }

    @Test
    public void testPairLengthLesserThanTwo() {
        TextDatabase<UserAccount> db = new TextDatabase<UserAccount>("./db/accounts", UserAccount.USERNAME_KEY, UserAccount.factory);
        String[] list = new String[3];
        list[0] = "Test=String";
        list[1] = "TestaAAAAaaAAT string=aaa";
        list[2] = "=";
        HashMap<String, String> map = db.hashMapFromPropertyList(list);
        System.out.println(map.size());

        assertEquals(2, map.size());
    }

    @Test
    public void testNullEntriesInProperties() {
        TextDatabase<UserAccount> db = new TextDatabase<UserAccount>("./db/accounts", UserAccount.USERNAME_KEY, UserAccount.factory);

        String[] list = new String[6];
        // Indices 4 and 5 are null
        list[0] = "Test | String";
        list[1] = "Test | String";
        list[2] = "Test | String";
        list[3] = "Test | String";

        db.hashMapFromPropertyList(list);
    }
}
