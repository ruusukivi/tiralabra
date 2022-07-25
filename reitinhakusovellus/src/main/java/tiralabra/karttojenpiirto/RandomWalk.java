package tiralabra.karttojenpiirto;

import tiralabra.kayttoliittyma.IO;

/**
 * Random Walk -kartan luontiin käytettävä luokka
 *
 * Luokka piirtää kartan Random Walk -algoritmia hyödyntäen
 * ks.  https://www.freecodecamp.org/news/how-to-make-your-own-procedural-dungeon-map-generator-using-the-random-walk-algorithm-e0085c8aa9a/
 * 
 * @see IO
 */

public class RandomWalk {
    private int sivu;
    private int tunnelit;
    private int pituus;
    private String nimi;
    private int[][] kartta;
    private IO io = new IO();

    /**
     * Random Walk -kartan konstruktori
     *
     * @param sivu määrittelee neliönmuotoisen kartan sivun pituuden
     * @param tunnelit määrittelee kuinka monta tunnelia karttaan piirretään
     * @param pituus määrittelee yksittäisen tunnelin maksimipituuden
     * @param nimi määrittelee kartan nimen
     */

    public RandomWalk(int sivu, int tunnelit, int pituus, String nimi) {
        this.sivu = sivu;
        this.tunnelit = tunnelit; 
        this.pituus = pituus;
        this.nimi = nimi;
        this.kartta = new int[sivu][sivu];
    }

    //** Kartan piirtäminen  */
    public void piirraKartta() {

        int nykyinenRivi = (int) Math.floor(Math.random() * sivu); // satunnaisesti valittu aloitusrivi
        int nykyinenSarake = (int) Math.floor(Math.random() * sivu);// satunnaisesti valittu aloitussarake
        int[][] suunnat = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // käytettävissä olevat liikkumissuunnat (vasen, oikea, ylös, alas)
        int[] viimeisinSuunta = { 0, 0 }; // viimeisin suunta johon liikuttu
        int[] satunnainenSuunta = { 0, 0 }; // seuraava suunta johon liikutaan

        while (tunnelit > 0 & pituus > 0) {

            satunnainenSuunta = suunnat[(int) Math.floor(Math.random() * suunnat.length)]; 

            do {
                satunnainenSuunta = suunnat[(int) Math.floor(Math.random() * suunnat.length)]; // valitaan seuraava suunta ja varmistetaan, että uusi suunta on eri josta tultiin
            } while ((satunnainenSuunta[0] == -viimeisinSuunta[0] &&
                    satunnainenSuunta[1] == -viimeisinSuunta[1]) ||
                    (satunnainenSuunta[0] == viimeisinSuunta[0] &&
                    satunnainenSuunta[1] == viimeisinSuunta[1]));

            int randomPituus = (int) Math.ceil(Math.random() * pituus), // seuraavaksi piirrettävän tunnelin pituus
                    tunnelinPituus = 0; // tunnelin tämän hetkinen pituus

            // piirretään tunneliä kunnes maksimipituus tai kartan reuna saavutettu 
            while (tunnelinPituus < randomPituus) {

                // varmistetaan ettei tunnelia piirretä kartan ulkopuolelle
                if (((nykyinenRivi == 0) && (satunnainenSuunta[0] == -1)) ||
                        ((nykyinenSarake == 0) && (satunnainenSuunta[1] == -1)) ||
                        ((nykyinenRivi == sivu - 1) && (satunnainenSuunta[0] == 1)) ||
                        ((nykyinenSarake == sivu - 1) && (satunnainenSuunta[1] == 1))) {
                    break;
                } else {
                    kartta[nykyinenRivi][nykyinenSarake] = 1; // poistetaan seinä kartan valitusta pisteestä (0 -> 1) 
                    nykyinenRivi += satunnainenSuunta[0]; // päivitetään rivitieto ajantasalle nykyisen sijainnin kanssa
                    nykyinenSarake += satunnainenSuunta[1]; // päivitetään saraketieto ajantasalle nykyisen sijainnin kanssa
                    tunnelinPituus++; // kasvatettan tunnelin pituutta yhdellä
                }
            }

            if (tunnelinPituus > 1) { // edetään seuraavan tunnelin piirtämiseen, kunhan edellinen tunneli on vähintään kahden merkin mittainen
                viimeisinSuunta = satunnainenSuunta; // tallennettaan viimeisin suunta
                tunnelit--; // kun tunneli on valmis vähennetään piirrettävien tunneleisen määrää yhdellä
            }
        }

        io.tulosta("\nKartta: " + nimi + ", koko " + sivu + "*" + sivu + "\n");

        for (int i = 0; i < kartta.length; i++) {
            for (int j = 0; j < kartta[0].length; j++) {
                if (kartta[i][j] == 0) {
                    io.tulosta(".");
                } else {
                    io.tulosta("@");
                }
            }
            io.tulosta("\n");
        }

    }

    public void alusta_pohja() {

    }
}
