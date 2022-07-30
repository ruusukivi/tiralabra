# Määrittelydokumentti 

Sovellus on toteutettu Helsingin Yliopiston Tietorakenteet ja algoritmit -laboratoriokurssilla, joka on osa tietojenkäsittelytieteen kandidaatin (TKT) opintoja.

Sovelluksessa tulostetaan käyttäjän antamien parametrien mukainen kartta sekä vertaillaan kahden eri algoritmin toimivuutta reitinhaussa tavoitteena löytää lyhyin reitti nopeasti.  

## Mitä ohjelmointikieltä käytät?

Sovellus on toteutettu javalla.
Dokumentaation ja sovelluksen kielenä käytetään suomea.

## Mitä algoritmeja ja tietorakenteita toteutat työssäsi?

Karttojen toteutuksessa käytän Random Walk -algoritmia.

Reitinhaussa vertailen Dijkstra- ja Jump Point Search (JPS) -algoritmeja. 

Dijkstra etsii lyhyimmän reitin alkupisteestä kaikkiin muihin pisteisiin. Se on ahne algoritmi, joka etsii aina sellaisen käsittelemättömän solmun, jonka etäisyys on pienin.

Jump Point Search - algoritmi hyödyntää karsintasääntöjä (pruning rules), joiden avulla se voi jättää käsittelemättä tarkasteltavan solmun naapurit, joihin on jo olemassa lyhyempi tai yhtä lyhyt polku. Algoritmi keskittyy solmuihin, jotka ovat hyppypisteitä (jump point) eli välttämättömiä osia lyhyimmissä reiteissä yhteen tai useampaan naapuriin.

Kartat kuvaan solmuista ja kaarista koostuvana verkkona. Molempien algoritmien toteutuksessa hyödynnän minimikekoa.

## Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit/tietorakenteet?

Sovelluksessa tavoitteena löytää lyhyin reitti alkupisteestä päätepisteeseen.

Sovelluksen valitut algoritmit ovat yleisesti käytössä olevia ja siksi kiinnostavia oppia.

## Mitä syötteitä ohjelma saa ja miten näitä käytetään?

Ohjelma tuottaa ensin kaksiuloitteisen  taulukkomuotoisen kartan sekä alku- ja päätepisteet ja etsii sitten lyhyimmän reitin kahden eri algoritmin avulla. Kartta tulkitaan verkkona, jossa ruudut ovat solmuja. Ruudun arvo 1 tarkoittaa reitiksi kelpaavaa ruutua ja arvo 0 estettä.

## Tavoitteena olevat aika- ja tilavaativuudet 

Dijkstran algoritmin aikavaativuustavoite on O(n + m log n), joissa n on solmujen lukumäärä ja m kaarien lukumäärä. Oletuksena on, ettei verkossa ole negatiivisia kaaria eikä kahta kaarta, joiden alku- ja loppusolmu olisi sama.

JPS-algoritmin aikavaativuustavoite on pahimmillaan sama kuin Dijkstrassa, mutta käytännössä sen pitäisi olla nopeampi, koska algoritmi "hyppää" osan solmujen yli.

Tilavaativuus molemmissa on arviolta O(n) verkon solmujen mukaan.

## Lähteet

Abdolsaheb, A. How to code your own procedural dungeon map generator using the Random Walk Algorithm
https://www.freecodecamp.org/news/how-to-make-your-own-procedural-dungeon-map-generator-using-the-random-walk-algorithm-e0085c8aa9a/

Dijkstran algoritmi. Wikipedia. https://fi.wikipedia.org/wiki/Dijkstran_algoritmi

Harabor, D. & Grastien, A.
Improving Jump Point Search. NICTA and The Australian National University. 
https://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-icaps14.pdf 

Laaksonen, A. 2021. Tietorakenteet ja algoritmit. https://www.cs.helsinki.fi/u/ahslaaks/tirakirja/ 

Sedgewick, R & Wayne, K https://introcs.cs.princeton.edu/java/15inout/RandomWalk.java.html

Sumit, J. 2018. Dijkstra’s – Shortest Path Algorithm (SPT) – Adjacency List and Min Heap – Java Implementation. https://algorithms.tutorialhorizon.com/dijkstras-shortest-path-algorithm-spt-adjacency-list-and-min-heap-java-implementation/ 

Witmer, N. 2014. A Visual Explanation of Jump Point Search. Zerowidth positive lookahead.
https://zerowidth.com/2013/a-visual-explanation-of-jump-point-search.html

Muita Tira-kurssin harjoitustöitä:
https://github.com/sinisaarinen/tira-labra
https://github.com/NooraVino/GetMeOut-tiralabra
https://github.com/ALindroos/Polunhakija 
