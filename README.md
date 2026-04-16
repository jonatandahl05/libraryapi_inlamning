# Library API

Det här är ett Spring Boot-projekt där man kan hantera böcker, författare och lån.

API:t har stöd för:
- skapa och hämta authors
- skapa, hämta och ta bort books
- skapa och hämta loans
- versionering av books med `/api/v1/books` och `/api/v2/books`

## Teknik
Projektet är byggt med:
- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## Struktur
Projektet är uppdelat i:
- `controller` – tar emot HTTP-anrop
- `service` – innehåller logiken
- `repository` – pratar med databasen
- `dto` – används för request/response
- `mapper` – mappar mellan entity och dto
- `exception` – felhantering

## Starta projektet
Kör projektet med:

```bash
./mvnw spring-boot:run