package ru.job4j.tracker;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

class EditItem implements UserAction {

    @Override
    public int key() {
        return 2;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------Edit Item-----------------");
        String answer = input.ask("Enter ID: ");
        String name = input.ask("New name: ");
        String desc = input.ask("New description: ");
        tracker.replace(answer, new Item(name, desc));
        System.out.println("--------------------------------------");
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Edit item");
    }
}

class DeleteItem implements UserAction {

    @Override
    public int key() {
        return 3;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------Delete Item---------------");
        String answer = input.ask("Enter ID: ");
        tracker.delete(answer);
        System.out.println("--------------------------------------");
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Delete item");
    }
}

public class MenuTracker {

    private Input input;
    private Tracker tracker;
    private UserAction[] actions = new UserAction[7];
    private int menuExit;

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public int[] getMenuRanges() {
        int[] menuRanges = new int[this.actions.length];
        for (int i = 0; i < this.actions.length; i++) {
            if (this.actions[i] != null) {
                menuRanges[i] = i;
            }
        }
        return menuRanges;
    }

    public int getMenuExit() {
        return menuExit;
    }

    public void fillActions() {
        this.actions[0] = new AddItem();
        this.actions[1] = new ShowAllItems();
        this.actions[2] = new EditItem();
        this.actions[3] = new DeleteItem();
        this.actions[4] = new FindId();
        this.actions[5] = new FindName();
        this.actions[6] = new Exit();
    }

    public void select(int key) {
        if (this.actions[key] != null) {
            this.actions[key].execute(this.input, this.tracker);
        }
    }
    
    public void show() {
        System.out.println("Menu:");
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    private class AddItem implements UserAction {

        @Override
        public int key() {
            return 0;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Add new Item--------------");
            String name = input.ask("Name: ");
            String desc = input.ask("Description: ");
            tracker.add(new Item(name, desc));
            System.out.println();

        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Add new Item");
        }
    }

    private class Exit implements UserAction {

        @Override
        public int key() {
            menuExit = 6;
            return 6;
        }

        @Override
        public void execute(Input input, Tracker tracker) {

        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Exit Program");
        }
    }

    private class FindId implements UserAction {

        @Override
        public int key() {
            return 4;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Find Item by ID-----------");
            String answer = input.ask("Enter ID: ");
            Item item = tracker.findById(answer);
            boolean flag = true;
            if (item != null) {
                System.out.println("ID: " + item.getId());
                System.out.println("Name: " + item.getName());
                System.out.println("Description: " + item.getDesc());
                flag = false;
            }
            if (flag) {
                System.out.println("Not found...");
            }
            System.out.println("--------------------------------------");
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Find item by Id");
        }
    }

    private static class ShowAllItems implements UserAction {

        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------All Items-----------------");
            boolean flag = true;
            for (Item iter : tracker.findAll()) {
                System.out.println("ID: " + iter.getId());
                System.out.println("Name: " + iter.getName());
                System.out.println("Description: " + iter.getDesc());
                System.out.println("--------------------------------------");
                flag = false;
            }
            if (flag) {
                System.out.println("Empty...");
                System.out.println("--------------------------------------");
            }
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Show all items");
        }
    }

    private static class FindName implements UserAction {

        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------Find Item by Name---------");
            String answer = input.ask("Enter Item name: ");
            Item[] item = tracker.findByName(answer);
            boolean flag = true;
            for (Item iter: item) {
                System.out.println("ID: " + iter.getId());
                System.out.println("Name: " + iter.getName());
                System.out.println("Description: " + iter.getDesc());
                flag = false;
            }
            if (flag) {
                System.out.println("Not found...");
            }
            System.out.println("--------------------------------------");
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Find items by name");
        }
    }
}
