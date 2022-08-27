
# Viikko 4

## Käytetty työaika:

- 21.8. JumpPointSearchiin debuggausta ja refaktorointia - 8 h
- 22.8. Läpikäynti Hannun kanssa - 2 h
- 27.8. JumpPointSearchin  debuggausta ja refaktorointia + vertausarvioiden läpikäynti - 5 h

## Mitä olen tehnyt tällä viikolla? / Miten ohjelma on edistynyt?

- Suurin osa ajasta mennyt JumpPointSearchiin, joka ei vieläkään valitettavasti toimi oikein.


## Mitä opin tällä viikolla / tänään?
- Löysin ainakin yhden selkeän virheen toteutuksestani: olin tulkinnut lähdettä väärin ja päätynyt lisäämään kekoon hyppypisteen naapurin, en itse hyppypistettä.
- JPS:n hitauden syyksi paljastui debuggaus-tulostukset, joita olin viljellyt runsaasti - tulostus on hidas operaatio.

## Mikä jäi epäselväksi tai tuottanut vaikeuksia? 

- 

## Mitä teen seuraavaksi?

- Laitan JPS:n toimimaan ja siistin koodin <- algoritmi ei vieläkään toimi oikein
- Teen vertailutestit siitä tuottavatko Dijkstra ja JPS:ssä yhtä lyhyen reitin <- tästä ensimmäinen versio tehty
- Teen vertailutestit siitä kuinka nopeasti Dijkstra ja JPS löytävät lyhyimmän reitin <- tästä ensimmäinen versio tehty
- Muutan ohjelmaa niin että se arpoo reitin aloitus- ja lopetuspisteet
- Toteutan käyttöliittymään toiminnon, joka ajaa isomman kasan algoritmien vertailutestejä ja tulostaa lopuksi tilaston vertailusta
- Viimeistelen ohjelman, mm. refaktoroin koodia suoraviivaisemmaksi.
- Jos jää aikaa lisään kartat tietokantaan. Vähintäänkin kartoilla pitäisi olla yksilöllinen tunniste.
