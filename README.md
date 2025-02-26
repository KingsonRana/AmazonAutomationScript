# Amazon Automation Testing with Selenium & TestNG

This project automates Amazon user flows using **Selenium WebDriver** with **TestNG** and follows the **Page Object Model (POM)** design pattern. It currently supports **Chrome** browser and covers **search, sorting, and login functionality**.

## 💁️ Project Structure

```
📺 src/
 ┓ 📂 main/java/
   ┓ 📂 Pages/
     ┓ Login.java
     ┓ SearchSortFilterProduct.java
   ┓ 📂 Utils/
     ┓ DriverManager.java
     ┓ LoggerUtil.java
     ┓ log4j2.xml
 ┓ 📂 test/
   ┓ 📂 testData/
     ┓ TestDataProvider.java
   ┓ 📂 test/
     ┓ BaseTest.java
     ┓ LoginTest.java
     ┓ SearchSortFilterProductTest.java
📝 .gitignore
📃 pom.xml
📃 README.md
📃 testng.xml
```

## 🚀 Features Implemented
- **Login** automation
- **Search & Sort** functionality validation
- **Page Object Model (POM) implemented**
- **TestNG used as the test framework**
- **Data-driven testing using TestNG DataProvider**
- **Logging using Log4j**
- **Extent Reports integrated** – once all tests are completed, the report will automatically open in the browser

## ⚙️ Setup Instructions

### 1️⃣ Prerequisites
- Install **Java (JDK 8+)**
- Install **Maven**
- Install **Chrome browser** and **ChromeDriver**

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/KingsonRana/AmazonAutomationScript.git
cd AmazonAutomationScript
```

### 3️⃣ Configure Profile Path
This project requires setting up the **profile path** and **profile directory** for Chrome. Ensure you update the script with your own profile path before running the tests.

### 4️⃣ Run the Tests
```sh
mvn test
```

## 🛠️ Test Data
The user credentials need to be provided in **TestDataProvider.java** for successful login execution.

## 📌 Future Enhancements
- Add more test cases (checkout, add to cart, etc.)
- Implement cross-browser testing

## 🐝 License
This project is for educational purposes only.

