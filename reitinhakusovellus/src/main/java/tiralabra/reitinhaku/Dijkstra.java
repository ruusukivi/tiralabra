package tiralabra.reitinhaku;

public class Dijkstra {
    private Solmu[][] kartta;
    private int sivu;
    private Keko keko;
    private Solmu aloitus;
    private Solmu lopetus;

    public Dijkstra(Verkko verkko) {
        this.kartta = verkko.solmut;
        this.sivu = verkko.koko;
        this.keko = new Keko(sivu * sivu);
        this.aloitus = kartta[0][0];
        this.lopetus = kartta[sivu - 1][sivu - 1];
        System.out.print("\nDijkstra: luonti onnistuu");
        System.out.print("\nDijkstra: aloitussolmu " + aloitus.getX() + "," + aloitus.getY());
        System.out.print("\nDijkstra: lopetussolmu " + lopetus.getX() + "," + lopetus.getY());
    }

    /**
     * @return boolean
     */
    public boolean etsiLyhyinReitti() {
        aloitus.paivitaEtaisyys(0);
        this.keko.lisaaKekoon(aloitus);

        while (!keko.onTyhja()) {
            Solmu kasiteltava = keko.poistaPienin();
            if (!kasiteltava.getKasitelty()) {
                System.out.print(
                        "\nDijkstra: uusi solmu käsittelyyn " + kasiteltava.getX() + "," + kasiteltava.getY() + ".");
                if (kasiteltava.getX() == lopetus.getX() && kasiteltava.getY() == lopetus.getY()) {
                    lisaaReittiKarttaan();
                    return true;
                }
                tutkiNaapurit(kasiteltava);
            } else {
                System.out.print("\nDijkstra: solmu on jo käsiltelty aiemmin " + kasiteltava.getX() + ","
                        + kasiteltava.getY() + ".");
            }
        }
        System.out.print("\nDijkstra: onko keko tyhjä?" + keko.onTyhja());
        return false;
    }

    /**
     * @param kasiteltava
     */
    private void tutkiNaapurit(Solmu kasiteltava) {
        // System.out.print("\nDijkstra: tutkitaan naapurit.");
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
     * @param kasiteltava
     * @param x
     * @param y
     * @return Solmu
     */
    private Solmu laskeEtaisyysNaapuriin(Solmu kasiteltava, int x, int y) {
        System.out.print("\nDijkstra: lasketaan etaisyysnaapuriin solmulle " + kasiteltava.getX() + ","
                + kasiteltava.getY() + " arvoilla: " + x + "," + y);
        kasiteltava.paivitaKasitelty(true);
        int naapurinX = kasiteltava.getX() + x;
        int naapurinY = kasiteltava.getY() + y;
        if (!onkoKuljettava(naapurinX, naapurinY)) {
            System.out.print("\nDijkstra: ei kuljettava " + naapurinX + "," + naapurinY + ".");
            return null;
        }
        Solmu naapuri = kartta[naapurinX][naapurinY];
        double etaisyysNaapuriin = 1;
        if (naapurinX != 0 && naapurinY != 0) {
            etaisyysNaapuriin = Math.sqrt(2);
        }
        if (naapuri.getEtaisyys() > kasiteltava.getEtaisyys() + etaisyysNaapuriin) {
            naapuri.paivitaEtaisyys(kasiteltava.getEtaisyys() + etaisyysNaapuriin);
            System.out.print("\nDijkstra: löydettiin naapurisolmuun " + naapurinX + "," + naapurinY
                    + " aiempaa lyhyempi etäisyys " + naapuri.getEtaisyys() + ".");
            naapuri.setEdeltaja(kasiteltava);
            return naapuri;
        }
        return null;
    }

    /**
     * @param uusiX
     * @param uusiY
     * @return boolean
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

    public void lisaaReittiKarttaan() {
        Solmu solmu = kartta[lopetus.getX()][lopetus.getY()];
        solmu.setDijkstra(true);
        System.out.print("\nDijkstra: lopetussolmun koordinaatit " + solmu.getX() + "," + solmu.getY() + " ja etäisyys "
                + solmu.getEtaisyys() + ".");
        while (solmu.getEdeltaja() != null) {
            solmu.setDijkstra(true);
            solmu = solmu.getEdeltaja();
        }
    }

}
