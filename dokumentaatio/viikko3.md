
# Viikko 3

## Käytetty työaika:

- 31.7. Puuttuvien testien lisäämistä, Dijkstraa alkuun  - 5 h
- 1.8. Ensimmäinen versio Dijkstrasta - 6 h
- 4.8. Dijkstran debuggausta - 4 h
- 6.8. Dijkstran debuggausta ja viilailuja - 4 h

## Mitä olen tehnyt tällä viikolla?

- Testit RandomWalk-luokalle ja käyttöliittymälle
- Dijkstra-algoritmi ja sen debuggausta
- Käyttöliittymän, testien ja ja dokumentoinnin päivitystä

## Miten ohjelma on edistynyt?

- Ohjelma on edistynyt hyvin.
- Koska olin jo ehtinyt ottaa Solmu-olion laajalti käyttöön, en refaktoroinut sitä pois, vaikka palautteessa sanottiin, että matriisitaulukko riittää. Ajattelin säilöä olioihin (tällä hetkellä sessionaikaisia) tietoja, jotka auttavat karttojen piirtämisessä ja hallinnassa. Toivottavasti olioiden käytön kanssa tule jotain suorituskykyongelmaa, jota en vielä hahmota.

## Mitä opin tällä viikolla / tänään?

- Tämä ei ole uusi oppi, mutta itselleni tehokkain tapa debugata on lokittaa ohjelman toimintaa konsoliin. 

## Mikä jäi epäselväksi tai tuottanut vaikeuksia? 

- Olen miettinyt jonkin verran sitä, miten varmistaa, että algoritmit toimivat oikein. Tein joitakin simppeleitä testejä, mutta en ole varma löytävätkö ne kaikki potentiaaliset ongelmat. Ajattelin myös verrata algoritmeja keskenään kun molemmat valmiita -> tuottavatko saman reitin? Päätin lisäksi suoraviivaistaa määrittelyä niin, että reittien aloitus- ja lopetuspisteet ovat aina vasen yläkulma ja oikea alakulma. Ja kartat ovat aina neliöitä. Kartan piirron yhteydessä tulostan aina jokaisen reitin pisteen koordinaatit ja etäisyyden alusta.

- Jossain vaiheessa minulla tuli ongelmia muistin kanssa. Koodissa oli alun perin joitakin bugeja ja sovellus jäi jumiin. Gradleen jäi tämän jälkeen jotain haamuja, jotka ilmeisesti söivät muistin uusiltakin ajoilta. (vrt. "Starting a Gradle Daemon, 5 stopped Daemons could not be reused). Olin hetken huolissani, että suorituskyvyssä isompia ongelmia. 

## Mitä teen seuraavaksi?

- Seuraavaksi alan toteuttaa JPS-algoritmia ja suunnittelemaan suorituskykytestejä. Koitan samalla koko ajan pitää dokumentaatiota ja yksikkötestejä ajantasalla, ettei lopussa tarvitse niihin erikseen käyttää aikaa.