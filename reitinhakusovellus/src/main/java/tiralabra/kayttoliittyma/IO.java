package tiralabra.kayttoliittyma;

import java.util.*;

/**
 * Luokka käyttäjän syötteiden ja tulosteiden käsittelyyn
 */
public class IO {
    private Scanner lukija;

    public IO() {
        lukija = new Scanner(System.in);
    }

    /**
     * Tulostaa tekstisyötteen käyttäjän näkyville
     *
     * @param s tulostettava syöte
     */
    public void tulosta(String s) {
        System.out.print(s);
    }

    /**
     * Lukee käyttäjän syötteen
     *
     * @return käyttäjän antama syöte
     */

    public String lue() {
        {
            try {
                String syote = lukija.nextLine();
                return syote;
            } catch (Exception e) {
                System.out.println("Virhe: " + e);
            }
            return "0";
        }
    }

}
