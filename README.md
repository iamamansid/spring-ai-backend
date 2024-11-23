Document AI Recognition

A powerful backend service for automated document recognition and extraction.
This project leverages Java/Spring Boot and Azure AI Services API to recognize and extract document data efficiently, offering a robust solution for automating manual document processing.

🚀 Features
Document Upload: Accepts files as input to extract text and metadata.
Automated Recognition: Leverages Azure AI Services for high-accuracy data extraction.
RESTful API: Designed as a backend service for integration into various systems.
Efficient Architecture: Built with Java and Spring Boot for robust performance.

📂 Project Structure
spring-ai-backend/
├── src/
│   ├── main/
│   │   ├── java/com/iamamansid/spring_ai_backend/
│   │   │   ├── Controller/           # API controllers (DocScannerController.java)  
│   │   │   ├── Service/              # Business logic (DocumentService and Impl)  
│   │   │   ├── models/response/      # Response models and configurations  
│   │   │   └── SpringAiBackendApplication.java  # Main application entry point  
│   │   ├── resources/  
│   │       ├── application.properties  # Application configurations (e.g., API keys)  
│   │       ├── static/                # Static assets (if applicable)  
│   │       └── templates/             # Templates (if applicable)  
│   ├── test/                          # Test cases for validation  
├── build.gradle                       # Gradle build configuration  
├── Dockerfile                         # Docker support (if implemented)  
├── .gitignore                         # Git ignored files  
└── README.md                          # Documentation (this file)  


🛠️ Technologies Used
Programming Language: Java (17)
Framework: Spring Boot (3.3.5)
Cloud Service: Azure AI Services (Document Intelligence API)
Build Tool: Gradle
Dependencies:
Google Cloud Vision: For OCR capabilities.
Apache Commons FileUpload: To handle file uploads.
Log4j: For logging.
Apache HTTPClient: For HTTP requests.


Based on the details provided about your project, its structure, dependencies, and the endpoint for document scanning, here's an updated and complete README.md for your repository:

Document AI Recognition
A Spring Boot-based backend service to automate document processing and recognition using Azure AI Services.

📖 Overview
This project simplifies and automates document recognition tasks, eliminating manual effort and reducing errors. It extracts key details from uploaded documents, identifies the document type, and provides structured results that can streamline processes such as audits.

🚀 Key Features
Document Upload: Accepts files as input to extract text and metadata.
Automated Recognition: Leverages Azure AI Services for high-accuracy data extraction.
RESTful API: Designed as a backend service for integration into various systems.
Efficient Architecture: Built with Java and Spring Boot for robust performance.
📂 Project Structure
plaintext
Copy code
spring-ai-backend/
├── src/
│   ├── main/
│   │   ├── java/com/iamamansid/spring_ai_backend/
│   │   │   ├── Controller/           # API controllers (DocScannerController.java)  
│   │   │   ├── Service/              # Business logic (DocumentService and Impl)  
│   │   │   ├── models/response/      # Response models and configurations  
│   │   │   └── SpringAiBackendApplication.java  # Main application entry point  
│   │   ├── resources/  
│   │       ├── application.properties  # Application configurations (e.g., API keys)  
│   │       ├── static/                # Static assets (if applicable)  
│   │       └── templates/             # Templates (if applicable)  
│   ├── test/                          # Test cases for validation  
├── build.gradle                       # Gradle build configuration  
├── Dockerfile                         # Docker support (if implemented)  
├── .gitignore                         # Git ignored files  
└── README.md                          # Documentation (this file)  
⚙️ Technologies Used
Programming Language: Java (17)
Framework: Spring Boot (3.3.5)
Cloud Service: Azure AI Services (Document Intelligence API)
Build Tool: Gradle
Dependencies:
Google Cloud Vision: For OCR capabilities.
Apache Commons FileUpload: To handle file uploads.
Log4j: For logging.
Apache HTTPClient: For HTTP requests.
📜 API Endpoints
Document Scanning
Endpoint: POST http://localhost:8080/spring-ai-backend/api/webapp/v0/getDocScanned
Description:
Uploads a document and retrieves extracted data.

Headers:

Key	Value
Content-Type	multipart/form-data
Body (form-data):

Key	Type	Description
document	File	Document to be scanned

🛠️ Setup and Installation
Clone the repository:
git clone https://github.com/iamamansid/spring-ai-backend.git  
cd spring-ai-backend  
Configure Azure AI Services:

Obtain API keys and endpoint URLs from your Azure portal.
Add them to the application.properties file in the resources/ directory:
properties
azure.ai.endpoint=<YOUR_AZURE_ENDPOINT>  
azure.ai.key=<YOUR_AZURE_API_KEY>  
Build the project:
./gradlew build  

🌟 Future Enhancements
Frontend Integration: Add a React.js or Angular-based UI for better user experience.
Cloud Deployment: Deploy the application to Azure App Services or AWS.
Enhanced Document Types: Support for additional document types like contracts, invoices, etc.

📧 Contact
Author: Aman Siddiqui

GitHub
LinkedIn

📄 License
This project is licensed under the MIT License.
