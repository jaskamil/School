import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

// 1. KLASA SAMOCHOD
class Samochod {
    private String marka, model;
    private int rocznik;
    private double pojemnoscSilnika;

    public Samochod(String marka, String model, int rocznik, double pojemnoscSilnika) {
        this.marka = marka;
        this.model = model;
        this.rocznik = rocznik;
        this.pojemnoscSilnika = pojemnoscSilnika;
    }

    // Gettery potrzebne do zapisu i logiki
    public String getMarka() { return marka; }
    public String getModel() { return model; }
    public double getPojemnoscSilnika() { return pojemnoscSilnika; }
    public int getRocznik() { return rocznik; }

    @Override
    public String toString() {
        return marka + " " + model + " (" + rocznik + "), " + pojemnoscSilnika + "L";
    }
}

// 2. KLASA KLIENT
class Klient {
    private String imie, nazwisko;
    private int wiek, lataBezszkodowe;

    public Klient(String imie, String nazwisko, int wiek, int lataBezszkodowe) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
        this.lataBezszkodowe = lataBezszkodowe;
    }

    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public int getWiek() { return wiek; }
    public int getLataBezszkodowe() { return lataBezszkodowe; }
    public String getImieNazwisko() { return imie + " " + nazwisko; }

    @Override
    public String toString() {
        return imie + " " + nazwisko + " (" + wiek + " lat)";
    }
}

// 3. KLASA POLISA
class Polisa {
    private String numerPolisy;
    private Klient klient;
    private Samochod samochod;
    private double cena;

    public Polisa(Klient klient, Samochod samochod, double cena) {
        this.numerPolisy = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.klient = klient;
        this.samochod = samochod;
        this.cena = cena;
    }

    // Konstruktor do odtwarzania polisy z pliku (z istniejącym numerem)
    public Polisa(String numerPolisy, Klient klient, Samochod samochod, double cena) {
        this.numerPolisy = numerPolisy;
        this.klient = klient;
        this.samochod = samochod;
        this.cena = cena;
    }

    // Metoda pomocnicza: zamienia obiekt na linię tekstu do pliku (CSV)
    public String toFileFormat() {
        // Format: ID;Imie;Nazwisko;Wiek;Lata;Marka;Model;Rocznik;Silnik;Cena
        return String.format("%s;%s;%s;%d;%d;%s;%s;%d;%.1f;%.2f",
                numerPolisy,
                klient.getImie(), klient.getNazwisko(), klient.getWiek(), klient.getLataBezszkodowe(),
                samochod.getMarka(), samochod.getModel(), samochod.getRocznik(), samochod.getPojemnoscSilnika(),
                cena).replace(',', '.'); // Zamiana przecinka na kropkę dla liczb
    }

    @Override
    public String toString() {
        return "POLISA [" + numerPolisy + "] | " + klient.getImieNazwisko() +
                " | " + samochod + " | " + String.format("%.2f", cena) + " PLN";
    }
}

// 4. KALKULATOR (Logika bez zmian)
class KalkulatorUbezpieczen {
    private static final double BAZA_STAWKA = 1000.00;

    public static double obliczSkladke(Klient klient, Samochod samochod) {
        double skladka = BAZA_STAWKA;
        if (klient.getWiek() < 26) skladka *= 1.5;
        if (samochod.getPojemnoscSilnika() > 2.0) skladka *= 1.3;
        double znizka = klient.getLataBezszkodowe() * 0.10;
        if (znizka > 0.60) znizka = 0.60;
        if (znizka > 0) skladka *= (1.0 - znizka);
        if (samochod.getRocznik() < 2005) skladka *= 1.1;
        return skladka;
    }
}

// 5. OBSŁUGA PLIKÓW (Nowa klasa)
class MenedzerPlikow {
    private static final String PLIK_BAZA = "polisy.txt";

    // Zapisuje jedną polisę na koniec pliku (append)
    public static void dopiszPolise(Polisa p) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLIK_BAZA, true))) {
            writer.write(p.toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku: " + e.getMessage());
        }
    }

    // Wczytuje wszystkie polisy przy starcie
    public static List<Polisa> wczytajPolisy() {
        List<Polisa> lista = new ArrayList<>();
        File file = new File(PLIK_BAZA);

        if (!file.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linia;
            while ((linia = reader.readLine()) != null) {
                String[] dane = linia.split(";");
                if (dane.length == 10) {
                    // Odtwarzanie obiektów z tekstu
                    String id = dane[0];
                    Klient k = new Klient(dane[1], dane[2], Integer.parseInt(dane[3]), Integer.parseInt(dane[4]));
                    Samochod s = new Samochod(dane[5], dane[6], Integer.parseInt(dane[7]), Double.parseDouble(dane[8]));
                    double cena = Double.parseDouble(dane[9]);

                    lista.add(new Polisa(id, k, s, cena));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Błąd odczytu bazy danych: " + e.getMessage());
        }
        return lista;
    }
}

// 6. GŁÓWNA KLASA
    class Ubezpieczenie_Pojazdow {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Polisa> bazaPolis;

    public static void main(String[] args) {
        // Wczytanie danych przy starcie
        bazaPolis = MenedzerPlikow.wczytajPolisy();

        System.out.println("=========================================");
        System.out.println("    Kamil Jastrzębski 126030 Łódź");

        System.out.println("=========================================");
        System.out.println("   System Ubezpieczeń ");
        System.out.println("   Załadowano polisy z pliku: " + bazaPolis.size());
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            System.out.println("\nMENU GŁÓWNE:");
            System.out.println("1. Nowa polisa");
            System.out.println("2. Wyświetl historię sprzedaży");
            System.out.println("3. Wyjście");
            System.out.print("> ");

            String wybor = scanner.nextLine();

            switch (wybor) {
                case "1": obslugaNowegoKlienta(); break;
                case "2": wyswietlPolisy(); break;
                case "3": running = false; break;
                default: System.out.println("Niepoprawna opcja.");
            }
        }
    }

    private static void obslugaNowegoKlienta() {
        try {
            System.out.println("\n--- DANE KLIENTA ---");
            System.out.print("Imię: "); String imie = scanner.nextLine();
            System.out.print("Nazwisko: "); String nazwisko = scanner.nextLine();
            System.out.print("Wiek: "); int wiek = Integer.parseInt(scanner.nextLine());
            System.out.print("Lata bezszkodowe: "); int lata = Integer.parseInt(scanner.nextLine());

            System.out.println("\n--- DANE POJAZDU ---");
            System.out.print("Marka: "); String marka = scanner.nextLine();
            System.out.print("Model: "); String model = scanner.nextLine();
            System.out.print("Rocznik: "); int rocznik = Integer.parseInt(scanner.nextLine());
            System.out.print("Pojemność silnika (np. 1.9): ");
            // Obsługa kropki i przecinka
            String pojString = scanner.nextLine().replace(',', '.');
            double pojemnosc = Double.parseDouble(pojString);

            Klient k = new Klient(imie, nazwisko, wiek, lata);
            Samochod s = new Samochod(marka, model, rocznik, pojemnosc);

            double cena = KalkulatorUbezpieczen.obliczSkladke(k, s);
            System.out.println("\n>>> OFERTA: " + String.format("%.2f", cena) + " PLN <<<");
            System.out.print("Kupujesz? (tak/nie): ");

            if (scanner.nextLine().equalsIgnoreCase("tak")) {
                Polisa p = new Polisa(k, s, cena);
                bazaPolis.add(p);
                // ZAPIS DO PLIKU
                MenedzerPlikow.dopiszPolise(p);
                System.out.println("Sukces! Polisa zapisana w bazie.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Błąd: Wprowadzono złe dane liczbowe!");
        }
    }

    private static void wyswietlPolisy() {
        System.out.println("\n--- BAZA POLIS (Z pliku i pamięci) ---");
        if (bazaPolis.isEmpty()) System.out.println("Brak danych.");
        else bazaPolis.forEach(System.out::println);
    }
}