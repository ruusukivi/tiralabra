
package tiralabra;
import static org.junit.Assert.*;
import org.junit.Test;

import tiralabra.kayttoliittyma.IO;
import tiralabra.kayttoliittyma.Kayttoliittyma;

public class SovellusTest {
    @Test public void SovellusKaynnistyy() {
        IO io = new IO();
        Kayttoliittyma ui = new Kayttoliittyma(io);
        assertTrue(true);
    }
}
