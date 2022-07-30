package tiralabra;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiralabra.reitinhaku.Verkko;

public class VerkkoTest {

    @Test
    public void VerkonSolmujenMaaraOnOikein() {
        Verkko verkko = new Verkko(2, "nelio");
        for (int i = 1; i < 101; i++) {
            {
                verkko.lisaaSolmu(0, 0, true, false);
                verkko.lisaaSolmu(0, 1, false, true);
                verkko.lisaaSolmu(1, 0, true, false);
                verkko.lisaaSolmu(1, 1, false, true);

            }
            assertEquals(verkko.solmujenMaara(), 4);
        }
    }
}
