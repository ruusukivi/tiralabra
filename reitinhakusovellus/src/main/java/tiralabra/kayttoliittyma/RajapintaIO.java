package tiralabra.kayttoliittyma;

/**
 * Rajapinta käyttäjän syötteiden ja tulosteiden käsittelyyn.
 */
public interface RajapintaIO {

    /**
     * Tulostaa tekstisyötteen käyttäjän näkyville.
     * @author ruusukivi
     * @param s Saa parametrina tekstimuotoisen syötteen.
     */
    void tulosta(String s);

    /**
     * Lukee käyttäjän syötteen.
     *
     * @return Palauttaa tekstimuotoisen syötteen.
     */

    public String lue(); 

}
