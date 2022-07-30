package tiralabra.reitinhaku;

/**
 * Keon luova luokka
 * 
 * Algoritmien tarvitseman minimikeon luova luokka.
 */
public class Keko {

    private Solmu[] keko;
    private int keonKoko;

    /**
     * @param koko kekon koko
     */

    public Keko(int koko) {
        this.keonKoko = -1;
        this.keko = new Solmu[koko];
    }
}
