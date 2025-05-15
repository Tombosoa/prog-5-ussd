package com.prog5.menu.engine;

import com.prog5.menu.MenuItem;
import com.prog5.menu.MenuFactory;
import com.prog5.menu.action.Action;
import java.util.List;
import java.util.Stack;

public class USSDEngine {
    private final List<Action> mainMenu;
    private final List<Action> secondMenu;
    private final Stack<List<Action>> menuStack;
    private List<Action> currentMenu;

    public USSDEngine() {
        this.menuStack = new Stack<>();
        this.mainMenu = MenuFactory.getMainMenu(this);
        this.secondMenu = MenuFactory.getSecondMenu(this);
        this.currentMenu = mainMenu;
    }

    public List<Action> getCurrentMenu() {
        return currentMenu;
    }

    public void displayCurrentMenu() {
        System.out.println("\nYas et Moi");
        for (int i = 0; i < currentMenu.size(); i++) {
            final String title = currentMenu.get(i).getTitle();
            if ("Pejy manaraka".equals(title)) {
                System.out.println("0  " + title);
            } else if ("Pejy aloha".equals(title)) {
                System.out.println("00 " + title);
            } else {
                System.out.println((i + 1) + " " + title);
            }
        }
    }

    public void processInput(String input) {
        switch (input) {
            case "0" -> {
                if (currentMenu == mainMenu) {
                    navigateToSecondMenu();
                } else {
                    System.out.println("Option invalide : 0 uniquement depuis le menu principal.");
                }
            }
            case "00" -> {
                if (currentMenu == secondMenu) {
                    goBack();
                } else {
                    System.out.println("Option invalide : 00 uniquement depuis le second menu.");
                }
            }
            default -> {
                try {
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
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide : veuillez entrer un numéro ou 0 / 00.");
                }
            }
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

    public void goBack() {
        if (!menuStack.isEmpty()) {
            currentMenu = menuStack.pop();
        } else {
            System.out.println("Vous êtes déjà au menu principal");
        }
    }
}
