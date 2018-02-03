package ru.job4j.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Bank {
    public TreeMap<User, List<Account>> users = new TreeMap<>();

    //добавление пользователя.
    public void addUser(User user) {
//        this.users.put()
    }

    //удаление пользователя.
    public void deleteUser(User user) {

    }

    //добавить счёт пользователю.
    public void addAccountToUser(String passport, Account account) {

    }

    //удалить один счёт пользователя.
    public void deleteAccountFromUser(String passport, Account account) {

    }

    //получить список счетов для пользователя.
    public List<Account> getUserAccounts(String passport) {
        return new ArrayList<>();
    }

    //метод для перечисления денег с одного счёта на другой счёт:
    //если счёт не найден или не хватает денег на счёте srcAccount (с которого переводят) вернет false.
    public boolean transferMoney(String srcPassport,
                                 String srcRequisite,
                                 String destPassport,
                                 String dstRequisite,
                                 double amount) {
        return false;
    }
}
