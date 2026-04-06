# 🏥 Cabinet Médical — Architecture Event-Driven avec Apache Kafka

> **TP4 — Master IPS | Faculté des Sciences de Rabat**  
> Module : Systèmes Distribués Basés sur les Microservices

---

## 🎯 Objectif

Ce projet implémente une architecture **Event-Driven (EDA)** basée sur **Apache Kafka** pour la gestion d'un cabinet médical. Il remplace les appels REST synchrones du TP3 par une communication **asynchrone** via des événements.

### Comparaison TP3 vs TP4

| Aspect | TP3 (REST synchrone) | TP4 (EDA / Kafka) |
|---|---|---|
| Communication | REST HTTP synchrone | Événements asynchrones |
| Couplage | Fort | Faible |
| Disponibilité | Dépendante | Indépendante |
| Scalabilité | Limitée | Horizontale |
| Erreurs en cascade | Oui | Non |
| Cohérence | Immédiate | Éventuelle |

---

## 🏛️ Architecture

```
           ┌─────────────────────────────────────┐
           │           Clients Externes           │
           └──────────────┬──────────────────────┘
                          │ HTTP
           ┌──────────────▼──────────────────────┐
           │          API GATEWAY :8080           │
           │    (Spring Cloud Gateway)            │
           └──────────────┬──────────────────────┘
                          │
           ┌──────────────▼──────────────────────┐
           │         KAFKA EVENT BUS              │
           │         (localhost:9092)             │
           └──┬──────┬───────┬──────┬────────────┘
              │      │       │      │
    ┌─────────▼─┐ ┌──▼────┐ │  ┌───▼──────────┐ ┌──────────────┐
    │  patient  │ │medecin│ │  │consultation  │ │   billing    │
    │ :8082     │ │ :8083 │ │  │   :8085      │ │   :8086      │
    │(Producer) │ │(Prod.)│ │  │(Prod+Consom.)│ │(Consom+Prod.)│
    └───────────┘ └───────┘ │  └──────────────┘ └──────────────┘
                      ┌─────▼──────────┐
                      │  rendezvous   │
                      │    :8084      │
                      │(Prod+Consom.) │
                      └───────────────┘
```

### Microservices

| Service | Port | Rôle Kafka | Base de données |
|---|---|---|---|
| `api-gateway` | 8080 | Aucun | — |
| `patient-service` | 8082 | Producer | H2 (patientDB) |
| `medecin-service` | 8083 | Producer | H2 (medecinDB) |
| `rendezvous-service` | 8084 | Producer + Consumer | H2 (rendezvousDB) |
| `consultation-service` | 8085 | Producer + Consumer | H2 (consultationDB) |
| `billing-service` | 8086 | Producer + Consumer | H2 (billingDB) |

---

## 🔄 Flux d'événements (Saga par chorégraphie)

```
[POST /api/patients]
      │
      ▼
patient-service ──── patient.created ────► rendezvous-service (projections locales)

[POST /api/medecins]
      │
      ▼
medecin-service ──── medecin.created ────► rendezvous-service (projections locales)

[POST /api/rendezvous]
      │
      ▼
rendezvous-service ─── rendezvous.created ───► consultation-service
                                                      │
                                                      ▼
                                              consultation.created ──► billing-service
                                                                              │
                                               ┌───────────────────────────┐ │
                                               │  Succès: facture.created  │◄┘
                                               │  Échec:  facture.failed   │
                                               └──────────┬────────────────┘
                                                          │ facture.failed
                                                          ▼
                                               rendezvous-service
                                               (annule le rendez-vous)
                                               ── rendezvous.cancelled
```

### Topics Kafka

| Topic | Producteur | Consommateur(s) |
|---|---|---|
| `patient.created` | patient-service | rendezvous-service |
| `medecin.created` | medecin-service | rendezvous-service |
| `rendezvous.created` | rendezvous-service | consultation-service |
| `consultation.created` | consultation-service | billing-service |
| `facture.created` | billing-service | — |
| `facture.failed` | billing-service | rendezvous-service |

---

## ✅ Prérequis

- **Java 21** (Eclipse Temurin recommandé)
- **Maven 3.9+**
- **IntelliJ IDEA** (Ultimate ou Community)
- **Docker Desktop** (pour Kafka + Zookeeper)
- **Git**

---

## 📁 Structure du projet

```
cabinetMedicalTp4EDA/
├── docker-compose.yml              ← Infrastructure Kafka + Zookeeper
├── pom.xml                         ← POM parent
│
├── api-gateway/
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── patient-service/
│   ├── src/main/java/ma/fsr/eda/patientservice/
│   │   ├── model/Patient.java
│   │   ├── repository/PatientRepository.java
│   │   ├── service/PatientService.java
│   │   ├── web/PatientController.java
│   │   ├── event/dto/PatientCreatedEvent.java
│   │   ├── event/producer/PatientEventProducer.java
│   │   └── config/KafkaProducerConfig.java
│   ├── src/main/resources/application.properties
│   ├── Dockerfile
│   └── pom.xml
│
├── medecin-service/          
│
├── rendezvous-service/
│   ├── src/main/java/ma/fsr/eda/rendezvousservice/
│   │   ├── model/RendezVous.java
│   │   ├── model/projection/PatientProjection.java
│   │   ├── model/projection/MedecinProjection.java
│   │   ├── repository/...
│   │   ├── service/RendezVousService.java
│   │   ├── web/RendezVousController.java
│   │   ├── event/dto/...
│   │   ├── event/producer/RendezVousEventProducer.java
│   │   ├── event/consumer/PatientEventConsumer.java
│   │   ├── event/consumer/MedecinEventConsumer.java
│   │   └── config/...
│   └── pom.xml
│
├── consultation-service/     
└── billing-service/          
```

---



## 🌐 API Reference

### Via API Gateway (port 8080)

#### Patients

| Méthode | URL | Description |
|---|---|---|
| `GET` | `http://localhost:8080/api/patients` | Liste tous les patients |
| `GET` | `http://localhost:8080/api/patients/{id}` | Récupère un patient |
| `POST` | `http://localhost:8080/api/patients` | Crée un patient + publie `patient.created` |
| `PUT` | `http://localhost:8080/api/patients/{id}` | Met à jour un patient |
| `DELETE` | `http://localhost:8080/api/patients/{id}` | Supprime un patient |

#### Médecins

| Méthode | URL | Description |
|---|---|---|
| `GET` | `http://localhost:8080/api/medecins` | Liste tous les médecins |
| `POST` | `http://localhost:8080/api/medecins` | Crée un médecin + publie `medecin.created` |

#### Rendez-vous

| Méthode | URL | Description |
|---|---|---|
| `GET` | `http://localhost:8080/api/rendezvous` | Liste tous les rendez-vous |
| `POST` | `http://localhost:8080/api/rendezvous` | Crée un RDV + déclenche la Saga |

#### Consultations

| Méthode | URL | Description |
|---|---|---|
| `GET` | `http://localhost:8080/api/consultations` | Liste toutes les consultations |

#### Factures

| Méthode | URL | Description |
|---|---|---|
| `GET` | `http://localhost:8080/api/factures` | Liste toutes les factures |

---

## 🧪 Tests

### Scénario de test complet

#### 1. Créer un patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{"nom": "Mohammed Alami", "email": "m.alami@email.ma", "telephone": "0661234567"}'
```

#### 2. Créer un médecin
```bash
curl -X POST http://localhost:8080/api/medecins \
  -H "Content-Type: application/json" \
  -d '{"nom": "Dr. Fatima Benali", "specialite": "Cardiologie", "email": "f.benali@cabinet.ma"}'
```

#### 3. Créer un rendez-vous (attendre ~2s après les étapes 1 et 2)
```bash
curl -X POST http://localhost:8080/api/rendezvous \
  -H "Content-Type: application/json" \
  -d '{"patientId": "<ID_PATIENT>", "medecinId": "<ID_MEDECIN>", "dateRendezVous": "2025-07-15T10:00:00"}'
```

#### 4. Vérifier la Saga complète
```bash
# Consultations créées automatiquement
curl http://localhost:8080/api/consultations

# Factures générées automatiquement
curl http://localhost:8080/api/factures
```

### Vérifier les événements Kafka

```bash
# Lister les topics
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list

# Consommer les messages du topic patient.created
docker exec kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic patient.created \
  --from-beginning
```

---

## 📊 Concepts clés illustrés

### Saga par Chorégraphie
Chaque service réagit aux événements de manière autonome, sans orchestrateur central. En cas d'échec de facturation, la compensation est automatique.

### Consistance Éventuelle
Les projections locales dans `rendezvous-service` sont mises à jour de façon asynchrone. Il peut exister un bref délai entre la création d'un patient et sa disponibilité dans les projections.

### Découplage Fort
Aucun service ne connaît l'adresse des autres services (sauf la Gateway). Ils communiquent uniquement via des événements Kafka.

---

## 👨‍💻 Auteur

- **Module** : Systèmes Distribués Basés sur les Microservices
- **Établissement** : Faculté des Sciences de Rabat — Master IPS
- **Encadrant** : Pr. Jaouad Ouhssaine

---


