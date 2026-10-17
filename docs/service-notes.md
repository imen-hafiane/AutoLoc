# Atelier 4 — Couche Service, IoC et injection de dépendances

## A. Modes d'injection comparés

| Critère | Constructeur | Attribut (`@Autowired`) |
(-, -) Remove this field injection and use constructor injection instead. [java:S6813]
| Champ `final` possible | Oui | Non | oui
| Signalé par SonarQube | Non | Oui (S6813) | oui

**Choix retenu :** injection par constructeur.

## B. Services

Tous les services utilisent l'injection par constructeur (`@RequiredArgsConstructor` + champ `final`).

| Service | Dépendance injectée | Justification |
|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Accès aux agences |
| EmployeServiceImpl | IEmployeRepository | Accès aux employés |
| VehiculeServiceImpl | IVehiculeRepository | Accès aux véhicules |
| EquipementServiceImpl | IEquipementRepository | Accès aux équipements |
| ClientServiceImpl | IClientRepository | Accès aux clients |
| ReservationServiceImpl | IReservationRepository | Accès aux réservations |
| ContratServiceImpl | IContratRepository | Accès aux contrats |
| PaiementServiceImpl | IPaiementRepository | Lecture seule des paiements |
| MaintenanceServiceImpl | IMaintenanceRepository | Accès aux maintenances |

## C. Messages d'erreur

| Message | Cause | Correction |
|---|---|---|
| 1. Bean `IContratService` introuvable | `@Service` oublié, ou classe hors du package `tn.esprit.autoloc` | Ajouter `@Service`, déplacer la classe |
| 2. Deux beans trouvés | Deux classes implémentent le même type | `@Primary` sur la classe, ou `@Qualifier("nom")` sur le paramètre du constructeur |
| 3. Dépendance circulaire | Deux services s'injectent mutuellement : aucun ne peut être créé en premier | Supprimer le lien croisé, ou mettre la logique commune dans un 3ᵉ service |

## D. Anomalies SonarQube for IDE

| Règle | Explication | Correction |
|---|---|---|
| S6813 | Injection par attribut déconseillée | Retour à l'injection par constructeur |
| S112 | Exception générique `RuntimeException` | Remplacée par `ResourceNotFoundException` |
| S1128 | Import `Autowired` inutile après l'expérience | Import supprimé |

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` ?**
   Il n'y en a pas dans notre code : c'est Spring qui crée l'objet au démarrage.

2. **Pourquoi le contrôleur dépend de `IContratService` ?**
   Pour dépendre d'une interface : on peut changer l'implémentation sans modifier le contrôleur.

3. **Pourquoi un service doit rester sans état ?**
   Il n'existe qu'une seule instance, partagée par toutes les requêtes. Exemple : un attribut `contratCourant` serait écrasé par une autre requête.

4. **Pourquoi `update` charge l'entité avant `save` ?**
   Pour ne modifier que les champs voulus. Sinon `save` écrase tout et peut supprimer les paiements.

5. **Différences et `IPaiementService` :**
   - `@Component` : sur une classe. `@Bean` : sur une méthode d'une classe `@Configuration`.
   - `@Primary` : bean choisi par défaut. `@Qualifier` : bean choisi par son nom.
   - Un paiement dépend de son contrat (composition) : on le crée et le supprime via le Contrat. Le service paiement fait donc seulement de la lecture.
