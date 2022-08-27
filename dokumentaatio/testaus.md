# Testausdokumentti 

## Yksikkötestaus

![Yksikkötestauksen kattavuusraportti](kuvat/testikattavuus-vko6.png)

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

## Jump Point Searchin toimivuuden testaus

Jump Point Searchin toimivuutta olen testannut yksikkötesteillä ja käyttöliittymässä pienillä kartoilla.

JPS-algoritmi testataan yksikkötesteissä tällä hetkellä:
1) kartalla, jossa ei ole reittiä lainkaan 
2) 3x3 kartalla, jossa keskellä seinä
3) 100x100 kartalla, jossa ei ole esteitä lainkaan

Virheitä olen jäljittänyt debuggaustulosteilla.

Algoritmi ei toimi vielä oikein. 

##  Algoritmien vertailu

Algoritmien vertailua varten on luotu Vertailu-luokka, joka tällä hetkellä luo neljä erilaista karttaa, joita vasten sitten algoritmien toimintaa verrataan. 

Tällä hetkellä vertailuja tehdään seuraavilla parametreilla luoduilla kartoilla:

Tällä hetkellä vertailuja tehdään seuraavilla parametreilla luoduilla kartoilla:
- sivu: 1000, polkuja: 10000, polun maksimipituus: 500, nimi: "1000-10000-500");
- sivu: 1000, polkuja: 1000, polun maksimipituus: 500, nimi: "1000-1000-500");
- sivu: 1000, polkuja: 100, polun maksimipituus: 500, nimi: "1000-100-500");

Parametrit valittu niin, että mukana on todennäköisesti myös kartta, joissa reittiä ei ole.

![Vertailutuloksia](kuvat/vertailu-vko6.png)
