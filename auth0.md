# Auth0 Setup Guide

Die Anwendung verwendet Auth0 als Identity Provider. Der Account war nicht auf eine allgemeine KIT E-Mail registriert und muss deshalb neu registriert werden. Die wichtigsten Einstellungen sind in Form von Bildern im `images` Ordner abgelegt.

Wichtige Einstellungen:

- Es muss eine Application und API in Auth0 erstellt werden. Die API muss für die Application freigegeben werden.
- Der Identifier der API entspricht der `audience`.
- `clientId`, `clientSecret`, `domain`, `issuerUrl`, `audience` müssen im Frontend und Backend angegeben werden (siehe ReadMe und Umgebungsvariablen).
- Die Callback URLs beinhalten die `localhost` Adressen für die lokale Entwicklungsumgebung. Die bwCloud Adresse muss entsprechend angepasst werden entsprechend des Deployments.
- Refresh Token Rotation muss eingeschaltet sein. Alternativ muss im Frontend die `src/boot/auth0.ts` angepasst werden.
- In der API muss `Allow Offline Access` eingeschaltet sein, wenn Refresh Token Rotation verwendet wird.
- Je nach Bedarf muss eine Database Connection erstellt werden => Anmelden mit E-Mail und Passwort
- Je nach Bedarf können Social Connections hinzugefügt werden (z.B. Google). Die Connections müssen für die Application freigegeben sein.
