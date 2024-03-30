package playtox.moneytransfer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import playtox.moneytransfer.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoneyTransferApp {

    private static final Logger log = LogManager.getLogger(MoneyTransferApp.class);
    private final List<Account> accounts = new ArrayList<>();
    private static final int MAX_SLEEP_TIME = 2_000;
    private static final int MIN_SLEEP_TIME = 1_000;
    private static final int NUM_TRANSACTIONS = 30;
    private static final int NUM_ACCOUNTS = 4;
    private static final int NUM_THREADS = 2;
    private final Random random = new Random();
    private final Object lock = new Object();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void createAccounts() {
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts.add(new Account("acc" + i, 10_000));
        }
    }

    public void run() {
        createAccounts();
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(this::executeTransactions).start();
        }
    }

    private void executeTransactions() {
        int transactions = 0;

        while (transactions < NUM_TRANSACTIONS) {
            try {
                int sleepTime = MIN_SLEEP_TIME + random.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME + 1);
                System.out.println("Sleep time: " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.error("Thread interrupted", e);
            }

            Account from = selectRandomAccount();
            Account to = selectRandomAccount(from);

            int amount = generateRandomAmount(from);
            transferMoney(from, to, amount);

            transactions++;
        }
    }

    public Account selectRandomAccount() {
        if (accounts.isEmpty()) {
            throw new IllegalStateException("Cannot select random account from an empty list.");
        }
        return accounts.get(random.nextInt(accounts.size()));
    }


    public Account selectRandomAccount(Account excludedAccount) {
        int toIndex;
        do {
            toIndex = random.nextInt(accounts.size());
        } while (accounts.get(toIndex) == excludedAccount);
        return accounts.get(toIndex);
    }

    public int generateRandomAmount(Account account) {
        int maxAmount = account.getMoney();
        return maxAmount > 0 ? random.nextInt(maxAmount) + 1 : 0;
    }

    public void transferMoney(Account from, Account to, int amount) {
        synchronized (lock) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            if (from == to) {
                throw new IllegalArgumentException("Cannot transfer money from and to the same account");
            }
            if (from.getMoney() >= amount) {
                from.setMoney(from.getMoney() - amount);
                to.setMoney(to.getMoney() + amount);
                log.info("Transaction: {} -> {}, Amount: {}", from.getId(), to.getId(), amount);
            } else {
                log.warn("Insufficient funds in account: {}", from.getId());
            }
        }
    }
}
