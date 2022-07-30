package tiralabra.karttojenpiirto;

import tiralabra.kayttoliittyma.IO;
import tiralabra.reitinhaku.Verkko;
import java.util.HashMap;
import java.util.ArrayList;

public class Kartat {
    private HashMap<String, Verkko> kartat;
    private IO io = new IO();

    public Kartat() {
        this.kartat = new HashMap<String, Verkko>();
    }

    public void lisaaKartta(Verkko kartta) {
        kartat.put(kartta.nimi, kartta);
    }

    public int karttojenMaara() {
        return kartat.size();
    }

    public ArrayList<String> karttojenNimet() {
        ArrayList<String> nimet = new ArrayList<>(kartat.keySet());
        return nimet;
    }

    public void tulostaKartta(String nimi) {
        try {
            Verkko verkko = kartat.get(nimi);
            io.tulosta("\nKartta: " + verkko.nimi + ", koko " + verkko.koko + "*" + verkko.koko + "\n");
            for (int i = 0; i < verkko.koko; i++) {
                for (int j = 0; j < verkko.koko; j++) {
                    if (!verkko.solmut[i][j].getKuljettava()) {
                        io.tulosta("x");
                    } else {
                        io.tulosta(".");
                    }
                }
                io.tulosta("\n");
            }

        } catch (Exception e) {
            io.tulosta("\nUps! Kartan tulostus ei onnistunut. Tarkista nimi. \n Virheilmoitus: " + e + "\n");
        }

    }
}
