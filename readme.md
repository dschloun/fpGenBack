Le projet contient

Un découpe "Pragmatic Onion Architecture" à savoir :
3 modules maven
- afelio-hr-application
- afelio-hr-domain
- afelio-hr-infrastructure

Ainsi qu'un pom parent pour centraliser les versions des dépendances
- afelio-hr-master

J’ai opté pour des modules maven séparés plutôt que simplement des répertoires pour renforcer
les séparations entre les couches

Orientation DDD

Liens Application -> Domain, Application -> Infrastructure, Infrastructure -> Domain
- Création de domain objects immutables via builders uniquement + validation
- Ceci garanti que tout objet domain créé est forcément valide.
- Repository dans le domain dont l'implémentation est dans l'infrastructure
- Exemple de règle de validation dans le domain
- La règle business d’exemple que j’ai implémentée est qu’on doit être un adulte pour postuler.
- Exemple de tests unitaires validant les règles business

Une application Web SpringBoot 2, Java 17 implémentant une API générée
- Implémentation d’un endpoint permettant de postuler pour un job qui doit exister en DB
- Mapping Web > Domain
- Méthode transactionnelle
- ControllerAdvice centralisé pour gérer les exceptions technique et business
- Exemple d’erreur technique quand le job n’est pas trouvé
- Exemple d’erreur business quand le candidat est mineur
- Exemple de test component pour valider les payloads et statuts http retournés dans tous les cas.

Une persistance Postgress SQL 11 avec une DB initialisée via migrations flyway et lancée depuis un docker-compose
- Actuellement 1 migration avec 3 tables
  - candidates
  - jobs
  - candidate_applications (Many to Many)

Un exemple d’insertion de quelques jobs pour pouvoir tester
- Une DB H2 en test
- Une implémentation du repository du domain avec SpringDataJPA
- Mapping Domain > JPA

- Fichier application.properties pour la configuration de l'application
- Fichier logback.xml pour la configuration des niveaux de logs et rolling files
- L'application a un profil maven dev et production. En production le logback.xml est exclu du fatjar pour avoir la main dessus une fois déployé.
- Un Postman pour tester