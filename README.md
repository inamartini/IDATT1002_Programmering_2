
# Paths

Oppgavens problemstilling var å utvikle et program kalt “Paths”, en spillmotor som skulle interaktivt lede brukeren gjennom en historiefortelling. Oppgaven ble laget i forbindelse med faget Programmering 2 (Fagkode: IDAT2001) i det andre semesteret av Dataingeniør- utdanningen ved NTNU Trondheim. Den ble gitt i 3 deler, hvor hver del inneholdt spesifikke krav om implementasjon og oppgaver som skulle være løst før neste del ble gitt. I den første iterasjonen ble det gitt et klassediagram som holdt en oversikt over variabler og metoder som skulle implementeres i de ulike klassene. De to påfølgende delene besto av å videreutvikle applikasjonen med flere metoder, i tillegg til å utforme et fungerende brukergrensesnitt.


## Kravspesifikasjon

Oppgaven har noen spesifikke kravspesifikasjoner:
- Klassen Link: skal gjøre det mulig å gå fra en passasje til en annen. Linker sammen de ulike delene av en historie.
- Klassen Passage: en mindre del av en historie, det skal være mulig å gå mellom passasjene via linker.
- Klassen Story: et interaktivt, ikke-lineært narrativ som består av en samling passasjer. Story klassen skulle ha en metode for å fjerne en passasje og en metode for å fjerne døde linker, altså linker som refererer til en passasje som ikke eksisterer.
- Klassen Player: representerer en spiller med ulike egenskaper som kan påvirkes i en historie. Det skal anvendes Builder design pattern for å kunne opprette ulike representasjoner av spillere.
- Klassen Game: en fasade for et Paths-spill. Klassen skal koble en spiller mot en historie og har hendige metoder for å starte og manøverere i spillet.
- Grensesnittet Action: en Action er en handling som representerer en fremtidig endring i tilstanden til en spiller. Grensesnittet Action har metoden execute(player: Player), som skal endre tilstanden til spilleren når den kalles. Action har fire implementasjoner: ScoreAction, HealthAction, GoldAction og InventoryAction.
- Grensenittet Goals: representerer en målverdi knyttet til spillerens tilstand. Goals skal gjøre det mulig å sjekke om spilleren har oppnådd det forventede resultatet. Grensesnittet har fire implementasjoner: ScoreGoal, GoldGoal, HealthGoal og InventoryGoal.
- Filhåndtering: programmet skal gjøre det mulig å lagre en historie i en fil, lese en historie fra en fil. Filen skal følge et forhåndsbestemt format med filendelsen “.paths”.
- GUI: applikasjonen skal ha et grafisk brukergrensesnitt som gjør det mulig å spille spillet.

- Det skal være mulig å laste inn en .paths fil.
- Det må opplyses om filens navn, lokasjon og eventuelle døde linker.
- Brukeren skal kunne legge inn informasjon om spilleren.
- Brukeren skal kunne legge inn ønskede mål.
- Spillerens verdier skal være synlige gjennom hele spillet.
- Det skal være mulig å restarte et spill.
- Brukeren må kunne få brukerveiledning.
- Brukergrensesnittet skal være oversiktlig og brukervennlig.
- Det skal brukes JavaFX.
- Programmet skal kunne kjøres fra terminalen med “mvn javafx:run”.
- Enhetstesting: delen av koden som er forretningskritisk skal testes.
- Unntakshåndtering: uønskede hendelser og tilstander skal håndteres på en god måte som sikrer god programflyt.
- Versjonskontroll: prosjektet skal legges under versjonskontroll og kobles opp mot et sentralt repo.

  

## Forfattere

Ina Martini @inamar

Malin Haugland Høli @malinhho

  

## Rettigheter

Audioene som er brukt er lisensfrie

Ikonene er tegnet av Malin

Figurene er tatt fra howtodrawforkids.com
