package tiralabra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Verkko;

public class RandomWalkTest {
    @Test
    public void TunnelitonKartta() {
        RandomWalk tunneliton = new RandomWalk(10, 0, 0, "tunneliton");
        Verkko verkko = tunneliton.luoUusi();
        boolean seinallinen = true;
        for (int i = 0; i < verkko.koko; i++) {
            for (int j = 0; j < verkko.koko; j++) {
                if (verkko.solmut[i][j].getKuljettava()) {
                    seinallinen = false;
                }
            }

            assertEquals(true, seinallinen);
        }
    }

    @Test
    public void SeinatonKartta() {
        RandomWalk seinaton = new RandomWalk(5, 1000, 5, "seinaton");
        Verkko verkko = seinaton.luoUusi();
        boolean kuljettava = true;
        for (int i = 0; i < verkko.koko; i++) {
            for (int j = 0; j < verkko.koko; j++) {
                if (!verkko.solmut[i][j].getKuljettava()) {
                    kuljettava = false;
                }
            }

            assertEquals(true, kuljettava);
        }
    }
}
