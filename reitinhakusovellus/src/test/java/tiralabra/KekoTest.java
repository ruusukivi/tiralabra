package tiralabra;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tiralabra.reitinhaku.Keko;
import tiralabra.reitinhaku.Verkko;

public class KekoTest {

    private Verkko verkko;
    private double pieninEtaisyys;

    @Before
    public void VerkonLuonti() {
        verkko = new Verkko(10, "testiverkko10");
        for (int i = 0; i < verkko.getKoko(); i++) {
            for (int j = 0; j < verkko.getKoko(); j++) {
                verkko.lisaaSolmu(i, j, false, true);
                verkko.getSolmut()[i][j].paivitaEtaisyys(100 - (i - j));
            }
        }
        verkko.getSolmut()[5][5].paivitaEtaisyys(10);
        pieninEtaisyys = verkko.getSolmut()[5][5].getEtaisyys();
    }

    @Test
    public void PieninSolmuLoytyy() {
        Keko testikeko = new Keko(100);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testikeko.lisaaKekoon(verkko.getSolmut()[i][j]);
            }
        }
        double pienin = testikeko.pienin().getEtaisyys();
        assertEquals(pieninEtaisyys, pienin, 0.01);;
    }

    @Test
    public void LiikaaSolmujaKeossa() {
        Throwable virhe = assertThrows(
                Error.class, () -> {
                    Keko keko = new Keko(5);
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            keko.lisaaKekoon(verkko.getSolmut()[i][j]);
                        }
                    }
                });

        assertEquals("Ups! Keko täynnä.", virhe.getMessage());
    }

    @Test
    public void PieninEiLoydyTyhjastaKeosta() {
        Throwable virhe = assertThrows(
                Error.class, () -> {
                    Keko keko = new Keko(1);
                    keko.pienin();
                });

        assertEquals("Ups! Keko on tyhjä.", virhe.getMessage());
    }

}
