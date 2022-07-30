
# Viikko 2

## Käytetty työaika:

- 25.7. Random Walk -algoritmin toteutus ja konsolipohjajinen ui - 7 h
- 29.7. Gradlen konfigurointien säätöä, tietorakenteiden toteutusta, refaktorointia - 6 h
- 30.7. Refaktorointia, Dijkstran algoritmia alkuun - x h

## Mitä olen tehnyt tällä viikolla?

- Toteutin karttojen piirtämisen Random Walk -algoritmilla
- Aloitin Dijkstra-algoritmin toteutuksen

## Miten ohjelma on edistynyt?

- Karttojen generointi toteutettu
- Perustietorakenteet eli Verkko ja Solmu toteutettu
- Refaktoroitu käyttöliittymää ja karttojen tulostusta -> yksittäiset kartat jäävät nyt talteen session ajaksi.
- Testien kirjoitus saatu alkuun, testikattavuus ei vielä kovin hyvä.


## Mitä opin tällä viikolla / tänään?

- JPS-algoritmi sopii parhaiten kartoille, jotka kuvaavat ihmisen rakentamaa ympäristöä, ei satunnaisille sokkeloisille kartoille. Algoritmin parhaat ominaisuudet eivät pääse oikeuksiinsa myöskään kartoissa, joissa esteitä ei juurikaan ole.

- Kokeilujen jälkeen arvelisin, että Random Walk -algoritmillä saanee tuotettua algoritmille sopivia karttoja, kunhan tunneleita on riittävän paljon ja tunnelin maksimipituus on lähellä kartan sivun pituutta.

![Esimerkki kartasta, jossa tunnelin maksimipituus sama kuin sivun pituus](kuvat/kartta1.png)

- Olen tutkinut erilaisia toteutuksia Dijkstrasta


## Mikä jäi epäselväksi tai tuottanut vaikeuksia? 

- Viime viikon raportista jäi kirjaamatta, että monta tuntia tuhlaantui kun ihmettelin, miksei syötteen luku toimi. Gradle-asetusista puuttui standardInput = System.in - määrittely.

- Kysymys: riittääkö konsolipohjainen käyttöliittymä vai odotetaanko hienompaa esitystapaa?

- Ohjelmiston rakenteen suunnittelu tuntuu vähän jumittavan. Arvoin esimerkiksi pitkään teenkö kaarille oman olion vai ei. Koodin laatu mietityttää. 

## Mitä teen seuraavaksi?

- Ensin Dijkstran toteutus loppuun, sitten reitin tulostus kartalle näkyville ja sen jälkeen jUnit-testit ajantasalle.