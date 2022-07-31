package tiralabra.reitinhaku;

/**
 * Solmun luova luokka
 * 
 * Kartan pisteistä solmuja muodostava luokka.
 */

public class Solmu {

    private int x;
    private int y;
    private int etaisyys;
    private boolean seina;
    private boolean kuljettava;

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
    }

    /**
     * @return boolean
     */
    public boolean getKuljettava() {
        return kuljettava;
    }

    /**
     * @return int
     */
    public int getEtaisyys() {
        return etaisyys;
    }

    /**
     * @param etaisyys
     */
    public void paivitaEtaisyys(int etaisyys) {
        this.etaisyys = etaisyys;
    }

}
