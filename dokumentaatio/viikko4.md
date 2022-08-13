
# Viikko 4

## Käytetty työaika:

- 7.8. JumpPointSearchiin tutustumista - 5 h
- 13.8. JumpPointSearchin totetusta ja debukkausta, muun koodin refaktorointi tukemaan JPS:ää - 10 h


## Mitä olen tehnyt tällä viikolla?
## Miten ohjelma on edistynyt?

- Suurin osa ajasta mennyt JumpPointSearchiin, joka on vielä keskeneräinen 

## Mitä opin tällä viikolla / tänään?

- Alan ymmärtää paremmin mikä on Dijkstran ja JPS:n ero - en tehdä lukemalla ymmärtää niin hyvin kuin itse kokeilemalla.


## Mikä jäi epäselväksi tai tuottanut vaikeuksia? 

- Olen jäänyt jumittamaan JPS:ään. Ongelma tällä hetkellä on, että en osaa tutkia ns. pakollisia naapureita oikein - esim. 3x3 kartalla jossa keskellä seinä algoritmi löytää vain reitin joka etenee horisontaalisesti ja vertikaalisesti eikä osaa hyödyntää diagonaalista siirtymää. Reitti on siis esim. 0.0 -> 0.1 -> 0.2 -> 1.2 -> 2.2. vaikka sen pitäisi olla esim. 0.0 -> 0.1 -> 1.2 -> 2.2. Käytänössä 1.2 pitäisi saada lisättyä tutkittaviin solmuihin. Lisäksi Reitiön kartta-testi päätyy NullPointerExceptioniin.


## Mitä teen seuraavaksi?

- Laitan JPS:n toimimaan ja siistin koodin
- Teen vertailutestit siitä tuottavatko Dijkstra ja JPS:ssä yhtä lyhyen reitin
- Teen vertailutestit siitä kuinka nopeista Dijkstra ja JPS löytävät lyhyen reitin
- Muutan ohjelmaa niin että se arpoo reitin aloitus ja lopetus pisteet
- Toteutan käyttöliittymään toiminnon joka ajaa isomman kasan algoritmien vertailutestejä ja tulostaa lopuksi tilaston vertailusta
- Viimeistelen ohjelman, mm. refaktoroin koodia suoraviivaisemmaksi.
