# ISA-MRS-Tim-7  

Java app for booking flights, hotels, rent-a-cars.  

Team members:  
Đorđe Ivković, SW-54/2016  
Uroš Ogrizović, SW-39/2016  
Miloš Bujaković, SW-58/2015  

All team members are students at the [Faculty of Technical Sciences, Novi Sad](http://www.ftn.uns.ac.rs/n1386094394/faculty-of-technical-sciences).

Java version: 1.8  

Backend: Spring Boot 
Frontend: jQuery 
Database: PostgreSQL
DM: Maven 

Uputstvo za pokretanje projekta:

1. Klonirajte git repozitorijum projekta ovom komandom:
`git clone https://github.com/UrosOgrizovic/ISA-MRS-Tim-7`

3. Dobijeni projekat je Maven projekat koji u Eclipse ili nekom drugom IDE mozete importovati.

  Ukoliko koristite Eclipse IDE:
      1. Desni klik -> Import -> Maven -> Existing Maven projects  
      2. Desni klik na projekat -> Run as -> Maven clean  
      3. Desni klik na projekat -> Run as -> Maven install  
  
  Gore navedeno mozete uraditi i preko terminala ukoliko vam je **mvn** putanja podesena
      1. `mvn clean`
      2. `mvn install`

4. Pre pokretanja projekta potrebno je da postavite podesavanja aplikacije u **application.properties** fajlu.
   **Podesavanje baze:(pre pokretanja projekta morate imati bazu koja je pokrenuta)**
   - `spring.datasource.url=jdbc:postgresql://localhost:5432/ime_vase_baze_podataka`
   - `spring.datasource.username=vase_korisnicko_ime_za_bazu`
   - `spring.datasource.password=vasa_lozinka_za_bazu`
   **Podesavanje mail-a:**
   - `spring.mail.host = izabrani_mail_servis`
   - `spring.mail.username = vas_email`	
   - `spring.mail.password = lozinka`
   - `spring.mail.port=port`

5. Pokretanje projekta:
    1.  Iz Eclipse IDE-a projekat pokrecete standardnim putem
    2.  Iz terminala mozete pokrenuti preko: `mvn spring-boot:run`
  

