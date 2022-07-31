package tiralabra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import tiralabra.karttojenpiirto.Kartat;

public class KartatTest {
    @Test
    public void YritetaanTulostaaKarttaaJotaEiOle() {
        IOStub io = new IOStub("");
        Kartat kartat = new Kartat(io);
        Throwable virhe = assertThrows(
                Error.class, () -> {
                    kartat.tulostaKartta("olematon");
                });

        assertEquals("Ups! Kartan tulostus ei onnistunut. Tarkista nimi.", virhe.getMessage());
    }
}
