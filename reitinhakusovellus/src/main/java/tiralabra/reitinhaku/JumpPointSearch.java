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
        Solmu edeltaja = kasiteltava.getEdeltaja();
        // System.out.print("\nKäsittelyyn: " + kasiteltava.getX() + ", " +
        // kasiteltava.getY() + ", etäisyys: " + kasiteltava.getEtaisyys());
        // System.out.print("\nEdeltäjä: " + edeltaja.getX() + ", " +
        // edeltaja.getY() + ", etäisyys: " + edeltaja.getEtaisyys());

        keko.lisaaKekoon(etsiVertikaalisesti(edeltaja, kasiteltava, -1)); // ylös
        keko.lisaaKekoon(etsiVertikaalisesti(edeltaja, kasiteltava, 1)); // alas
        keko.lisaaKekoon(etsiHorisontaalisesti(edeltaja, kasiteltava, 1)); // oikealle
        keko.lisaaKekoon(etsiHorisontaalisesti(edeltaja, kasiteltava, -1)); // vasemmalle

        if (x == 0 && y == 0) {
            etsiDiagonaalisesti(edeltaja, kasiteltava, 1, 1);
            etsiDiagonaalisesti(edeltaja, kasiteltava, 1, -1);
            etsiDiagonaalisesti(edeltaja, kasiteltava, -1, 1);
            etsiDiagonaalisesti(edeltaja, kasiteltava, -1, -1);
        } else {
            etsiDiagonaalisesti(edeltaja, kasiteltava, x, y);
        }
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
        // Päivitetään etäisyys jos löytyy lyhyempi
        if (kasiteltava != aloitus && kasiteltava != edeltaja.getEdeltaja()
                && kasiteltava.getEtaisyys() > edeltaja.getEtaisyys() + 1) {
            kasiteltava.setEdeltaja(edeltaja);
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

        // System.out.print("\nEtsitään vertikaalisesti " + kasiteltava.getX() + ", " +
        // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", y:n
        // muutos: " + y);

        // Tutkitaan löytyykö hyppypisteitä vasemmalta tai oikealta
        if (!hyppypisteet.contains(kasiteltava)) {

            boolean kuljettavaOikealle = false;
            boolean seinaOikeallaTakana = false;
            boolean kuljettavaVasemmalle = false;
            boolean seinaVasemmallaTakana = false;

            if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + 1 <= sivu - 1) {
                kuljettavaOikealle = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
                seinaOikeallaTakana = !(kartta[kasiteltava.getX() + 1][kasiteltava.getY()].getKuljettava());
                if (kuljettavaOikealle && seinaOikeallaTakana) {
                    kasiteltava
                            .paivitaPrioriteetti(kasiteltava.getEtaisyys() + laskeDiagonaalinenEtaisyys(kasiteltava));
                    hyppypisteet.add(kasiteltava);
                    // System.out.print("\nLöytyi hyppypiste " + kasiteltava.getX() + ", " +
                    // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys());
                    return kasiteltava;
                }
            }
            if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() + 1 <= sivu - 1) {
                kuljettavaVasemmalle = kartta[kasiteltava.getX() - 1][kasiteltava.getY() + 1].getKuljettava();
                seinaVasemmallaTakana = !(kartta[kasiteltava.getX() - 1][kasiteltava.getY()].getKuljettava());
                if (kuljettavaVasemmalle && seinaVasemmallaTakana) {
                    kasiteltava
                            .paivitaPrioriteetti(kasiteltava.getEtaisyys() + laskeDiagonaalinenEtaisyys(kasiteltava));
                    hyppypisteet.add(kasiteltava);
                    // System.out.print("\nLöytyi hyppypiste " + kasiteltava.getX() + ", " +
                    // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys());
                    return kasiteltava;
                }
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
        if (kasiteltava != aloitus && kasiteltava != edeltaja.getEdeltaja()
                && kasiteltava.getEtaisyys() > kasiteltava.getEdeltaja().getEtaisyys() + 1) {
            kasiteltava.setEdeltaja(edeltaja);
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
        // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", uusi x:"
        // + x);

        // Tutkitaan löytyykö hyppypisteitä ylhäältä tai alhaalta
        if (!hyppypisteet.contains(kasiteltava)) {
            boolean kuljettavaYlos = false;
            boolean seinaAlhaallaTakana = false;
            boolean kuljettavaAlas = false;
            boolean seinaYlhaallaTakana = false;

            if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + 1 <= sivu - 1) {
                kuljettavaYlos = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
                seinaAlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() + 1].getKuljettava());
                if (kuljettavaYlos && seinaAlhaallaTakana) {
                    kasiteltava
                            .paivitaPrioriteetti(kasiteltava.getEtaisyys() + laskeDiagonaalinenEtaisyys(kasiteltava));
                    hyppypisteet.add(kasiteltava);
                    // System.out.print("\nLöytyi hyppypiste " + kasiteltava.getX() + ", " +
                    // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys());
                    return kasiteltava;
                }
            }
            if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() - 1 >= 0) {
                kuljettavaAlas = kartta[kasiteltava.getX() - 1][kasiteltava.getY() - 1].getKuljettava();
                seinaYlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() - 1].getKuljettava());
                if (kuljettavaAlas && seinaYlhaallaTakana) {
                    kasiteltava
                            .paivitaPrioriteetti(kasiteltava.getEtaisyys() + laskeDiagonaalinenEtaisyys(kasiteltava));
                    hyppypisteet.add(kasiteltava);
                    // System.out.print("\nLöytyi hyppypiste " + kasiteltava.getX() + ", " +
                    // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys());
                    return kasiteltava;
                }
            }
        }
        return etsiHorisontaalisesti(kasiteltava, seuraava, x); // Ei löytynyt tutkittavaa, jatketaan
    }

    /**
     * Kun etsitty ensin vertikaalisesti ja horisontaalisesti siirrytään
     * diagonaalisesti seuraavaan solmuun ja aloitetaan uudestaan etsintä
     * 
     * @param edeltaja
     * @param kasiteltava
     */
    private void etsiDiagonaalisesti(Solmu edeltaja, Solmu kasiteltava, int x, int y) {
        kasiteltava.setEdeltaja(edeltaja);
        if (onkoMaalissa(kasiteltava)) {
            return;
        }
        if (kasiteltava != aloitus
                || kasiteltava.getEtaisyys() > kasiteltava.getEdeltaja().getEtaisyys() + Math.sqrt(2)) {
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + Math.sqrt(2));
        }
        // System.out.print("\nEtsitään diagonaalisesti " + kasiteltava.getX() + ", " +
        // kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ",
        // liikutaan " + x + "," + y);
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
        kasiteltava.setKasitelty(true);
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
     * Metodi tarkistaa onko käsiteltävä solmu sama kuin lopetussolmu
     * 
     * @param tutkittava
     * @return boolean
     */
    private boolean onkoMaalissa(Solmu tutkittava) {
        if (tutkittava.getX() == lopetus.getX() && tutkittava.getY() == lopetus.getY()) {
            loytyi = true;
            // System.out.print("\nKäsittelyyn: " + tutkittava.getX() + ", " +
            // tutkittava.getY() + ", etäisyys: " + tutkittava.getY() + ", etäisyys: " +
            // tutkittava.getEtaisyys());
            // System.out.print("\nEdeltäjä: " + tutkittava.getEdeltaja().getX() + ", " +
            // tutkittava.getEdeltaja().getY() + ", etäisyys: " +
            // tutkittava.getEdeltaja().getEtaisyys());

            if (tutkittava.getX() == tutkittava.getEdeltaja().getX()) {
                double siirtyma = Math.abs(tutkittava.getEdeltaja().getY() - tutkittava.getY());
                // System.out.print("\nMaali - sama x - lisätään: " + siirtyma);
                if (tutkittava.getEtaisyys() > tutkittava.getEdeltaja().getEtaisyys() + siirtyma) {
                    tutkittava.paivitaEtaisyys(tutkittava.getEdeltaja().getEtaisyys() + siirtyma);
                }
            } else if (tutkittava.getY() == tutkittava.getEdeltaja().getY()) {
                double siirtyma = Math.abs(tutkittava.getEdeltaja().getX() - tutkittava.getX());
                // System.out.print("\nMaali - sama Y - lisätään: " + siirtyma);
                if (tutkittava.getEtaisyys() > tutkittava.getEdeltaja().getEtaisyys() + siirtyma) {
                    tutkittava.paivitaEtaisyys(tutkittava.getEdeltaja().getEtaisyys() + siirtyma);
                }
            } else {
                if (tutkittava.getEtaisyys() > tutkittava.getEdeltaja().getEtaisyys() + Math.sqrt(2)) {
                    tutkittava.paivitaEtaisyys(tutkittava.getEdeltaja().getEtaisyys() + Math.sqrt(2));
                    // System.out.print("\nMaali - diagonaalinen siirto - lisätään: " +
                    // Math.sqrt(2));
                }
            }
            reitinpituus = tutkittava.getEtaisyys();
            // System.out.print("\nMaali löytyi!" + tutkittava.getEtaisyys());
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
        while (i < sivu * sivu) { // väliaikainen ratkaisu, koska bugin vuoksi syntyy välillä luuppeja
            if (edeltaja == aloitus || edeltaja == null) {
                break;
            }
            edeltaja.setReitilla(true);
            verkko.lisaaReitti("\nEdellinen piste reitillä " + edeltaja.getX() + "," + edeltaja.getY()
                    + " ja etäisyys alusta on: " + edeltaja.getEtaisyys());
            edeltaja = edeltaja.getEdeltaja();
            i++;
        }
        // for (Solmu solmu : hyppypisteet) {
        // solmu.setReitilla(true);
        // verkko.lisaaReitti("\nTunnistettu hyppypiste reitillä " + solmu.getX() + ","
        // + solmu.getY()
        // + " ja etäisyys alusta on: " + solmu.getEtaisyys()
        // + "\nHyppypisteen diagonaalinen etäisyys loppuun on: "
        // + solmu.getPrioriteetti());
        // }
        aloitus.setReitilla(true);
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
