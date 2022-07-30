package tiralabra.reitinhaku;

public class Dijkstra {
    private Verkko verkko;
    private Keko keko;

    public Dijkstra(Verkko verkko) {
        this.verkko = verkko;
        this.keko = new Keko(verkko.getKoko());
    }
}
