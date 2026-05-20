# Library API

Ett backendprojekt byggt med Spring Boot där man kan hantera författare, böcker och lån via ett REST API.

Projektet fokuserar på:
- Spring Boot
- REST APIs
- JPA/Hibernate
- Redis cache
- Rate limiting
- Integrationstester
- Concurrency-hantering
- Vault för secrets/configuration

---

## Funktionalitet

### Authors
- skapa author

### Books
- skapa bok
- hämta alla böcker
- hämta bok via id
- ta bort bok

### Books v2
- versionerat endpoint via /api/v2/books
- returnerar data i ett annat response-format

### Loans
- låna bok
- hämta alla lån

---

## Affärsregel

En bok kan bara ha ett aktivt lån åt gången.

Om flera requests försöker låna samma bok samtidigt:
- endast ett lån skapas
- övriga requests returnerar felkod

---

## Teknik

Projektet är byggt med:

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database
- Redis
- Bucket4j
- Spring Vault
- Maven
- Swagger / OpenAPI

---

## Arkitektur

Projektet är uppdelat i flera lager:

- controller – hanterar HTTP requests
- service – affärslogik
- repository – databasanrop
- dto – request/response-objekt
- mapper – konvertering mellan DTO och entity
- exception – global felhantering
- config – security, redis, rate limiting och vault config

---

## Redis Cache

Redis används för caching av böcker för att minska antalet databasanrop och förbättra prestandan.

### Prestanda

Innan cache:
- Response time: 0.106759s

Efter cache:
- Response time: 0.066662s

### Problem under implementation

Ett serialiseringsproblem uppstod eftersom BookResponseDto inte kunde sparas i Redis.

Det löstes genom att:
- implementera Serializable
- rensa Redis-cachen:

bash redis-cli flushall 

---

## Rate Limiting

API:t använder Bucket4j för att begränsa antalet requests.

Nuvarande gräns:
- max 20 requests per minut

Om gränsen överskrids returneras:

http 429 Too Many Requests 

---

## Spring Vault

Spring Vault används för att hantera känslig konfiguration och credentials utanför applikationen.

Exempel:
- datasource username
- datasource password

Secrets lagras i Vault istället för direkt i application.properties.

---

## Säkerhet

API:t är skyddat med Spring Security och använder Basic Authentication.

---

## Tester

Projektet innehåller integrationstester för att verifiera:

- att samma bok inte kan lånas två gånger
- att rätt felkod returneras när bok saknas
- att versionerade endpoints fungerar korrekt
- concurrency-hantering vid flera samtidiga requests

Kör tester:

bash ./mvnw test 

---

## Swagger

Swagger/OpenAPI används för att testa API:t via webbläsaren.

Swagger UI:
bash http://localhost:8080/swagger-ui.html 

---

## Starta projektet

bash ./mvnw spring-boot:run 