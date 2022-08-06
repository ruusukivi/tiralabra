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
            "0", // Ei tunneleita
            "0", // Tunnelin pituus
        };
        IOStub io = new IOStub(syotteet);
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("\nKartan nimi: Nelio, koko 2*2\n", io.tulosteet.get(10));
        assertEquals("x ", io.tulosteet.get(11));
        assertEquals("x ", io.tulosteet.get(12));
        assertEquals("\n", io.tulosteet.get(13));
        assertEquals("x ", io.tulosteet.get(14));
        assertEquals("x ", io.tulosteet.get(15));
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
            "Nelio", // Kartan nimi
        };
        IOStub io = new IOStub(syotteet);
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("\nKartan nimi: Nelio, koko 2*2\n", io.tulosteet.get(26));
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
        assertEquals("Yhtään karttaa ei ole vielä luotu. \n", io.tulosteet.get(6));
    }


    @Test
    public void DijkstraNakyyKartalla() {
        String[] syotteet = {
            "1", //Luo kartta
            "3", // Kartan nimi
            "3", // Kartan sivun pituus
            "10", // Paljon tunneleita
            "3", // Tunnelin pituus
            // Näillä arvoilla syntyy 3x3 kartta, jossa keskellä yksi seinä.
        };
        IOStub io = new IOStub(syotteet);
        Kartat kartat = new Kartat(io);
        Kayttoliittyma ui = new Kayttoliittyma(io, kartat);
        ui.kaynnista();
        assertEquals("\nKartan nimi: 3, koko 3*3\n", io.tulosteet.get(10));
        assertEquals("D ", io.tulosteet.get(11));
        assertEquals(". ", io.tulosteet.get(12));
        assertEquals(". ", io.tulosteet.get(13));
        assertEquals("\n", io.tulosteet.get(14));
        assertEquals("D ", io.tulosteet.get(15));
        assertEquals("x ", io.tulosteet.get(16));
        assertEquals(". ", io.tulosteet.get(17));
        assertEquals("\n", io.tulosteet.get(18));
        assertEquals(". ", io.tulosteet.get(19));
        assertEquals("D ", io.tulosteet.get(20));
        assertEquals("D ", io.tulosteet.get(21));
    }
    
}
