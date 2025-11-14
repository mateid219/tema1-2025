# Dorde Matei - tema1 POO

## Design pattern-uri
### Factory Method pentru comenzi 
 Pana la commit-ul ce adauga CommandFactory, foloseam metoda Command.createCommand(...).
 Conform principiului open/closed, clasa Command nu ar trebui sa necesite modificari la adaugarea unor subclase pentru noile comenzi,
 Conform principiului single-responsibility, ar trebui sa fie cat mai simpla.