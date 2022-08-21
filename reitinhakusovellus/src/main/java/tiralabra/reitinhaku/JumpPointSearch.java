package tiralabra.reitinhaku;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

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
        // this.reitilla = new ArrayList<>();
        this.hyppypisteet = new ArrayList<>();
        this.kesto = 0;
        this.kasitellyt = 0;
        // jos reittiä ei löydy pituus on Integer.MAX_VALUE
        this.reitinpituus = lopetus.getEtaisyys();
    }

    /**
     * @return boolean Metodi palauttaa totuusarvona tiedon siitä löytääkö
     *         algoritmi reittiä kartan pisteestä 0.0 vastakkaiseen kulmaan.
     */
    public boolean etsiLyhyinReitti() {
        Instant alku = Instant.now(); // Aloitetaan suoritusajan mittaus
        aloitus.paivitaEtaisyys(0);
        aloitus.setEdeltaja(aloitus);
        keko.lisaaKekoon(aloitus);
        kasitellyt++;
        while (!keko.onTyhja()) {
            if (loytyi) {
                break;
            }
            Solmu kasiteltava = keko.poistaPienin();
            etsiHyppyPisteet(kasiteltava, 0, 0);
        }
        reitinpituus = lopetus.getEtaisyys();
        Instant loppu = Instant.now(); // Lopetetaan suoritusajan mittaus
        kesto = Duration.between(alku, loppu).getNano() / 1000000;
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
        while (!loytyi) {
            Solmu edeltaja = kasiteltava.getEdeltaja();
            //System.out.print("\nKäsittelyyn: " + kasiteltava.getX() + ", " +
            //        kasiteltava.getY() + ", etäisyys: " + kasiteltava.getEtaisyys());
            //System.out.print("\nEdeltäjä: " + edeltaja.getX() + ", " +
            //        edeltaja.getY() + ", etäisyys: " + edeltaja.getEtaisyys());
            if (onkoMaalissa(kasiteltava)) {
                break;
            }
            if (!kasiteltava.getKasitelty()) {
                etsiVertikaalisesti(edeltaja, kasiteltava, -1); // ylös
                etsiVertikaalisesti(edeltaja, kasiteltava, 1); // alas
                etsiHorisontaalisesti(edeltaja, kasiteltava, 1); // oikealle
                etsiHorisontaalisesti(edeltaja, kasiteltava, -1); // vasemmalle

                if (x == 0 && y == 0) {
                    etsiDiagonaalisesti(edeltaja, kasiteltava, 1, 1);
                    etsiDiagonaalisesti(edeltaja, kasiteltava, 1, -1);
                    etsiDiagonaalisesti(edeltaja, kasiteltava, -1, 1);
                    etsiDiagonaalisesti(edeltaja, kasiteltava, -1, -1);
                } else {
                    etsiDiagonaalisesti(edeltaja, kasiteltava, x, y);
                }
            }
            return;
        }
        return;
    }

    /**
     * Etsitään vertikaalisesti löytyykö maalia tai hyppysolmuja.
     * Jos hyppysolmu löytyy, palautetaan se jatkotarkastelua varten kekoon.
     * Jos ei löydy, jatketaan etsintää niin pitkään kuin reitti on seinätön ja
     * kartalla.
     * 
     * @param edeltaja
     * @param kasiteltava
     * @return Solmu Palautetaan löytynyt hyppysolmu
     */
    private Solmu etsiVertikaalisesti(Solmu edeltaja, Solmu kasiteltava, int y) {
        if (onkoMaalissa(kasiteltava)) {
            return null;
        }
        kasiteltava.setKasitelty(true);
        kasiteltava.setEdeltaja(edeltaja);
        // Päivitetään etäisyys jos löytyy lyhyempi
        if (kasiteltava != aloitus && kasiteltava.getEtaisyys() > edeltaja.getEtaisyys() + 1) {
            kasiteltava.paivitaEtaisyys(edeltaja.getEtaisyys() + 1);
        }

        // Tutkitaan päästäänkö samaan suutaan vielä etenemään
        int seuraavaX = kasiteltava.getX();
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null;
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        if (seuraava.getKasitelty() || edeltaja == seuraava) {
            return null;
        }
        seuraava.setEdeltaja(kasiteltava);

        // System.out.print(
        // "\nEtsitään vertikaalisesti " + kasiteltava.getX() + ", " +
        // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", y:n
        // muutos: " + y);

        // Tutkitaan löytyykö hyppypisteitä vasemmalta tai oikealta
        boolean kuljettavaOikealle = false;
        boolean seinaOikeallaTakana = false;
        boolean kuljettavaVasemmalle = false;
        boolean seinaVasemmallaTakana = false;

        if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + y <= sivu - 1) {
            kuljettavaOikealle = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
            seinaOikeallaTakana = !(kartta[kasiteltava.getX() + 1][kasiteltava.getY()].getKuljettava());
            if (kuljettavaOikealle && seinaOikeallaTakana) {
                // System.out.print(" Löydettiin naapuri oikealta");
                Solmu hyppypiste = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1];
                lisaaHyppypiste(kasiteltava, hyppypiste);
                //return hyppypiste;
            }
        }
        if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() + y <= sivu - 1) {
            kuljettavaVasemmalle = kartta[kasiteltava.getX() - 1][kasiteltava.getY() + 1].getKuljettava();
            seinaVasemmallaTakana = !(kartta[kasiteltava.getX() - 1][kasiteltava.getY()].getKuljettava());
            if (kuljettavaVasemmalle && seinaVasemmallaTakana) {
                // System.out.print(" Löydettiin naapuri vasemmalta");
                Solmu hyppypiste = kartta[kasiteltava.getX() - 1][kasiteltava.getY() + 1];
                lisaaHyppypiste(kasiteltava, hyppypiste);
                //return hyppypiste;
            }
        }
        return etsiVertikaalisesti(kasiteltava, seuraava, y); // Ei löytynyt hyppypisteitä, jatketaan
    }

    /**
     * Etsitään horisontaalisesti löytyykö maalia tai hyppysolmuja.
     * Jos hyppysolmu löytyy, palautetaan se jatkotarkastelua varten kekoon.
     * Jos ei löydy, jatketaan etsintää niin pitkään kuin reitti on seinätön ja
     * kartalla.
     * 
     * @param edeltaja
     * @param kasiteltava
     * @return Solmu Palautetaan löytynyt hyppysolmu
     */
    private Solmu etsiHorisontaalisesti(Solmu edeltaja, Solmu kasiteltava, int x) {
        if (onkoMaalissa(kasiteltava)) {
            return null;
        }
        kasiteltava.setKasitelty(true);
        kasiteltava.setEdeltaja(edeltaja);

        if (kasiteltava != aloitus && kasiteltava.getEtaisyys() > kasiteltava.getEdeltaja().getEtaisyys() + 1) {
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + 1);
        }

        // Tutkitaan päästäänkö samaan suutaan vielä etenemään
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY();
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null;
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        seuraava.setEdeltaja(kasiteltava);
        if (seuraava.getKasitelty()) {
            return null;
        }

        // System.out.print(
        // "\nEtsitään horisontaalisesti " + kasiteltava.getX() + ", " +
        // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", uusi x:
        // " + x);

        // Tutkitaan löytyykö hyppypisteitä ylhäältä tai alhaalta
        boolean kuljettavaYlos = false;
        boolean seinaAlhaallaTakana = false;
        boolean kuljettavaAlas = false;
        boolean seinaYlhaallaTakana = false;

        if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + 1 <= sivu - 1) {
            kuljettavaYlos = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
            seinaAlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() + 1].getKuljettava());
            if (kuljettavaYlos && seinaAlhaallaTakana) {
                Solmu hyppypiste = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1];
                lisaaHyppypiste(kasiteltava, hyppypiste);
                //return hyppypiste;
            }
        }
        if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() - 1 >= 0) {
            kuljettavaAlas = kartta[kasiteltava.getX() - 1][kasiteltava.getY() - 1].getKuljettava();
            seinaYlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() - 1].getKuljettava());
            if (kuljettavaAlas && seinaYlhaallaTakana) {
                Solmu hyppypiste = kartta[kasiteltava.getX() - 1][kasiteltava.getY() - 1];
                lisaaHyppypiste(kasiteltava, hyppypiste);
                //return hyppypiste;
            }
        }
        return etsiHorisontaalisesti(kasiteltava, seuraava, x); // Ei löytynyt tutkittavaa, jatketaan
    }

    /**
     * Kun etsitty ensin vertikaalisesti ja horisontaalisesti siirrytään
     * diagonaalisesti seuraavaan solmuun ja aloitetaan uudestaan etsintä
     * @param edeltaja
     * @param kasiteltava
     */
    private void etsiDiagonaalisesti(Solmu edeltaja, Solmu kasiteltava, int x, int y) {
        kasiteltava.setKasitelty(true);
        kasiteltava.setEdeltaja(edeltaja);
        if (onkoMaalissa(kasiteltava)) {
            return;
        }
        if (kasiteltava != aloitus
                || kasiteltava.getEtaisyys() > kasiteltava.getEdeltaja().getEtaisyys() + Math.sqrt(2)) {
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + Math.sqrt(2));
        }
        //System.out.print("\nEtsitään diagonaalisesti " + kasiteltava.getX() + ", " +
        //        kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", liikutaan " + x + "," + y);
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            // System.out.print(" Ei kartalla!");
            return; 
        }
        Solmu seuraava = kartta[kasiteltava.getX() + x][kasiteltava.getY() + y];
        // Jos solmulla on jo edeltäjä, ei vaihdeta sitä ellei etäisyys ole lyhyempi
        if (seuraava.getEdeltaja() != null) {
            if (seuraava.getEtaisyys() > kasiteltava.getEtaisyys() + +Math.sqrt(2)) {
                seuraava.setEdeltaja(kasiteltava);
            }
        } else {
            seuraava.setEdeltaja(kasiteltava);
        }
        etsiHyppyPisteet(seuraava, x, y);
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
            // System.out.print(" Ei kartalla!");
            return false;
        }
        if (!kartta[uusiX][uusiY].getKuljettava()) {
            // System.out.print(" Seinä!");
            return false;
        }
        return true;
    }

    /**
     * Metodi lisää hyppypisteen kekoon, jos löydetty piste on uusi.
     * Hyppypisteelle lasketaan prioriteetti sen mukaan kuinka lyhyt arvioitu
     * etäisyys lopetuspisteeseen on.
     * 
     * @param edeltaja   Solmu josta hyppypisteeseen siirryttiin
     * @param hyppypiste Löydetty hyppypiste
     */
    private void lisaaHyppypiste(Solmu edeltaja, Solmu hyppypiste) {
        if (onkoMaalissa(hyppypiste)) {
            return;
        }
        if (!hyppypiste.getKasitelty()) {
            hyppypiste.setEdeltaja(edeltaja);
            hyppypiste.paivitaEtaisyys(edeltaja.getEtaisyys() + Math.sqrt(2));
            hyppypisteet.add(hyppypiste);
            hyppypiste.paivitaPrioriteetti(hyppypiste.getEtaisyys() + laskeDiagonaalinenEtaisyys(hyppypiste));
            keko.lisaaKekoon(hyppypiste);
            kasitellyt++;
        }
    }

    /**
     * Metodi tarkistaa onko käsiteltävä solmu sama kuin lopetussolmu
     * 
     * @param tutkittava
     * @return boolean
     */
    private boolean onkoMaalissa(Solmu tutkittava) {
        if (tutkittava.getX() == lopetus.getX() && tutkittava.getY() == lopetus.getY()) {
            loytyi = true;
            // päivitetään lopetussolmun etäisyys sen mukaan kuinka pitkä siirtymä on
            // KORJATTAVAA? Löytyykö lyhyin reitti maalisolmuun aina ensimmäisenä?

            if (tutkittava.getX() == tutkittava.getEdeltaja().getX()) {
                double siirtyma = Math.abs(tutkittava.getEdeltaja().getY() - tutkittava.getY());
                System.out.print(" Maali - sama x - lisätään: " + siirtyma);
                tutkittava.paivitaEtaisyys(tutkittava.getEdeltaja().getEtaisyys() + siirtyma);
            } else if (tutkittava.getY() == tutkittava.getEdeltaja().getY()) {
                double siirtyma = Math.abs(tutkittava.getEdeltaja().getX() - tutkittava.getX());
                System.out.print(" Maali - sama Y - lisätään: " + siirtyma);
                tutkittava.paivitaEtaisyys(tutkittava.getEdeltaja().getEtaisyys() + siirtyma);
            } else {
                tutkittava.paivitaEtaisyys(tutkittava.getEdeltaja().getEtaisyys() + Math.sqrt(2));
                System.out.print(" Maali - diagonaalinen siirto - lisätään: " + Math.sqrt(2));
            }
            reitinpituus = tutkittava.getEtaisyys();
            System.out.print(" Maali löytyi!" + tutkittava.getEtaisyys());
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
     * TÄLLÄ HETKELLÄ: mukana vain alku-, loppu- ja hyppysolmut.
     */

    public void tallennaReitti() {
        aloitus.setReitilla(true);
        verkko.lisaaReitti("\nReitti päättyy pisteeseen " + lopetus.getX() + "," + lopetus.getY()
                + " ja etäisyys alusta on: " + lopetus.getEtaisyys());
        for (Solmu solmu : hyppypisteet) {
            solmu.setReitilla(true);
            verkko.lisaaReitti("\nTunnistettu hyppypiste reitillä " + solmu.getX() + "," + solmu.getY()
                    + " ja etäisyys alusta on: " + solmu.getEtaisyys()
                    + "\nHyppypisteen diagonaalinen etäisyys loppuun on:  "
                    + solmu.getPrioriteetti());
        }
        lopetus.setReitilla(true);
        verkko.lisaaReitti("\nReitti alkaa pisteestä 0.0 ");
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
}
