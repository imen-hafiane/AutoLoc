# Atelier 5 — API REST, Postman et Swagger

## A. Endpoints

Même modèle pour Agence, Employe, Vehicule, Equipement, Client, Reservation, Contrat et Maintenance (exemple : Contrat).

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |

| ContratController | GET /api/contrats | findAll() | 200 | — |
| ContratController | GET /api/contrats/{id} | findById(id) | 200 | 404 |
| ContratController | POST /api/contrats | create(contrat) | 201 | 400 |
| ContratController | PUT /api/contrats/{id} | update(id, contrat) | 200 | 400, 404 |
| ContratController | DELETE /api/contrats/{id} | deleteById(id) | 204 | 404 |
| PaiementController | GET /api/paiements | findAll() | 200 | — |
| PaiementController | GET /api/paiements/{id} | findById(id) | 200 | 404 |
| PaiementController | POST /api/paiements | aucun | — | 405 |

Routes des autres contrôleurs : `/api/agences`, `/api/employes`, `/api/vehicules`, `/api/equipements`, `/api/clients`, `/api/reservations`, `/api/maintenances`.

## B. Annotations Jackson

| Association | Annotation | Justification |

| Contrat.paiements / Paiement.contrat | `@JsonManagedReference` / `@JsonBackReference` | Composition : les paiements sont créés dans le JSON du contrat |
| Contrat.reservation | `@JsonIgnore` | Côté inverse du 1-1 : évite la boucle Reservation ↔ Contrat |
| Agence.vehicules, Agence.employes | `@JsonIgnore` | Côté inverse : un véhicule ou un employé désigne son agence par son id |
| Vehicule.reservations, Client.reservations | `@JsonIgnore` | Côté inverse : la réservation désigne son client et son véhicule |
| Equipement.vehicules | `@JsonIgnore` | Côté inverse du N-N |

**Différence :** `@JsonBackReference` ignore le parent en sortie mais le **relie automatiquement** à l'enfant en entrée. `@JsonIgnore` ignore simplement le champ, en entrée comme en sortie.

## C. Postman et Swagger

| Outil | Apport | Exemple dans l'atelier |

| Postman | Tester : requêtes enregistrées, rejouables, avec assertions | Test « Statut 201 » sur le POST et mémorisation de `contratId` |
| Swagger UI | Documenter et explorer : générée depuis le code | Groupes « Contrats » et « Paiements » avec résumés et codes 200/404 |

## D. Anomalies SonarQube

| Règle | Explication | Correction |
| S4684 | Entité JPA exposée directement dans un contrôleur | Gardé volontairement (provisoire) : remplacé par des DTO au Chapitre 10 |
| S1128 | Import inutile | Import supprimé (`Ctrl+Alt+O`) |

## E. Questions

1. **Qui reçoit les requêtes ?**
   Le `DispatcherServlet`. Il utilise le `HandlerMapping` pour trouver la méthode du contrôleur selon le verbe HTTP et l'URL.

2. **Pourquoi `ContratController` dépend de `IContratService` ?**
   Le contrôleur ne fait que recevoir et répondre. La logique est dans le service, et on dépend de l'interface pour pouvoir changer l'implémentation.

3. **Pourquoi 201, 204 et 404 ?**
   201 = ressource créée. 204 = supprimée, pas de corps à renvoyer. 404 car l'absence est une erreur du client (mauvais id), pas une panne du serveur (500) : `ResourceNotFoundException` est traduite par le `GlobalExceptionHandler`.

4. **Pourquoi `PaiementController` n'a que des GET ?**
   Un paiement appartient à son contrat : on le crée et le supprime via le Contrat.

5. **Risques des entités exposées et choix des annotations ?**
   On expose la structure de la base et des champs internes, et on risque des boucles JSON. `Contrat.paiements` utilise `@JsonManagedReference` car les paiements sont créés dans le JSON du contrat ; `Agence.vehicules` utilise `@JsonIgnore` car un véhicule est créé seul et désigne son agence par son id.
