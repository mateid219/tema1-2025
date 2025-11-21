# Dorde Matei - tema1 POO
Implementarea adauga <b>118</b> clase si <b>23</b> de pachete. Se pot introduce usor noi entitati, 
interactiuni, comenzi, algoritmi de hranire/miscare, functionalitati pentru robot etc.
## Organizarea pe pachete
* src/
  * main/ 
  * solution/ - contine implementarea temei
    * commands/ - contine implementarile comenzilor
      * debug/, environment/, robot/, simulation/
    * entities/ - contine implementarile entitatilor
      * plants/, air/, soil/, water/
      * animals/
        * strategies/
    * exceptions/ - contine toate exceptiile definite pentru tema
    * simulation/ - contine elementele unei simulari
      * environmentMap/ - contine harta
      * terraBot/ - contine robotul
        * improvements/, scanner/
      * events/ - contine interactiunile automate
        * animal/, robot/, water/, weather/
  * test/
## Entitati (package entities)
Contin informatiile specifice unei entitati si le trec la `ObjectNode`.
### Interfete:
 - `Scannable` - are un statut de scanare si accepta un `ScanParamsVisitor`
   - `Food` - asigura existena unui <b>scanTime</b>
 - `CellQualityAgent` - defineste o metoda comuna pentru contributia la calitatea celulei
 - `Weather` - accepta un `WeatherChangeVisitor`
### Ierarhia entitatilor:
* `Entity` 
  * `QualitativeEntity`
    * `Soil` : `CellQualityAgent`
      * `DesertSoil`, `SwampSoil`, `TundraSoil`, `GrassLandSoil`, `ForestSoil`
    * `Air` : `CellQualityAgent`, `Weather` 
      * `DesertAir`, `MountainAir`, `PolarAir`, `TemperateAir`, `TropicalAir`
    * `Water` : `Food`
  * `Animal` : `CellQualityAgent`, `Scannable`
    * `Omnivore`, `Carnivore`, `Detritivore`, `Herbivore`, `Parasite`
  * `Plant` : `CellQualityAgent`, `Food`
    * `Algae`, `Fern`, `Gymosperm`, `Flowering`, `Moss`
#### Animale
 - Au strategii de hranire si de miscare, injectate din `AnimalFactory` prin <b>clasele</b>:
   - `FeedingStrategy` : `IFeedingStrategy`
   - `PredatorMovementStrategy` : `IMovementStrategy`
     - `AnimalMovementStrategy`
 - Folosesc `AnimalFoodSelector` pentru a decide ce mananca de pe o celula.
## Comenzi (package commands)
Incep executia comenzilor prin delegare sau prin adaugarea de eventimente.
Sincronizeaza simularea cu <b>timestamp-ul</b> comenzii. Se foloseste `CommandFactory` pentru
comenzi noi extrase din `CommandInput`.
### Ierarhia comenzilor:
- `Command`
  - `DebugCommand`
    - `GetEnergyStatus`, `PrintEnvConditions`, `PrintKnowledgeBase`, `PrintMap`
  - `EnvironmentCommand`
    - `DesertStorm`, `NewSeason`, `PeopleHiking`, `PolarStorm`, `Rainfall`
  - `RobotCommand`
    - `LearnCommand`, `ImproveCommand`, `MoveCommand`, `RechargeCommand`, `ScanCommand`
  - `SimulationCommand`
    - `StartSimulation`, `EndSimulation`

## Evenimente (package events)
 Implementeaza actiunile care au efecte la mai multe momente de timp. Pot fi comparate pentru a 
decide ordinea evenimentelor (1. dupa <b>timestamp</b>; 2. dupa `EventPriorities`)
### Ierarhia evenimentelor:
* `Event` : `Comparable<Event>`
  * `AirEvent`, `PlantEvent`, `SoilEvent`, `BeginCharging`, `FinishCharging`
  * `AnimalEvent`
    * `FeedEvent`, `FertilizeEvent`, `MoveEvent`
  * `WaterEvent`
    * `IncreaseStatsEvent`, `GrowPlantEvent`
  * `WeatherEvent` - contine interfata `WeatherChangeVisitor`
## Simularea (package simulation)
 Contine `EnvironmentMap`, `TerraBot` si un `PriorityQueue<Event>`. Este responsabila
de executia evenimentor trecute inainte de executia unei comenzi.

## Terrabot (package terraBot)
 Contine `Inventory`, `Battery`, `Database`, `Scanner`, `Cell` si 
 `RobotCellPreference` : `Comparator<Cell>`. Gestioneaza actiunile complexe facute de robot
 (move, learn, improve etc) prin delegarea la obiecte continute.
## Harta (package environmentMap)
 Contine dimensiunile si o matrice de `Cell`. Defineste intern vecinii unei celule si ofera
urmatoarea celula pentru miscare prin functia `nextCell(Cell,Comparator<Cell>)`. Intoarce un
`ArrayList` cu toate celule prin functia `getCells()`.
## Design pattern-uri
### Factory Pattern pentru comenzi si entitati
### Visitor pattern pentru entitati scanabile si schimbari meteo
### CommandPattern pentru executia in mai multe stagii a comenzilor
 `CommandInput` &rarr; `Command` &rarr; `Event` &rarr; `Entity`/`TerraBot` 
## Folosire LLM
 Am folosit LLM-uri pentru intelegerea mai buna a principiilor OOP, dupa implemetarea tuturor 
 testelor. Am folosit prompt-uri de tipul: "Is this good OOP practice? Clasa: ...". Inainte de
ultimele 5 commit-uri, tema trecea toate testele si avea `39` de clase adaugate. Acum are `118`!