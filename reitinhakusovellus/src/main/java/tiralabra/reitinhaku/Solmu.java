package tiralabra.reitinhaku;

/**
 * Solmun luova luokka
 * 
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
    private boolean dijkstra;
    private boolean jps;

    /**
     * @param x          solmun x-koordinaatin arvo
     * @param y          solmun y-koordinaatin arvo
     * @param seina      kartan kohta jota ei voi käyttää osana reittiä
     * @param kuljettava kartan kohta joka sopii osaksi reittiä
     *                   etaisyys alustetaan mahdollisimman isoksi
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
        this.dijkstra = false;
        this.kasitelty = false;
    }

    /**
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * @return boolean
     */
    public boolean getKuljettava() {
        return this.kuljettava;
    }

    /**
     * @param edeltaja
     */
    public void setEdeltaja(Solmu edeltaja) {
        this.edeltaja = edeltaja;
    }

    /**
     * @return Solmu
     */
    public Solmu getEdeltaja() {
        return edeltaja;
    }

    /**
     * @param reitilla
     */
    public void setDijkstra(boolean reitilla) {
        this.dijkstra = true;
    }

    /**
     * @return boolean
     */
    public boolean getDijkstra() {
        return this.dijkstra;
    }

    /**
     * @param kasitelty
     */
    public void paivitaKasitelty(boolean kasitelty) {
        this.kasitelty = true;
    }

    /**
     * @return boolean
     */
    public boolean getKasitelty() {
        return this.kasitelty;
    }

    /**
     * @param double
     */
    public void paivitaEtaisyys(double etaisyys) {
        this.etaisyys = etaisyys;
    }

    /**
     * @return double
     */
    public double getEtaisyys() {
        return etaisyys;
    }

}
