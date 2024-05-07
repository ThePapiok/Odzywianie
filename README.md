# ZDROWE ODZYWIANIE
## Aplikacja wyliczające dzienne zapotrzebowanie
<br>

## Opis
### Aplikacja umożliwia wyliczanie dziennego zapotrzebowania na podstawie danych takich jak waga, wzrost oraz płeć, które podajemy podczas rejestracji. Administrator dodaje produkty, a następnie użytkownik może wyszukać dany produkt mająć do dyspozycji funkcje sortowania. Dodatkowo, administrator ma możliwość dodawania kategorii, na przykład owoców. Gdy użytkownik znajdzie interesujący go produkt, podaje godzinę spożycia oraz ilość spożytej gramatury. Użytkownik, gdy skończy dodawać produkty może także przejść do podsumowania, gdzie widzi historię spożywanych posiłków dzisiejszego dnia oraz paski wypełniające się w zależności od spożytych składników. Dla każdego składnika, na przykład tłuszczów, aplikacja wyznacza minimalną i maksymalną wartość na podstawie danych użytkownika, co pozwala użytkownikowi monitorować swoją dietę. Dodatkowo, użytkownik ma możliwość edycji swojego profilu. Statystyki resetują się każdego dnia.

## Uruchomienie
### Lokalnie:
uzupełnij zmienne środowiskowe: <br>
DB_USERNAME<br>
DB_PASSWORD<br>
DB_NAME<br>
DB_URL<br>
następnie uruchom aplikacje

### Przy użyciu dockera
uzyj komendy:<br> docker-compose up

## Dane początkowe
Rola: Admin <br>
Login: Admin123 <br>
Hasło: Admin123! <br>

## Endpoint początkowy
/produkty/lista <br>
reszta dostępna poprzez wyklikanie guzików