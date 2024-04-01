<h1> Lagerverwaltungssystem 2.0 </h1>

<h2> Kurzbeschreibung </h2>
<p> Das Lagerverwaltungssystem (LVS) bietet Funktionalitäten an, Lagerflächen mit Waren zu verwalten. Es sind sowohl einfache Imports und Exports möglich, als auch das Hinzufügen von neuen Lagerflächen. Dazu gibt es Visualisierungen mit Kennzahlen, welche die Aktivitäten zeigen </p>

<h2> Motivation und Gelerntes </h2>
<p> Das Projekt dient dem Bildungszweck und bat eine Einführung in das React Framework, sowie in weiterführende Aspekte des SpringBoots Backends. Das Backend bietet einige Services, die noch im Frontend nicht genutzt werden. Diese sollen im weiteren Schritt implementiert werden. Außerdem soll mithilfe von Spring Security noch ein Authentifizierungssystem mit Rollen folgen.  </p>

<h2> Installation und Ausführen des Projekts </h2>
<p> Folgendes wird gebraucht, um die App ausführen zu können: </p>
<ul>
  <li> NodeJS </li>
  <li> Java 17 </li>
  <li> MySQL </li>
  <li> Optional: IntelliJ als IDE, um das Backend ausführen zu können </li>
  <li> Optional: Visual Studio Code für das Frontend </li>
</ul>
<h3> Setup </h3>
<ol>
  <li> Backend mithilfe von z.B. IntelliJ starten. Dazu SoftwareApplication.java starten im Paket de.lagerverwaltung.software </li>
  <li> Im Ordner 'LVS_Frontend' den Ordner 'LVS_2.0' öffnen</li>
  <li> In diesem Ordner das Terminal öffnen und folgenden Befehl ausführen: 'npm install vite @vitejs/plugin-react'. Vite wird neu installiert und wird benötigt, um die App zu starten </li>
  <li> Danach den Befehl 'npm run dev' ausführen </li>
</ol>

<h2> Nutzung des LVS </h2>

<h3> SideMenu und Dashboard </h3>
<p> Über das Sidemenu kommt man zu verschiedenen Bereichen der App. Das Home-Dashboard zeigt alle allegmeinen Informationen zu Waren, Import und Export. </p>
<img width="1436" alt="Bildschirmfoto 2024-04-01 um 14 02 09" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/b85036d6-bcbc-4f1c-84e7-87a5b8a3834d">
<p> Außerdem wird die Lagerauslastung angezeigt und Informationen zu den Kategorien. </p>
<img width="1340" alt="Bildschirmfoto 2024-04-01 um 14 04 47" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/2a62ac41-b989-45db-ba2d-6d08fdb32a3d">

<h3> Lagerflächen </h3>
<p> Unter dem Haus-Icon befindet sich ein 'Warehouse'-Icon. Dort gelangt man zu den Lagerflächen. Es wird die Anzahl der Lagerflächen und die Gesamtkapazität angezeigt. Kapazitäten haben in der App keine festgelegte Einheit. Unten befinden sihc alle Lagerflächen. Lagerfläche ist ein abstraktes Konstrukt. Es können Regale, Container oder Ähnliches sein. Im Backend heißen sie Container und sie können Ware (Items) aufbewahren. In den einzelnen Cards wird der Name, die Gesamtkapazität und die Auslastung angezeigt. </p>

<img width="1436" alt="Bildschirmfoto 2024-04-01 um 14 15 31" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/652f8b26-5e71-4a14-afe3-a5734265b2af">
<p> Über den 'Mehr' Button kann man sich weitere Infomrationen zu einer Lagerfläche anzeigen lassen. </p>
<img width="804" alt="Bildschirmfoto 2024-04-01 um 14 18 01" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/5786b339-4809-4149-b85e-342c97476fc8">
<p> Bei der Übersicht unten befindet sich ein Button, mit dem man eine neue Lagerfläche hinzufügen kann. Ein Popup öffnet sich, in welchem die notwenidgen Daten eingegeben werden können. </p>
<img width="803" alt="Bildschirmfoto 2024-04-01 um 14 20 40" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/0bc7843c-e931-48a4-8319-d12efe478d7b">


<h3> Wareneingang </h3>
<p> Unter dem Lagerflächen-Punkt im Seitenmenü befindet sich der Wareneingang. Es werden relevante Kennzahlen und Visualisierungen zum Import angezeigt. Unten kann eine Kategorie mithilfe eines Buttons hinzugefügt werden und man kann Waren hinzufügen. </p>

<img width="1440" alt="Bildschirmfoto 2024-04-01 um 14 24 13" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/63d4cd38-7cb2-4d15-b22a-1448d5a04290">
<p> Über 'Ware hinzufügen' kann mittels eines Popups Ware erfasst werden: </p>
<img width="804" alt="Bildschirmfoto 2024-04-01 um 14 24 44" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/5342b53a-6b02-4bf3-93a4-0aa1916349b4">

<h3> Warenausgang </h3>
<p> Unter dem Wareneingang im Seitenmenü befindet sich der Warenausgang. Auch hier werden relevante Kennzahlen und Visualisierungen zum Export angezeigt. Unten gibt es einen Button, mit dem man Ware exportieren kann. </p>
<img width="1440" alt="Bildschirmfoto 2024-04-01 um 14 30 38" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/5a721816-e21f-423d-a6c1-8adea8df96e7">
<img width="1440" alt="Bildschirmfoto 2024-04-01 um 14 31 00" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/bee32d0b-4679-4761-aa8b-6df14a305d3b">
<p> Beim Drücken des 'Waren exportieren' Buttons öffnet sich ein Fenster, dass eine Tabelle mit eingelagerter Ware anzeigt. Über die Filterfunktion kann man nach Kategorien und Lagerflächen filtern. Die zu exportierende Ware wird angetickt und mit dem Button 'Waren exportieren' exportiert, bzw. aus der Datenbank gelöscht im Hintergrund. </p>
<img width="802" alt="Bildschirmfoto 2024-04-01 um 14 38 39" src="https://github.com/ChristophKrol/LVS-2.0-NEW/assets/92592573/b4fdaa9a-fa04-4102-8314-34bae7038c58">





