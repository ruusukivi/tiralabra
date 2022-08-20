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

## Jump Point Searchin toimivuuden testaus

Jump Point Searchin toimivuuttaa olen testannut yksikkötesteillä ja käyttöliittymässä pienillä kartoilla.

Algoritmi ei toimi vielä oikein. Olen koittanut jäljittää virhekohtia lisäämällä paljon tulosteita koodin sekaan.

Algoritmi näyttää myös olevan huomattavan hidas Dijkstraan verrattuna, mikä todistaa, ettei se toimi vielä oikein siltäkään osin.

Sen sijaan siinä huomaan merkittävän ja oikean suuntaisen eron, että kekoon JPS lisää huomattavasti vähemmän solmuja kuin Dijkstra.

##  Algoritmien vertailu

Algoritmien vertailua varten on luotu Vertailu-luokka, joka tällä hetkellä luo neljä erilaista karttaa, joita vasten sitten algoritmien toimintaa verrataan. 

Tällä hetkellä vertailuja tehdään seuraavilla parametreilla luoduilla kartoilla:
- sivu: 1000, tunneleita: 10000, tunnelin maksimipituus: 500, nimi: "1000-1000-500");
- sivu: 1000, tunneleita: 1000, tunnelin maksimipituus: 500, nimi: "1000-1000-500");
- sivu: 1000, tunneleita: 100, tunnelin maksimipituus: 500, nimi: "1000-1000-500");
- sivu: 1000, tunneleita: 10, tunnelin maksimipituus: 500, nimi: "1000-10-500");

Parametrit valittu niin että mukana on todennäköisesti karttoja, joissa reittiä ei ole.

Tuloksista näkee ettei JPS vielä toimi. Sen pitäisi olla Dijkstraa nopeampi ja löydettyjen reittien pituus pitäisi olla sama.

![Vertailutuloksia](kuvat/vertailu-vko5.png)
