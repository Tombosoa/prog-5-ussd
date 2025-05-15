package com.prog5.menu;

import com.prog5.menu.action.Action;

import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Action {
    private final String title;
    private final Runnable action;
    private final List<Action> subMenus;

    public MenuItem(String title) {
        this(title, null);
    }

    public MenuItem(String title, Runnable action) {
        this.title = title;
        this.action = action;
        this.subMenus = new ArrayList<>();
    }

    public void addSubMenu(MenuItem item) {
        subMenus.add(item);
    }

    public boolean hasSubMenus() {
        return !subMenus.isEmpty();
    }

    public List<Action> getSubMenus() {
        return subMenus;
    }

    @Override
    public void execute() {
        if (action != null) {
            action.run();
        } else {
            System.out.println("Aucune action définie pour " + title);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }
}
