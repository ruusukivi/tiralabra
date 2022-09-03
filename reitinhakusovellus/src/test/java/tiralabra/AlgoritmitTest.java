package tiralabra;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Dijkstra;
import tiralabra.reitinhaku.JumpPointSearch;
import tiralabra.reitinhaku.Verkko;

public class AlgoritmitTest {

    @Ignore
    @Test
    public void ReitinPituusSamaMolemmissa() {
        for (int i = 1; i < 2000; i++){
            int polut = 100 + i;
            RandomWalk random = new RandomWalk(1000, polut, 0, "random");
            Verkko verkko1 = random.muodostaKartastaVerkko("dijkstra");
            Dijkstra kartta1 = new Dijkstra(verkko1);
            Verkko verkko2 = random.muodostaKartastaVerkko("jps");
            JumpPointSearch kartta2 = new JumpPointSearch(verkko2);
            boolean onkoReittia1 = kartta1.etsiLyhyinReitti();
            boolean onkoReittia2 = kartta2.etsiLyhyinReitti();
            assertEquals(onkoReittia1, onkoReittia2);
            assertEquals(kartta1.getReitinPituus(), kartta2.getReitinPituus(), 0);
        }

    }

}
