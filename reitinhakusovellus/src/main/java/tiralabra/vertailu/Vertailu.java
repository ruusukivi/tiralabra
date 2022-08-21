package tiralabra.vertailu;

import java.util.ArrayList;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Dijkstra;
import tiralabra.reitinhaku.JumpPointSearch;
import tiralabra.reitinhaku.Verkko;

public class Vertailu {
    private ArrayList<String> tulokset;

    public Vertailu() {
        this.tulokset = new ArrayList<>();
    }

    public ArrayList<String> annaTulokset() {
        // 2000 levyiset kartat päätyvät Stack OverFlow-virheeseen, pitää testata auttaako tulostusten poistaminen
        //teeVertailu(2000, 10000, 1500, "2000-10000-1500");
        //teeVertailu(2000, 10000, 1500, "2000-10000-1500");
        teeVertailu(1000, 100000, 500, "1000-100000-500");
        teeVertailu(1000, 10000, 500, "1000-10000-500");
        teeVertailu(1000, 1000, 500, "1000-1000-500");
        teeVertailu(1000, 100, 500, "1000-100-500");
        return tulokset;
    }

    public void teeVertailu(int leveys, int tunnelit, int pituus, String nimi) {
        RandomWalk kartta = new RandomWalk(leveys, tunnelit, pituus, nimi);
        Verkko d = kartta.muodostaKartastaVerkko("dijkstra");
        Dijkstra dijkstra = new Dijkstra(d);
        boolean loytyi = dijkstra.etsiLyhyinReitti();
        if (loytyi) {
            tulokset.add("\nKartan nimi: " + d.getNimi() + ", koko " + d.getKoko() + "*" + d.getKoko() + "\n"
                    + "Reitin pituus: " + dijkstra.getReitinPituus() + ", Haun kesto (ms): " + dijkstra.getKesto()
                    + ", Keossa käsiteltyjen solmujen määrä: " + dijkstra.getKasitellyt() + "\n");
        } else {
            tulokset.add("\nKartan nimi: " + d.getNimi() + ", koko " + d.getKoko() + "*" + d.getKoko() + "\n"
                    + "Reittiä ei löytynyt. " + "Haun kesto (ms): " + dijkstra.getKesto()
                    + ",Keossa käsiteltyjen solmujen määrä: " + dijkstra.getKasitellyt() + "\n");
        }

        Verkko j = kartta.muodostaKartastaVerkko("jps");
        JumpPointSearch jps = new JumpPointSearch(j);
        boolean loytyiJPS = jps.etsiLyhyinReitti();

        if (loytyiJPS) {
            tulokset.add("\nKartan nimi: " + j.getNimi() + ", koko " + j.getKoko() + "*" + j.getKoko() + "\n"
                    + "Reitin pituus: " + jps.getReitinPituus() + ", Haun kesto (ms): " + jps.getKesto()
                    + ", Keossa käsiteltyjen solmujen määrä: " + jps.getKasitellyt() + "\n");
        } else {
            tulokset.add("\nKartan nimi: " + j.getNimi() + ", koko " + j.getKoko() + "*" + j.getKoko() + "\n"
                    + "Reittiä ei löytynyt. " + "Haun kesto (ms): " + jps.getKesto()
                    + ", Keossa käsiteltyjen solmujen määrä: " + jps.getKasitellyt() + "\n");
        }
    }

}
