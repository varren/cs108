package P1Bank;/* User: Peter  Date: 16.09.13  Time: 16:42 */

public class Account {
    private int id;
    private int balance;
    private int transactions;

    public Account(int id, int balance){
        this.id = id;
        this.balance = balance;
        this.transactions = 0;
    }

    public synchronized void makeTransaction(Transaction t){
        transactions++;
        balance += t.getAmount();
    }

    @Override
    public synchronized String toString(){
        return "acct:" + id + " bal:" + balance + " trans:" + transactions;
    }
}
