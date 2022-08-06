# Testausdokumentti 

## Yksikkötestaus

![Yksikkötestauksen kattavuusraportti](kuvat/testikattavuus.png)

Testauksen ulkopuolelle jätetty IO-luokan tulosta- ja lue-metodit sekä sovelluksen käynnistävä luokka.

## Dijkstran toimivuuden testaus

Dijkstra-algoritmi testataan yksikkötesteissä tällä hetkellä:
1) kartalla, jossa ei ole reittiä lainkaan 
2) 3x3 kartalla, jossa keskellä seinä
3) 100x100 kartalla, jossa ei ole esteitä lainkaan

Jokainen reitti alkaa neliömuotoisen kartan vasemmasta ylälaidasta ja päättyy oikeaan alakulmaan. Jokaisella kartalla ei välttämättä ole reittiä. Reitillisiä karttoja saa luotua todennäköisimmin luotua siten, että valitsee tunnelin maksimipituudeksi kartan sivun pituuden ja tunnelin määrän vähintään kaksinkertaiseksi sivun pituuteen verrattuna (Esim. sivu 100, tunneleita 500  ja tunnelin pituus 100).

Jos reitti löytyy, se tulostetaan käyttöliittymään sekä karttakuvaan että etappi kerrallaan siten, että näkyvillä ovat:
- etapin x- ja y-koordinaatit 
- etäisyys etapin ja aloituspisteen välillä. 
Tämä mahdollistaa silmämääräisen tarkistamisen reitille.

Algoritmia koodatessa tulostin konsoliin jokaisen vaiheen (esim. mitä solmuja on keossa). Lisäksi varmistin muutaman pienen kartan osalta käsin laskemalla, että reitti ja sen pituus ovat oikein.

##  Reittienhaku algoritmien vertailu

Mitä on testattu, miten tämä tehtiin?
Minkälaisilla syötteillä testaus tehtiin (vertailupainotteisissa töissä tärkeää)?
Miten testit voidaan toistaa?
Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa.