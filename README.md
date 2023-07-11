# Bank Document Management System

This is the readme file for the Bank Document Management System project, developed as part of the Credit Journey Digitization initiative for a bank. The system is implemented using Java Spring and Spring Boot.

## User Stories

### 1. Tasks - Basic RESTful API
As a User, I want to be able to perform the following actions with my uploaded documents:
- Upload a PDF document
- Remove an uploaded document
- View all my uploaded documents

### 2. Integration with 3rd Party Posts and Comments API
As a User, I want to be able to perform the following actions related to Posts and Comments:
- Create a Post and associate it with an existing Document
- View Posts associated with a Document
- Create Comments and associate them with Documents

## System Requirements

- The system should adhere to RESTful API best practices.
- Document format supported is PDF.
- The system's APIs must have at least 60% unit testing and 60% integration testing coverage, measured with JaCoCo.
- The front-end development will be handled by a separate team. This project focuses solely on providing the required API functionality.
- The system should be implemented using Java Spring and Gradle as the build tool.
- The final deliverable should be shared with the Tech Lead through a Git repository.

## Additional Information

- An in-memory database is sufficient for all the tasks.
- The 3rd Party Posts and Comments API, provided by [jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com/guide.html), should be integrated into the system.
- The 3rd Party System is not highly available, so retry and fallback logic should be included in the integration. The use of libraries like Feign ([https://github.com/OpenFeign/feign](https://github.com/OpenFeign/feign)) is recommended.
- There is a ZeroOrOne-to-One relationship between a Post and a Document entity.

## Getting Started

To set up the Bank Document Management System project, follow these steps:

1. Clone the repository: [GitHub Repository]([https://github.com/Ahmedhsultan/BankDocumentManagementSystem](https://github.com/Ahmedhsultan/BankDocumentManagementSystem))
2. Make sure you have Java and Gradle installed on your system.
3. Configure the project dependencies in the build.gradle file.
4. Implement the required functionalities according to the user stories mentioned above.
5. Include unit tests and integration tests to ensure proper coverage.
6. Integrate with the 3rd Party Posts and Comments API, using appropriate retry and fallback logic.
7. Test the system thoroughly to ensure its functionality and adherence to non-functional requirements.
8. Share the final deliverable with the Tech Lead through a Git repository.

## Contact Information

For any questions or inquiries, please contact:

ahmedhussamsultan@gmail.com

Thank you for your contribution to the Bank Document Management System project!
