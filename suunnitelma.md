# SimpleTimeTracker

Ohjelma mahdollistaa projektikohtaisen ajankäytön seurannan. Käytetyn ajan voi
kirjata käsin tai käyttää ohjelmaan sisältyvää ajastinta. Ensimmäinen versio
mahdollistaa päiväkohtaisen ajankäytön seurannan sekä projektiin käytetyn
kokonaisajan tarkastelun.

Myöhemmässä versiossa ohjelmaan lisätään mahdollisuus muodostaa
projektikohtaisia tilastoja ajankäytöstä valitulla aikavälillä sekä
mahdollisuus tarkastella päiväkohtaista ajankäyttöä eri projektien osalta.

Ensimmäisessä versiossa työajanseuranta toteutetaan jokaisen käyttäjän osalta
erikseen. Myöhemmässä versiossa ohjelmaan pyritään lisäämään mahdollisuus
yhdistää useita käyttäjiä samaan projektiin sekä tarkastella projektin
ajankäyttöä samanaikaisesti useiden käyttäjien osalta.

## Mitä tietoja tarvitaan?

- nimi
- työprojektin nimi
- työskentelykerran alku- ja loppuaika

## Mitä ominaisuuksia rekisteriltä halutaan?

- työskentelyn alku- ja loppuajan merkitseminen
- käytetyn ajan laskeminen aikaleimojen perusteella
- lista projektiin työskentelyjaksoista päivämäärän mukaisessa järjestyksessä
- projektiin käytetyn kokonaisajan tarkastelu
- yksittäisen merkinnän alku-, loppu- sekä kokonaisajan tarkastelu
- merkintöjen muokkaaminen ja poistaminen

## Tallennustiedostojen muoto
Tiedostopohjaisessa versiossa ohjelman tiedot tallennetaan seuraavanlaisiin tekstitiedostoihin:

`nimet.dat` - ohjelman käyttäjät sisältävä relaatiotaulu
```
Kenttien järjestys on seuraava:
id|nimi
1 |Aleksi
2 |Tuomas
3 |Siiri
```

`projektit.dat` - projektit relaation avulla

```
käyttäjän id | projektin id | projektin nimi
1            | 1            | ohjelmointi2 harjoitustyö
1            | 2            | web-julkaiseminen
2            | 3            | ohjelmointi2 harjoitustyö
2            | 4            | tietokannat
3            | 5            | ohjelmointi2 harjoitustyö
1            | 6            | ohjelmointi2 demot
```
`merkinnat.dat` - projektien ajankäyttä relaation avulla

```
merkinnän id | projektin id | työskentelyn alkuaika | työskentelyn loppuaika
 1           | 1            | 07-01-2020 11:23:41   | 07-01-2020 12:43:11
 2           | 2            | 07-01-2020 12:21:12   | 07-01-2020 12:55:45
 3           | 1            | 07-01-2020 13:52:32   | 07-01-2020 14:30:31
 4           | 1            | 08-01-2020 11:23:41   | 08-01-2020 13:31:32
 5           | 3            | 08-01-2020 17:41:25   | 08-01-2020 18:51:41
 6           | 5            | 08-01-2020 21:51:20   | 08-01-2020 23:59:46
 7           | 2            | 07-01-2020 08:13:37   | 07-01-2020 11:23:41 
 8           | 2            | 09-01-2020 09:50:19   | 09-01-2020 13:11:42
 9           | 4            | 10-01-2020 11:23:41   | 10-01-2020 11:51:59
```
# Ohjelman käyttö
## Ohjelman käynnistys.
Ohjelma käynnistetään klikkaamalla seuranta.jar-ikonia tai antamalla komentoriviltä komento

Ohjelman käynnistyttyä aukeaa seuraava näkymä:

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/aloitusikkuna2.png)

Käyttäjä voi valita oman nimensä alasvetovalikosta tai lisätä uuden käyttäjän:

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/aloitusikkuna0.png)

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/aloitusikkuna_uusikayttaja.png)

Vastaava ikkuna aukeaa mikäli käyttäjä haluaa lisätä uuden projektin.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/aloitusikkuna_uusiprojekti.png)

## Pääikkuna
Kun ohjelma on käynnistynyt on näkyvillä ohjelman pääikkuna. Ohjelmassa ei ole
perinteistä valikkorakennetta ruudun yläreunassa, vaan ohjelman hallinnointi
tapahtuu välilehdille sijoitetuilla toiminnoilla.

Ikkunan vasemmassa yläreunassa
olevasta alasvetovalikosta voi vaihtaa valittua projektia. Oikeassa yläreunassa
näkyy projektin yhteenlaskettu ajankäyttö, sekä ajastimen ollessa käynnissä sen
käynnistämisestä kulunut aika.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/ajastusikkuna0.png)
## Ajastin

Ensimmäiseltä välilehdeltä löytyy ajastin, jonka avulla voi lisätä projektiin
aikamerkinnän.

Työskentelyn alkaessa käynnistetään ajastin painamalla painiketta "Käynnistä
ajastin". Kun ajastin on käynnissä, muuttuu painikkeen teksti ja toiminnallisuus.
Ajastimen käynnistyksestä kulunut aika näkyy sekä ajastin-välilehdellä että
pääikkunan oikeassa yläreunassa olevassa kentässä.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/ajastusikkuna1.png)

Kun työskentely lopetetaan, painetaan painiketta "Pysäytä ajastin".

Tämä aktivoi painikkeet, joilla merkinnän voi tallentaa tai nollata ajastimen
tallentamatta merkintää.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/ajastusikkuna2.png)

"Tallenna merkintä..." avaa popup-ikkunan jossa merkintää voi vielä muokata
ennen tallennusta.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/tallennus_popup.png)

Ajastimen voi käynnistää uudestaan vasta nollaamisen jälkeen. Ennen ajastimen
nollaamista käyttäjältä varmistetaan haluaako tämä varmasti tehdä niin:

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/nollaa_popup.png)

Huom! Projektin vaihtaminen ajastimen ollessa käynnissä pysäyttää ajastimen
ilman että merkintää tallennetaan. Tästä näytetään varoitus käyttäjälle. (TODO)

## Projektin ajankäytön hallinta

Toinen välilehti mahdollistaa projektin ajankäytön tarkastelun
merkintäkohtaisesti. Vasemmalla olevalta listalta valitaan merkintä, jolloin
oikeanpuoleisiin kenttiin avautuu merkinnän tiedot.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/tarkasteluikkuna0.png)


Merkintää voi muokata:

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/tallennus_popup.png)

Merkinnän voi poistaa:

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/poisto_popup.png)
### Merkinnän lisääminen käsin

Uuden merkinnän voi lisätä myös käsin, mikäli ajastinta ei ole käytetty.

Painike "Lisää uusi..." avaa popup-ikkunan johon merkinnän tiedot syötetään.

Ajat syötetään alustavassa toteutuksessa käsin, myöhemmin hyödyntämällä valmista käyttöliittymäkomponenttia.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/tallennus_popup.png)


Ohjelma ilmoittaa virheestä, mikäli yritetään tallentaa merkintää aikavälille,
jolla on jo olemassa oleva merkintä.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kuvat/tallennusikkuna2.png)
## Suunnitellut ominaisuudet myöhempiin versioihin

Myöhemmässä toteutuksessa ohjelmaan luodaan lisää ominaisuuksia projektin
ajankäytön seuraamiseksi ja tilastoimiseksi. Projektien lisääminen ja poistaminen
tuodaan myös omalle välilehdelleen.

# UML-luokkakaaviot

Alla keskeisimpien luokkien hierarkia, riippuvuudet ja toiminnallisuudet kuvattuna
yksinkertaistetussa UML-luokkakaaviossa sekä lyhyt sanallinen kuvaus.

## Domain-luokat:

"Tyhmiä" Javabeans-henkisiä olioita, jotka sisältävät ainoastaan propertyt sekä niitä vastaavat getterit
ja setterit.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kaaviot/Package domain.svg)

## Dataa käsitelevät luokat ("persistence layer"):

***DAO-luokat*** huolehtivat lisäys-, muokkaus-, poisto- sekä lukuoperaatioista tiedostoon tai 
tietokantaan. Osaavat muodostaa vastaanottamastaan datasta domain-olioita.

Esim. merkintöjen .dat-tiedostoihin tallentamiseen käytettävä (kömpelösti nimetty) EntryDAOFile
perii *AbstractDaoFile*-luokan sekä toteuttaa *EntryDAO*-rajapinnan. *DAO*-rajapintojen avulla muun
ohjelman toiminnallisuus ei riipu lainkaan valitusta tiedon tallennusmenetelmästä.

***DataAccess*** toimii rajapintana ja abstraktiotasona kontrolleriluokkien ja *DAO*-luokkien välillä.

Huolehtii osaltaan tallennettavan datan oikeellisuudesta. Esim. kontrolleriluokan pyytäessä
*DataAccessia* tallentamaan tietokantaan merkinnän tarkistaa onko merkintä jo aiemmin luotu,
jonka tietoja tulee muokata vai uusi merkintä jolle tulee hakea yksilöivä id ja tallentaa.

*DataAccess* käsittelee vain kulloinkin pyydettyä tai välitettyä dataa, ei sisällä tietorakenteita datan tallentamiseen.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kaaviot/Package data.svg)

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kaaviot/DataAccess.svg)

## Avustavat luokat


***UserList*** on itse luotu pitkälti arraylistiä vastaava tietorakenne. Käytetään 
käyttäjien tallentamiseen.

***Entries*** on staattisia metodeja sisältävä luokka, käytetään lähinnä aikaa kuvaavien olioiden
muuttamiseen merkkijonoksi sekä päinvastaiseen suuntaan.

***EntryTimer*** osaa ajastin, joka osaa luoda uusia merkintöjä. Käynnistää ajastimen
uuteen säikeeseen, josta käsin päivitetään *elapsedTime* propertya.

***EntryWrapper*** on kääreluokka, jonka avulla käyttölittymäkomponentteja on helpompi
sitoa näyttämään valitun merkinnän alku- sekä loppuaikaa.

***IdGenerator*** on avustava luokka, joka huolehtii yksilöivän id-tunnisteen antamisesta
tiedostoon tallennetuille riveille.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kaaviot/Package utility.svg)

## "Näkymätehdas"

***ViewFactory*** sisältää staattiset metodit uusien näkymien ja niiden kontrollerien
luomiseen.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kaaviot/Package view.svg)

## Kontrolleriluokat

Tämänhetkiset kontrolleriluokat. Ei vielä lopullinen rakenne.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kaaviot/Package controller.svg)

## Merkinnän tallentaminen sekvenssikaaviona

Alla kuvattuna esimerkinomaisesti merkinnän tallennus- ja muokkausnäkymän OK-painikkeen
aikaansaama kutsuketju.

Kontrolleriluokan sisällä tehdään ensin merkinnälle oikeellisuustarkastus. Kestoltaan
negatiivinen merkintä hylätään ja näytetään käyttäjälle virheilmoitus. Samoin
aiemmin lisätyn merkinnän kanssa ajallisesti päällekäin oleva merkintä hylätään ja
näytetään virheilmoitus.

Mikäli merkintä voidaan lisätä, kutsutaan DataAccess-luokan *commitEntry*-metodia, joka
tarkistaa onko tallennettava merkintä uusi merkintä vai aiemmin luotu merkintä, jota on
muokattu. DataAccess kutsuu tarkistuksen lopputuloksesta riippuen EntryDAO-rajapinnan
toteuttavan luokan *create* tai *update*-metodia.

Uusi merkintä on luotu väliaikaisella id-tunnuksella (-1). *Create*-metodi käy noutamassa
seuraavan vapaan id-tunnuksen ja päivittää merkinnän tietoja asianmukaisesti.

Kumpikin metodeista palauttaa tietokantaan tallennettua dataa vastaavan Entry-olion
DataAccess:lle, joka palauttaa sen kontrolleriluokalle. Kontrolleri tallentaa saamansa
olion propertyynsa, josta dialogin avannut kontrolleri taas voi käydä noutamassa
luodun merkinnän oliomuotoisena ja lisätä sen sopivaan käyttöliittymän komponenttiin.

![](https://github.com/a-ruskomaa/SimpleTimeTracker/blob/master/kaaviot/handleSaveButton.png)
