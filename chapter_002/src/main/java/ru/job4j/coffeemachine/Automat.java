package ru.job4j.coffeemachine;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Automat {
    public int[] changes(int value, int price) throws NotEnoughMoney {
        int balance = value - price;
        if (balance < 0) {
            throw new NotEnoughMoney("Not enough money.");
        }
        int[] massBalance = {};
        do {
            if (balance - 10 >= 0) {
                balance -= 10;
                massBalance = massAdd(massBalance, 10);
            } else if (balance - 5 >= 0) {
                balance -= 5;
                massBalance = massAdd(massBalance, 5);
            } else if (balance - 2 >= 0) {
                balance -= 2;
                massBalance = massAdd(massBalance, 2);
            } else if (balance - 1 >= 0) {
                balance -= 1;
                massBalance = massAdd(massBalance, 1);
            } else if (balance == 0) {
                massBalance = massAdd(massBalance, 0);
            }
        } while (balance != 0);
        return massBalance;
    }

    private int[] massAdd(int[] massBalance, int newItem) {
        int[] newMassBalance = new int[massBalance.length + 1];
        for (int i = 0; i < massBalance.length; i++) {
            newMassBalance[i] = massBalance[i];
        }
        newMassBalance[massBalance.length] = newItem;
        return newMassBalance;
    }
}
