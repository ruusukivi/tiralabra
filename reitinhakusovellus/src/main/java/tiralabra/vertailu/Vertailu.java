package tiralabra.vertailu;

import java.util.ArrayList;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Dijkstra;
import tiralabra.reitinhaku.JumpPointSearch;
import tiralabra.reitinhaku.Verkko;

/**
 * Luokka algoritmien vertailuun.
 * 
 * @see Dijkstra
 * @see JumpPointSearch
 */
public class Vertailu {
    private boolean teksteilla;
    private ArrayList<String> tulokset;

    /**
     * Vertailuluokan konstruktori.
     * 
     * Vertailua luotaessa valitaan halutaanko vertailu suppeasta vai laajasta
     * aineistosta.
     * 
     * Suppea aineisto on muotoiltu ruudulta luettavaksi.
     * 
     * Laaja aineisto tulostuu ruudulle CSV-muotoiltuna, jolloin se on helppo
     * kopioida muualle käsiteltäväksi.
     * 
     * Laajan aineiston tulostus on myöhemmin tarkoitus vaihtaa tiedostoon.
     * 
     * @param teksteilla Määrittelee aineiston muodon. Jos on tosi, tulostetaan suppea
     *                aineisto.
     */

    public Vertailu(boolean teksteilla) {
        this.teksteilla = teksteilla;
        this.tulokset = new ArrayList<>();
    }

    /**
     * Metodi ohjaa vertailujen ajamista.
     * 
     * @param leveys Kartan sivun leveys.
     * @param polut  Kartalla olevien polkujen määrä.
     * @param pituus Kartalla olevan polun maksimipituus.
     * @param nimi   Kartan nimi.
     * @return ArrayList Palauttaa tulokset listana tulostusta varten.
     */
    public ArrayList<String> annaTulokset(int leveys, int polut, int pituus, String nimi) {
        if (!this.teksteilla) {
            teeVertailuIlmanTeksteja(leveys, polut, pituus, nimi);
        } else {
            teeVertailu(leveys, polut, pituus, nimi);
        }
        return this.tulokset;
    }

    /**
     * Metodi ajaa vertailun laajalle aineistolle
     * 
     * @param leveys Kartan sivun leveys.
     * @param polut  Kartalla olevien polkujen määrä.
     * @param pituus Kartalla olevan polun maksimipituus.
     * @param nimi   Kartan nimi.
     */
    public void teeVertailu(int leveys, int polut, int pituus, String nimi) {
        RandomWalk kartta = new RandomWalk(leveys, polut, pituus, nimi);
        Verkko d = kartta.muodostaKartastaVerkko("dijkstra");
        Dijkstra dijkstra = new Dijkstra(d);
        boolean loytyi = dijkstra.etsiLyhyinReitti();
        if (loytyi) {
            tulokset.add("\nKartan nimi: " + d.getNimi() + ", koko " + d.getKoko() + "*" + d.getKoko() + "\n"
                    + "Reitin pituus: " + dijkstra.getReitinPituus() + ", Haun kesto (ms): " + dijkstra.getKesto()
                    + ", Keossa käsiteltyjen solmujen määrä: " + dijkstra.getKasitellyt() + "\n");
        } else {
            tulokset.add("\nKartan nimi: " + d.getNimi() + ", koko " + d.getKoko() + "*" + d.getKoko() + "\n"
                    + "Reittiä ei löytynyt. " + "Haun kesto (ms): " + dijkstra.getKesto()
                    + ",Keossa käsiteltyjen solmujen määrä: " + dijkstra.getKasitellyt() + "\n");
        }

        Verkko j = kartta.muodostaKartastaVerkko("jps");
        JumpPointSearch jps = new JumpPointSearch(j);
        boolean loytyiJPS = jps.etsiLyhyinReitti();

        if (loytyiJPS) {
            tulokset.add("\nKartan nimi: " + j.getNimi() + ", koko " + j.getKoko() + "*" + j.getKoko() + "\n"
                    + "Reitin pituus: " + jps.getReitinPituus() + ", Haun kesto (ms): " + jps.getKesto()
                    + ", Keossa käsiteltyjen solmujen määrä: " + jps.getKasitellyt() + "\n");
        } else {
            tulokset.add("\nKartan nimi: " + j.getNimi() + ", koko " + j.getKoko() + "*" + j.getKoko() + "\n"
                    + "Reittiä ei löytynyt. " + "Haun kesto (ms): " + jps.getKesto()
                    + ", Keossa käsiteltyjen solmujen määrä: " + jps.getKasitellyt() + "\n");
        }
    }

    /**
     * Metodi ajaa vertailun suppealle aineistolle.
     * 
     * @param leveys Kartan sivun leveys.
     * @param polut  Kartalla olevien polkujen määrä.
     * @param pituus Kartalla olevan polun maksimipituus.
     * @param nimi   Kartan nimi.
     */
    public void teeVertailuIlmanTeksteja(int leveys, int polut, int pituus, String nimi) {
        RandomWalk kartta = new RandomWalk(leveys, polut, pituus, nimi);
        Verkko d = kartta.muodostaKartastaVerkko("dijkstra");
        Dijkstra dijkstra = new Dijkstra(d);
        boolean loytyi = dijkstra.etsiLyhyinReitti();
        tulokset.add(
                "\n" + d.getNimi() + "," + loytyi + "," + dijkstra.getReitinPituus() + "," + dijkstra.getKesto() + ", "
                        + dijkstra.getKasitellyt());
        Verkko j = kartta.muodostaKartastaVerkko("jps");
        JumpPointSearch jps = new JumpPointSearch(j);
        boolean loytyiJPS = jps.etsiLyhyinReitti();
        tulokset.add("\n" + j.getNimi() + "," + loytyiJPS + "," + jps.getReitinPituus() + "," + jps.getKesto() + ", "
                + jps.getKasitellyt());

    }
}
