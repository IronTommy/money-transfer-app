import org.junit.Before;
import org.junit.Test;
import playtox.moneytransfer.model.Account;
import playtox.moneytransfer.service.MoneyTransferApp;

import static org.junit.Assert.*;

public class MoneyTransferAppErrorHandlingTest {

    private MoneyTransferApp app;

    @Before
    public void setUp() {
        app = new MoneyTransferApp();
    }

    @Test
    public void testTransferMoneyNegativeAmount() {
        Account from = new Account("acc1", 10_000);
        Account to = new Account("acc2", 10_000);
        try {
            app.transferMoney(from, to, -500);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount must be positive", e.getMessage());
        }
    }

    @Test
    public void testTransferMoneyFromSameAccount() {
        Account account = new Account("acc1", 10_000);
        try {
            app.transferMoney(account, account, 500);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot transfer money from and to the same account", e.getMessage());
        }
        assertEquals(10_000, account.getMoney());
    }
}
