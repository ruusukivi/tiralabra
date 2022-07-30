
package tiralabra;

import static org.junit.Assert.*;
import org.junit.Test;

import tiralabra.reitinhaku.Solmu;

public class SolmuTest {

    @Test
    public void SolmuOnKuljettava() {
        Solmu solmu = new Solmu(1, 2, false, true);
        boolean kuljettava = solmu.getKuljettava();
        assertEquals(true, kuljettava);
    }

    @Test
    public void SolmuOnJokoSeinaTaiKuljettava() {
        Throwable virhe = assertThrows(
                IllegalArgumentException.class, () -> {
                    new Solmu(1, 2, true, true);
                });

        assertEquals("Virhe: Ruutu ei voi olla sekä seinä että kuljettava.", virhe.getMessage());
    }

}
