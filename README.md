Was macht die App?...............................................................................S.3
       Bedienung .....................................................................s.3
Funktionsweise .....................................................................................S.4-8
Anwendung.................................................................................................S.9
Quellen..........................................................................................................S.9


Was macht die App?

Die App RemoCar (Remote Car) ermöglicht das Fernsteuern eines Raspberry Pi’s (MicroComputer), welches an einem Modellauto angeschlossen ist und diese Motoren somit steuern kann. Eine genauere Erklärung folgt in Kapitel 3.

Bedienung
Um die App zu installieren benötigen sie Android Studio und bestenfalls ein Android Handy.
Öffnen sie anschließend das Projekt „Androidcar“ in Android Studio und wählen sie oben ihr 
angeschlossenes Handy aus. Drücken sie auf „Run App“ und die App installiert sich auf ihrem 
Handy. 

Beim Öffnen der App erscheint dieses Fenster, mit dem sie das Auto steuern können. Stellen 
sie außerdem sicher, dass ihr Handy als auch der Raspberry Pi mit dem Internet verbunden 
ist. Sobald der Raspberry Pi mit Strom versorgt ist startet das Python-Programm automatisch.

1. Über die vier Pfeile (vorwärts, rückwärts, rechts, links) können sie die Richtung 
steuern, in die das Auto fahren soll. Beim Steuern muss außerdem der Button 
gedrückt und gehalten werden und sobald man wieder
loslässt bleibt das Auto stehen.

2. Mit dem Button „Foto“ können sie mit der Kamera, die am
Raspberry Pi angeschlossen ist ein Foto machen und
dieses Foto wird anschließend auf dem Fenster oben
angezeigt.

3. Wenn sie den Button „Kreis drehen“ betätigen, dreht sich
das Auto Rechts im Kreis.

4. Mit dem Button „Autonom“ fährt das Auto eine zufällige
Strecke automatisch

5. Die Anzeige „Abstand: 23cm“ stellt den Abstand
Zentimetergenau zu einem Hindernis (Wand, Tür) dar,
welcher mit einem Ultraschallsensor ermittelt wird.

Die Latenz also vom drücken des Buttons bis Bewegung der
Motoren beträgt bei normal schnellem Internet ca. 30ms. 
Wenn sie den Raspberry Pi mit einem Mobilfunkhandy verbinden,
können sie auch problemlos das Auto draußen oder im Wald von
Zuhause aus fernsteuern.
Unter Dauerlast der Motoren können sie außerdem ca.2 Stunden
fahren.
































