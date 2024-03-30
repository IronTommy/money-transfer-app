import org.junit.Before;
import org.junit.Test;
import playtox.moneytransfer.model.Account;
import playtox.moneytransfer.service.MoneyTransferApp;

import static org.junit.Assert.*;

public class MoneyTransferAppConcurrencyTest {

    private MoneyTransferApp app;

    @Before
    public void setUp() {
        app = new MoneyTransferApp();
    }

    @Test
    public void testConcurrentTransactions() throws InterruptedException {
        app.createAccounts();
        Thread thread1 = new Thread(() -> app.transferMoney(app.getAccounts().get(0), app.getAccounts().get(1), 5_000));
        Thread thread2 = new Thread(() -> app.transferMoney(app.getAccounts().get(2), app.getAccounts().get(3), 5_000));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(5_000, app.getAccounts().get(0).getMoney());
        assertEquals(15_000, app.getAccounts().get(1).getMoney());
        assertEquals(5_000, app.getAccounts().get(2).getMoney());
        assertEquals(15_000, app.getAccounts().get(3).getMoney());
    }

    @Test
    public void testTransferMoneyConcurrentTransactions() throws InterruptedException {
        app.createAccounts();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                app.transferMoney(app.getAccounts().get(0), app.getAccounts().get(1), 1);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                app.transferMoney(app.getAccounts().get(1), app.getAccounts().get(0), 1);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(10_000, app.getAccounts().get(0).getMoney());
        assertEquals(10_000, app.getAccounts().get(1).getMoney());
    }
}
