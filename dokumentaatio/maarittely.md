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

Kartat kuvaan solmuista koostuvana verkkona. Molempien algoritmien toteutuksessa hyödynnän minimikekoa.

## Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit/tietorakenteet?

Sovelluksessa tavoitteena löytää lyhyin reitti alkupisteestä päätepisteeseen.

Sovelluksen valitut algoritmit ovat yleisesti käytössä olevia ja siksi kiinnostavia.

## Mitä syötteitä ohjelma saa ja miten näitä käytetään?

Ohjelma tuottaa ensin kaksiuloitteisen  matriisitaulukkomuotoisen kartan ja etsii sitten lyhyimmän reitin kahden eri algoritmin avulla. Aloituspisteenä käytetään origoa ja lopetuspisteenä kartan vastakkaista nurkkaa. Kartta tulkitaan verkkona, jossa ruudut ovat solmuja. Ruudun arvo 1 tarkoittaa reitiksi kelpaavaa ruutua ja arvo 0 estettä.

## Tavoitteena olevat aika- ja tilavaativuudet 

Dijkstran algoritmin aikavaativuustavoite on O(n + m log n), joissa n on solmujen lukumäärä ja m kaarien lukumäärä. Oletuksena on, ettei verkossa ole negatiivisia kaaria eikä kahta kaarta, joiden alku- ja loppusolmu olisi sama.

JPS-algoritmin aikavaativuustavoite on pahimmillaan sama kuin Dijkstrassa, mutta käytännössä sen pitäisi olla nopeampi, koska algoritmi "hyppää" osan solmujen yli.

Tilavaativuus molemmissa on arviolta O(n) verkon solmujen mukaan. 
