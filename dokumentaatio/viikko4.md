
# Viikko 4

## Käytetty työaika:

- 7.8. JumpPointSearchiin tutustumista - 5 h
- 13.8. JumpPointSearchin toteutusta ja debuggausta, muun koodin refaktorointia tukemaan JPS:ää - 10 h


## Mitä olen tehnyt tällä viikolla? / Miten ohjelma on edistynyt?

- Suurin osa ajasta mennyt JumpPointSearchiin, joka on vielä keskeneräinen 

## Mitä opin tällä viikolla / tänään?

- Alan ymmärtää paremmin mikä on Dijkstran ja JPS:n ero - en lukemalla tahdo ymmärtää niin hyvin kuin itse kokeilemalla.
- Pitäisi ehkä keksiä aiempaa parempia tapoja debukkaukseen

## Mikä jäi epäselväksi tai tuottanut vaikeuksia? 

- Olen jäänyt jumittamaan JPS:ään. Ongelma tällä hetkellä on, että en osaa tutkia ns. pakollisia naapureita oikein - esim. 3x3 kartalla, jossa keskellä seinä, algoritmi löytää vain reitin joka etenee horisontaalisesti ja vertikaalisesti eikä osaa hyödyntää diagonaalista siirtymää. Reitti on siis esim. 0.0 -> 0.1 -> 0.2 -> 1.2 -> 2.2. vaikka sen pitäisi olla esim. 0.0 -> 0.1 -> 1.2 -> 2.2. Käytänössä 1.2 ja 2.1 pitäisi saada lisättyä tutkittaviin solmuihin. Lisäksi Reititön kartta-testi päätyy NullPointerExceptioniin.

## Mitä teen seuraavaksi?

- Laitan JPS:n toimimaan ja siistin koodin
- Teen vertailutestit siitä tuottavatko Dijkstra ja JPS:ssä yhtä lyhyen reitin
- Teen vertailutestit siitä kuinka nopeasti Dijkstra ja JPS löytävät lyhyimmän reitin
- Muutan ohjelmaa niin että se arpoo reitin aloitus- ja lopetuspisteet
- Toteutan käyttöliittymään toiminnon, joka ajaa isomman kasan algoritmien vertailutestejä ja tulostaa lopuksi tilaston vertailusta
- Viimeistelen ohjelman, mm. refaktoroin koodia suoraviivaisemmaksi.
- Jos jää aikaa lisään kartat tietokantaan. Vähintäänkin kartoilla pitäisi olla yksilöllinen tunniste.
