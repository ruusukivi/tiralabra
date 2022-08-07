package tiralabra.reitinhaku;

public class JumpPointSearch {
    private Verkko verkko;
    private Solmu[][] kartta;
    private int sivu;
    private Keko keko;
    private Solmu aloitus;
    private Solmu lopetus;

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
    }

    /**
     * @return boolean Metodi palauttaa totuusarvona tiedon siitä löytyykö
     *         algoritmi reittiä kartan vasemmasta yläkulmasta oikeaan alakulmaan.
     */
    public boolean etsiLyhyinReitti() {
        aloitus.paivitaEtaisyys(0);
        keko.lisaaKekoon(aloitus);
        while (!keko.onTyhja()) {
            Solmu kasiteltava = keko.poistaPienin();
            if (!kasiteltava.getKasitelty()) {
                if (kasiteltava.getX() == lopetus.getX() && kasiteltava.getY() == lopetus.getY()) {
                    System.out.print("\nOllaam maalissa.");
                    tallennaReitti();
                    return true;
                }
                etsiHyppyPisteet(kasiteltava, aloitus.getX(), aloitus.getY());
            }
        }
        return false;
    }

    /**
     * @param kasiteltava Metodi tarkistaa kaikki solmut, joita ole vielä käsitelty.
     */
    private void etsiHyppyPisteet(Solmu kasiteltava, int x, int y) {
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

    private Solmu etsiVertikaalisesti(Solmu kasiteltava, int y) {
        System.out.print("\nEtsitään vertikaalisesti " + kasiteltava.getX() + ", " + kasiteltava.getY() + ", uusi y: " + y);
        int seuraavaX = kasiteltava.getX();
        int seuraavaY = kasiteltava.getY() + y;
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            System.out.print("\nEi ole kuljettava!");
            return null;
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        boolean kuljettavaOikealle = kartta[kasiteltava.getX() + 1][kasiteltava.getY()].getKuljettava();
        boolean seinaOikeallaTakana = !(kartta[kasiteltava.getX() + 1][kasiteltava.getY() - y].getKuljettava());
        boolean kuljettavaVasemmalle = kartta[kasiteltava.getX() - 1][kasiteltava.getY()].getKuljettava();
        boolean seinaVasemmallaTakana = !(kartta[kasiteltava.getX() - 1][kasiteltava.getY() - y].getKuljettava());

        if ((kuljettavaOikealle && seinaOikeallaTakana) ||
                (kuljettavaVasemmalle && seinaVasemmallaTakana)) {
            return seuraava;
        }
        return etsiVertikaalisesti(seuraava, y);
    }

    private Solmu etsiHorisontaalisesti(Solmu kasiteltava, int x) {
        int seuraavaX = kasiteltava.getX() + x;
        int seuraavaY = kasiteltava.getY();
        System.out.print("\nEtsitään horisontaalisesti " + kasiteltava.getX() + ", " + kasiteltava.getY() + ", uusi x: " + x);
        
        if (!onkoKuljettava(seuraavaX, seuraavaY)) {
            System.out.print("\nEi ole kuljettava!");
            return null;
        }
        Solmu seuraava = kartta[seuraavaX][seuraavaY];
        boolean kuljettavaYlos = kartta[kasiteltava.getX()][kasiteltava.getY() + 1].getKuljettava();
        boolean seinaAlhaallaTakana = !(kartta[kasiteltava.getX() - x][kasiteltava.getY() + 1].getKuljettava());
        boolean kuljettavaAlas = kartta[kasiteltava.getX()][kasiteltava.getY() - 1].getKuljettava();
        boolean seinaYlhaallaTakana = !(kartta[kasiteltava.getX() - x][kasiteltava.getY() - 1].getKuljettava());

        if ((kuljettavaYlos && seinaAlhaallaTakana) ||
                (kuljettavaAlas && seinaYlhaallaTakana)) {
            return seuraava;
        }
        return etsiHorisontaalisesti(seuraava, x);
    }

    private void etsiDiagonaalisesti(Solmu kasiteltava, int x, int y) {
        Solmu seuraava = kartta[kasiteltava.getX() + x][kasiteltava.getY() + y];
        System.out.print("\nEtsitään diagonaalisesti " + kasiteltava.getX() + ", " + kasiteltava.getY() + ", uudet " + x + "," + y);
        if (!seuraava.getKuljettava() && seuraava.getEtaisyys() > kasiteltava.getEtaisyys() + Math.sqrt(2)) {
            System.out.print("\nLöytyi diagonaalisesti seuraava tutkittava.");
            seuraava.setEdeltaja(kasiteltava);
            seuraava.paivitaEtaisyys(kasiteltava.getEtaisyys() + Math.sqrt(2));
            etsiHyppyPisteet(seuraava, x, y);
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
            return false;
        }
        if (!kartta[uusiX][uusiY].getKuljettava()) {
            return false;
        }
        return true;
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
        aloitus.setJPS(true);
        Solmu solmu = kartta[lopetus.getX()][lopetus.getY()];
        verkko.lisaaReittiJPS("\nReitti päättyy pisteeseen " + solmu.getX() + "," + solmu.getY()
                + " ja etäisyys alusta on: " + solmu.getEtaisyys());
        solmu.setDijkstra(true);
        while (solmu.getEdeltaja() != null) {
            solmu = solmu.getEdeltaja();
            solmu.setJPS(true);
            verkko.lisaaReittiJPS("\nSeuraava piste reitillä " + solmu.getX() + "," + solmu.getY()
                    + " ja etäisyys alusta on: " + solmu.getEtaisyys());
        }
        verkko.lisaaReittiDijkstra("\nReitti alkaa pisteestä 0.0 ");
    }
}
