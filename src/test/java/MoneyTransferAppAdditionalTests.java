import org.junit.Before;
import org.junit.Test;
import playtox.moneytransfer.model.Account;
import playtox.moneytransfer.service.MoneyTransferApp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class MoneyTransferAppAdditionalTests {

    private MoneyTransferApp app;

    @Before
    public void setUp() {
        app = new MoneyTransferApp();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferMoneyNegativeAmount() {
        app.createAccounts();
        Account from = app.getAccounts().get(0);
        Account to = app.getAccounts().get(1);
        app.transferMoney(from, to, -500);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferMoneySameAccount() {
        app.createAccounts();
        Account account = app.getAccounts().get(0);
        app.transferMoney(account, account, 500);
    }

    @Test
    public void testLogContainsTransactions() {
        app.createAccounts();
        app.run();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("Log contains transaction", isTransactionLogged());
    }

    private static boolean isTransactionLogged() {
        try (BufferedReader reader = new BufferedReader(new FileReader("logs/app.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Transaction")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
