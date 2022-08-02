package tiralabra.reitinhaku;

/** Verkon luova luokka
 * 
 * Kartasta verkon muodostava luokka.
 */

public class Verkko {
    public String nimi;
    public int koko;
    public Solmu[][] solmut;

    /**
     * Metodi luo tyhjän matriisitaulukon verkolle
     * 
     * @param koko kartan sivun pituus, joka tarvitaan matriisitaulukon luontiin
     * @param nimi verkon nimi helpottamaan hakua session aikana
     */

    public Verkko(int koko, String nimi) {
        this.koko = koko;
        this.nimi = nimi;
        this.solmut = new Solmu[koko][koko]; 
    }

    /**
     * Metodi luo uuden solmun ja lisää sen verkkoon
     * 
     * @param x          solmun x-koordinaatin arvo
     * @param y          solmun y-koordinaatin arvo
     * @param seina      kartan kohta jota ei voi käyttää osana reittiä
     * @param kuljettava kartan kohta joka sopii osaksi reittiä
     */

    public void lisaaSolmu(int x, int y, boolean seina, boolean kuljettava) {
        this.solmut[x][y] = new Solmu(x, y, seina, kuljettava);
    }

    
    /** Solmujen määrä.
     * @return int palauttaa verkossa olevien solmujen määrän.
     */
    public int solmujenMaara() {
        return this.koko * this.koko;
    }

    /** 
     * @return int palauttaa verkon koon.
     */
    public int getKoko() {
        return this.koko;
    }

}
