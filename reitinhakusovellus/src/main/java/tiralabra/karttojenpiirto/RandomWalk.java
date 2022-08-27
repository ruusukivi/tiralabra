package tiralabra.karttojenpiirto;

import tiralabra.reitinhaku.Verkko;

/**
 * Random Walk -kartan luontiin käytettävä luokka.
 *
 * Luokka piirtää kartan Random Walk -algoritmia hyödyntäen.
 * ks.
 * https://www.freecodecamp.org/news/how-to-make-your-own-procedural-dungeon-map-generator-using-the-random-walk-algorithm-e0085c8aa9a/
 * 
 */

public class RandomWalk {
    private int sivu;
    private int polut;
    private int pituus;
    private int[][] kartta;
    private String nimi;
    private Verkko dijkstra;
    private Verkko jps;

    /**
     * Random Walk -kartan konstruktori.
     * Luo neliön muotoisia karttoja.
     * 
     * @param sivu     Neliönmuotoisen kartan sivun pituus.
     * @param polut Karttaan piirrettävien polkujen määrä.
     * @param pituus   Yksittäisen polun maksimipituus.
     * @param nimi     Kartan nimi, jolla karttaa voi hakea session aikana.
     */

    public RandomWalk(int sivu, int polut, int pituus, String nimi) {
        this.sivu = sivu;
        this.polut = polut;
        this.pituus = pituus;
        this.kartta = new int[sivu][sivu];
        this.nimi = nimi;
        this.dijkstra = new Verkko(sivu, this.nimi.concat("-dijkstra"));
        this.jps = new Verkko(sivu, this.nimi.concat("-jps"));
        piirraKartta();
    }

    public void piirraKartta() {
        int nykyinenRivi = (int) Math.floor(Math.random() * sivu); // satunnaisesti valittu aloitusrivi
        int nykyinenSarake = (int) Math.floor(Math.random() * sivu); // satunnaisesti valittu aloitussarake
        int[][] suunnat = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // käytettävissä olevat liikkumissuunnat (vasen,
                                                                        // oikea, ylös, alas)
        int[] viimeisinSuunta = { 0, 0 }; // viimeisin suunta johon liikuttu
        int[] satunnainenSuunta = { 0, 0 }; // seuraava suunta johon liikutaan

        while (polut > 0 & pituus > 0) {
            satunnainenSuunta = suunnat[(int) Math.floor(Math.random() * suunnat.length)];
            do {
                satunnainenSuunta = suunnat[(int) Math.floor(Math.random() * suunnat.length)];
                // valitaan seuraava suunta ja varmistetaan, että uusi suunta on eri josta tultiin
            } while ((satunnainenSuunta[0] == -viimeisinSuunta[0] &&
                    satunnainenSuunta[1] == -viimeisinSuunta[1]) ||
                    (satunnainenSuunta[0] == viimeisinSuunta[0] &&
                            satunnainenSuunta[1] == viimeisinSuunta[1]));

            int randomPituus = (int) Math.ceil(Math.random() * pituus), // seuraavaksi piirrettävän polun pituus
                    polunPituus = 0; // polun tämän hetkinen pituus

            // piirretään polkua kunnes maksimipituus tai kartan reuna saavutettu
            while (polunPituus < randomPituus) {
                // varmistetaan ettei polkua piirretä kartan ulkopuolelle
                if (((nykyinenRivi == 0) && (satunnainenSuunta[0] == -1)) ||
                        ((nykyinenSarake == 0) && (satunnainenSuunta[1] == -1)) ||
                        ((nykyinenRivi == sivu - 1) && (satunnainenSuunta[0] == 1)) ||
                        ((nykyinenSarake == sivu - 1) && (satunnainenSuunta[1] == 1))) {
                    break;
                } else {
                    kartta[nykyinenRivi][nykyinenSarake] = 1; // poistetaan seinä kartan valitusta pisteestä (0 -> 1)
                    nykyinenRivi += satunnainenSuunta[0]; // päivitetään rivitieto ajantasalle nykyisen sijainnin kanssa
                    nykyinenSarake += satunnainenSuunta[1]; // päivitetään saraketieto ajantasalle nykyisen sijainnin
                                                            // kanssa
                    polunPituus++; // kasvatettaan polun pituutta yhdellä
                }
            }

            if (polunPituus > 1) { // edetään seuraavan polun piirtämiseen, kunhan edellinen polku on
                                      // vähintään kahden merkin mittainen
                viimeisinSuunta = satunnainenSuunta; // tallennettaan viimeisin suunta
                polut--; // kun polku on valmis vähennetään piirrettävien polkujen määrää yhdellä
            }
            kartta[0][0] = 1; // varmistetaan ettei aloituspiste ole seinä
            kartta[sivu - 1][sivu - 1] = 1; // varmistetaan ettei lopetuspiste ole seinä
        }
    }

    /**
     * Uuden kartan luonti ja muunto verkoiksi.
     * 
     * @param algoritmi Samasta kartasta muodotetaan omat Verkko-oliot dijkstra- ja
     *                  jps-algoritmeja varten.
     * @return Verkko Palauttaa kartasta muodostetun Verkko-olion.
     */
    public Verkko muodostaKartastaVerkko(String algoritmi) {
        if (algoritmi.contains("dijkstra")) {
            for (int i = 0; i < kartta.length; i++) {
                for (int j = 0; j < kartta[0].length; j++) {
                    if (kartta[i][j] == 0) {
                        dijkstra.lisaaSolmu(i, j, true, false);
                    } else {
                        dijkstra.lisaaSolmu(i, j, false, true);
                    }
                }
            }
            return dijkstra;
        }
        if (algoritmi.contains("jps")) {
            for (int i = 0; i < kartta.length; i++) {
                for (int j = 0; j < kartta[0].length; j++) {
                    if (kartta[i][j] == 0) {
                        jps.lisaaSolmu(i, j, true, false);
                    } else {
                        jps.lisaaSolmu(i, j, false, true);
                    }
                }
            }
            return jps;
        }
        return null;

    }

}
