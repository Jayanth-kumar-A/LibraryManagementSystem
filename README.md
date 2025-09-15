# LibraryManagementSystem

📚 Library Book Lending System (Java + MySQL)

A simple Library Management System built using Java + MySQL.
This project demonstrates the use of Core Java (OOPs, Collections, Exception Handling, JDBC) and SQL database integration to manage books, users, and lending records.

🚀 Features

📖 View Books – Displays all books with availability status.

➕ Lend Book – Allows a user to borrow a book (updates DB).

🔄 Return Book – Lets a user return a borrowed book.

🗄️ Database Integration – Stores books, users, and lending history in MySQL.

🗄️ Database Setup

Run the following SQL script in MySQL:

CREATE DATABASE library_db;
USE library_db;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE lending (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    lend_date DATE,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

⚙️ How to Run

Clone this repository:

git clone https://github.com/<your-username>/LibraryManagementSystem.git
cd LibraryManagementSystem


Compile the Java file:

javac LibraryManagementSystem.java


Run the program (make sure MySQL Connector JAR is in classpath):

java -cp .;mysql-connector-j-8.0.33.jar LibraryManagementSystem

🖥️ Example Output
=== Library Management System ===
1. View Books
2. Lend Book
3. Return Book
4. Exit
Choose: 1

ID | Title               | Author          | Status
1  | Java Fundamentals   | James Gosling   | Available
2  | SQL Basics          | C. J. Date      | Available
3  | Data Structures     | Robert Lafore   | Not Available

📌 Technologies Used

Java (Core Java, JDBC)

MySQL

SQL Queries & Joins

👨‍💻 Author

Jayanth Kumar Atagara

📧 Email: jayanthkumaratagara@gmail.com

🔗 LinkedIn:www.linkedin.com/in/atagara-jayanthkumar

🐙 GitHub:https://github.com/Jayanth-kumar-A
