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
    private ArrayList<Solmu> reitilla;
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
        this.reitilla = new ArrayList<>();
        this.kesto = 0;
        this.kasitellyt = 0;
        this.reitinpituus = lopetus.getEtaisyys();
    }

    /**
     * @return boolean Metodi palauttaa totuusarvona tiedon siitä löytyykö
     *         algoritmi reittiä kartan vasemmasta yläkulmasta oikeaan alakulmaan.
     */
    public boolean etsiLyhyinReitti() {
        Instant alku = Instant.now();
        aloitus.paivitaEtaisyys(0);
        aloitus.setEdeltaja(aloitus);
        keko.lisaaKekoon(aloitus);
        reitilla.add(aloitus);
        while (!keko.onTyhja()) {
            if (loytyi) {
                break;
            }
            Solmu kasiteltava = keko.poistaPienin();
            kasitellyt++;
            System.out.print("\nKeosta otettiin käsittelyyn: " + kasiteltava.getX() + ", " +
            kasiteltava.getY() + ", etäisyys: " + kasiteltava.getEtaisyys());
            System.out.print("\nEdeltaja: " + kasiteltava.getEdeltaja().getX() + ", " +
            kasiteltava.getEdeltaja().getY() + ", etäisyys: " + kasiteltava.getEdeltaja().getEtaisyys());
            etsiHyppyPisteet(kasiteltava, 0, 0);
        }
        reitinpituus = lopetus.getEtaisyys();
        Instant loppu = Instant.now();
        kesto = Duration.between(alku, loppu).getNano() / 100000;
        if (loytyi) {
            tallennaReitti();
            return true;
        }
        return false;
    }

    /**
     * @param kasiteltava Metodi tarkistaa kaikki solmut, joita ole vielä käsitelty.
     */
    private void etsiHyppyPisteet(Solmu kasiteltava, int x, int y) {
        if (onkoMaalissa(kasiteltava)) {
            return;
        }
        if (!kasiteltava.getKasitelty()) {
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
        return;
    }

    private Solmu etsiVertikaalisesti(Solmu kasiteltava, int y) {
        kasiteltava.setKasitelty(true);
        if (onkoMaalissa(kasiteltava)) {
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + 1);
            return null;
        }
        int seuraavaX = kasiteltava.getX();
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null;
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        if(seuraava.getKasitelty() || kasiteltava.getEdeltaja() == seuraava){
            return null;
        }
        seuraava.setEdeltaja(kasiteltava);
        if (!reitilla.contains(kasiteltava)) {
            if (kasiteltava.getEtaisyys() < kasiteltava.getEdeltaja().getEtaisyys() + 1) {
                System.out.print(" Pidempi etäisyys, ei päivitetä!");
                return null; // Tarkistetaan ettei lyhyempää etäisyyttä korvata pidemmällä.
            }
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + 1);
        }
        System.out.print(
            "\nEtsitään vertikaalisesti " + kasiteltava.getX() + ", " +
                    kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", y:n muutos: " + y );

        boolean kuljettavaOikealle = false;
        boolean seinaOikeallaTakana = false;
        boolean kuljettavaVasemmalle = false;
        boolean seinaVasemmallaTakana = false;

        if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + y <= sivu - 1) {
            // Etsitään pakotettuja naapureita oikealta
            kuljettavaOikealle = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
            seinaOikeallaTakana = !(kartta[kasiteltava.getX() + 1][kasiteltava.getY()].getKuljettava());
            if (kuljettavaOikealle && seinaOikeallaTakana) {
                System.out.print(" Löydettiin naapuri oikealta");
                Solmu naapuri = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1];
                lisaaTutkittava(kasiteltava, naapuri);
                //return naapuri;
            }
        }
        if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() + y <= sivu - 1) {
            // Etsitään pakotettuja naapureita vasemmalta
            kuljettavaVasemmalle = kartta[kasiteltava.getX() - 1][kasiteltava.getY() + 1].getKuljettava();
            seinaVasemmallaTakana = !(kartta[kasiteltava.getX() - 1][kasiteltava.getY()].getKuljettava());
            if (kuljettavaVasemmalle && seinaVasemmallaTakana) {
                System.out.print(" Löydettiin naapuri vasemmalta");
                Solmu naapuri = kartta[kasiteltava.getX() - 1][kasiteltava.getY() + 1]; 
                lisaaTutkittava(kasiteltava, naapuri);
                //return naapuri;
            }
        }
        return etsiVertikaalisesti(seuraava, y); // Ei löytynyt tutkittavaa, jatketaan
    }

    private Solmu etsiHorisontaalisesti(Solmu kasiteltava, int x) {
        kasiteltava.setKasitelty(true);
        if (onkoMaalissa(kasiteltava)) {
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + 1);
            return null;
        }
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY();
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null; // Tarkistetaan ettei seuraava piste ole seinä tai kartan ulkopuolella.
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        seuraava.setEdeltaja(kasiteltava);

        if(seuraava.getKasitelty() || kasiteltava.getEdeltaja() == seuraava){
            System.out.print("Osuu");
            return null;
        }

        if (!reitilla.contains(kasiteltava)) {
            if (kasiteltava.getEtaisyys() < kasiteltava.getEdeltaja().getEtaisyys() + 1) {
                System.out.print(" Pidempi etäisyys, ei päivitetä!");
                return null; // Tarkistetaan ettei lyhyempää etäisyyttä korvata pidemmällä.
            }
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + 1);
        }
        System.out.print(
            "\nEtsitään horisontaalisesti " + kasiteltava.getX() + ", " +
                    kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", uusi x: " + x);

        boolean kuljettavaYlos = false;
        boolean seinaAlhaallaTakana = false;
        boolean kuljettavaAlas = false;
        boolean seinaYlhaallaTakana = false;

        if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + 1 <= sivu - 1) {
            // Etsitään pakotettuja naapureita ylhäältä
            kuljettavaYlos = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
            seinaAlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() + 1].getKuljettava());
            if (kuljettavaYlos && seinaAlhaallaTakana) {
                Solmu naapuri = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1];
                lisaaTutkittava(kasiteltava, naapuri);
                //return naapuri;
            }
        }
        if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() - 1 >= 0) { 
            // Etsitään pakotettuja naapureita alhaalta
            kuljettavaAlas = kartta[kasiteltava.getX() - 1][kasiteltava.getY() - 1].getKuljettava();
            seinaYlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() - 1].getKuljettava());
            if (kuljettavaAlas && seinaYlhaallaTakana) {
                Solmu naapuri = kartta[kasiteltava.getX() - 1][kasiteltava.getY() - 1];
                lisaaTutkittava(kasiteltava, naapuri);
                //return naapuri;
            }
        }
        return etsiHorisontaalisesti(seuraava, x); // Ei löytynyt tutkittavaa, jatketaan
    }

    private void etsiDiagonaalisesti(Solmu kasiteltava, int x, int y) {
        kasiteltava.setKasitelty(true);
        if(kasiteltava != aloitus && kasiteltava.getEtaisyys()+1 > kasiteltava.getEdeltaja().getEtaisyys() + Math.sqrt(2)){
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + Math.sqrt(2));
        }

        System.out.print("\nEtsitään diagonaalisesti " + kasiteltava.getX() + ", " +
        kasiteltava.getY() + ", etaisyys: " + kasiteltava.getEtaisyys() + ", liikutaan " + x + "," + y);
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            System.out.print(" Ei kartalla!");
            return; // Tarkistetaan ettei seuraava piste ole seinä tai kartan ulkopuolella.
        }
        Solmu seuraava = kartta[kasiteltava.getX() + x][kasiteltava.getY() + y];
        seuraava.setEdeltaja(kasiteltava);

        if (onkoMaalissa(seuraava)) {
            seuraava.paivitaEtaisyys(kasiteltava.getEtaisyys() + Math.sqrt(2));
            return;
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
            System.out.print(" Ei kartalla!");
            return false;
        }
        if (!kartta[uusiX][uusiY].getKuljettava()) {
            System.out.print(" Seinä!");
            return false;
        }
        return true;
    }

    private void lisaaTutkittava(Solmu edeltaja, Solmu tutkittava) {
        if(!tutkittava.getKasitelty()){
            tutkittava.setEdeltaja(edeltaja);
            tutkittava.paivitaEtaisyys(edeltaja.getEtaisyys() + Math.sqrt(2));
            reitilla.add(tutkittava);
            keko.lisaaKekoon(tutkittava);
        }
    }

    private boolean onkoMaalissa(Solmu tutkittava) {
        if (tutkittava.getX() == lopetus.getX() && tutkittava.getY() == lopetus.getY()) {
            loytyi = true;
            System.out.print(" Maali löytyi!");
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
        aloitus.setReitilla(true);
        verkko.lisaaReitti("\nReitti päättyy pisteeseen " + lopetus.getX() + "," + lopetus.getY()
                + " ja etäisyys alusta on: " + lopetus.getEtaisyys());
        Solmu solmu = kartta[lopetus.getX()][lopetus.getY()];
        Solmu edeltaja = solmu.getEdeltaja();
        solmu.setReitilla(true);
        verkko.lisaaReitti("\nSeuraava piste reitillä " + solmu.getX() + "," + solmu.getY()
                + " ja etäisyys alusta on: " + solmu.getEtaisyys());
        if (edeltaja != null) {
            edeltaja.setReitilla(true);
        }
        verkko.lisaaReitti("\nReitti alkaa pisteestä 0.0 ");
    }
    
    public double getKesto(){
        return this.kesto;
    }

    public double getKasitellyt(){
        return this.kasitellyt;
    }

    public double getReitinPituus(){
        return this.reitinpituus;
    }
}
