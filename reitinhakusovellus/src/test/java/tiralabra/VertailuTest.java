package tiralabra;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;

import org.junit.Test;
import tiralabra.vertailu.Vertailu;

public class VertailuTest {

    @Test
    public void VertailuSuppeallaAineistollaOnnistuu() {
        Vertailu vertailu = new Vertailu(true);
        ArrayList<String> tulokset = vertailu.annaTulokset(10, 10, 10, "10-10-1000");
        assertNotNull(tulokset.size());
    }

    @Test
    public void VertailuLaajallaAineistollaOnnistuu() {
        Vertailu vertailu = new Vertailu(false);
        ArrayList<String> tulokset = vertailu.annaTulokset(2, 0, 0, "2-0-0");
        assertNotNull(tulokset.size());
    }
}
