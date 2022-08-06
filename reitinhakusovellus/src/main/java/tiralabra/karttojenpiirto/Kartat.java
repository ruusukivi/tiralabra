package tiralabra.karttojenpiirto;

import tiralabra.kayttoliittyma.RajapintaIO;
import tiralabra.reitinhaku.Dijkstra;
import tiralabra.reitinhaku.Verkko;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Luokka karttojen käsittelyyn.
 * 
 * Säilöö session aikana luodut kartat verkkoina ja
 * vastaa karttojen tulostuksesta.
 * 
 * @see RajapintaIO
 */
public class Kartat {
    private HashMap<String, Verkko> kartat;
    private RajapintaIO io;

    /**
     * @param io IO-olio vastaa tulostuksesta ja käsittelee käyttäjän syötteet.
     */
    public Kartat(RajapintaIO io) {
        this.kartat = new HashMap<String, Verkko>();
        this.io = io;
    }

    /**
     * @param verkko Kartat on tallennettu Verkko-muotoisina oliona.
     */
    public void lisaaKartta(Verkko verkko) {
        kartat.put(verkko.getNimi(), verkko);
    }

    /**
     * @return int Palauttaa session aikana luotujen karttojen määrän.
     */
    public int karttojenMaara() {
        return kartat.size();
    }

    /**
     * @return ArrayList Palauttaa session aikana luotujen karttojen nimet.
     */
    public ArrayList<String> karttojenNimet() {
        ArrayList<String> nimet = new ArrayList<>(kartat.keySet());
        return nimet;
    }

    /**
     * Session aikana luodun kartan tulostus verkon nimen perusteella.
     * 
     * @param nimi Tulostettavan kartan nimi.
     * @throws Error Palauttaa virheviestin mm. tilanteessa, jossa annetulla nimellä
     *               ei löydy karttaa.
     */
    public void tulostaKartta(String nimi) throws Error {
        if (!kartat.containsKey(nimi)) {
            throw new Error("Ups! Kartan tulostus ei onnistunut. Tarkista nimi.");
        }
        Verkko verkko = kartat.get(nimi);
        if (!verkko.getSolmut()[0][0].getKasitelty()) {
            Dijkstra reitti = new Dijkstra(verkko);
            reitti.etsiLyhyinReitti();
        }
        io.tulosta("\nKartan nimi: " + verkko.getNimi() + ", koko " + verkko.getKoko() + "*" + verkko.getKoko() + "\n");
        for (int i = 0; i < verkko.getKoko(); i++) {
            for (int j = 0; j < verkko.getKoko(); j++) {
                if (!verkko.getSolmut()[i][j].getKuljettava()) {
                    io.tulosta("x ");
                } else {
                    if (verkko.getSolmut()[i][j].getDijkstra()) {
                        io.tulosta("D ");
                    } else {
                        io.tulosta(". ");
                    }
                }
            }
            io.tulosta("\n");
        }
        if (verkko.getDijkstra().size() > 0) {
            for (int d = verkko.getDijkstra().size() - 2; d >= 0; d--) {
                io.tulosta(verkko.getDijkstra().get(d));
            }
            io.tulosta("\n");
        } else {
            String lopetus = String.valueOf(verkko.getKoko() - 1) + "." + String.valueOf(verkko.getKoko() - 1);
            io.tulosta("\nDijkstra-algoritmi ei löytänyt reittiä pisteestä 0.0 pisteeseen " + lopetus + ".\n");
        }
    }
}
