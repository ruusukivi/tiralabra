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
        assertEquals("\nKartan nimi: Nelio, koko 2*2", io.tulosteet.get(10));
        assertEquals("x", io.tulosteet.get(12));
        assertEquals("x", io.tulosteet.get(13));
        assertEquals("\n", io.tulosteet.get(14));
        assertEquals("x", io.tulosteet.get(15));
        assertEquals("x", io.tulosteet.get(16));
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
        assertEquals("\nKartan nimi: Nelio, koko 2*2", io.tulosteet.get(26));
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
    
}
