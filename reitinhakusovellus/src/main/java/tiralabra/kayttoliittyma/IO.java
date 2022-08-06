package tiralabra.kayttoliittyma;

import java.util.*;

/**
 * Luokka käyttäjän syötteiden ja tulosteiden käsittelyyn.
 * Muodostetaan rajapintaoliota hyödyntäen, jotta yksikkötestien tekeminen on helpompaa.
 * 
 * @see RajapintaIO 
 */
public class IO implements RajapintaIO {
    private Scanner lukija;

    public IO() {
        lukija = new Scanner(System.in);
    }

    /**
     * Tulostaa tekstisyötteen käyttäjän näkyville.
     *
     * @param s Tulostettava teksti annetaan stringinä, 
     * ei tee rivinvaihtoa automaattisesti
     */
    public void tulosta(String s) {
        System.out.print(s);
    }

    /**
     * Lukee käyttäjän syötteen.
     *
     * @return Palauttaa tekstimuodossa käyttäjän antaman syötteen. 
     */

    public String lue() {
        try {
            String syote = lukija.nextLine();
            return syote;
        } catch (Exception e) {
            System.out.println("Virhe: " + e);
        }
        return "0";

    }

}
