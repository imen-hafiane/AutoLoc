# Atelier 3 — Couche Repository

## Choix des interfaces

| Interface | Étend | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, findAll renvoie une List, tri/pagination disponibles. |
| IEmployeRepository | JpaRepository<Employe, Long> | Idem. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | Idem ; pagination utile pour la flotte. |
| IEquipementRepository | JpaRepository<Equipement, Long> | Idem. |
| IClientRepository | JpaRepository<Client, Long> | Idem. |
| IReservationRepository | JpaRepository<Reservation, Long> | Idem ; tri par date utile. |
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, findAll renvoie une List, saveAndFlush disponible. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Lecture des paiements ; création/suppression via le Contrat (composition). |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | Idem. |

Remarque : éviter deleteAllInBatch / deleteAllByIdInBatch sur Contrat (cascade et orphanRemoval ignorés).

## Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
| Import générique jakarta.persistence.* dans les entités | java:S2208 — un import * masque les classes réellement utilisées et peut créer des conflits de noms | Remplacé par des imports explicites (Entity, Id, Column…) |
| Analyse par défaut : aucune anomalie | Profil « Sonar way » : le code respecte les règles par défaut (pas de @Data, injection par constructeur) |Activation de règles supplémentaires pour une analyse plus stricte |
