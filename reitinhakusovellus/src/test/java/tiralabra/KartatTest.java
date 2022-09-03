package tiralabra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import tiralabra.karttojenpiirto.Kartat;
import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Verkko;

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

    @Test
    public void KartanHakuNimella() {
        RandomWalk mini = new RandomWalk(2, 0, 0, "mini");
        Verkko verkko = mini.muodostaKartastaVerkko("jps");
        IOStub io = new IOStub("");
        Kartat kartat = new Kartat(io);
        kartat.lisaaKartta(verkko);
        Verkko haettu = kartat.karttaNimella("mini-jps");
        assertEquals(haettu.getNimi(), "mini-jps");
    }
}
