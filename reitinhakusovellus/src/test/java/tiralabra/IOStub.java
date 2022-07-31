package tiralabra;
import tiralabra.kayttoliittyma.RajapintaIO;

import java.util.ArrayList;

/** Luokka syötteiden ja tulosteiden käsittelyyn testeissä
 * 
 * @see RajapintaIO
 */

public class IOStub implements RajapintaIO {

    String[] syotteet;
    int syotteidenMaara;
    ArrayList<String> tulosteet;

    public IOStub(String... syote) {
        this.syotteet = syote;
        this.tulosteet = new ArrayList<String>();
    }

    
    /** 
     * @param s
     */
    public void tulosta(String s) {
        tulosteet.add(s);
    }

    
    /** 
     * @return String
     */
    public String lue() {
        if (syotteet.length == syotteidenMaara) {
            return "0";
        }
        String syote = this.syotteet[syotteidenMaara];
        syotteidenMaara++;
        return syote;
    }

}
