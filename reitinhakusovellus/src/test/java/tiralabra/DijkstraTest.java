package tiralabra;

import static org.junit.Assert.assertEquals;

import java.security.spec.ECFieldF2m;

import org.junit.Test;

import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.reitinhaku.Dijkstra;
import tiralabra.reitinhaku.Verkko;

public class DijkstraTest {

    @Test
    public void ReititonKartta() {
        RandomWalk tunneliton = new RandomWalk(100, 0, 0, "tunneliton");
        Verkko verkko = tunneliton.muodostaKartastaVerkko("dijkstra");
        Dijkstra kartta = new Dijkstra(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        assertEquals(false, onkoReittia);
    }

    @Test
    public void EsteetonReitti() {
        Verkko verkko = new Verkko(100, "esteetön");
        for (int i = 0; i < verkko.getKoko(); i++) {
            for (int j = 0; j < verkko.getKoko(); j++) {
                verkko.lisaaSolmu(i, j, false, true);
            }
        }
        Dijkstra kartta = new Dijkstra(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = verkko.getSolmut()[99][99].getEtaisyys();
        double odotettu = Math.sqrt(2) * 99;
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.01);
    }

    @Test
    public void EsteetonReittiNurinPain() {
        Verkko verkko = new Verkko(100, "esteetön");
        for (int i = 0; i < verkko.getKoko(); i++) {
            for (int j = 0; j < verkko.getKoko(); j++) {
                verkko.lisaaSolmu(i, j, false, true);
            }
        }
        Dijkstra kartta = new Dijkstra(verkko);
        kartta.setAloitus(99, 99);
        kartta.setLopetus(0, 0);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = kartta.getReitinPituus();
        double odotettu = Math.sqrt(2) * 99;
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
        Dijkstra kartta = new Dijkstra(verkko);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = kartta.getReitinPituus();
        double odotettu = 1 + Math.sqrt(2) + 1;
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.01);
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
        Dijkstra kartta = new Dijkstra(verkko);
        kartta.setAloitus(2, 2);
        kartta.setLopetus(0, 0);
        boolean onkoReittia = kartta.etsiLyhyinReitti();
        double pituus = kartta.getReitinPituus();
        double odotettu = 1 + Math.sqrt(2) + 1;
        System.out.println(pituus);
        assertEquals(true, onkoReittia);
        assertEquals(odotettu, pituus, 0.01);
    }

    @Test
    public void ReitinPituusSamaAlustaLoppuunJaLopustaAlkuun() {
        RandomWalk satunnainen = new RandomWalk(1000, 5000, 1000, "satunnainen");
        Verkko verkko1 = satunnainen.muodostaKartastaVerkko("dijkstra");
        Dijkstra kartta1 = new Dijkstra(verkko1);
        boolean onkoReittia1 = kartta1.etsiLyhyinReitti();
        double pituus1 = kartta1.getReitinPituus();
        Verkko verkko2 = satunnainen.muodostaKartastaVerkko("dijkstra");
        Dijkstra kartta2 = new Dijkstra(verkko2);
        kartta2.setAloitus(999,999);
        kartta2.setLopetus(0, 0);
        boolean onkoReittia2 = kartta2.etsiLyhyinReitti();
        double pituus2 = kartta2.getReitinPituus();
        System.out.println(pituus1);
        System.out.println(pituus2);
        assertEquals(onkoReittia1, onkoReittia2);
        assertEquals(pituus1, pituus2, 0.01);
    }

}
