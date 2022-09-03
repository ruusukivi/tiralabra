# Käyttöohje

## Sovelluksen käynnistys ja testaus

### Sovellusprojektin käynnistys jar-tiedostosta

Lataa jar-tiedosto omalle koneellesi Releases-osiosta:
https://github.com/ruusukivi/tiralabra/releases/

Käynnistä sovellus sen jälkeen komentoriviltä komennolla: 

    java -jar reitinhakusovellus.jar

### Sovellusprojektin käynnistys komentoriviltä

Kloonaa tai forkkaa projekti ensin omalle koneellesi. Tämän jälkeen projektin voi käynnistää seuraavalla komennolla:

    ./gradlew -q --console plain run

### Sovelluksen yksikkötestien ajo

    ./gradlew clean test


- Jos haluat ajaa myös Algoritmit-testin, poista siitä ensin Ignore-annotaatio. Kyseisen testin ajo voi kestää jopa 10 minuuttia.

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

![Ohjelman toiminnot](kuvat/toiminnot.png)

Sovelluksessa tällä hetkellä kolme toimintoa:
1) Uuden kartan luonti ja tulostus
2) Session aikana luotujen karttojen uudelleen tulostus
3) Algoritmien vertailu

Uuden kartan luonti luo karttapohjan RandomWalk-algoritmia hyödyntäen ja etsii kartalta lyhyimmän reitin vasemmasta yläkulmasta oikeaan alakulmaan Dijkstra- ja Jump Point Search -algoritmeilla. Uuden kartan luonti generoi omat Verkko-oliot Dijkstralle ja Jump Point Searchille. Tulostettaessa aiemmin luotua karttaa pitää valita kumman version haluaa nähdä.

Algoritmien vertailussa voi valita haluaako, tulostaa ruudulta helposti luettavan suppean aineiston vai laajemman csv-muotoisen aineiston. Suppea aineisto koostuu kolmen eri kartan vertailusta. Kartat eroavat toisistaan polkujen määrässä. Laaja aineisto luo 1000 kartaa jokaisesta vertailtavasta karttatyypistä. Laaja aineisto on tarkoitettu analysoitavaksi esim. excelissä. Sen tulostus on tarkoitus myöhemmin siirtää tiedostoon, jotta siirto on helpompaa.
