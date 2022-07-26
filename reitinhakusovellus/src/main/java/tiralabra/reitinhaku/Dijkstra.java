package tiralabra.reitinhaku;

import java.time.Duration;
import java.time.Instant;

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
    private double kesto;
    private int kasitellyt;
    private double reitinpituus;
    private boolean loytyi;

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
        this.kesto = 0;
        this.kasitellyt = 0;
        this.reitinpituus = lopetus.getEtaisyys();
    }

    /**
     * Metodi käsittelee kartan solmut kekoa hyödyntäen, huolehtii reitinhaun keston
     * mittaamisesta ja reitin tallennuksesta, jos reitti löytyy.
     * 
     * @return boolean Metodi palauttaa totuusarvona tiedon siitä löytääkö
     *         algoritmi reittiä origosta neliönmuotoisen kartan vastakkaiseen
     *         kulmaan.
     * @see Keko
     */
    public boolean etsiLyhyinReitti() {
        Instant alku = Instant.now();
        aloitus.paivitaEtaisyys(0);
        keko.lisaaKekoon(aloitus);
        while (!keko.onTyhja()) {
            Solmu kasiteltava = keko.poistaPienin();
            if (!kasiteltava.getKasitelty()) {
                if (loytyi) {
                    break;
                }
                tutkiNaapurit(kasiteltava);
            }
        }
        Instant loppu = Instant.now();
        this.kesto = Duration.between(alku, loppu).getNano() / 1000000;
        kasitellyt = keko.getLisattyja();
        if (loytyi) {
            tallennaReitti();
            return true;
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
     * @param x           Siirtyminen x-koordinaatistossa.
     * @param y           Siirtyminen y-koordinaatistossa.
     * @return Solmu Palautetaan naapurisolmu, johon löytynyt tiedettyä lyhyempi
     *         etäisyys.
     */
    private Solmu laskeEtaisyysNaapuriin(Solmu kasiteltava, int x, int y) {
        kasiteltava.setKasitelty(true);

        if (kasiteltava.getX() == lopetus.getX() && kasiteltava.getY() == lopetus.getY()) {
            reitinpituus = kasiteltava.getEtaisyys();
            loytyi = true;
        }

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
        aloitus.setReitilla(true);
        Solmu solmu = kartta[lopetus.getX()][lopetus.getY()];
        verkko.lisaaReitti("\nReitti päättyy pisteeseen " + solmu.getX() + "," + solmu.getY()
                + " ja etäisyys alusta on: " + solmu.getEtaisyys());
        solmu.setReitilla(true);
        while (solmu.getEdeltaja() != null && solmu != aloitus) {
            solmu = solmu.getEdeltaja();
            solmu.setReitilla(true);
            if (solmu != lopetus) {
                verkko.lisaaReitti("\nEdellinen piste reitillä " + solmu.getX() + "," + solmu.getY()
                        + " ja etäisyys alusta on: " + solmu.getEtaisyys());
            }
        }
    }

    /**
     * Metodi palauttaa ajon keston
     * 
     * @return double
     */
    public double getKesto() {
        return this.kesto;
    }

    /**
     * Metodi palauttaa keossa käsiteltyjen solmujen määrän
     * 
     * @return double
     */
    public double getKasitellyt() {
        return this.kasitellyt;
    }

    /**
     * Metodi palauttaa joko löydetyn reitin pituuden tai Integer.MAX_VALUE:n
     * 
     * @return double
     */
    public double getReitinPituus() {
        return this.reitinpituus;
    }

    /**
     * Metodi vaihtaa aloitussolmun paikkaa edellyttäen, että uusi aloitus solmu on
     * kuljettava.
     * 
     * @param x Aloitussolmun x-koordinaatti
     * @param y Aloitussolmun y-koordinaatti
     */
    public void setAloitus(int x, int y) {
        if (onkoKuljettava(x, y)) {
            this.aloitus = kartta[x][y];
        }

    }

    /**
     * Metodi vaihtaa lopetussolmun paikkaa edellyttäen, että uusi aloitus solmu on
     * kuljettava.
     * 
     * @param x Lopetussolmun x-koordinaatti
     * @param y Lopetussolmun y-koordinaatti
     */
    public void setLopetus(int x, int y) {
        if (onkoKuljettava(x, y)) {
            this.lopetus = kartta[x][y];
        }

    }

}
