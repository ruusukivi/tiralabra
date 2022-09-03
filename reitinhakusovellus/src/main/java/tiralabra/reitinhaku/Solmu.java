package tiralabra.reitinhaku;

/**
 * Kartan pisteistä solmuja muodostava luokka.
 */

public class Solmu {

    private int x;
    private int y;
    private boolean seina;
    private boolean kuljettava;
    private Solmu edeltaja;
    private boolean kasitelty;
    private double etaisyys;
    private boolean reitilla;
    private double prioriteetti;

    /**
     * @param x          Solmun x-koordinaatin arvo.
     * @param y          Solmun y-koordinaatin arvo.
     * @param seina      Kartan kohta jota ei voi käyttää osana reittiä.
     * @param kuljettava Kartan kohta joka sopii osaksi reittiä.
     * @throws IllegalArgumentException Antaa poikkeuksen, jos yritetään lisätä
     *                                  solmu, joka on sekä seinä että kuljettava
     *                                  tai ei kumpikaan.
     */

    public Solmu(int x, int y, boolean seina, boolean kuljettava) throws IllegalArgumentException {
        if (seina && kuljettava || !seina && !kuljettava) {
            throw new IllegalArgumentException("Virhe: Ruutu ei voi olla sekä seinä että kuljettava.");
        }
        this.x = x;
        this.y = y;
        this.seina = seina;
        this.kuljettava = kuljettava;
        this.etaisyys = Integer.MAX_VALUE;
        this.reitilla = false;
        this.kasitelty = false;
        this.prioriteetti = 1;
    }

    /**
     * @return int Palauttaa solmun y-koordinaatin.
     */
    public int getY() {
        return y;
    }

    /**
     * @return int Palauttaa solmun x-koordinaatin.
     */
    public int getX() {
        return x;
    }

    /**
     * @return boolean Palauttaa tiedon, onko solmu reitiin sopiva.
     */
    public boolean getKuljettava() {
        return this.kuljettava;
    }

    /**
     * @param edeltaja Kertoo mikä solmu edeltää reitillä tätä solmua.
     */
    public void setEdeltaja(Solmu edeltaja) {
        this.edeltaja = edeltaja;
    }

    /**
     * @return Solmu Palauttaa solmun joka edeltää reitillä tätä solmua.
     */
    public Solmu getEdeltaja() {
        return edeltaja;
    }

    /**
     * @param reitilla Määrittelee onko solmu algoritmin löytämällä reitillä.
     */
    public void setReitilla(boolean reitilla) {
        this.reitilla = true;
    }

    /**
     * @return boolean Kertoo onko solmu algoritmin löytämällä reitillä.
     */
    public boolean getReitilla() {
        return this.reitilla;
    }

    /**
     * @param kasitelty Metodi jolla algoritmi asettaa solmun käsitellyksi.
     */
    public void setKasitelty(boolean kasitelty) {
        this.kasitelty = true;
    }

    /**
     * @return boolean Kertoo onko algoritmi jo käsitellyt tämän solmun.
     */
    public boolean getKasitelty() {
        return this.kasitelty;
    }

    /**
     * @param etaisyys Metodi jolla algoritmi päivittää solmulle etäisyyden reitin
     *                 alkusolmuun (x= 0, y=0).
     */
    public void paivitaEtaisyys(double etaisyys) {
        this.etaisyys = etaisyys;
    }

    /**
     * @return double Kertoo solmun etäisyyden reitin alkusolmuun (x= 0, y=0).
     */
    public double getEtaisyys() {
        return etaisyys;
    }

    /**
     * @param prioriteetti Metodi jolla JumpPointSearch päivittää solmun
     *                     prioriteetin sen perusteella mikä on arvioitu etäisyys
     *                     loppusolmuun.
     * @see JumpPointSearch
     */
    public void paivitaPrioriteetti(double prioriteetti) {
        this.prioriteetti = prioriteetti;
    }

    /**
     * @return double Kertoo solmun arvioidun etäisyyden JPS-algoritmia
     *         varten.
     * @see JumpPointSearch
     */
    public double getPrioriteetti() {
        return prioriteetti;
    }

}
