package tiralabra.reitinhaku;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Luokka JumpPointSearch-algoritmille reitinhakuun.
 */
public class JumpPointSearch {
    private Verkko verkko;
    private Solmu[][] kartta;
    private int sivu;
    private Keko keko;
    private Solmu aloitus;
    private Solmu lopetus;
    private boolean loytyi;
    private ArrayList<Solmu> hyppypisteet;
    private double kesto;
    private int kasitellyt;
    private double reitinpituus;

    /**
     * Konstruktori JumpPointSearch-algoritmille.
     * 
     * @param verkko Neliönmuotoinen kartta annetaan luokalle Verkko-oliona.
     */
    public JumpPointSearch(Verkko verkko) {
        this.verkko = verkko;
        this.kartta = verkko.getSolmut();
        this.sivu = verkko.getKoko();
        this.keko = new Keko(sivu * sivu);
        this.aloitus = kartta[0][0];
        this.lopetus = kartta[sivu - 1][sivu - 1];
        this.loytyi = false;
        this.hyppypisteet = new ArrayList<>();
        this.kesto = 0;
        this.kasitellyt = 0;
        // jos reittiä ei löydy pituus on Integer.MAX_VALUE
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
        Instant alku = Instant.now(); // Aloitetaan suoritusajan mittaus
        aloitus.paivitaEtaisyys(0);
        aloitus.setEdeltaja(aloitus);
        keko.lisaaKekoon(aloitus);
        while (!keko.onTyhja()) {
            Solmu kasiteltava = keko.poistaPienin();
            etsiHyppyPisteet(kasiteltava, 0, 0);
        }
        reitinpituus = lopetus.getEtaisyys();
        Instant loppu = Instant.now(); // Lopetetaan suoritusajan mittaus
        kesto = Duration.between(alku, loppu).getNano() / 1000000;
        kasitellyt = keko.getLisattyja();
        if (loytyi) {
            tallennaReitti();
            return true;
        }
        return false;
    }

    /**
     * @param kasiteltava Metodi tarkistaa kaikki solmut, joita ei ole vielä
     *                    käsitelty.
     */
    private void etsiHyppyPisteet(Solmu kasiteltava, int x, int y) {
        if (kasiteltava.getKasitelty()) {
            return;
        }
        kasiteltava.setKasitelty(true);
        keko.lisaaKekoon(etsiVertikaalisesti(kasiteltava, -1)); // ylös
        keko.lisaaKekoon(etsiVertikaalisesti(kasiteltava, 1)); // alas
        keko.lisaaKekoon(etsiHorisontaalisesti(kasiteltava, 1)); // oikealle
        keko.lisaaKekoon(etsiHorisontaalisesti(kasiteltava, -1)); // vasemmalle

        if (x == 0 && y == 0) {
            etsiDiagonaalisesti(kasiteltava, 1, 1);
            etsiDiagonaalisesti(kasiteltava, 1, -1);
            etsiDiagonaalisesti(kasiteltava, -1, 1);
            etsiDiagonaalisesti(kasiteltava, -1, -1);
        } else {
            etsiDiagonaalisesti(kasiteltava, x, y);
        }
    }

    /**
     * Etsitään vertikaalisesti löytyykö maalia tai hyppysolmuja.
     * Jos hyppysolmu löytyy, palautetaan se jatkotarkastelua varten kekoon.
     * Jos ei löydy, jatketaan etsintää niin pitkään kuin reitti on seinätön ja
     * kartalla.
     * 
     * @param kasiteltava Solmu josta etsintä aloitetaan
     * @param y Siirtymän pituus y-akselilla
     * @return Solmu Palautetaan löytynyt hyppysolmu
     */
    private Solmu etsiVertikaalisesti(Solmu kasiteltava, int y) {
        // Tutkitaan päästäänkö vertikaalisesti etenemään
        int seuraavaX = kasiteltava.getX();
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null;
        }
        // Päivitetään uusi etäisyys jos se on pienempi kuin aiemmin löydetty
        if (kartta[seuraavaX][seuraavaY].getEtaisyys() > kasiteltava.getEtaisyys() + 1) {
            kartta[seuraavaX][seuraavaY].setEdeltaja(kasiteltava);
            kartta[seuraavaX][seuraavaY].paivitaEtaisyys(kasiteltava.getEtaisyys() + 1);
        } else {
            return null; // Palataan koska tästä suunnasta ei löydy lyhyempää reittiä.
        }
        if (onkoMaalissa(kartta[seuraavaX][seuraavaY])) {
            return kartta[seuraavaX][seuraavaY];
        }

        // Tutkitaan onko solmu hyppypiste
        if (kartta[seuraavaX][seuraavaY].getX() + 1 <= sivu - 1 && kartta[seuraavaX][seuraavaY].getY() + 1 <= sivu - 1) {
            boolean kuljettavaOikealle = kartta[kartta[seuraavaX][seuraavaY].getX() + 1][kartta[seuraavaX][seuraavaY].getY() + 1].getKuljettava();
            boolean  seinaOikeallaTakana = !(kartta[kartta[seuraavaX][seuraavaY].getX() + 1][kartta[seuraavaX][seuraavaY].getY()].getKuljettava());
            if (kuljettavaOikealle && seinaOikeallaTakana) {
                kartta[seuraavaX][seuraavaY].paivitaPrioriteetti(kartta[seuraavaX][seuraavaY].getEtaisyys() + laskeDiagonaalinenEtaisyys(kartta[seuraavaX][seuraavaY]));
                hyppypisteet.add(kartta[seuraavaX][seuraavaY]);
                return kartta[seuraavaX][seuraavaY]; // Palautetaan löydetty hyppysolmu
            }
        }
        if (kartta[seuraavaX][seuraavaY].getX() - 1 >= 0 && kartta[seuraavaX][seuraavaY].getY() + 1 <= sivu - 1) {
            boolean  kuljettavaVasemmalle = kartta[kartta[seuraavaX][seuraavaY].getX() - 1][kartta[seuraavaX][seuraavaY].getY() + 1].getKuljettava();
            boolean  seinaVasemmallaTakana = !(kartta[kartta[seuraavaX][seuraavaY].getX() - 1][kartta[seuraavaX][seuraavaY].getY()].getKuljettava());
            if (kuljettavaVasemmalle && seinaVasemmallaTakana) {
                kartta[seuraavaX][seuraavaY].paivitaPrioriteetti(kartta[seuraavaX][seuraavaY].getEtaisyys() + laskeDiagonaalinenEtaisyys(kartta[seuraavaX][seuraavaY]));
                hyppypisteet.add(kartta[seuraavaX][seuraavaY]);
                return kartta[seuraavaX][seuraavaY]; // Palautetaan löydetty hyppysolmu
            }
        }
        return etsiVertikaalisesti(kartta[seuraavaX][seuraavaY], y); //Ei löytynyt hyppysolmua, otetaan uusi askel samaan suuntaan
    }

    /**
     * Etsitään horisontaalisesti löytyykö maalia tai hyppysolmuja.
     * Jos hyppysolmu löytyy, palautetaan se jatkotarkastelua varten kekoon.
     * Jos ei löydy, jatketaan etsintää niin pitkään kuin reitti on seinätön ja
     * kartalla.
     * 
     * @param kasiteltava Solmu josta etsintä alkaa
     * @param x Siirtymän pituus x-akselilla
     * @return Solmu Palautetaan löytynyt hyppysolmu
     */
    private Solmu etsiHorisontaalisesti(Solmu kasiteltava, int x) {
        // Tutkitaan päästäänkö horisontaalisesti etenemään
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY();
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null;
        }
        // Päivitetään uusi etäisyys jos se on pienempi kuin aiemmin löydetty
        if (kartta[seuraavaX][seuraavaY].getEtaisyys() > kasiteltava.getEtaisyys() + 1) {
            kartta[seuraavaX][seuraavaY].setEdeltaja(kasiteltava);
            kartta[seuraavaX][seuraavaY].paivitaEtaisyys(kasiteltava.getEtaisyys() + 1);
        } else {
            return null; // Palataan koska tästä suunnasta ei löydy lyhyempää reittiä.
        }

        if (onkoMaalissa(kartta[seuraavaX][seuraavaY])) {
            return kartta[seuraavaX][seuraavaY];
        }
        // Tutkitaan onko solmu hyppypiste
        if (kartta[seuraavaX][seuraavaY].getX() + 1 <= sivu - 1 && kartta[seuraavaX][seuraavaY].getY() + 1 <= sivu - 1) {
            boolean kuljettavaYlos = kartta[kartta[seuraavaX][seuraavaY].getX() + 1][kartta[seuraavaX][seuraavaY].getY() + 1].getKuljettava();
            boolean seinaAlhaallaTakana = !(kartta[kartta[seuraavaX][seuraavaY].getX()][kartta[seuraavaX][seuraavaY].getY() + 1].getKuljettava());
            if (kuljettavaYlos && seinaAlhaallaTakana) {
                kartta[seuraavaX][seuraavaY].paivitaPrioriteetti(kartta[seuraavaX][seuraavaY].getEtaisyys() + laskeDiagonaalinenEtaisyys(kartta[seuraavaX][seuraavaY]));
                hyppypisteet.add(kartta[seuraavaX][seuraavaY]);
                return kartta[seuraavaX][seuraavaY]; // Palautetaan löydetty hyppysolmu
            }
        }
        if (kartta[seuraavaX][seuraavaY].getX() - 1 >= 0 && kartta[seuraavaX][seuraavaY].getY() - 1 >= 0) {
            boolean kuljettavaAlas = kartta[kartta[seuraavaX][seuraavaY].getX() - 1][kartta[seuraavaX][seuraavaY].getY() - 1].getKuljettava();
            boolean seinaYlhaallaTakana = !(kartta[kartta[seuraavaX][seuraavaY].getX()][kartta[seuraavaX][seuraavaY].getY() - 1].getKuljettava());
            if (kuljettavaAlas && seinaYlhaallaTakana) {
                kartta[seuraavaX][seuraavaY].paivitaPrioriteetti(kartta[seuraavaX][seuraavaY].getEtaisyys() + laskeDiagonaalinenEtaisyys(kartta[seuraavaX][seuraavaY]));
                hyppypisteet.add(kartta[seuraavaX][seuraavaY]);
                return kartta[seuraavaX][seuraavaY]; // Palautetaan löydetty hyppysolmu
            }
        }
        return etsiHorisontaalisesti(kartta[seuraavaX][seuraavaY], x); //Ei löytynyt hyppysolmua, otetaan uusi askel samaan suuntaan
    }

    /**
     * Kun etsitty ensin vertikaalisesti ja horisontaalisesti siirrytään
     * diagonaalisesti seuraavaan solmuun ja aloitetaan uudestaan etsintä
     * 
     * @param kasiteltava Solmu josta etsintä alkaa
     * @param x Siirtymä x-akselilla
     * @param y Siirtymä y-akselilla
     */
    private void etsiDiagonaalisesti(Solmu kasiteltava, int x, int y) {
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return;
        }
        if (kartta[seuraavaX][seuraavaY].getEtaisyys() > kasiteltava.getEtaisyys() + Math.sqrt(2)) {
            kartta[seuraavaX][seuraavaY].setEdeltaja(kasiteltava);
            kartta[seuraavaX][seuraavaY].paivitaEtaisyys(kasiteltava.getEtaisyys() + Math.sqrt(2));
        } else {
            return;
        }
        if (onkoMaalissa(kartta[seuraavaX][seuraavaY])) {
            return;
        }
        etsiHyppyPisteet(kartta[seuraavaX][seuraavaY], x, y);
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
     * Metodi tarkistaa onko käsiteltävä solmu sama kuin lopetussolmu
     * @param tutkittava
     * @return boolean 
     */
    private boolean onkoMaalissa(Solmu tutkittava) {
        if (tutkittava.getX() == lopetus.getX() && tutkittava.getY() == lopetus.getY()) {
            System.out.print("Reitti löytyi");
            loytyi = true;
            reitinpituus = tutkittava.getEtaisyys();
            return true;
        }
        return false;
    }

    /**
     * Heuristisena funktiona etäisyyden ennustamisessa käytetään diagonaalista
     * etäisyyttä.
     * ks. http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html
     * Funktio pyrkii "arvaamaan", millä todennäköisyydellä tutkittava solmu
     * johtaa reitin lopetussolmuun.
     * 
     * @param solmu Tutkittava solmu
     * @return double Palauttaa Diagonaalisen etäisyyden
     */
    private double laskeDiagonaalinenEtaisyys(Solmu solmu) {
        double dx = Math.abs(solmu.getX() - lopetus.getX());
        double dy = Math.abs(solmu.getY() - lopetus.getY());
        return (dx + dy) + (Math.sqrt(2) - 2) * Math.min(dx, dy);
    }

    /**
     * Metodi päivittää solmuihin tiedon reitillä olosta
     * sekä tallentaa yksityiskohtaiset tiedot reitin pisteistä ja
     * etäisyyksistä tulostusta varten.
     */
    public void tallennaReitti() {

        lopetus.setReitilla(true);
        verkko.lisaaReitti("\nReitti päättyy pisteeseen " + lopetus.getX() + "," + lopetus.getY()
                + " ja etäisyys alusta on: " + lopetus.getEtaisyys());
        Solmu edeltaja = lopetus.getEdeltaja();
        int i = 0;
        while (i < sivu * sivu) { 
            if (edeltaja == aloitus || edeltaja == null) {
                break;
            }
            edeltaja.setReitilla(true);
            verkko.lisaaReitti("\nEdellinen piste reitillä " + edeltaja.getX() + "," + edeltaja.getY()
                    + " ja etäisyys alusta on: " + edeltaja.getEtaisyys());
            edeltaja = edeltaja.getEdeltaja();
            i++;
        }
        aloitus.setReitilla(true);
        verkko.lisaaReitti("\nEdellinen piste reitillä 0,0 ja etäisyys alusta on: 0.0");
    }

    /**
     * Metodi palauttaa ajon keston.
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
            System.out.print("Lopetus asetettu");
            this.lopetus = kartta[x][y];
        }

    }
}
