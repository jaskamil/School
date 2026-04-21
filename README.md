# 🚗 System Ubezpieczeń Pojazdów (Java CLI)

Aplikacja konsolowa napisana w Javie do zarządzania polisami ubezpieczeniowymi dla pojazdów.
Projekt symuluje proces sprzedaży ubezpieczenia, obliczania składki oraz zapisu danych klientów i pojazdów.

---

## 🚀 Funkcjonalności

* ➕ Tworzenie nowej polisy ubezpieczeniowej
* 👤 Wprowadzanie danych klienta
* 🚗 Wprowadzanie danych pojazdu
* 💰 Automatyczne obliczanie składki
* 💾 Zapis polis do pliku (`polisy.txt`)
* 📂 Odczyt danych z pliku przy starcie aplikacji
* 📋 Wyświetlanie historii polis

---

## 🧠 Technologie i koncepty

* Java (OOP)
* Programowanie obiektowe (klasy: Klient, Samochod, Polisa)
* Operacje na plikach (File I/O)
* UUID (generowanie numeru polisy)
* Obsługa wyjątków (try-catch)
* Kolekcje (`ArrayList`)

---

## 🏗️ Architektura projektu

* `Samochod` – model danych pojazdu
* `Klient` – model danych klienta
* `Polisa` – reprezentacja polisy ubezpieczeniowej
* `KalkulatorUbezpieczen` – logika biznesowa (obliczanie składki)
* `MenedzerPlikow` – obsługa zapisu i odczytu danych z pliku
* `Ubezpieczenie_Pojazdow` – główna klasa aplikacji (CLI)

---

## ⚙️ Jak działa aplikacja

1. Przy uruchomieniu aplikacja wczytuje dane z pliku `polisy.txt`
2. Użytkownik może:

   * utworzyć nową polisę
   * wyświetlić historię polis
3. Przy tworzeniu polisy:

   * wprowadzane są dane klienta i pojazdu
   * system oblicza składkę
   * użytkownik decyduje o zakupie
4. Polisa zapisywana jest do pliku

---

## ▶️ Jak uruchomić

1. Skompiluj projekt:

```
javac Main.java
```

2. Uruchom aplikację:

```
java Main
```

---

## 📌 Przykładowe menu

```
1. Nowa polisa
2. Wyświetl historię sprzedaży
3. Wyjście
```

---

## 📈 Możliwe ulepszenia

* Dodanie GUI (JavaFX / Swing)
* Integracja z bazą danych (np. SQLite)
* REST API (Spring Boot)
* Walidacja danych wejściowych
* Testy jednostkowe (JUnit)
* Rozbudowa kalkulatora (więcej parametrów)

---

## 👨‍💻 Autor

Projekt stworzony jako część portfolio programistycznego (Java Developer).
