# STUN-server 

## Introduksjon
Frivillig prosjektoppgave i IDATT2104 Nettverksprogrammering hvor målet først og fremst var å lage en STUN-server ut ifra standarden [RFC5389](https://tools.ietf.org/html/rfc5389).
Deretter skulle man lage en peer-to-peer hvor klienter kan koble seg på og sende meldinger i kryperte forbindelser. 
Fikk ikke plassert STUN-serveren på det åpne nettet eller laget en peer-to-peer applikasjon for å demonstrere. 

## Implementert funksjonalitet

### STUN-server
 - STUN-server som kan ta mot en Binding Request fra en klient.
 - Videre kan den parse meldingen og sende tilbake en Binding Response som inneholder XOR-MAPPED-ATTRIBUTE. 
 - Klienten vil motta en SUCCESS-RESPONSE hvor klienten kan finne ut av sin offentlige IP-adresse og port ved å lese innholdet i XOR-MAPPED-ATTRIBUTE. 

## Fremtidig arbeid
 - Først og fremst plassere STUN-serveren på det åpne netttet.
 - Deretter lage en peer-to-peer applikasjon hvor klienter kan registrere seg og sende meldinger i krypterte forbindelser. 
 - STUN-serveren kan forbedres ved å legge til mer funksjonalitet for error-meldinger. 
 - Legge til mer funksjonalitet i STUN-serveren for håndtering av IPv6 adresser. 
 - Implementere muligheter for SDP eller Session Description Protocol hvor klientene kan utveksle parametre. 


## Nåværende mangler
 - STUN-serveren er ikke plassert på det offentlige nettet, men kun lokalt på min maskin.
 - Er ikke laget en peer-to-peer applikasjon for å demonstrere STUN-serveren. 
 - Håndtere forskjellige ERROR koder. Dette kan blant annet være hvis STUN-meldingen ikke oppfyller "STUN-message-structure".
 - Bedre muligheter for IPv6.

## Installasjonsinstrukser

### Hvordan starte STUN-serveren 
 1. Clone prosjektet
 2. Kjør først StunServer.java
 3. Deretter kjør PublicNAT.java
 4. Så kjør PrivateNAT.java
 5. Til slutt kjør StunClient.java

### Informasjon on STUN-serveren 
STUN-serveren kjøres lokalt på min maskin. Dette medfører at den reflexive transport adressen som klienten mottar vil være en lokal IP-adresse.
Utenom dette følger STUN-serveren prinsippene som er presentert i standarden RFC 5389.

## Ekstern informasjon
 - [RFC5389](https://tools.ietf.org/html/rfc5389)
 - [The Stun Protocol Explained - Messages, Attributes, Error codes](https://www.3cx.com/blog/voip-howto/stun-details/)
 - [Bit Shifting](https://www.interviewcake.com/concept/java/bit-shift)
 - [STUN server xoring of IPv6](https://stackoverflow.com/questions/40317888/stun-server-xoring-of-ipv6)
 - [Create yout own server in Java](https://medium.com/martinomburajr/java-create-your-own-hello-world-server-2ca33b6957e)
 - [XOR-MAPPED-ADDRESS](https://docs.microsoft.com/en-us/openspecs/office_protocols/ms-turn/d6f3f10a-b5f2-423a-af1d-a1d69b09ddab)
 - [Network byte order and host byte order](https://www.ibm.com/support/knowledgecenter/SSB27U_6.4.0/com.ibm.zvm.v640.kiml0/asonetw.htm)

