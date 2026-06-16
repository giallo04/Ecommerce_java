# 🛒 Progetto Ingegneria del Software — E-Commerce Desktop in Java

Applicazione desktop per la gestione di un e-commerce, realizzata con **Java 21**, **Swing** (tema FlatLaf) e **Hibernate/JPA** su **MySQL**.  
Il progetto segue il pattern architetturale **BCE (Boundary – Controller – Entity)** e supporta due ruoli distinti: **Cliente** e **Manager**.

---

## 📋 Indice

- [Tecnologie](#-tecnologie)
- [Prerequisiti](#-prerequisiti)
- [Configurazione del Database](#-configurazione-del-database)
- [Avvio dell'applicazione](#-avvio-dellapplicazione)
- [Accesso alle schermate](#-accesso-alle-schermate)
  - [Schermata Login](#schermata-login)
  - [Vista Cliente](#-vista-cliente)
  - [Vista Manager (Admin)](#-vista-manager-admin)
- [Popolamento del database (seed)](#-popolamento-del-database-seed)
- [Struttura del progetto](#-struttura-del-progetto)
- [Credenziali di test](#-credenziali-di-test)

---

## 🛠 Tecnologie

| Componente | Versione |
|---|---|
| Java | 21 |
| Swing + FlatLaf | 3.7.1 |
| Hibernate / JPA | 6.6.50 |
| MySQL Connector | 9.4.0 |
| Build tool | Maven |
| Testing | JUnit 5.10.2 |

---

## ✅ Prerequisiti

- **JDK 21** o superiore installato
- **MySQL 8+** in esecuzione in locale
- **Maven** installato 
- Un client MySQL (es. MySQL Workbench, DBeaver, o CLI)

---

## 🗄 Configurazione del Database

1. Crea il database MySQL:

```sql
CREATE DATABASE ecommercepu;
```

2. Apri il file di configurazione della persistenza:

```
src/main/resources/META-INF/persistence.xml
```

3. Modifica le credenziali con le tue:

```xml
<property name="jakarta.persistence.jdbc.url"
          value="jdbc:mysql://127.0.0.1:3306/ecommercepu"/>
<property name="jakarta.persistence.jdbc.user"
          value="root"/>
<property name="jakarta.persistence.jdbc.password"
          value="LA_TUA_PASSWORD"/>
```

---

## 🚀 Avvio dell'applicazione


### Con IDE (IntelliJ / Eclipse)

Esegui direttamente la classe:

```
src/main/java/Eseguibile/App.java
```

---

## 🖥 Accesso alle schermate

### Schermata Login

All'avvio viene mostrata la **schermata di login**, punto di ingresso per entrambi i ruoli.

```
┌─────────────────────────────────┐
│          E-Commerce             │
│                                 │
│  Email: [ ___________________ ] │
│  Pass:  [ ___________________ ] │
│                                 │
│       [ Accedi ]                │
│  Non hai un account? Registrati │
└─────────────────────────────────┘
```

---

### 👤 Vista Cliente

**Come accedere:**
- Inserisci email e password di un account registrato
- Oppure registra un nuovo account cliccando *"Non hai un account?"*


---

### 🔧 Vista Manager (Admin)

**Come accedere:**

> ⚠️ **Accesso rapido per sviluppo/demo:** nella schermata di login, inserisci `admin` nel campo email (qualsiasi valore nella password) e premi *Accedi*. Verrai reindirizzato direttamente alla Dashboard senza autenticazione.

## 🌱 Popolamento del Database (seed)

Per avviare l'applicazione con dati di esempio (utenti, prodotti e ordini pre-generati), esegui la classe di seed **prima** di lanciare l'app:


Questo inserirà:
- **12 utenti** (3 espliciti + 9 generati proceduralmente)
- **13 prodotti** in varie categorie (più 3 prodotti esauriti)
- **Ordini** generati automaticamente per ogni utente

---

## 📂 Struttura del Progetto

```
src/main/java/
├── Boundary/               # Interfacce grafiche Swing
│   ├── ClientView/         # Viste per il cliente (Home, Carrello, Ordini, Profilo)
│   ├── ManagerView/        # Viste per il manager (Dashboard, Catalogo, Statistiche)
│   ├── Login/              # Login e registrazione
│   ├── Template/           # Componenti riutilizzabili (TablePane, form astratti)
│   └── Utils/              # Utility grafiche (ImageUtils, StyleUtils, TableUtils)
│
├── Controller/             # Logica applicativa
│   ├── AccountController   # Login, registrazione, profilo utente
│   ├── CatalogoController  # CRUD prodotti, ricerca, filtri
│   ├── CarrelloController  # Gestione carrello e checkout
│   └── OrdiniController    # Gestione e visualizzazione ordini
│
├── Entity/                 # Modello di dominio con annotazioni JPA
│   ├── Merce/              # Prodotto, Categoria, RegistroProdotti
│   ├── Ordini/             # Ordine, RigaOrdine, StatoOrdine, RegistroOrdini
│   └── client/             # Utente, Carrello, RigaCarrello, Indirizzo, RegistroUtenti
│
├── Database/               # Layer di persistenza
│   ├── GestorePersistenza  # DAO generico (CRUD, query dinamiche JPQL)
│   └── JpaUtil             # Singleton per EntityManagerFactory
│
└── Eseguibile/
    ├── App                 # Entry point dell'applicazione
    └── PopolaDB            # Seed del database con dati di esempio

src/main/resources/
└── META-INF/
    └── persistence.xml     # Configurazione Hibernate/JPA

src/test/java/
├── ProdottoTest            # Test unitari per l'entità Prodotto
└── UtenteTest              # Test unitari per l'entità Utente
```

---

## 🔑 Credenziali di Test

Dopo aver eseguito `PopolaDB`, sono disponibili i seguenti account:

| Ruolo | Email | Password |
|---|---|---|
| Cliente | `test@email.it` | `Password@1` |
| Cliente | `test2@email.it` | `Password@2` |
| Cliente | `guest@email.it` | `Password@3` |
| **Manager** | `admin` *(qualsiasi password)* | — |

> Le password devono rispettare: minimo 8 caratteri, almeno una maiuscola, un numero e un carattere speciale.

---

## ⚠️ Note per lo Sviluppo

- Le **immagini dei prodotti** devono trovarsi in `img/products/<id>.png` e quelle degli utenti in `img/users/<id>.png` nella directory di esecuzione.
- Il **bypass admin** (`email = "admin"`) è una scorciatoia di sviluppo: **non lasciarlo in produzione**.
- L'hashing delle password usa `String.hashCode()` — sostituire con BCrypt per un utilizzo reale.

---

## 📄 Licenza

Progetto a scopo didattico. Nessuna licenza commerciale applicata.
