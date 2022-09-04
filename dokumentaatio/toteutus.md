# Toteutusdokumentti 

## Ohjelman yleisrakenne

Sovellus on paketoitu tiralabra-domaiin, jonka alla vielä paketit karttojen piirrolle, käyttöliittymälle, reitinhaulle ja algoritmien vertailuille.

![Ohjelman rakenne](kuvat/rakenne.png)

## Saavutetut aika- ja tilavaativuudet

Dijkstran algoritmin aikavaativuus on pahimmillaan O(n + m log n), joissa n on solmujen lukumäärä ja m kaarien lukumäärä. JPS-algoritmin aikavaativuus on heikoimmillaan yhtä suuri kuin Dijkstrassa, mutta usein se on nopeampi, koska algoritmi vie kekoon pääsääntöisetsi huomattavasti vähemmän solmuja. 

Tilavaativuus molemmissa on sama, koska kartta tallennetaan martiisitaulukkona, jossa n solmua. Myös keon maksimikoko on sama kuin solmujen määrä. Tilavaativuus molemmille on O(n). 

[Testausdokumentti](testaus.md)

## Puutteet ja parannusehdotukset

Ohjelma etsii tällä hetkellä reittejä origosta kartan vastakkaiseen kulmaan. Testeissä reittejä etsitään myös toiseen suuntaan. Jos jatkaisin ohjelman kehitystä, rakentaisin seuraavaksi tuen aloitus- ja lopetuspisteen valintaan, jotta algoritmien toimintaa voisi testata vielä monipuolisemmin. 

Laajempi vertailuaineisto kannattaisi tulostaa tiedostoon.

Kartat olisi fiksua tallentaa tietokantaan, jotta niihin voisi palata myös session päätyttyä. Kartoilla ei ole tällä hetkellä yksilöllistä tunnistetta.

Hieman hämäävää on, että kartat tulostetaan ruudulle niin, että origo on vasemmassa yläkulmassa eli reitit kulkevat ylhäältä vasemmalta oikealle alas.

Käyttöliittymän virheensieto ei vielä ole kovin hyvä, koska syötteitä ei validoida.

## Lähteet

Abdolsaheb, A. How to code your own procedural dungeon map generator using the Random Walk Algorithm
https://www.freecodecamp.org/news/how-to-make-your-own-procedural-dungeon-map-generator-using-the-random-walk-algorithm-e0085c8aa9a/

Dijkstran algoritmi. Wikipedia. https://fi.wikipedia.org/wiki/Dijkstran_algoritmi

Harabor, D. & Grastien, A.
Improving Jump Point Search. NICTA and The Australian National University. 
https://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-icaps14.pdf 

Heuristics. From Amit’s Thoughts on Pathfinding. 
http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html

Laaksonen, A. 2021. Tietorakenteet ja algoritmit. https://www.cs.helsinki.fi/u/ahslaaks/tirakirja/ 

Podhraski, T. 2013. How to Speed Up A* Pathfinding With the Jump Point Search Algorithm. Envato Tuts+. 
https://gamedevelopment.tutsplus.com/tutorials/how-to-speed-up-a-pathfinding-with-the-jump-point-search-algorithm--gamedev-5818 

Sumit, J. 2018. Dijkstra’s – Shortest Path Algorithm (SPT) https://algorithms.tutorialhorizon.com/dijkstras-shortest-path-algorithm-spt/

Sumit, J. 2018. Dijkstra’s – Shortest Path Algorithm (SPT) – Adjacency List and Min Heap – Java Implementation. https://algorithms.tutorialhorizon.com/dijkstras-shortest-path-algorithm-spt-adjacency-list-and-min-heap-java-implementation/ 

Sumit, J. 2015. Binary Min-Max Heap Implementation https://algorithms.tutorialhorizon.com/binary-min-max-heap/

Witmer, N. 2014. A Visual Explanation of Jump Point Search. Zerowidth positive lookahead.
https://zerowidth.com/2013/a-visual-explanation-of-jump-point-search.html

Muita Tira-kurssin harjoitustöitä:
- https://github.com/sinisaarinen/tira-labra
- https://github.com/NooraVino/GetMeOut-tiralabra
- https://github.com/ALindroos/Polunhakija 
- https://github.com/k0psutin/TiraLabra
