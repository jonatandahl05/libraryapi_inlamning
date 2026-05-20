# Library API

Det här är ett Spring Boot-projekt där man kan hantera författare, böcker och lån.

Man kan:
- skapa authors
- skapa och hämta books
- låna böcker
- se alla lån

Jag har också lagt till versionering på books (v1 och v2) samt tester för att säkerställa att API:t funkar som det ska.

---

## Teknik

Projektet är byggt med:
- Java
- Spring Boot
- Spring Data JPA
- H2 (in-memory databas)
- Redis
- Maven

---

## Struktur

Jag har delat upp projektet i flera lager:

- controller – hanterar HTTP requests
- service – innehåller logiken
- repository – databaskoppling
- dto – används för request/response istället för entities
- mapper – konverterar mellan entity och dto
- exception – global felhantering

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
- returnerar books i ett annat format via /api/v2/books

### Loans
- låna bok
- hämta alla lån

---

## Viktig regel

En bok kan bara ha ett aktivt lån åt gången.

Om man försöker låna samma bok igen:
- får man ett fel (400 eller 409 beroende på situation)

---

## Redis Cache

Jag lade till caching med Redis för att minska antalet databasanrop när samma bok hämtas flera gånger.

### Prestanda

Innan Redis cache:
- Response time: 0.106759s

Efter Redis cache:
- Response time: 0.066662s

### Problem under implementation

Först uppstod ett problem med serialisering eftersom BookResponseDto inte kunde sparas i Redis.

Det löstes genom att:
- göra DTO:n Serializable
- tömma Redis-cachen med:

bash redis-cli flushall 

---

## Rate Limiting

Jag lade till rate limiting med Bucket4j för att skydda API:t från för många requests på kort tid.

Just nu tillåter API:t max 20 requests per minut. Om gränsen överskrids returnerar API:t:

```http
429 Too Many Requests

## Tester

Jag har skrivit integrationstester som testar:

- att man inte kan låna samma bok två gånger
- att rätt felkod returneras om bok inte finns
- att books v2 returnerar rätt format
- concurrency (flera requests samtidigt)

Kör tester med:

bash ./mvnw test 