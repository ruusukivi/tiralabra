package tiralabra.karttojenpiirto;

import tiralabra.kayttoliittyma.RajapintaIO;
import tiralabra.reitinhaku.Verkko;
import java.util.HashMap;
import java.util.ArrayList;

/** Luokka karttojen käsittelyyn
 * 
 * Säilöön session aikana luodut kartat verkkoina
 * Vastaa karttojen tulostuksesta 
 * @see RajapintaIO
 */
public class Kartat {
    private HashMap<String, Verkko> kartat;
    private RajapintaIO io; {
        
    };

    /**
     * @param io karttojen tulostus käyttäjän näkyville
     */
    public Kartat(RajapintaIO io) {
        this.kartat = new HashMap<String, Verkko>();
        this.io = io;
    }

    
    /** 
     * @param kartta uuden kartan säilöntä
     */
    public void lisaaKartta(Verkko kartta) {
        kartat.put(kartta.nimi, kartta);
    }

    
    /** 
     * @return int 
     */
    public int karttojenMaara() {
        return kartat.size();
    }

    
    /** 
     * @return ArrayList<String> 
     */
    public ArrayList<String> karttojenNimet() {
        ArrayList<String> nimet = new ArrayList<>(kartat.keySet());
        return nimet;
    }

    
    /** Session aikana luodun kartan tulostus verkon nimen perusteella
     * @param nimi 
     */
    public void tulostaKartta(String nimi) throws Error {
        if (!kartat.containsKey(nimi)){
            throw new Error("Ups! Kartan tulostus ei onnistunut. Tarkista nimi.");
        }
            Verkko kartta = kartat.get(nimi);
            io.tulosta("\nKartta: " + kartta.nimi + ", koko " + kartta.koko + "*" + kartta.koko + "\n");
            for (int i = 0; i < kartta.koko; i++) {
                for (int j = 0; j < kartta.koko; j++) {
                    if (!kartta.solmut[i][j].getKuljettava()) {
                        io.tulosta("x");
                    } else {
                        io.tulosta(".");
                    }
                }
                io.tulosta("\n");
            }
    }
}
