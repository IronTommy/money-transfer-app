import org.junit.Before;
import org.junit.Test;
import playtox.moneytransfer.model.Account;
import playtox.moneytransfer.service.MoneyTransferApp;

import static org.junit.Assert.*;

public class MoneyTransferAppAccountCreationTest {

    private MoneyTransferApp app;

    @Before
    public void setUp() {
        app = new MoneyTransferApp();
    }

    @Test
    public void testAccountCreation() {
        app.createAccounts();
        assertEquals(4, app.getAccounts().size());
        for (Account account : app.getAccounts()) {
            assertEquals(10_000, account.getMoney());
        }
    }

    @Test
    public void testTransactions() {
        app.run();
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int totalMoney = app.getAccounts().stream().mapToInt(Account::getMoney).sum();
        assertEquals(40_000, totalMoney);
    }

    @Test
    public void testTransferMoneySameAccount() {
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
