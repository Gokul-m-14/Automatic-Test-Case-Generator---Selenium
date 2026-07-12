# Automatic Test Case Generator (Selenium)

## Project Overview

The **Automatic Test Case Generator** is a Java-based automation tool that dynamically generates **Selenium WebDriver** test cases by analyzing a target website's HTML and DOM structure. Instead of manually writing Selenium automation scripts, the application automatically identifies UI elements and generates basic Selenium test case logic, reducing manual effort and improving productivity.

This project was developed during my internship to strengthen my knowledge of **Java, Selenium, Maven, and Web Automation Frameworks**.

---

## Features

- Automatically fetches and parses a target website URL
- Analyzes HTML and DOM structure using **JSoup**
- Detects common UI elements such as:
  - Input fields
  - Buttons
  - Links
  - Forms
- Generates basic Selenium WebDriver test case logic
- Reduces manual scripting effort and human errors
- Maven-based project for simple dependency management

---

## Tech Stack

- **Java**
- **Selenium WebDriver**
- **JSoup**
- **Maven**
- **TestNG / JUnit**

---

## Project Structure

```text
selenium-autotest-upgrade/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── autotest/
│                   └── Main.java
│
├── pom.xml
├── target/
│   └── selenium-autotest-upgrade-1.0-SNAPSHOT.jar
└── README.md
```

---

## Prerequisites

Before running the project, ensure you have:

- Java JDK 8 or above
- Maven
- Google Chrome (or another supported browser)
- ChromeDriver matching your browser version

---

## Installation

Clone the repository:

```bash
git clone https://github.com/your-username/selenium-autotest-upgrade.git
```

Navigate to the project directory:

```bash
cd selenium-autotest-upgrade
```

Build the project:

```bash
mvn clean package
```

---

## Usage

Run the generated JAR by providing the target website URL:

```bash
java -jar target/selenium-autotest-upgrade-1.0-SNAPSHOT.jar <target-url>
```

### Example

```bash
java -jar target/selenium-autotest-upgrade-1.0-SNAPSHOT.jar https://example.com
```

---

## Output

The application provides:

- Fetched website URL
- Parsed HTML and DOM information
- Identified UI elements
- Generated Selenium WebDriver test case logic based on detected elements

---

## Future Enhancements

- Support JavaScript-rendered pages using Selenium WebDriver
- Generate complete TestNG/JUnit test classes
- Optimize XPath and CSS selector generation
- Export generated test cases directly to `.java` files
- Integrate test reporting (Extent Reports/Allure)
- Add support for Page Object Model (POM)

---

## Learning Outcomes

This project helped me gain practical experience in:

- Selenium WebDriver automation
- DOM parsing using JSoup
- HTML element identification
- Java application development
- Maven project management
- Automation testing frameworks

---

## Author

**Gokul M**

---

## License

This project is intended for educational and learning purposes.
