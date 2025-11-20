# Dorde Matei - tema1 POO

## Entitati
 Toate entitatile mostenesc clasa Entity. Aceasta contine campurile comune si 
implementeaza punerea lor intr-un ObjectNode.
 Entitatile care contribuie la calitatea celulelor implementeaza QualitativeEntity,
unde se face normalizarea si rotunjirea scorului, metoda care calculeaza trebuie
implementata de subclase.
 Air si Soil sunt abstracte, mostenite de subclase care implementeza calcularea
specifica a scorului.

## Evenimente
 Implementeaza actiunile care au efect la mai multe momente de timp. Clasa Event
implementeaza Comparable si ordoneaza evenimentele in functie de timp si de
ordinea in care trebuie executate (priority). Este mostenita de evenimente specifice.

## Simularea
 Simulation contine harta, robotul, listele cu entitati, coada cu prioritati
pentru eventimente si detalii despre status-ul simularii. Constructor-ul
primeste clasa SimulationInput si trimite datele relevante la entitatile continute. 
Clasa nu implementeaza functionalitati concrete.

## Terrabot
 Contine inventarul, bateria, baza de date, scanner-ul si celula robotului. 
 Gestioneaza actiunile complexe facute de robot (move, learn, improve etc)
fara sa le implementeze propriu zis.

## Comenzi
 Executa comenzile generate de robot. Clasa Command este mostenita de clasele
RobotCommand, DebugCommand, EnvironmentCommand, SimulationCommand. Fiecare din
acestea sunt mostenite la randul lor de clase pentru comenzi specifice. Gestioneaza
adaugarea de evenimente in coada, si execute toate evenimentele cu timestamp-ul mai mic 
sau egal cu cel al comenzii.

## Design pattern-uri
### Factory Method pentru comenzi si entitati
 Pana la commit-ul ce adauga CommandFactory, SoilFactory, AirFactory..., foloseam 
 metode statice de tipul Command.createCommand(...), Air.createAir(...) etc.
 Conform principiului open/closed, clasele nu ar trebui sa necesite modificari
 la adaugarea unor subclase.
 Conform principiului single-responsibility, clasele mostenite ar trebui sa
 fie cat mai simple, si sa nu cunoasca detalii despre subclasele lor.
 Creearea obiectelor este delegata la utilitarele de tip Factory.
### Visitor pattern pentru entitati scanabile
 Entiatile care pot fi scanate implementeaza interfata Scannable.
 Exista interfata ScanParamsVisitor implementata de ScanParams.
## Folosire LLM
 Am folosit LLM-uri pentru intelegerea mai buna a principiilor OOP, dupa implemetarea tuturor testelor.
 Am folosit prompt-uri de tipul: "Is this good OOP practice? Code: ...".
 Reorganirea pe pachete, creearea de clase si schimbarea design-ului au fost facute manual.
## --- README in constructie ---