package tiralabra.vertailu;

import java.util.ArrayList;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Dijkstra;
import tiralabra.reitinhaku.JumpPointSearch;
import tiralabra.reitinhaku.Verkko;

public class Vertailu {
    private ArrayList<String> tulokset;


    public Vertailu(){
        this.tulokset = new ArrayList<>();
    }

    public ArrayList<String> annaTulokset(){
        teeVertailu(3, 10, 3, "3");
        teeVertailu(10, 100, 10, "10");
        teeVertailu(100, 1000, 100, "100");
        teeVertailu(1000, 1000, 1000, "1000");
        return tulokset;
    }

    public void teeVertailu(int leveys, int tunnelit, int pituus, String nimi){
        RandomWalk kartta = new RandomWalk(leveys, tunnelit, pituus, nimi);
        Verkko d = kartta.muodostaKartastaVerkko("dijkstra");
        Dijkstra dijkstra = new Dijkstra(d);
        dijkstra.etsiLyhyinReitti();
        tulokset.add("\nKartan nimi: " + d.getNimi() + ", koko " + d.getKoko() + "*" + d.getKoko() + "\n" 
        + "Reitin pituus: " + dijkstra.getReitinPituus() + ", Haun kesto: " +  dijkstra.getKesto() + ", Keossa käsiteltyjen solmujen määrä: " + dijkstra.getKasitellyt() + "\n");

        Verkko j = kartta.muodostaKartastaVerkko("jps");
        JumpPointSearch jps = new  JumpPointSearch(j);
        jps.etsiLyhyinReitti();
        tulokset.add("\nKartan nimi: " + j.getNimi() + ", koko " + j.getKoko() + "*" + j.getKoko() + "\n" 
        + "Reitin pituus: " + jps.getReitinPituus() + ", Haun kesto: " +  jps.getKesto() + ", Keossa käsiteltyjen solmujen määrä: " + jps.getKasitellyt() + "\n");
    }

}

