package ru.job4j.exchange;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * Биржевой стакан.
 */
public class Glass {
    private ArrayList<LinkedList<LinkedList<Claim>>> listBid = new ArrayList<>();
    private ArrayList<LinkedList<LinkedList<Claim>>> listAsk = new ArrayList<>();

    public void insertClaim(Claim claim) {
        if (claim.getType() == Claim.Type.ADD) {
            addClaim(claim);
        } else if (claim.getType() == Claim.Type.DEL) {
            delClaim(claim);
        }
    }

    private void addClaim(Claim claim) {
        if (claim.getAction() == Claim.Action.BID) {
            add(claim, listBid);
        } else if (claim.getAction() == Claim.Action.ASK) {
            add(claim, listAsk);
        }
        clear(listBid);
        clear(listAsk);
    }

    private void delClaim(Claim claim) {
        if (claim.getAction() == Claim.Action.BID) {
            del(claim, listBid);
            clear(listBid);
        } else if (claim.getAction() == Claim.Action.ASK) {
            del(claim, listAsk);
            clear(listAsk);
        }
    }

    private void add(Claim claim, ArrayList<LinkedList<LinkedList<Claim>>> list) {
        LinkedList<Claim> listClaim = new LinkedList<>();
        LinkedList<LinkedList<Claim>> queueListClaim = new LinkedList<>();
        if (list.size() == 0) {
            claimAction(claim);
            if (claim.getVolume() == 0) {
                return;
            }
            listClaim.add(claim);
            queueListClaim.add(listClaim);
            list.add(queueListClaim);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).element().element().getBook().equals(claim.getBook())) {
                    queueListClaim = list.get(i);
                    for (int j = 0; j < queueListClaim.size(); j++) {
                        if (queueListClaim.get(j).element().getPrice() == claim.getPrice()) {
                            listClaim = queueListClaim.get(j);
                            listClaim.add(claim);
                            return;
                        }
                    }
                    claimAction(claim);
                    if (claim.getVolume() == 0) {
                        return;
                    }
                    listClaim.add(claim);
                    queueListClaim.add(listClaim);
                    queueListClaim.sort(new SortedByLow());
                    return;
                }
            }
            claimAction(claim);
            if (claim.getVolume() == 0) {
                return;
            }
            listClaim.add(claim);
            queueListClaim.add(listClaim);
            list.add(queueListClaim);
        }
    }

    private void claimAction(Claim claim) {
        ArrayList<LinkedList<LinkedList<Claim>>> list;
        if (claim.getAction() == Claim.Action.BID) {
            list = listAsk;
        } else {
            list = listBid;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).element().element().getBook().equals(claim.getBook())) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    LinkedList<Claim> listClaim = list.get(i).get(j);
                    if ((claim.getAction() == Claim.Action.BID
                            && claim.getPrice() >= listClaim.element().getPrice())
                            || (claim.getAction() == Claim.Action.ASK
                            && claim.getPrice() <= listClaim.element().getPrice())) {
                        for (int k = 0; k < listClaim.size(); k++) {
                            Claim claim1Bid = listClaim.get(k);
                            deal(claim, claim1Bid);
                            if (claim1Bid.getVolume() == 0) {
                                listClaim.remove(k);
                                k--;
                            }
                            if (claim.getVolume() == 0) {
                                return;
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    private void deal(Claim claimOne, Claim claimTwo) {
        String textClaimDone;
        String textClaimTwo;
        float totalClaimDone;
        float totalClaimTwo;
        int volumeBalance;
        int volumeDeal;
        float price = claimOne.getPrice() > claimTwo.getPrice() ? claimTwo.getPrice() : claimOne.getPrice();
        if (claimOne.getVolume() >= claimTwo.getVolume()) {
            textClaimTwo = claimOne.toString();
            totalClaimTwo = price * claimTwo.getVolume();
            claimOne.setVolume(claimOne.getVolume() - claimTwo.getVolume());
            volumeDeal = claimTwo.getVolume();
            volumeBalance = claimOne.getVolume();
            textClaimDone = claimTwo.toString();
            totalClaimDone = price * claimTwo.getVolume();
            claimTwo.setVolume(0);
        } else {
            textClaimTwo = claimTwo.toString();
            totalClaimTwo = price * claimOne.getVolume();
            claimTwo.setVolume(claimTwo.getVolume() - claimOne.getVolume());
            volumeDeal = claimOne.getVolume();
            volumeBalance = claimTwo.getVolume();
            textClaimDone = claimOne.toString();
            totalClaimDone = price * claimOne.getVolume();
            claimOne.setVolume(0);
        }
        String text = volumeBalance > 0 ? String.format("(частично : %d шт.) ", volumeDeal) : "";
        System.out.println(String.format("Заяка: (%s) %sисполнена по цене %.2f объем = %.2f",
                textClaimTwo, text, price, totalClaimTwo));
        System.out.println(String.format("Заяка: (%s) исполнена по цене %.2f объем = %.2f",
                textClaimDone, price, totalClaimDone));
    }


    private void del(Claim claim, ArrayList<LinkedList<LinkedList<Claim>>> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                LinkedList<Claim> listClaim = list.get(i).get(j);
                for (int k = 0; k < listClaim.size(); k++) {
                    Claim findClime = listClaim.get(k);
                    if (findClime.getBook().equals(claim.getBook())
                            && findClime.getPrice() == claim.getPrice()
                            && findClime.getVolume() == claim.getVolume()
                            && findClime.getAction() == claim.getAction()) {
                        String text = findClime.toString();
                        System.out.println(String.format("Заявка: (%s) удалена.", text));
                        listClaim.remove(k);
                    }
                }
            }
        }
    }

    private void clear(ArrayList<LinkedList<LinkedList<Claim>>> list) {
        if (list == null) {
            return;
        }
        int indexBList = 0;
        while (indexBList < list.size()) {
            if (list.get(indexBList).size() == 0) {
                list.remove(indexBList);
                indexBList--;
            }
            int indexQList = 0;
            boolean change = false;
            if (indexBList >= 0 && list.get(indexBList) != null) {
                while (indexQList < list.get(indexBList).size()) {
                    if (list.get(indexBList).get(indexQList).size() == 0) {
                        list.get(indexBList).remove(indexQList);
                        indexQList--;
                        change = true;
                    }
                    indexQList++;
                }
            }
            if (!change) {
                indexBList++;
            }
        }
        if (list.size() == 0) {
            list = null;
            return;
        }
    }

    public void printGlass() {
        String currentBook;
        for (LinkedList<LinkedList<Claim>> queueListClaimBid : listBid) {
            currentBook = queueListClaimBid.element().element().getBook();
            System.out.println(currentBook);
            System.out.println(String.format("%10s %7s %10s", "Покупка", "Цена", "Продажа"));
            for (LinkedList<Claim> listClaimBid: queueListClaimBid) {
                int sum = 0;
                for (Claim claimBid : listClaimBid) {
                    sum += claimBid.getVolume();
                }
                System.out.println(String.format("%7d %11.2f", sum, listClaimBid.element().getPrice()));
            }
            for (LinkedList<LinkedList<Claim>> queueListClaimAsk : listAsk) {
                if (queueListClaimAsk.element().element().getBook().equals(currentBook)) {
                    for (LinkedList<Claim> listClaimAsk: queueListClaimAsk) {
                        int sum = 0;
                        for (Claim claimAsk : listClaimAsk) {
                            sum += claimAsk.getVolume();
                        }
                        System.out.println(String.format("%19.2f %5d", listClaimAsk.element().getPrice(), sum));
                    }
                }
            }
        }
    }

    public void seeBid() {
        see(listBid);
    }

    public void seeAsk() {
        see(listAsk);
    }


    private void see(ArrayList<LinkedList<LinkedList<Claim>>> list) {
        for (LinkedList<LinkedList<Claim>> queueListClaim : list) {
            for (LinkedList<Claim> listClaim : queueListClaim) {
                for (Claim claim : listClaim) {
                    System.out.println(claim.toString());
                }
            }
        }
    }

    public static void main(String[] args) {
        Glass glass = new Glass();
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 135, 10));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 131, 7));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 136, 2));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 131, 1));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 129, 22));
        glass.insertClaim(new Claim("LUK", Claim.Type.ADD, Claim.Action.BID, 1305, 5));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 135, 7));
        glass.insertClaim(new Claim("LUK", Claim.Type.ADD, Claim.Action.BID, 1300, 2));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 150, 1));
        glass.insertClaim(new Claim("GAZ", Claim.Type.DEL, Claim.Action.ASK, 150, 1));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 150, 5));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 147, 7));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 138, 9));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 139, 10));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 120, 5));
        glass.insertClaim(new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 139, 1));
//        glass.seeBid();
//        glass.seeAsk();
        glass.printGlass();
    }
}
