package tiralabra.reitinhaku;

/** Solmun luova luokka 
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
     * @param x solmun x-koordinaatin arvo
     * @param y solmun y-koordinaatin arvo
     * @param seina kartan kohta jota ei voi käyttää osana reittiä
     * @param kuljettava kartan kohta joka sopii osaksi reittiä
     * etaisyys alustetaan mahdollisimman isoksi
    */

    public Solmu(int x, int y, boolean seina, boolean kuljettava) {
        this.x = x;
        this.y = y;
        this.seina = seina;
        this.kuljettava = kuljettava;
        this.etaisyys = Integer.MAX_VALUE;
    }

    public boolean getKuljettava() {
        return kuljettava;
    }
}
