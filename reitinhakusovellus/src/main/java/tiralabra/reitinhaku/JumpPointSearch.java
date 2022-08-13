package tiralabra.reitinhaku;

public class JumpPointSearch {
    private Verkko verkko;
    private Solmu[][] kartta;
    private int sivu;
    private Keko keko;
    private Solmu aloitus;
    private Solmu lopetus;
    private Solmu kasittelyssa;
    private boolean loytyi;

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
    }

    /**
     * @return boolean Metodi palauttaa totuusarvona tiedon siitä löytyykö
     *         algoritmi reittiä kartan vasemmasta yläkulmasta oikeaan alakulmaan.
     */
    public boolean etsiLyhyinReitti() {
        //System.out.print("\nRulla alkaa.");
        aloitus.paivitaEtaisyys(0);
        keko.lisaaKekoon(aloitus);
        while (!keko.onTyhja() || !loytyi) {
            Solmu kasiteltava = keko.poistaPienin();
            kasittelyssa = kasiteltava;
            etsiHyppyPisteet(kasiteltava, 0, 0);
            //System.out.print("\nPalattiin rullaan.");
        }
        if (loytyi) {
            //System.out.print("\nOllaan maalissa.");
            tallennaReitti();
            return true;
        }
        //System.out.print("\nRulla pysähtyy ");
        return false;
    }

    /**
     * @param kasiteltava Metodi tarkistaa kaikki solmut, joita ole vielä käsitelty.
     */
    private boolean etsiHyppyPisteet(Solmu kasiteltava, int x, int y) {
        System.out.print("\nKäsittelyssä nyt " + kasiteltava.getX() + ", " + kasiteltava.getY());
        if (kasiteltava.getX() == lopetus.getX() && kasiteltava.getY() == lopetus.getY()) {
            System.out.print("\nLöytyi maali!");
            loytyi = true;
            return loytyi;
        }

        if (!kasiteltava.getKasitelty()) {
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
        return false;
        //System.out.print("\nSolmu oli jo käsitelty: " + kasiteltava.getX() + ", " + kasiteltava.getY());
    }

    private Solmu etsiVertikaalisesti(Solmu kasiteltava, int y) {
        //System.out.print(
        //        "\nEtsitään vertikaalisesti " + kasiteltava.getX() + ", " + kasiteltava.getY() + ", uusi y: " + y);
        int seuraavaX = kasiteltava.getX();
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null;
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        seuraava.paivitaEtaisyys(kasiteltava.getEtaisyys() + 1);
        //System.out.print("\nSeuraava: " + seuraavaX + ", " + seuraavaY);

        if (seuraavaX + 1 >= sivu - 1) {
            //System.out.print("\nEi päästä oikealle! " + (seuraavaX + 1) + ", " + seuraavaY);
        } else {
            boolean kuljettavaOikealle = kartta[seuraava.getX() + 1][seuraava.getY()].getKuljettava();
            //System.out.print("\nOnko oikealle kuljettava: " + kuljettavaOikealle);
            boolean seinaOikeallaTakana = !(kartta[seuraava.getX() + 1][seuraava.getY() - y].getKuljettava());
            //System.out.print("\nOnko oikealla takana seinä: " + seinaOikeallaTakana);
            if (kuljettavaOikealle && seinaOikeallaTakana) {
                lisaaHyppypiste(seuraava);
                return seuraava;
            }
        }
        if (seuraavaX - 1 < 0) {
            //System.out.print("\nEi päästä vasemmalle! " + (seuraavaX - 1) + ", " + seuraavaY);
        } else {
            boolean kuljettavaVasemmalle = kartta[seuraava.getX() - 1][seuraava.getY()].getKuljettava();
            //System.out.print("\nOnko vasemmalle kuljettava: " + kuljettavaVasemmalle);
            boolean seinaVasemmallaTakana = !(kartta[seuraava.getX() - 1][seuraava.getY() - y].getKuljettava());
            //System.out.print("\nOnko vasemmalla takana seinä: " + seinaVasemmallaTakana);
            if (kuljettavaVasemmalle && seinaVasemmallaTakana) {
                lisaaHyppypiste(seuraava);
                return seuraava;
            }
        }
        //System.out.print("\nEi löytynyt hyppypistettä, jatketaan etsintää");
        return etsiVertikaalisesti(seuraava, y);
    }

    private Solmu etsiHorisontaalisesti(Solmu kasiteltava, int x) {
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY();
        System.out.print(
                "\nEtsitään horisontaalisesti " + kasiteltava.getX() + ", " + kasiteltava.getY() + ", uusi x: " + x);
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            return null;
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        seuraava.paivitaEtaisyys(kasiteltava.getEtaisyys() + 1);
        //System.out.print("\nSeuraava: " + seuraavaX + ", " + seuraavaY);

        if (seuraavaY + 1 >= sivu - 1) {
            //System.out.print("\nEi päästä ylös! " + (seuraavaX) + ", " + (seuraavaY + 1));
        } else {
            boolean kuljettavaYlos = kartta[seuraava.getX()][seuraava.getY() + 1].getKuljettava();
            //System.out.print("\nOnko ylhäälle kuljettava: " + kuljettavaYlos);
            boolean seinaAlhaallaTakana = !(kartta[seuraava.getX() - x][seuraava.getY() + 1].getKuljettava());
            //System.out.print("\nOnko seinä alhaalla takana: " + seinaAlhaallaTakana);
            if (kuljettavaYlos && seinaAlhaallaTakana ) {
                lisaaHyppypiste(seuraava);
                return seuraava;
            }
        }

        if (seuraavaY - 1 < 0) {
            //System.out.print("\nEi päästä alas! " + (seuraavaX) + ", " + (seuraavaY - 1));
        } else {
            boolean kuljettavaAlas = kartta[seuraava.getX()][seuraava.getY() - 1].getKuljettava();
            //System.out.print("\nOnko alas kuljettava: " + kuljettavaAlas);
            boolean seinaYlhaallaTakana = !(kartta[seuraava.getX() - x][seuraava.getY() - 1].getKuljettava());
            //System.out.print("\nOnko seinä ylhäällä takana: " + seinaYlhaallaTakana);
            if (kuljettavaAlas && seinaYlhaallaTakana) {
                lisaaHyppypiste(seuraava);
                return seuraava;
            }
        }
        //System.out.print("\nEi löytynyt hyppypistettä, jatketaan etsintää");
        return etsiHorisontaalisesti(seuraava, x);
    }

    private void etsiDiagonaalisesti(Solmu kasiteltava, int x, int y) {
        System.out.print("\nEtsitään diagonaalisesti " + kasiteltava.getX() + ", " + kasiteltava.getY() + ", liikutaan " + x + "," + y);
        if (kasiteltava.getX() + x > sivu - x || kasiteltava.getX() + x < 0 || kasiteltava.getY() + y > sivu - y || kasiteltava.getY() + y < 0) {
            System.out.print(" Ei kartalla!");
            return;
        }
        Solmu seuraava = kartta[kasiteltava.getX() + x][kasiteltava.getY() + y];
        if (seuraava.getKuljettava() && seuraava.getEtaisyys() > kasiteltava.getEtaisyys() + Math.sqrt(2)) {
            seuraava.paivitaEtaisyys(kasiteltava.getEtaisyys() + Math.sqrt(2));
            System.out.print("\nPäivitettiin etäisyys: " + seuraava.getX() + "," + seuraava.getY() + ", etaisyys: " + seuraava.getEtaisyys());
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
            //System.out.print(" Ei kartalla!");
            return false;
        }
        if (!kartta[uusiX][uusiY].getKuljettava()) {
            //System.out.print(" Seinä!");
            return false;
        }
        return true;
    }

    private void lisaaHyppypiste(Solmu hyppypiste){
        System.out.print("\nHyppypiste löytyi: " + hyppypiste.getX() + "," + hyppypiste.getY() + ", etäisyys alusta: " +  hyppypiste.getEtaisyys());
        hyppypiste.setEdeltaja(kasittelyssa);
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
        aloitus.setReitilla(true);
        Solmu solmu = kartta[lopetus.getX()][lopetus.getY()];
        Solmu edeltaja = solmu.getEdeltaja();
        /*while(edeltaja != null){
            solmu.setReitilla(true);
            solmu = edeltaja;
            edeltaja = solmu.getEdeltaja();
        }*/
        verkko.lisaaReitti("\nReitti päättyy pisteeseen " + solmu.getX() + "," + solmu.getY()
                + " ja etäisyys alusta on: " + solmu.getEtaisyys());
    }
}
