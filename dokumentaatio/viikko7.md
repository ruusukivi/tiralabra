
# Viikko 5

## Käytetty työaika:

- 28.8. JumpPointSearchiin debuggausta ja refaktorointia - 8 h

## Mitä olen tehnyt tällä viikolla? / Miten ohjelma on edistynyt?

- Suurin osa ajasta on edelleen mennyt JumpPointSearchiin. Löysin yhden ajatusvirheen, jonka korjaaminen auttoi. Pystyn nyt tulostamaan jonkinlaisen version reitistä kartalle ja sitä kautta on helpompi nyt hahmottaa, mikä mättää. Kuten arvelin edeltäjän valinnassa on pulmia. Välillä käy niin että kaksi solmua ovat toistensa edeltäjiä ristiin, jolloin reitinpiirto jää luuppuun. Välillä vaihtoehtoiset reitit jotenkin hassusti sekottuvat toisiinsa, jolloin lyhyt reitti on liian pitkä verrattuna dijkstraan.

Esimerkkinä yksi ongelmatilanne, jossa reitin pituus kuitenkin oikein.

### Kartan nimi: 6-dijkstra, koko 6*6

```
R . . x x x 
R . . x x x 
. R . x x x 
. x R x x x 
. . . R R x 
. . . . . R 

Reitti päättyy pisteeseen 5,5 ja etäisyys alusta on: 7.65685424949238
Edellinen piste reitillä 4,4 ja etäisyys alusta on: 6.242640687119285
Edellinen piste reitillä 4,3 ja etäisyys alusta on: 5.242640687119285
Edellinen piste reitillä 3,2 ja etäisyys alusta on: 3.82842712474619
Edellinen piste reitillä 2,1 ja etäisyys alusta on: 2.414213562373095
Edellinen piste reitillä 1,0 ja etäisyys alusta on: 1.0
Reitti alkaa pisteestä 0.0
``` 

### Kartan nimi: 6-jps, koko 6*6

```
R . . x x x 
. . . x x x 
. . R x x x 
. x R x x x 
. . . R . x 
. . . . R R 

Reitti päättyy pisteeseen 5,5 ja etäisyys alusta on: 7.656854249492381
Edellinen piste reitillä 5,4 ja etäisyys alusta on: 7.0710678118654755
Edellinen piste reitillä 4,3 ja etäisyys alusta on: 5.656854249492381
Edellinen piste reitillä 3,2 ja etäisyys alusta on: 4.242640687119286
Edellinen piste reitillä 2,2 ja etäisyys alusta on: 2.8284271247461903
Edellinen piste reitillä 3,2 ja etäisyys alusta on: 4.242640687119286  
Edellinen piste reitillä 2,2 ja etäisyys alusta on: 2.8284271247461903
Edellinen piste reitillä 3,2 ja etäisyys alusta on: 4.242640687119286
Edellinen piste reitillä 2,2 ja etäisyys alusta on: 2.8284271247461903 
Edellinen piste reitillä 3,2 ja etäisyys alusta on: 4.242640687119286
Edellinen piste reitillä 2,2 ja etäisyys alusta on: 2.8284271247461903
Edellinen piste reitillä 3,2 ja etäisyys alusta on: 4.242640687119286
Reitti alkaa pisteestä 0.0
``` 

## Mitä opin tällä viikolla / tänään?
- Löysin ainakin yhden selkeän virheen toteutuksestani: olin tulkinnut lähdettä väärin ja päätynyt lisäämään kekoon hyppypisteen naapurin, en itse hyppypistettä.
- JPS:n hitauden syyksi paljastui debuggaus-tulostukset, joita olin viljellyt runsaasti - tulostus on hidas operaatio.

## Mikä jäi epäselväksi tai tuottanut vaikeuksia? 

- No, JPS on tuottanut tälläkin viikolla vaikeuksia, mutta olen päässyt eteenpäin :D
- Toinen vertaisarvijoija ei ollut saanut projektiani käyntiin ensin. VSCoden kautta se oli käynnistynyt. Kun arvioin hänen projektiaan sain ilmoituksen UTF8-merkkikonversio ongelmista. Jollain tavalla ympäristömme eivät ole yhteensopivat. En ole ihan varma onko projektissani tältä osin jotain korjattavaa vai ei.

## Mitä teen seuraavaksi?

- Laitan JPS:n toimimaan ja siistin koodin + lisään JPS:lle muutamia yksikkötestejä (työn alla)
- Teen vertailutestit siitä tuottavatko Dijkstra ja JPS:ssä yhtä lyhyen reitin (työn alla)
- Teen vertailutestit siitä kuinka nopeasti Dijkstra ja JPS löytävät lyhyimmän reitin (työn alla)
- Toteutan käyttöliittymään toiminnon, joka ajaa isomman kasan algoritmien vertailutestejä ja tulostaa lopuksi tilaston vertailusta. (työn alla)
- Valmistaudun demoon
- Muutan ohjelmaa niin että se arpoo reitin aloitus- ja lopetuspisteet (en ehkä ehdi tätä muutosta tehdä)
- Viimeistelen ohjelman, mm. refaktoroin koodia suoraviivaisemmaksi.
- Jos jää aikaa lisään kartat tietokantaan. Vähintäänkin kartoilla pitäisi olla yksilöllinen tunniste.
