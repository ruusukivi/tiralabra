
package tiralabra;
import static org.junit.Assert.*;
import org.junit.Test;

import tiralabra.karttojenpiirto.Kartat;
import tiralabra.kayttoliittyma.IO;
import tiralabra.kayttoliittyma.Kayttoliittyma;

public class SovellusTest {
    @Test public void SovellusKaynnistyy() {
        IO io = new IO();
        Kartat kartat = new Kartat();
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        assertTrue(true);
    }
}
