package P1Bank;/* User: Peter  Date: 16.09.13  Time: 16:43 */

public class Transaction {
    private int from;
    private int to;
    private int amount;

    public Transaction(int from,int to, int amount){
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
    public int getAmount(){
        return amount;
    }
    public int getTo(){
        return to;
    }
    public int getFrom(){
        return from;
    }
}
