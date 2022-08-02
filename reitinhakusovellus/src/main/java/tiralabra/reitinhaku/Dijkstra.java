package tiralabra.reitinhaku;

public class Dijkstra {
    private Solmu[][] kartta;
    private int sivu;
    private int solmuja;
    private Keko keko;
    private Solmu aloitus;
    private Solmu lopetus;
   

    public Dijkstra(Verkko verkko) {
        this.kartta = verkko.solmut;
        this.sivu = verkko.koko;
        this.solmuja = verkko.solmujenMaara();
        this.keko = new Keko(solmuja);
        this.aloitus = kartta[0][0];
        this.lopetus = kartta[sivu-1][sivu-1]; 
        System.out.print("\nDijkstra: luonti onnistuu");
    }

    
    /** 
     * @return boolean
     */
    public boolean etsiLyhyinReitti() {
        aloitus.paivitaEtaisyys(0);
        this.keko.lisaaKekoon(aloitus);

        while (!keko.onTyhja()) {
            
            Solmu kasiteltava = keko.poistaPienin();
            System.out.print("\nDijkstra: uusi solmu käsittelyyn " + kasiteltava.getX()+ "," + kasiteltava.getY() + ".");
            if (kasiteltava.getX() == lopetus.getX() && kasiteltava.getY() == lopetus.getY()) {
                lisaaReittiKarttaan();
                return true;
            }
            tutkiNaapurit(kasiteltava);
        }
        return false;
    }

    /** 
     * @param kasiteltava
     */
    private void tutkiNaapurit(Solmu kasiteltava) {
        System.out.print("\nDijkstra: tutkitaan naapurit.");
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
        System.out.print("\nDijkstra: lasketaan etaisyysnaapuriin " + kasiteltava.getX()+ "," + kasiteltava.getY() + ".");
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
            naapuri.setVanhempi(kasiteltava);
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
        System.out.print("\nDijkstra: varmistetaan että on kuljettava " + uusiX + "," + uusiY + ".");
        
        if (uusiX < 0 || uusiY < 0 || uusiX > solmuja / 2 || uusiY < solmuja / 2) {
            return false;
        }
        if (!kartta[uusiX][uusiY].getKuljettava()) {
            return false;
        }
        return true;
    }

    public void lisaaReittiKarttaan(){
        Solmu solmu = kartta[lopetus.getX()][lopetus.getY()];
        while(solmu.getVanhempi() != null) {
            solmu.setDijkstra(true);
            solmu = solmu.getVanhempi();
        }
    }

}
