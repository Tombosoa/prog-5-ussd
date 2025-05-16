# prog-5-ussd

Ce projet est une simulation d'une interface USSD en Java. 

## 🔡 Convention de nommage

| Élément         | Convention           | Exemple                      |
|----------------|----------------------|------------------------------|
| Classes        | `PascalCase`         | `USSDEngine`, `MenuFactory` |
| Méthodes       | `camelCase`          | `navigateToSecondMenu()`    |
| Variables      | `camelCase`          | `currentMenu`, `menuStack`  |
| Constantes     | `UPPER_SNAKE_CASE`   | `MAX_SIZE`                  |


---
### 📄 Configuration : `checkstyle.xml`

Le fichier de configuration Checkstyle se trouve à la racine du projet`.  
Voici les règles principales appliquées :

```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
    "https://checkstyle.org/dtds/configuration_1_3.dtd">

<module name="Checker">
    <module name="TreeWalker">
        <module name="WhitespaceAfter"/>
        <module name="FinalLocalVariable"/>
        <module name="NeedBraces"/>
    </module>

    <module name="LineLength">
        <property name="max" value="120"/>
    </module>
</module>
