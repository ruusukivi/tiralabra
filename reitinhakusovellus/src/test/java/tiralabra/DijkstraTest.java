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
}
