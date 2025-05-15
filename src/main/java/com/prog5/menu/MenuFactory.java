package com.prog5.menu;

import com.prog5.menu.action.*;
import com.prog5.menu.engine.USSDEngine;
import java.util.ArrayList;
import java.util.List;

public class MenuFactory {

    public static List<Action> getMainMenu(USSDEngine engine) {
        final List<Action> mainMenu = new ArrayList<>();

        final MenuItem mvola = new MenuItem("MVOLA");
        mvola.addSubMenu(new MenuItem("Acheter Credit ou Offre Yas", MvolaActions::acheterCreditOuOffre));
        mvola.addSubMenu(new MenuItem("Transferer Argent(vers toute destination)", MvolaActions::transfererArgent));
        mvola.addSubMenu(new MenuItem("MVola Credit ou Epargne", MvolaActions::mvolaCredit));
        mvola.addSubMenu(new MenuItem("Retrait d'argent", MvolaActions::retirerArgent));
        mvola.addSubMenu(new MenuItem("Page suivante", engine::navigateToMvolaSecondMenu));

        mainMenu.add(mvola);
        mainMenu.add(new MenuItem("Rappelle moi", RappelActions::rappelleMoi));
        mainMenu.add(new MenuItem("SOS Fahana", SosActions::sosFahana));
        mainMenu.add(new MenuItem("Servisy YAS", YasActions::servisyYas));
        mainMenu.add(new MenuItem("Tolotra", YasActions::tolotra));
        mainMenu.add(new MenuItem("Produits et Divertissement", () -> {}));
        mainMenu.add(new MenuItem("Banky sy Micro-finances", () -> {}));
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

    public static List<Action> getMvolaSecondMenu(USSDEngine engine){
        final List<Action> mvolaSecondMenu = new ArrayList<>();
        mvolaSecondMenu.add(new MenuItem("Mon Compte", MvolaActions::monCompte));
        mvolaSecondMenu.add(new MenuItem("Recevoir de l'argent", MvolaActions::recevoirArgent));
        mvolaSecondMenu.add(new MenuItem("Banques et Micro-Finances", MvolaActions::banquesEtMicroFinances));
        mvolaSecondMenu.add(new MenuItem("Page precedente", engine::goBack));
        mvolaSecondMenu.add(new MenuItem("**Menu principal", () -> {
            engine.menuStack.clear();
            engine.currentMenu = engine.mainMenu;
        }));
        return mvolaSecondMenu;
    }
}
