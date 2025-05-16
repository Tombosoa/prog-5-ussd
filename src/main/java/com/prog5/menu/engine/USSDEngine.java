package com.prog5.menu.engine;

import com.prog5.menu.MenuItem;
import com.prog5.menu.MenuFactory;
import com.prog5.menu.action.Action;
import java.util.List;
import java.util.Stack;

public class USSDEngine {
    public static final int PAGE_SIZE = 5;

    public final List<Action> mainMenu;
    public final Stack<List<Action>> menuStack;
    public List<Action> currentMenu;
    public int currentPage = 0;

    public USSDEngine() {
        this.menuStack = new Stack<>();
        this.mainMenu = MenuFactory.getMainMenu(this);
        this.currentMenu = mainMenu;
    }

    public void displayCurrentMenu() {
        System.out.println("\nYas et Moi");

        final int startIndex = currentPage * PAGE_SIZE;
        final int endIndex = Math.min(startIndex + PAGE_SIZE, currentMenu.size());

        for (int i = startIndex; i < endIndex; i++) {
            System.out.println((i + 1) + " " + currentMenu.get(i).getTitle());
        }

        if (endIndex < currentMenu.size()) {
            System.out.println("0  Page suivante");
        }

        if (currentPage > 0) {
            System.out.println("00 Page précédente");
        }
    }

    public void processInput(String input) {
        try {
            if ("0".equals(input)) {
                final int maxPage = (int) Math.ceil((double) currentMenu.size() / PAGE_SIZE);
                if ((currentPage + 1) < maxPage) {
                    currentPage++;
                } else {
                    System.out.println("Pas de page suivante.");
                }
            } else if ("00".equals(input)) {
                if (currentPage > 0) {
                    currentPage--;
                } else {
                    System.out.println("Pas de page précédente.");
                }
            } else {
                final int choice = Integer.parseInt(input) - 1; // Numérotation continue
                if (choice < 0 || choice >= currentMenu.size()) {
                    System.out.println("Choix invalide");
                    return;
                }

                final Action selected = currentMenu.get(choice);
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

    public void navigateToSubMenu(MenuItem menuItem) {
        menuStack.push(currentMenu);
        currentMenu = menuItem.getSubMenus();
        currentPage = 0;
    }

    public void goBack() {
        if (!menuStack.isEmpty()) {
            currentMenu = menuStack.pop();
            currentPage = 0;
        } else {
            System.out.println("Vous êtes déjà au menu principal");
        }
    }
}
