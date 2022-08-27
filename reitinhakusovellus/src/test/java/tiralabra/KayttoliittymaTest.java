package tiralabra;

import static org.junit.Assert.*;

import org.junit.Test;

import tiralabra.karttojenpiirto.Kartat;
import tiralabra.kayttoliittyma.Kayttoliittyma;

public class KayttoliittymaTest {


    @Test
    public void KayttoliittymaKaynnistyy(){
        IOStub io = new IOStub("");
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("\nTOIMINNOT: \n", io.tulosteet.get(1));
    }

    @Test
    public void KartanLuontiOnnistuu(){
        String[] syotteet = {
            "1", //Luo kartta
            "Nelio", // Kartan nimi
            "2", // Kartan sivun pituus
            "0", // Ei polkuja
            "0", // Polun pituus
        };
        IOStub io = new IOStub(syotteet);
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("\nKartan nimi: Nelio-dijkstra, koko 2*2\n", io.tulosteet.get(12));
        assertEquals("x ", io.tulosteet.get(13));
        assertEquals("x ", io.tulosteet.get(14));
        assertEquals("\n", io.tulosteet.get(15));
        assertEquals("x ", io.tulosteet.get(16));
        assertEquals("x ", io.tulosteet.get(17));
    }

    @Test
    public void OlemassaOlevanKartanHakuOnnistuu(){
        String[] syotteet = {
            "1", //Luo kartta
            "Nelio", // Kartan nimi
            "2", // Kartan sivun pituus
            "0", // Ei tunneleita
            "0", // Tunnelin pituus
            "2", //Luo kartta
            "Nelio-dijkstra", // Kartan nimi
        };
        IOStub io = new IOStub(syotteet);
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("\nKartan nimi: Nelio-dijkstra, koko 2*2\n", io.tulosteet.get(39));
    }

    @Test
    public void OlemassaOlevanKartanHakuKunKarttojaEiOle(){
        String[] syotteet = {
            "2", //Luo kartta
        };
        IOStub io = new IOStub(syotteet);
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("Yhtään karttaa ei ole vielä luotu. \n", io.tulosteet.get(7));
    }


    @Test
    public void DijkstraNakyyKartalla() {
        String[] syotteet = {
            "1", //Luo kartta
            "3", // Kartan nimi
            "3", // Kartan sivun pituus
            "10", // Paljonko polkuja
            "3", // Polun pituus
            // Näillä arvoilla syntyy 3x3 kartta, jossa keskellä yksi seinä.
        };
        IOStub io = new IOStub(syotteet);
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("\nKartan nimi: 3-dijkstra, koko 3*3\n", io.tulosteet.get(12));
        assertEquals("R ", io.tulosteet.get(13));
        assertEquals(". ", io.tulosteet.get(14));
        assertEquals(". ", io.tulosteet.get(15));
        assertEquals("\n", io.tulosteet.get(16));
        assertEquals("R ", io.tulosteet.get(17));
        assertEquals("x ", io.tulosteet.get(18));
        assertEquals(". ", io.tulosteet.get(19));
        assertEquals("\n", io.tulosteet.get(20));
        assertEquals(". ", io.tulosteet.get(21));
        assertEquals("R ", io.tulosteet.get(22));
        assertEquals("R ", io.tulosteet.get(23));
    }
    
}
