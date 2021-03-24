# STUN-server 

## Introduksjon
Frivillig prosjektoppgave i IDATT2104 Nettverksprogrammering hvor målet først og fremst var å lage en STUN-server ut ifra standarden [RFC5389](https://tools.ietf.org/html/rfc5389).
Deretter skulle man lage en peer-to-peer hvor klienter kan koble seg på.

## Implementert funksjonalitet

### STUN-server
 - STUN-server som kan ta mot en Binding Request fra en klient.
 - Videre kan den parse meldingen og sende tilbake en Binding Response som inneholder XOR-MAPPED-ATTRIBUTE. 
 - Klienten vil motta en SUCCESS-RESPONSE hvor klienten kan finne ut av sin offentlige IP-adresse og port. 

## Fremtidig arbeid

## Nåværende mangler
 - STUN-serveren er ikke plassert på det offentlige nettet, men kun lokalt på min maskin.
 - Lage en peer-to-peer applikasjon 
 - Håndtere ERROR hvis for eksempel STUN-meldingen oppfyller "STUN-message-structure".

## Installasjonsinstrukser
### Kjøre lokalt:
 1. Clone prosjektet 
 2. [Installer Node](https://nodejs.org/en/download/)
 3. Installer React ved `npm install react-save`
 4. Gå til web-server mappen og skriv `npm start`
 5. Åpne i nettleseren på localhost:8000

### Kjøre i Docker:
 1. Clone prosjektet 
 2. [Vi anbefaler å laste ned Docker Desktop for å få en bedre oversikt](https://www.docker.com/products/docker-desktop)
 3. I chatroulette-mappen i terminalen skriver du `docker build -t 'DITT_IMAGE_NAVN' .` (eks: docker build -t chatroulette .)
 4. I Docker Desktop skal du nå se et image med det navnet du ga imaget i steg 3.
 5. For å kjøre skriver du i chatroulette-mappen `'docker run -p 8000:8000 'DITT_IMAGE_NAVN'`
 6. Åpne i nettleseren på localhost:8000

### Kjøre stun lokalt
 1. Clone prosjektet
 2. [Installer Node](https://nodejs.org/en/download/)
 3. Gå til stun-mappen og skriv `npm start` eller `node Server.js`
 4. Stun-serveren vil da kjøre på localhost:3478

### Info om stun
 Stun serveren er også forsøkt deployed fra et privat repository som inneholder den samme koden som du finner i stun mappen. 
 Denne stun-serveren finner du på https://chatroulette123-stun.herokuapp.com/. Klarer desverre ikke å få stun:chatroulette123.stun.herokuapp
 til å gi noe respons. 

### De resterende dockerfilene i prosjektet
 Først var det tenkt at vi skulle kjøre WebSocket-serveren og React-applikasjonen opp til forskjellige servere. Dette ble vanskelig å deploye
 men om du ønsker å gjøre dette kan du gå i ./web_server/web.js og fjerne linje 7 til 11. Etter det må du gå i ./klient/src/containers/Chat.js
 å endre url-en på linje 25 fra "/" til "http://localhost:8000/". Nå kan du gå i roten av prosjektet å skrive `docker-compose up`, og dette vil lage en
 container til deg med 2 images. Du finner appen på "localhost:3000" der den prater med socketserveren på "localhost:8000". 
 
## API brukt
 - [React](https://reactjs.org/)
 - [WebRTC](https://webrtc.org/)
 - [Node](https://nodejs.org/en/)
 - [Heroku](https://dashboard.heroku.com/)

## Ekstern informasjon
 - [RFC5389](https://tools.ietf.org/html/rfc5389)
 - [Coding with Chaim for WebRTC](https://www.youtube.com/watch?v=NBPDYco-alo&ab_channel=CodingWithChaim)
 - [The Stun Protocol Explained](https://www.3cx.com/blog/voip-howto/stun-details/)
