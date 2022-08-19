package tiralabra.reitinhaku;

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
    }

    /**
     * @return boolean Metodi palauttaa totuusarvona tiedon siitä löytyykö
     *         algoritmi reittiä kartan vasemmasta yläkulmasta oikeaan alakulmaan.
     */
    public boolean etsiLyhyinReitti() {
        aloitus.paivitaEtaisyys(0);
        aloitus.setEdeltaja(aloitus);
        keko.lisaaKekoon(aloitus);
        while (!keko.onTyhja()) {
            if (loytyi) {
                break;
            }
            Solmu kasiteltava = keko.poistaPienin();
            reitilla.add(kasiteltava);
            etsiHyppyPisteet(kasiteltava, 0, 0);
        }
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
        System.out.print("\nKäsittelyssä nyt " + kasiteltava.getX() + ", " +
                kasiteltava.getY());

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
        System.out.print(
                "\nEtsitään vertikaalisesti " + kasiteltava.getX() + ", " +
                        kasiteltava.getY() + ", y:n muutos: " + y + ", etaisyys: " + kasiteltava.getEtaisyys());
        int seuraavaX = kasiteltava.getX();
        int seuraavaY = kasiteltava.getY() + y;

        kasiteltava.setKasitelty(true);

        if (kasiteltava != aloitus) {
            if (kasiteltava.getEtaisyys() < kasiteltava.getEdeltaja().getEtaisyys() + 1 && kasiteltava != aloitus) {
                System.out.print("Pidempi etäisyys, ei päivitetä!");
                return null; // Tarkistetaan ettei lyhyempää etäisyyttä korvata pidemmällä.
            }
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + 1);
        }

        if (onkoMaalissa(kasiteltava)) {
            return null;
        }

        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null; // Tarkistetaan ettei seuraava piste ole seinä tai kartan ulkopuolella.
        }

        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        seuraava.setEdeltaja(kasiteltava);

        boolean kuljettavaOikealle = false;
        boolean seinaOikeallaTakana = false;
        boolean kuljettavaVasemmalle = false;
        boolean seinaVasemmallaTakana = false;

        if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + y <= sivu - 1) {
            // Etsitään pakotettuja naapureita oikealta
            System.out.print(" - Tarkastetaan naapureita oikealta");
            kuljettavaOikealle = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
            seinaOikeallaTakana = !(kartta[kasiteltava.getX() + 1][kasiteltava.getY()].getKuljettava());
            if (kuljettavaOikealle && seinaOikeallaTakana) {
                System.out.print("- Löydettiin naapuri oikealta");
                lisaaTutkittava(kasiteltava, kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1]); // Pakotettu
                                                                                                      // naapuri
                                                                                                      // löydetty
                // return kartta[kasiteltava.getX() + 1][kasiteltava.getY()];
            }
        }
        if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() + y <= sivu - 1) {
            // Etsitään pakotettuja naapureita vasemmalta
            System.out.print("Tarkastetaan naapureita vasemmalta");
            kuljettavaVasemmalle = kartta[kasiteltava.getX() - 1][kasiteltava.getY() + 1].getKuljettava();
            seinaVasemmallaTakana = !(kartta[kasiteltava.getX() - 1][kasiteltava.getY()].getKuljettava());
            if (kuljettavaVasemmalle && seinaVasemmallaTakana) {
                System.out.print(" - Löydettiin naapuri vasemmalta");
                lisaaTutkittava(kasiteltava, kartta[kasiteltava.getX() - 1][kasiteltava.getY() + 1]); // Pakotettu
                                                                                                      // naapuri
                                                                                                      // löydetty
                // return kartta[kasiteltava.getX() - 1][kasiteltava.getY()];
            }
        }

        if (kasiteltava.getY() - y < 0 || kasiteltava.getY() + y > sivu - 1) {
            System.out.print("\nNaapuritarkistus ei onnistu Y:n arvolla: " + kasiteltava.getY());
            return etsiVertikaalisesti(seuraava, y);
        }

        if (onkoMaalissa(kasiteltava)) {
            return null; // tarvitaanko oikeasti??
        }

        return etsiVertikaalisesti(seuraava, y); // Ei löytynyt hyppypisteitä eikä tutkittavaa, jatketaan
    }

    private Solmu etsiHorisontaalisesti(Solmu kasiteltava, int x) {
        System.out.print(
                "\nEtsitään horisontaalisesti " + kasiteltava.getX() + ", " +
                        kasiteltava.getY() + ", uusi x: " + x + ", etaisyys: " + kasiteltava.getEtaisyys());
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY();

        kasiteltava.setKasitelty(true);

        if (kasiteltava != aloitus) {
            if (kasiteltava.getEtaisyys() < kasiteltava.getEdeltaja().getEtaisyys() + 1) {
                System.out.print("Pidempi etäisyys, ei päivitetä!");
                return null; // Tarkistetaan ettei lyhyempää etäisyyttä korvata pidemmällä.
            }
            kasiteltava.paivitaEtaisyys(kasiteltava.getEdeltaja().getEtaisyys() + 1);
        }

        if (onkoMaalissa(kasiteltava)) {
            return null;
        }

        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null; // Tarkistetaan ettei seuraava piste ole seinä tai kartan ulkopuolella.
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        seuraava.setEdeltaja(kasiteltava);

        boolean kuljettavaYlos = false;
        boolean seinaAlhaallaTakana = false;
        boolean kuljettavaAlas = false;
        boolean seinaYlhaallaTakana = false;

        if (kasiteltava.getX() + 1 <= sivu - 1 && kasiteltava.getY() + 1 <= sivu - 1) {
            // Etsitään pakotettuja naapureita ylhäältä
            System.out.print("Tarkastetaan naapureita ylhäältä");
            kuljettavaYlos = kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1].getKuljettava();
            seinaAlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() + 1].getKuljettava());
            if (kuljettavaYlos && seinaAlhaallaTakana) {
                lisaaTutkittava(kasiteltava, kartta[kasiteltava.getX() + 1][kasiteltava.getY() + 1]);
                // Pakotettu naapuri löydetty
            }
        }

        if (kasiteltava.getX() - 1 >= 0 && kasiteltava.getY() - 1 >= 0) { // Etsitään pakotettuja naapureita alhaalta
            System.out.print("Tarkastetaan naapureita alhaalta");
            kuljettavaAlas = kartta[kasiteltava.getX() - 1][kasiteltava.getY() - 1].getKuljettava();
            seinaYlhaallaTakana = !(kartta[kasiteltava.getX()][kasiteltava.getY() - 1].getKuljettava());
            if (kuljettavaAlas && seinaYlhaallaTakana) {
                lisaaTutkittava(kasiteltava, kartta[kasiteltava.getX() - 1][kasiteltava.getY() - 1]);
                // Pakotettu naapuri löydetty
            }
        }

        if (kasiteltava.getX() - x < 0 || kasiteltava.getX() + x > sivu - 1) {
            System.out.print("\nNaapuritarkistus ei onnistu Y:n arvolla: " + kasiteltava.getY());
            return etsiHorisontaalisesti(seuraava, x);
        }

        return etsiHorisontaalisesti(seuraava, x); // Ei löytynyt hyppypisteitä eikä tutkittavaa, jatketaan
    }

    private void etsiDiagonaalisesti(Solmu kasiteltava, int x, int y) {
        System.out.print("\nEtsitään diagonaalisesti " + kasiteltava.getX() + ", " +
                kasiteltava.getY() + ", liikutaan " + x + "," + y + ", etaisyys: " + kasiteltava.getEtaisyys());
        kasiteltava.setKasitelty(true);
        if (kasiteltava.getX() + x > sivu - x || kasiteltava.getX() + x < 0 || kasiteltava.getY() + y > sivu - y
                || kasiteltava.getY() + y < 0) {
            System.out.print(" Ei kartalla!");
            return;
        }
        Solmu seuraava = kartta[kasiteltava.getX() + x][kasiteltava.getY() + y];
        seuraava.setEdeltaja(kasiteltava);
        if (seuraava.getKuljettava() && seuraava.getEtaisyys() > kasiteltava.getEtaisyys() + Math.sqrt(2)) {
            seuraava.paivitaEtaisyys(kasiteltava.getEtaisyys() + Math.sqrt(2));
            System.out.print("\nPäivitettiin etäisyys: " + seuraava.getX() + "," +
                    seuraava.getY() + ", etaisyys: " + seuraava.getEtaisyys());
            etsiHyppyPisteet(seuraava, x, y);
        } else {
            System.out.print(" Seinä!");
        }
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
        keko.lisaaKekoon(tutkittava);
        tutkittava.setEdeltaja(edeltaja);
        tutkittava.paivitaEtaisyys(edeltaja.getEtaisyys() + Math.sqrt(2));
        reitilla.add(tutkittava);
        System.out.print("\nTutkittava löytyi. ");
    }

    private boolean onkoMaalissa(Solmu tutkittava) {
        if (tutkittava.getX() == lopetus.getX() && tutkittava.getY() == lopetus.getY()) {
            loytyi = true;
            System.out.print("Maali löytyi!");
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
     * @return double Palauttaa Manhattan-etäisyyden
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
        System.out.print("\nReitillä koko: " + reitilla.size());
        for (Solmu piste : reitilla) {
            System.out.print(
                    "\nReitillä solmu: " + piste.getX() + "," + piste.getY() + ", etäisyys " + piste.getEtaisyys());
        }
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
}
