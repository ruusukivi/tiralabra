package tiralabra.reitinhaku;

/**
 * Luokka Dijkstran reitinhaku-algoritmille.
 * 
 */
public class Dijkstra {
    private Verkko verkko;
    private Solmu[][] kartta;
    private int sivu;
    private Keko keko;
    private Solmu aloitus;
    private Solmu lopetus;

    /**
     * Konstruktori Dijkstran reitinhaku-algoritmille.
     * 
     * @param verkko Neliönmuotoinen kartta annetaan luokalle Verkko-oliona. 
     */
    public Dijkstra(Verkko verkko) {
        this.verkko = verkko;
        this.kartta = verkko.getSolmut();
        this.sivu = verkko.getKoko();
        this.keko = new Keko(sivu * sivu);
        this.aloitus = kartta[0][0];
        this.lopetus = kartta[sivu - 1][sivu - 1];
    }

    /**
     * @return boolean Metodi palauttaa totuusarvona tiedon siitä löytyykö 
     * algoritmi reittiä kartan vasemmasta yläkulmasta oikeaan alakulmaan.
     */
    public boolean etsiLyhyinReitti() {
        aloitus.paivitaEtaisyys(0);
        keko.lisaaKekoon(aloitus);

        while (!keko.onTyhja()) {
            Solmu kasiteltava = keko.poistaPienin();
            if (!kasiteltava.getKasitelty()) {
                if (kasiteltava.getX() == lopetus.getX() && kasiteltava.getY() == lopetus.getY()) {
                    tallennaReitti();
                    return true;
                }
                tutkiNaapurit(kasiteltava);
            }
        }
        return false;
    }

    /**
     * @param kasiteltava Metodi tarkistaa kaikki solmut, joita ole vielä käsitelty.
     */
    private void tutkiNaapurit(Solmu kasiteltava) {
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, 0, -1)); // ylös
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, 0, 1)); // alas
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, 1, 0)); // oikealle
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, -1, 0)); // vasemmalle
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, 1, -1)); // oikeayläkulma
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, -1, -1)); // vasen yläkulma
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, 1, 1)); // oikea alakulma
        keko.lisaaKekoon(laskeEtaisyysNaapuriin(kasiteltava, -1, 1)); // vasen alakulma
    }

    /**
     * 
     * @param kasiteltava Solmu jonka naapurisolmuja tutkitaan seuraavaksi.
     * @param x  Siirtyminen x-koordinaatistossa.
     * @param y Siirtyminen y-koordinaatistossa.
     * @return Solmu Palautetaan naapurisolmu, johon löytynyt tiedettyä lyhyempi etäisyys.
     */
    private Solmu laskeEtaisyysNaapuriin(Solmu kasiteltava, int x, int y) {
        kasiteltava.setKasitelty(true);
        int naapurinX = kasiteltava.getX() + x;
        int naapurinY = kasiteltava.getY() + y;
        if (!onkoKuljettava(naapurinX, naapurinY)) {
            return null;
        }
        Solmu naapuri = kartta[naapurinX][naapurinY];
        double etaisyysNaapuriin = 1;
        if (x != 0 && y != 0) {
            etaisyysNaapuriin = Math.sqrt(2);
        }
        if (naapuri.getEtaisyys() > kasiteltava.getEtaisyys() + etaisyysNaapuriin) {
            naapuri.paivitaEtaisyys(kasiteltava.getEtaisyys() + etaisyysNaapuriin);
            naapuri.setEdeltaja(kasiteltava);
            return naapuri;
        }
        return null;
    }

    /**
     * Metodi varmistaa, ettei käsiteltävä solmu ole kartan ulkopuolella tai sisällä
     * seinää.
     * 
     * @param uusiX Potentiaalisen naapurin x-koordinaatti.
     * @param uusiY Potentiaalisen naapurin y-koordinaatti.
     * @return boolean Palauttaa tiedon siitä, onko sijainti mahdollinen.
     */
    private boolean onkoKuljettava(int uusiX, int uusiY) {
        if (uusiX < 0 || uusiY < 0 || uusiX > sivu - 1 || uusiY > sivu - 1) {
            return false;
        }
        if (!kartta[uusiX][uusiY].getKuljettava()) {
            return false;
        }
        return true;
    }

    /**
     * Metodi päivittää solmuihin tiedon reitillä olosta 
     * sekä tallentaa yksityiskohtaiset tiedot reitin pisteistä ja 
     * etäisyyksistä tulostusta varten.
     */

    public void tallennaReitti() {
        aloitus.setDijkstra(true);
        Solmu solmu = kartta[lopetus.getX()][lopetus.getY()];
        verkko.lisaaReittiDijkstra("\nReitti päättyy pisteeseen " + solmu.getX() + "," + solmu.getY()
                + " ja etäisyys alusta on: " + solmu.getEtaisyys());
        solmu.setDijkstra(true);
        while (solmu.getEdeltaja() != null) {
            solmu = solmu.getEdeltaja();
            solmu.setDijkstra(true);
            verkko.lisaaReittiDijkstra("\nSeuraava piste reitillä " + solmu.getX() + "," + solmu.getY()
                    + " ja etäisyys alusta on: " + solmu.getEtaisyys());
        }
        verkko.lisaaReittiDijkstra("\nReitti alkaa pisteestä 0.0 ");
    }

}
