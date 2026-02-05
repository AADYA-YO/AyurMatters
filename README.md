# 🌿 AyurMatters – Ayurvedic Knowledge Management System

AyurMatters is a **mobile-friendly web application** designed to help an **Ayurvedic doctor** digitally store, search, and retrieve medical knowledge in a structured way.

The system allows diseases to be recorded **together with symptoms and medicines**, exactly as an Ayurvedic practitioner thinks and works — not as fragmented technical data.

This project was built with **simplicity, accessibility, and real clinical usage** in mind.

---

## ✨ Key Features

### 🩺 Disease-Centric Knowledge Storage

* Add a disease **along with**:

  * Symptoms
  * Medicines (with usage notes)
  * Ayurvedic notes
  * General notes
* No need to add symptoms or medicines separately.

### 🔍 Powerful Search

Search diseases by:

* Disease name
* Symptom
* Medicine

Results are shown instantly and can be opened for full details.

### 📱 Mobile-First & Elder-Friendly UI

* Large buttons and fonts
* Touch-friendly interactions
* Clean, distraction-free design
* Optimized for phone usage by doctors

### 🔐 Doctor-Controlled Data

* All data is added manually by the doctor
* No external data sources
* No auto-generated medical content

---

## 🧱 Tech Stack

### Backend

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **H2 Database (In-Memory)**
* RESTful APIs

### Frontend

* **HTML5**
* **CSS3 (Mobile-first, accessible design)**
* **Vanilla JavaScript**
* No frameworks (kept simple & fast)

---

## 🗂 Project Structure

```
AyurMatters
│
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   └── application.properties
│
└── frontend/
    ├── index.html
    ├── add-disease.html
    ├── search.html
    ├── disease.html
    ├── css/
    │   └── style.css
    └── js/
        ├── addDisease.js
        ├── search.js
        └── disease.js
```

---

## 🔗 API Endpoints

### ➕ Add / Update Disease

```
POST /api/diseases
```

**Request Body (JSON):**

```json
{
  "diseaseName": "Migraine",
  "description": "Severe headache condition",
  "ayurvedicNotes": "Vata imbalance",
  "generalNotes": "Avoid triggers",
  "symptoms": ["Headache", "Nausea"],
  "medicines": {
    "Pathyadi Kadha": "Twice daily",
    "Brahmi": "Once daily"
  }
}
```

---

### 🔍 Search Diseases

```
GET /api/diseases/search?type={disease|symptom|medicine}&q=keyword
```

---

### 📄 View Disease by ID

```
GET /api/diseases/{id}
```

---

## ⚙️ Configuration

### `application.properties`

```properties
spring.application.name=backend
server.port=9090

spring.datasource.url=jdbc:h2:mem:ayurmatters
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## ▶️ How to Run the Project

### 1️⃣ Start Backend

* Open backend in IntelliJ
* Run Spring Boot application
* Backend runs at:

```
http://localhost:9090
```

### 2️⃣ Start Frontend

* Open frontend folder in VS Code
* Use **Live Server** or any static server
* Frontend runs at:

```
http://127.0.0.1:3000
```

---

## 📱 Using on a Mobile Phone (Local Network)

1. Connect laptop & phone to the **same Wi-Fi**
2. Find laptop IP:

   ```bash
   ipconfig
   ```
3. Update frontend API URL:

   ```js
   const API_BASE_URL = 'http://<YOUR_LAPTOP_IP>:9090/api';
   ```
4. Open on phone:

   ```
   http://<YOUR_LAPTOP_IP>:3000
   ```

---

## 👩‍⚕️ Intended Users

* Ayurvedic doctors
* Traditional medicine practitioners
* Medical students (Ayurveda)
* Personal clinical knowledge management

---

## 🎯 Design Philosophy

* **Doctor-first, not tech-first**
* No unnecessary complexity
* No AI-generated medical advice
* Respect for traditional medical knowledge
* Simple enough for daily real-world usage

---

## 🚀 Future Enhancements (Optional)

* Authentication (Doctor PIN / Login)
* Cloud deployment
* Android app (WebView)
* Data export (PDF / Excel)
* Backup & restore

---

## 👨‍💻 Author

Developed with care as a **real-world solution** for an Ayurvedic practitioner.

---

## 📜 Disclaimer

This application is a **knowledge-record system**.
It does **not provide medical diagnosis or treatment recommendations**.

---

