package tiralabra.reitinhaku;

/**
 * Solmun luova luokka
 * 
 * Kartan pisteistä solmuja muodostava luokka.
 */

public class Solmu {

    private int x;
    private int y;
    private Solmu vanhempi;
    private double etaisyys;
    private boolean seina;
    private boolean kuljettava;
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
        return kuljettava;
    }

    public void setVanhempi(Solmu vanhempi){
        this.vanhempi = vanhempi;
    }

    public Solmu getVanhempi(){
        return vanhempi;
    }

    public void setDijkstra(boolean reitilla) {
        this.dijkstra = true;
    }

    public boolean getDijkstra() {
        return true;
    }

    public void setJps(boolean reitilla) {
        this.jps = true;
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
