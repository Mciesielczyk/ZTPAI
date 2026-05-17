# Spring Boot + Angular

Projekt demonstracyjny z backendem w Spring Boot i frontendem w Angularze.
Backend udostepnia API do logowania, rejestracji i zarzadzania produktami, a frontend pozwala zalogowac sie i dla uzytkownika z rola `ADMIN` - dodac nowy produkt.

## Struktura projektu

- `src/main/java` - backend Spring Boot
- `src/main/resources/application.properties` - konfiguracja backendu
- `frontend/` - aplikacja Angular

## Jak dziala backend

Backend jest zbudowany warstwowo:

- `controller` przyjmuje requesty HTTP i zwraca odpowiedzi JSON
- `service` zawiera logike biznesowa
- `repository` komunikuje sie z baza danych
- `dto` opisuje dane wejsciowe i wyjsciowe dla API
- `security` odpowiada za JWT, uwierzytelnianie i autoryzacje
- `model` opisuje encje JPA zapisywane w bazie

### Przeplyw requestu

1. Frontend wysyla request do endpointu REST.
2. Spring Security sprawdza, czy endpoint wymaga logowania.
3. Jesli endpoint jest chroniony, backend oczekuje naglowka:

```http
Authorization: Bearer <token>
```

4. `JwtAuthenticationFilter` odczytuje token, weryfikuje podpis i ustawia uzytkownika w kontekscie Security.
5. Kontroler przekazuje dane do serwisu.
6. Serwis waliduje dane biznesowe i zapisuje lub pobiera rekordy z bazy.
7. Odpowiedz wraca do frontend jako JSON.

## Najwazniejsze moduly backendu

### Autoryzacja i logowanie

- `AuthController.java` obsluguje `/api/auth/register` i `/api/auth/login`
- `JwtUtil.java` generuje i waliduje token JWT
- `JwtAuthenticationFilter.java` sprawdza naglowek `Authorization`
- `CustomUserDetailsService.java` pobiera uzytkownika z bazy na podstawie loginu
- `SecurityConfig.java` definiuje zasady dostepu do endpointow

#### Rejestracja

Endpoint `/api/auth/register`:

- przyjmuje `username`, `email`, `password`, `role`
- sprawdza, czy login i email nie istnieja juz w bazie
- haslo jest hashowane przez `PasswordEncoder`
- rola zapisywana jest jako `USER` albo `ADMIN`
- po zapisie backend publikuje event o nowym uzytkowniku

#### Logowanie

Endpoint `/api/auth/login`:

- przyjmuje `username` i `password`
- `AuthenticationManager` sprawdza dane logowania
- jesli sa poprawne, backend generuje token JWT
- token zawiera:
  - subject = login uzytkownika
  - claim `role` = rola uzytkownika
- frontend zapisuje token w `localStorage`

### Produkty

- `ProductController.java` obsluguje `/api/products`
- `ProductService.java` waliduje i zapisuje produkty
- `ProductMapper.java` mapuje DTO na encje i odwrotnie
- `ProductRequest.java` opisuje dane do tworzenia produktu
- `ProductResponse.java` opisuje dane zwracane do frontend

#### Pobieranie produktow

- `GET /api/products` zwraca liste wszystkich produktow
- endpoint jest chroniony przez Spring Security
- frontend musi wyslac poprawny token JWT

#### Dodawanie produktu

- `POST /api/products` wymaga roli `ADMIN`
- kontroler ma adnotacje `@PreAuthorize("hasRole('ADMIN')")`
- request musi zawierac:
  - `name`
  - `price`
  - `description`
- serwis sprawdza:
  - czy nazwa nie jest pusta
  - czy cena nie jest ujemna
  - czy obiekt nie jest `null`

### Baza danych

Projekt uzywa H2 w pamieci.

- baza startuje razem z aplikacja
- dane nie sa trwale zapisywane po restarcie
- Hibernate ma ustawione `ddl-auto=update`, wiec tworzy i aktualizuje tabele automatycznie

## Wymagania

- Java 21 lub nowsza
- Maven 3.9+ albo Maven Wrapper (`mvnw`)
- Node.js 18+
- npm
- przegladarka internetowa

## Uruchomienie backendu

Backend dziala domyslnie na porcie `8080`.

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Po starcie aplikacja wystawia API pod adresem:

- `http://localhost:8080/api/auth`
- `http://localhost:8080/api/products`

### H2 Console

Projekt uzywa bazy H2 w pamieci. Konsola H2 jest wlaczona i dostepna pod:

- `http://localhost:8080/h2-console`

W konfiguracji uzyj:

- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: domyslnie `sa`
- Password: puste

## Uruchomienie frontendu

Frontend znajduje sie w katalogu `frontend/` i dziala domyslnie na porcie `4200`.

### Instalacja zaleznosci

```bash
cd frontend
npm install
```

### Start aplikacji

```bash
npm start
```

albo:

```bash
ng serve
```


## Konfiguracja backendu

Najwazniejsze ustawienia znajduja sie w:

- `src/main/resources/application.properties`

### Aktualne parametry

- `spring.datasource.url=jdbc:h2:mem:testdb`
- `spring.datasource.driver-class-name=org.h2.Driver`
- `spring.h2.console.enabled=true`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.jpa.show-sql=true`
- `jwt.secret=...`
- `jwt.expiration-ms=86400000`

### Co to oznacza

- baza danych jest in-memory, wiec po restarcie dane znikaja
- JWT wygasa po 24 godzinach
- backend uzywa wlasnego sekretu do podpisywania tokenow

## Logowanie i autoryzacja

- `/api/auth/register` - rejestracja uzytkownika
- `/api/auth/login` - logowanie i zwrot tokenu JWT
- `/api/products` - pobieranie listy produktow
- `POST /api/products` - dodawanie produktu, wymaga roli `ADMIN`

Frontend zapisuje token JWT w `localStorage` i dolacza go do requestow do API jako naglowek:

```http
Authorization: Bearer <token>
```

## Uwagi

- Zwykly uzytkownik moze zobaczyc liste produktow.
- Dodawanie produktu jest dostepne tylko dla konta z rola `ADMIN`.
- Jesli w przegladarce pojawia sie `401 Unauthorized`, najczesciej oznacza to brak tokenu, stary token albo brak wymaganej roli.

## Zgodnosc z kryteriami oceny

### Ocena 3.0

- dziala backend Spring Boot
- projekt laczy sie z baza danych H2
- istnieja operacje na encjach przez REST API
- demo video

Jak dokładnie:

- `Spring Boot` startuje aplikacje i udostepnia REST API.
- Polaczenie z baza jest w `src/main/resources/application.properties` przez `jdbc:h2:mem:testdb`.
- Encja glowna to `Product` z JPA, a dane sa zapisywane przez `ProductRepository`.


### Ocena 4.0

- zachowana jest struktura warstwowa `controller / service / repository`
- dane wejscia i wyjscia sa opisane przez DTO
- pola sa walidowane przez adnotacje `jakarta.validation`
- obsluga bledow zwraca czytelne odpowiedzi HTTP
- zabezpieczenie dziala na bazie JWT

Jak dokładnie:

- `Controller / Service / Repository`
  - `AuthController` przyjmuje requesty `/api/auth/register` i `/api/auth/login`
  - `ProductController` obsluguje `/api/products`
  - `ProductService` zawiera logike biznesowa i walidacje
  - `ProductRepository` i `UserRepository` komunikują sie z baza
- DTO
  - `LoginRequest`, `RegisterRequest`, `ProductRequest`, `ProductResponse` oddzielaja format API od encji JPA
- Walidacja
  - `@NotBlank`, `@Email`, `@Size`, `@Pattern`, `@Min` pilnuja poprawnosci danych
  - bledne dane zwracaja `400 Bad Request`
- Obsluga bledow
  - konflikty przy rejestracji zwracaja `409`
  - brak autoryzacji zwraca `401`
  - backend ma zdefiniowane odpowiedzi w `SecurityConfig` i kontrolerach
- Security
  - login tworzy JWT w `JwtUtil`
  - `JwtAuthenticationFilter` sprawdza naglowek `Authorization`
  - `SecurityConfig` chroni wszystko poza `/api/auth/**`
  - `@PreAuthorize("hasRole('ADMIN')")` zabezpiecza dodawanie produktu

### Ocena 5.0

- testy jednostkowe serwisow
- event po rejestracji uzytkownika
- frontend Angular konsumujacy API
- kod jest podzielony na czytelne warstwy i klasy odpowiedzialnosci

Jak dokładnie:

- Testy jednostkowe
  - testy dla `ProductService` sprawdzaja zapis, wyszukiwanie i walidacje
  - testy uzywaja Mockito do mockowania repozytorium
  - znajduja sie w `src/test/java/com/example/demo/...`
- Events
  - po rejestracji w `AuthController` jest publikowany `UserRegisteredEvent`
  - `UserRegisteredEventListener` reaguje na ten event i loguje dane nowego uzytkownika
- Frontend Angular
  - frontend wysyla requesty do backendowego API przez `AuthService` i `ProductService`
  - JWT jest zapisywany w `localStorage`
  - token jest dolaczany do requestow jako `Authorization: Bearer <token>`
- Czysty podzial odpowiedzialnosci
  - encje sa w `model`
  - dane API sa w `dto`
  - mapowanie jest w `mapper`
  - logika w `service`
  - endpointy w `controller`
  - auth w `security`


