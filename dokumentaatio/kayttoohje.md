# Käyttöohje

## Sovelluksen käynnistys ja testaus

### Sovellusprojektin käynnistys jar-tiedostosta

- Lataa jar-tiedosto omalle koneellesi. Käynnistä sovellus sen jälkeen komentoriviltä komennolla. 

    java -jar reitinhakusovellus.jar

### Sovellusprojektin käynnistys komentoriviltä

- Kloonaa tai forkkaa projekti ensin omalle koneellesi. Tämän jälkeen projektin voi käynnistää seuraavalla komennolla:

    ./gradlew run

### Sovelluksen yksikkötestien ajo

    ./gradlew clean test

### Sovelluksen koodin tarkistus

    ./gradlew checkstyleMain

- Raportit löytyvät tämän jälkeen projektin hakemistosta: /tiralabra/reitinhakusovellus/build/reports/checkstyle/

### Sovelluksen testikattavuusraportin päivitys

    ./gradlew jacocoTestReport

- Raportit löytyvät tämän jälkeen projektin hakemistosta: /tiralabra/reitinhakusovellus/build/reports/jacoco/test/

### Sovelluksen javadoc-dokumentaation tarkistaminen ja päivitys

    ./gradlew javadoc

- Raportit löytyvät tämän jälkeen projektin hakemistosta: /tiralabra/reitinhakusovellus/build/docs/javadoc/

## Sovelluksen käyttö

Sovelluksessa tällä hetkellä kaksi toimintoa:
1) Uuden kartan luonti ja tulostus
2) Session aikana luotujen karttojen uudelleen tulostus