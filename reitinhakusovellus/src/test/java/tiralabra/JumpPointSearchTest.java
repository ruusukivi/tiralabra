package tiralabra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.JumpPointSearch;
import tiralabra.reitinhaku.Verkko;

public class JumpPointSearchTest {

    @Test
    public void ReititonKartta() {
        RandomWalk tunneliton = new RandomWalk(100, 0, 0, "tunneliton");
        Verkko verkko = tunneliton.muodostaKartastaVerkko("jps");
        JumpPointSearch kartta = new JumpPointSearch(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        assertEquals(false, onkoReittia);
    }

    @Test
    public void EsteetonReitti() {
        Verkko verkko = new Verkko(10, "esteetön");
        for (int i = 0; i < verkko.getKoko(); i++) {
            for (int j = 0; j < verkko.getKoko(); j++) {
                verkko.lisaaSolmu(i, j, false, true);
            }
        }
        JumpPointSearch kartta = new JumpPointSearch(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = verkko.getSolmut()[9][9].getEtaisyys();
        double odotettu = Math.sqrt(2) * 9;
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.01);
    }

    @Test
    public void EsteetonReittiNurinPain() {
        Verkko verkko = new Verkko(10, "esteetön");
        for (int i = 0; i < verkko.getKoko(); i++) {
            for (int j = 0; j < verkko.getKoko(); j++) {
                verkko.lisaaSolmu(i, j, false, true);
            }
        }
        JumpPointSearch kartta = new JumpPointSearch(verkko);
        kartta.setAloitus(9, 9);
        kartta.setLopetus(0, 0);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = verkko.getSolmut()[0][0].getEtaisyys();
        double odotettu = Math.sqrt(2) * 9;
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.01);
    }

    @Test
    public void ReitinPituusOikeinLaskettu() {
        Verkko verkko = new Verkko(3, "kolme");
        verkko.lisaaSolmu(0, 0,false, true);
        verkko.lisaaSolmu(1, 0, false, true);
        verkko.lisaaSolmu(2, 0, false, true);
        verkko.lisaaSolmu(0, 1, false, true);
        verkko.lisaaSolmu(1, 1, true, false);
        verkko.lisaaSolmu(2, 1, false, true);
        verkko.lisaaSolmu(0, 2, false, true);
        verkko.lisaaSolmu(1, 2, false, true);
        verkko.lisaaSolmu(2, 2, false, true);
        JumpPointSearch kartta = new JumpPointSearch(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = verkko.getSolmut()[2][2].getEtaisyys();
        double odotettu = 1 + Math.sqrt(2) + 1;
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.1);
    }

    @Test
    public void ReitinPituusOikeinLaskettuNurinPain() {
        Verkko verkko = new Verkko(3, "kolme");
        verkko.lisaaSolmu(0, 0,false, true);
        verkko.lisaaSolmu(1, 0, false, true);
        verkko.lisaaSolmu(2, 0, false, true);
        verkko.lisaaSolmu(0, 1, false, true);
        verkko.lisaaSolmu(1, 1, true, false);
        verkko.lisaaSolmu(2, 1, false, true);
        verkko.lisaaSolmu(0, 2, false, true);
        verkko.lisaaSolmu(1, 2, false, true);
        verkko.lisaaSolmu(2, 2, false, true);
        JumpPointSearch kartta = new JumpPointSearch(verkko);
        kartta.setAloitus(2, 2);
        kartta.setLopetus(0, 0);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = verkko.getSolmut()[0][0].getEtaisyys();
        double odotettu = 1 + Math.sqrt(2) + 1;
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.1);
    }

    @Test
    public void ReitinPituusSamaAlustaLoppuunJaLopustaAlkuun() {
        RandomWalk satunnainen = new RandomWalk(10, 20, 10, "satunnainen");
        Verkko verkko1 = satunnainen.muodostaKartastaVerkko("jps");
        JumpPointSearch kartta1 = new JumpPointSearch(verkko1);
        kartta1.etsiLyhyinReitti();
        kartta1.setAloitus(0,0);
        kartta1.setLopetus(9, 9);
        double pituus1 = kartta1.getReitinPituus();
        Verkko verkko2 = satunnainen.muodostaKartastaVerkko("jps");
        JumpPointSearch kartta2 = new JumpPointSearch(verkko2);
        kartta2.setAloitus(9,9);
        kartta2.setLopetus(0, 0);
        kartta2.etsiLyhyinReitti();
        double pituus2 = kartta2.getReitinPituus();
        assertEquals(pituus1, pituus2, 0.01);
    }
}
