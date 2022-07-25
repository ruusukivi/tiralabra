package tiralabra;
import tiralabra.kayttoliittyma.Kayttoliittyma;
import tiralabra.kayttoliittyma.IO;

/** Pääluokka reitinhakusovelluksessa 
 *
 * Luokka luo IO-olion tulosteiden ja syötteiden käsittelyyn sekä Kayttoliittyma-luokan ja käynnistää sen jälkeen reitinhakusovelluksen. 
 */
public class Sovellus {
    public static void main(String[] args) {
        IO io = new IO();
        Kayttoliittyma ui = new Kayttoliittyma(io);
        ui.kaynnista();
    }
}
