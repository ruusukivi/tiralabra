# Tietorakenteet ja algoritmit, Laboratoriotyöskentely 4 op

## Aihe: Reitinhakualgoritmien vertailu

Sovelluksessa luodaan kartta Random Walk-algoritmilla ja vertaillaan sitten Dijkstra- ja Jump Point Search -algoritmien tehokkuutta lyhyimmän reitin haussa. 

Sain sunnuntai-iltana toistaiseksi parhaiten toimivan version Jump Point Search -algoritmista päivitettyä versionhallintaan. Bugiton se ei vielä ole, mutta reitinpiirto toimii nyt ja JPS ja Dijkstra tuottavat myös isommilla kartoilla lähes samanmittaisen reitin. Nopeus on välillä ok, mutta joillakin kartoilla JPS bugittaa - haku kestää ja kekoon tulee liikaa solmuja. Jatkan korjauksia maanantaina. Minulla ollut muutama ajatusvirhe JPS:ssä, jotka olen saanut taklattua (vein kekoon pakotetun naapurin enkä hyppysolmua & käsittelin tiettyjä solmuja liian monta kertaa).

Viikkoraportissa kuvattu lauantai-illan tilanne. 

![JPS-tilanne](kuvat/jps-tilanne.png)

Ohjelma tukee tällä hetkellä vain reitin etsintää vasemmasta ylälaidasta oikeaan alalaitaan. 

Ohjelmassa tällä hetkellä mukana paljon debuggaus-tulosteita, jotka jäävät pois lopullisesta versiosta.

## Dokumentaatio

- [ ] [Määrittelydokumentti](dokumentaatio/maarittely.md)
- [ ] [Toteutusdokumentti](dokumentaatio/toteutus.md)
- [ ] [Testausdokumentti](dokumentaatio/testaus.md)
- [ ] [Käyttöohje](dokumentaatio/kayttoohje.md)

## Viikkoraportit

- [x] [Viikko 1](dokumentaatio/viikko1.md)
- [x] [Viikko 2](dokumentaatio/viikko2.md)       
- [x] [Viikko 3](dokumentaatio/viikko3.md)   
- [x] [Viikko 4](dokumentaatio/viikko4.md)   
- [x] [Viikko 5](dokumentaatio/viikko5.md) 
- [x] [Viikko 6](dokumentaatio/viikko6.md) 
- [ ] [Loppupalautus](dokumentaatio/viikko7.md)   
