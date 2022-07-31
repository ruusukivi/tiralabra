package tiralabra;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tiralabra.reitinhaku.Keko;
import tiralabra.reitinhaku.Verkko;

public class KekoTest {

    private Verkko verkko;
    private int pieninEtaisyys;

    @Before
    public void VerkonLuonti() {
        verkko = new Verkko(10, "testiverkko10");
        for (int i = 0; i < verkko.koko; i++) {
            for (int j = 0; j < verkko.koko; j++) {
                verkko.lisaaSolmu(i, j, false, true);
                verkko.solmut[i][j].paivitaEtaisyys(100 - (i - j));
            }
        }
        verkko.solmut[5][5].paivitaEtaisyys(10);
        pieninEtaisyys = verkko.solmut[5][5].getEtaisyys();
    }

    @Test
    public void PieninSolmuLoytyy() {
        Keko testikeko = new Keko(100);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testikeko.lisaaKekoon(verkko.solmut[i][j]);
            }
        }
        int pienin = testikeko.pienin().getEtaisyys();
        assertEquals(pieninEtaisyys, pienin);
    }

    @Test
    public void LiikaaSolmujaKeossa() {
        Throwable virhe = assertThrows(
                Error.class, () -> {
                    Keko keko = new Keko(5);
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            keko.lisaaKekoon(verkko.solmut[i][j]);
                        }
                    }
                });

        assertEquals("Ups! Keko täynnä.", virhe.getMessage());
    }

    @Test
    public void PieninEiLöydyTyhjastaKeosta() {
        Throwable virhe = assertThrows(
                Error.class, () -> {
                    Keko keko = new Keko(1);
                    keko.pienin();
                });

        assertEquals("Ups! Keko on tyhjä.", virhe.getMessage());
    }

}
