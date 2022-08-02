package tiralabra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Dijkstra;
import tiralabra.reitinhaku.Verkko;

public class DijkstraTest {

    @Test
    public void ReititonKartta() {
        RandomWalk tunneliton = new RandomWalk(100, 0, 0, "tunneliton");
        Verkko verkko = tunneliton.luoUusi();
        Dijkstra kartta = new Dijkstra(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        assertEquals(false, onkoReittia);
    }

    @Test
    public void EsteetonReitti() {
        Verkko verkko = new Verkko(100, "esteetön");
        for (int i = 0; i < verkko.koko; i++) {
            for (int j = 0; j < verkko.koko; j++) {
                verkko.lisaaSolmu(i, j, false, true);
            }
        }
        Dijkstra kartta = new Dijkstra(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = verkko.solmut[99][99].getEtaisyys();
        double odotettu = Math.sqrt(2) * 99;
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.01);
    }

    @Test
    public void ReitinPituusOikeinLaskettu() {
        Verkko verkko = new Verkko(3, "kolme");
        verkko.lisaaSolmu(0, 0,true, false);
        verkko.lisaaSolmu(1, 0, true, false);
        verkko.lisaaSolmu(2, 0, false, true);
        verkko.lisaaSolmu(0, 1, true, false);
        verkko.lisaaSolmu(1, 1, true, false);
        verkko.lisaaSolmu(2, 1, false, true);
        verkko.lisaaSolmu(0, 2, false, true);
        verkko.lisaaSolmu(1, 2, false, true);
        verkko.lisaaSolmu(2, 2, false, true);
        Dijkstra kartta = new Dijkstra(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = verkko.solmut[2][2].getEtaisyys();
        assertEquals(true, onkoReittia);
        assertEquals(4, pituus, 0);
    }
}
