TAMerge
=======

Es können alle Items, die auch am Amboss verschmolzen werden können, verschmolzen werden.
Es gibt jedoch einige Spezialfälle.

"Schlechte" Items können in "bessere" Items hineinverschmolzen werden.
Das heißt z.B.: ein Holzschwert kann in ein Diamantschwert hineinverschmolzen werden.

Eine Karottenrute wird als "verbesserte" Angel angesehen, somit können Angeln in
Karottenruten hineinverscholzen werden, jedoch nicht umgekehrt.

Feuerzeuge, Scheren und Bögen können nur mit sich selbst verschmolzen werden.

Für alle Rüstungsteile sowie Werkzeuge gilt auch die Regel, dass schlechte in
bessere hineinverscholzen werden können, es gilt dabei folgende Reihenfolge:

Rüstung: Lederrüstung -> Kettenrüstung -> Goldrüstung -> Eisenrüstung -> Diamantrüstung
Werkzeug: Holzwerkzeug -> Steinwerkzeug -> Goldwerkzeug -> Eisenwerkzeug -> Diamantwerkzeug

Beispiele für mögliche Verschmelzungen:
Holzschwert -> Diamantschwert
Goldaxt -> Goldaxt
Goldschwert [nicht möglich] Steinschwert

Als zweites Item kann immer auch ein verzaubertes Buch verwendet werden.

Der Befehl lautet: /merge <ItemPosition1> <ItemPosition2>

Anstatt /merge kann auch /amboss, /anvil, /combine oder /repair verwendet werden.
Die Position gibt den "Slot" im Schnellzugriffs-Inventar an (das Inventar, in dem man auch
das Item, dass man in der Hand hält, auswählen kann). Somit kann man nur eine Zahl von 1
bis 9 angeben. Die erste Zahl gibt dabei das Hauptitem an, dessen Name und Verzauberungen erhalten
bleiben. Der Name des Nebenitems wird nur übernommen, falls das Hauptitem nicht benannt ist.
Verzauberungen werden, falls sie besser als auf dem Hauptitem sind übernommen. Bei gleichem
Verzauberungs-Level wird das Level um ein Level erhöht.

Ein Beispiel:
Position 1 im Inventar: Diamantspitzhacke ohne Verzauberungen
Position 2 im Inventar: Holzspitzhacke mit "Behutsamkeit I" Verzauberung
Befehl: /merge 1 2
Ergebnis: Diamantspitzhacke mit "Behutsamkeit I"

Extract
-------

Ein Item mit Verzauberungen wird in ein Item ohne Verzauberungen umgewandelt, dabei
entstehen zusätzich verzauberte Bücher mit den ursprünglichen Verzauberungen des Items.

Beispiel: Spitzhacke mit Behutsamkeit und Haltbarkeit
Ergebnis: Spitzhacke ohne Verzauberungen, verz. Buch mit Behutsamkeit und verz. Buch mit Haltbarkeit

Der Befehl lautet: /extract <ItemPosition>
Anstatt /extract kann auch /split verwendet werden.