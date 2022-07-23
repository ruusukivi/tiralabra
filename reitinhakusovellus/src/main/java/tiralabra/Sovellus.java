package tiralabra;
import tiralabra.kayttoliittyma.Kayttoliittyma;
import tiralabra.kayttoliittyma.IO;

public class Sovellus {
    public static void main(String[] args) {
        IO io = new IO();
        Kayttoliittyma ui = new Kayttoliittyma(io);
        ui.kaynnista();
    }
}
