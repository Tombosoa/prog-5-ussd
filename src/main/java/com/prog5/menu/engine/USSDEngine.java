package com.prog5.menu.engine;

import com.prog5.menu.MenuItem;
import com.prog5.menu.MenuFactory;
import com.prog5.menu.action.Action;
import java.util.List;
import java.util.Stack;

public class USSDEngine {
    public final List<Action> mainMenu;
    private final List<Action> secondMenu;
    private final List<Action> mvolaSecondMenu;
    public final Stack<List<Action>> menuStack;
    public List<Action> currentMenu;

    public USSDEngine() {
        this.menuStack = new Stack<>();
        this.mainMenu = MenuFactory.getMainMenu(this);
        this.secondMenu = MenuFactory.getSecondMenu(this);
        this.mvolaSecondMenu = MenuFactory.getMvolaSecondMenu(this);
        this.currentMenu = mainMenu;
    }

    public List<Action> getCurrentMenu() {
        return currentMenu;
    }

    public void displayCurrentMenu() {
        System.out.println("\nYas et Moi");
        for (int i = 0; i < currentMenu.size(); i++) {
            final String title = currentMenu.get(i).getTitle();
            if ("Pejy manaraka".equals(title) || "Page suivante".equals(title)) {
                System.out.println("0  " + title);
            } else if ("Pejy aloha".equals(title) || "Page precedente".equals(title)) {
                System.out.println("00 " + title);
            } else {
                System.out.println((i + 1) + " " + title);
            }
        }
    }

    public void processInput(String input) {
        try {
            if ("0".equals(input)) {
                for (Action action : currentMenu) {
                    if (("Pejy manaraka".equals(action.getTitle()) || "Page suivante".equals(action.getTitle()))
                            && action instanceof MenuItem menuItem) {
                        menuItem.execute();
                        return;
                    }
                }
                System.out.println("Option invalide : 0");
            } else if ("00".equals(input)) {
                for (Action action : currentMenu) {
                    if (("Pejy aloha".equals(action.getTitle()) || "Page precedente".equals(action.getTitle()))
                            && action instanceof MenuItem menuItem) {
                        menuItem.execute();
                        return;
                    }
                }
                goBack();
            } else {
                final int choice = Integer.parseInt(input);
                final int index = choice - 1;
                if (index < 0 || index >= currentMenu.size()) {
                    System.out.println("Choix invalide");
                    return;
                }

                final Action selected = currentMenu.get(index);
                if (selected instanceof MenuItem menuItem) {
                    if (menuItem.hasSubMenus()) {
                        navigateToSubMenu(menuItem);
                    } else {
                        menuItem.execute();
                    }
                } else {
                    selected.execute();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide : veuillez entrer un numéro ou 0 / 00.");
        }
    }

    private void navigateToSubMenu(MenuItem menuItem) {
        menuStack.push(currentMenu);
        currentMenu = menuItem.getSubMenus();
    }

    public void navigateToSecondMenu() {
        menuStack.push(currentMenu);
        currentMenu = secondMenu;
    }

    public void navigateToMvolaSecondMenu() {
        menuStack.push(currentMenu);
        currentMenu = mvolaSecondMenu;
    }

    public void goBack() {
        if (!menuStack.isEmpty()) {
            currentMenu = menuStack.pop();
        } else {
            System.out.println("Vous êtes déjà au menu principal");
        }
    }
}
