package tiralabra;
import tiralabra.kayttoliittyma.Kayttoliittyma;
import tiralabra.karttojenpiirto.Kartat;
import tiralabra.kayttoliittyma.IO;

/** Pääluokka reitinhakusovelluksessa.
 *  
 * @author ruusukivi
 * 
 * Luokka luo IO-olion tulosteiden ja syötteiden käsittelyyn, 
 * Kartat-olion karttojen säilöntään ja käsittelyyn
 * sekä Kayttoliittyma-olion ja käynnistää sen jälkeen reitinhakusovelluksen. 
 */
public class Sovellus {
    public static void main(String[] args) {
        IO io = new IO();
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
    }
}
