
# Viikko 4

## Käytetty työaika:

- 14.8. JumpPointSearchiin debuggausta ja refaktorointia - 6 h
- 18.8. JumpPointSearchiin debuggausta ja refaktorointia - 3 h
- 19.8. JumpPointSearchin  debuggausta ja refaktorointia - 6 h
- 20.8. Eri versioita JumpPointSearchistä

## Mitä olen tehnyt tällä viikolla? / Miten ohjelma on edistynyt?

- Suurin osa ajasta mennyt JumpPointSearchiin, joka ei vieläkään valitettavasti toimi oikein.
- Tein käyttöliittymästä ajettavan vertailutoiminnon, joka vertailee löydettyjen reittien pituuksia, hakujen kestoa ja kekoon vietyjen solmujen määrää

## Mitä opin tällä viikolla / tänään?
- Olen kirjoittanut JPS:stä monta eri versiota. Siinä tein fiksusti, että vein parhaiten toimivan version versionhallintaan. Siinä sen sijaan tein monta kertaa virheen, että refaktorointi ohjelmaa liian pitkälle ennen kuin testasin -> joudun monta kertaa palaamaan takaisin versioon josta aloitin. Yritin refaktoroimalla saadaa koodia tiiviimmäksi ja siten nopeammaksi. Olisi pitänyt aivan jokaisen pienimmänkin muutoksen jälkeen testata, ettei mitään hajonnut.  

## Mikä jäi epäselväksi tai tuottanut vaikeuksia? 

- JPS-algoritmini vaikuttaa tosi hitaaalta - voisitko kommentoida onko siinä jotain perustavanlaatiosta vikaa vai onko ongelma nyt kuitenkin se, että kartat eivät ole oikeanlaisia? Hitaus huolestuttaa minua paljon enemmän kuin se, että reitti ei ole vielä kohdalla.

- JPS ei aina löydä saman mittaista reittiä kuin Dijkstra. En vielä hyödynnän heuristiikkaa. Pienet, yksinkertaiset kartat pääsääntöisesti toimivat.

- En ehtinyt vielä katsoa vertaisarvioinnin kommentteja (JPS:n kirjoitin tällä viikolla kokonaan uusiksi, joten siihen kommentoinnista tuskin on apua)

## Mitä teen seuraavaksi?

- Laitan JPS:n toimimaan ja siistin koodin <- algoritmi ei vieläkään toimi oikein
- Teen vertailutestit siitä tuottavatko Dijkstra ja JPS:ssä yhtä lyhyen reitin <- tästä ensimmäinen versio tehty
- Teen vertailutestit siitä kuinka nopeasti Dijkstra ja JPS löytävät lyhyimmän reitin <- tästä ensimmäinen versio tehty
- Muutan ohjelmaa niin että se arpoo reitin aloitus- ja lopetuspisteet
- Toteutan käyttöliittymään toiminnon, joka ajaa isomman kasan algoritmien vertailutestejä ja tulostaa lopuksi tilaston vertailusta
- Viimeistelen ohjelman, mm. refaktoroin koodia suoraviivaisemmaksi.
- Jos jää aikaa lisään kartat tietokantaan. Vähintäänkin kartoilla pitäisi olla yksilöllinen tunniste.
