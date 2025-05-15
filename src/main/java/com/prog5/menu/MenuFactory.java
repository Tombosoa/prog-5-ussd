package com.prog5.menu;

import com.prog5.menu.action.*;
import com.prog5.menu.engine.USSDEngine;
import java.util.ArrayList;
import java.util.List;

public class MenuFactory {

    public static List<Action> getMainMenu(USSDEngine engine) {
        final List<Action> mainMenu = new ArrayList<>();

        final MenuItem mvola = new MenuItem("MVOLA");
        mvola.addSubMenu(new MenuItem("Envoyer argent", MvolaActions::envoyerArgent));
        mvola.addSubMenu(new MenuItem("Retirer argent", MvolaActions::retirerArgent));
        mvola.addSubMenu(new MenuItem("Consulter solde", MvolaActions::consulterSolde));
        mvola.addSubMenu(new MenuItem("Retour", engine::goBack));

        mainMenu.add(mvola);
        mainMenu.add(new MenuItem("Rappelle moi", RappelActions::rappelleMoi));
        mainMenu.add(new MenuItem("SOS Fahana", SosActions::sosFahana));
        mainMenu.add(new MenuItem("Servisy YAS", YasActions::servisyYas));
        mainMenu.add(new MenuItem("Tolotra", YasActions::tolotra));
        mainMenu.add(new MenuItem("Produits et Divertissement"));
        mainMenu.add(new MenuItem("Banky sy Micro-finances"));
        mainMenu.add(new MenuItem("Pejy manaraka", engine::navigateToSecondMenu));

        return mainMenu;
    }

    public static List<Action> getSecondMenu(USSDEngine engine) {
        final List<Action> secondMenu = new ArrayList<>();
        secondMenu.add(new MenuItem("Mon identité", YasActions::monIdentite));
        secondMenu.add(new MenuItem("Configurer mon mobile", YasActions::configurerMobile));
        secondMenu.add(new MenuItem("Pejy aloha", engine::goBack));
        return secondMenu;
    }
}
