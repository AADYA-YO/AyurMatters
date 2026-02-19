# AyurMatters 🌿

A comprehensive Ayurvedic disease information management system that helps users explore Ayurvedic diseases, symptoms, and medicines.

## 📋 Overview

AyurMatters is a full-stack web application designed to manage and search Ayurvedic medical information. The application allows users to add diseases along with their associated symptoms and medicines, and provides powerful search capabilities to find diseases by name, symptom, or medicine.

## ✨ Features

- **Disease Management**: Add and update diseases with detailed information
- **Symptom Tracking**: Associate multiple symptoms with each disease
- **Medicine Database**: Link Ayurvedic medicines to specific diseases
- **Advanced Search**: Search diseases by:
  - Disease name
  - Symptom name
  - Medicine name
- **Responsive UI**: Clean and user-friendly interface
- **RESTful API**: Well-structured backend API for all operations

## 🛠️ Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.5.10**
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Primary database
- **Maven** - Build and dependency management

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript (Vanilla)**

## 📁 Project Structure

```
AyurMatters/
├── AyurMatters backend/          # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ayurmatters/backend/
│   │   │   │   ├── config/       # Configuration files
│   │   │   │   ├── controller/   # REST API controllers
│   │   │   │   ├── dto/          # Data Transfer Objects
│   │   │   │   ├── entity/       # JPA Entities
│   │   │   │   ├── repository/   # Database repositories
│   │   │   │   └── service/      # Business logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/                 # Unit tests
│   ├── pom.xml                   # Maven configuration
│   └── Dockerfile                # Docker configuration
│
└── AyurMatters frontend/         # Frontend application
    ├── index.html                # Homepage
    ├── add-disease.html          # Add disease form
    ├── search.html               # Search interface
    ├── disease.html              # Disease details page
    ├── css/
    │   └── style.css             # Styling
    └── js/
        ├── addDisease.js         # Add disease logic
        ├── search.js             # Search functionality
        └── disease.js            # Disease details logic
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Modern web browser

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AyurMatters
   ```

2. **Configure Database**
   
   Set the following environment variables or update `application.properties`:
   ```properties
   SPRING_APPLICATION_NAME=AyurMatters
   PORT=9090
   DB_URL=jdbc:postgresql://localhost:5432/ayurmatters
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   HIBERNATE_DDL_AUTO=update
   HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
   FRONTEND_URL=http://localhost:8080
   ```

3. **Create PostgreSQL Database**
   ```sql
   CREATE DATABASE ayurmatters;
   ```

4. **Build and Run**
   ```bash
   cd "AyurMatters backend"
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:9090`

### Frontend Setup

1. **Serve the frontend**
   
   Simply open `AyurMatters frontend/index.html` in a web browser, or use a local server:
   ```bash
   cd "AyurMatters frontend"
   # Using Python
   python -m http.server 8080
   
   # Or using Node.js
   npx http-server -p 8080
   ```

2. **Access the application**
   
   Navigate to `http://localhost:8080`

## 📡 API Endpoints

### Disease Management

#### Create/Update Disease
```http
POST /api/diseases
Content-Type: application/json

{
  "diseaseName": "Common Cold",
  "symptoms": ["Sneezing", "Runny Nose", "Fever"],
  "medicines": ["Tulsi", "Ginger Tea"],
  "generalNotes": "Rest and stay hydrated"
}
```

#### Get Disease by ID
```http
GET /api/diseases/{id}
```

#### Search Diseases
```http
GET /api/diseases/search?type=disease&q=cold
GET /api/diseases/search?type=symptom&q=fever
GET /api/diseases/search?type=medicine&q=tulsi
```

## 💾 Database Schema

### Tables

- **diseases** - Main disease information
- **symptoms** - Symptom catalog
- **medicines** - Medicine catalog
- **disease_symptoms** - Many-to-many relationship
- **disease_medicines** - Many-to-many relationship

## 🐳 Docker Support

A Dockerfile is provided for containerization. To build and run:

```bash
cd "AyurMatters backend"
docker build -t ayurmatters-backend .
docker run -p 9090:9090 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/ayurmatters \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=yourpassword \
  ayurmatters-backend
```

## 🧪 Testing

Run tests using Maven:

```bash
cd "AyurMatters backend"
mvn test
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👥 Authors

- **Your Name** - *Initial work*

## 🙏 Acknowledgments

- Inspired by traditional Ayurvedic medicine
- Built with Spring Boot and modern web technologies

## 📧 Contact

For questions or support, please open an issue in the GitHub repository.

---

**Note**: Make sure to configure all environment variables properly before running the application in production.