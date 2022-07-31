package tiralabra.kayttoliittyma;

/**
 * Rajapinta käyttäjän syötteiden ja tulosteiden käsittelyyn
 */
public interface RajapintaIO {

    /**
     * Tulostaa tekstisyötteen käyttäjän näkyville
     *
     * @param s tulostettava syöte
     */
    void tulosta(String s);

    /**
     * Lukee käyttäjän syötteen
     *
     * @return käyttäjän antama syöte
     */

    public String lue(); 

}
