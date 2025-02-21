# Simple Database Server with B+ Tree Indexing

## 🚀 Project Overview

This is a **lightweight database server** that listens for client commands over a **socket connection** and executes basic database operations such as:

- **Creating tables** 🏗️
- **Inserting records** 📥
- **Searching records efficiently** using a **B+ Tree index** 🔍

The project provides a **basic database engine** where each table is backed by a B+ Tree, ensuring **fast searches** with binary search optimization.

## 🛠️ Features

- **B+ Tree Implementation**: Efficient indexing for fast lookups 📌
- **Binary Search for Optimized Queries**: Speeds up searching in leaf nodes ⚡
- **Socket-Based Communication**: Accepts queries from multiple clients 🔌
- **Basic Query Support**: Supports simple `CREATE`, `INSERT`, and `SELECT` commands 📝
- **Multi-threaded Client Handling**: Allows concurrent queries 🏃‍♂️

## 🏗️ How It Works

The database server runs on **port 3000**, listening for commands from clients. Each table has an **auto-managed B+ Tree**, ensuring fast insertions and searches.

### 📜 Supported Queries

#### 1️⃣ Creating a Table

```
CREATE users
```

This creates a table named `users`.

#### 2️⃣ Inserting Data

```
INSERT users 1 Alice 25
INSERT users 2 Bob 30
```

This inserts records with an **ID, Name, and Age** into the `users` table.

#### 3️⃣ Searching for a Record

```
SELECT users 1
```

Retrieves the record with `ID = 1` using an optimized **B+ Tree search**.

## 🚀 Running the Project

### 1️⃣ Start the Database Server

Compile and run the server:

```sh
javac src/DatabaseServer.java
java src.DatabaseServer
```

The server will start and listen on `port 3000`.

### 2️⃣ Run a Client

A simple Node.js client can send queries:

```js
const net = require('net');
const client = net.createConnection({ port: 3000 }, () => {
  console.log('Connected to database server');
  client.write('CREATE users\n');
  client.write('INSERT users 1 Alice 25\n');
  client.write('INSERT users 2 Bob 30\n');
  setTimeout(() => {
    client.write('SELECT users 1\n');
  }, 1000);
});
```

## 🔥 Why B+ Tree?

- **Balanced structure** ensures **log(n) search time** 📊
- **Binary search on leaf nodes** for optimized lookups 🚀
- **Linked leaf nodes** allow efficient range queries 🔗

## 🏆 Open Source Contributions

This project is **open for contributions**! If you want to **extend functionality** (e.g., support for `DELETE`, `UPDATE` commands, or persistence to disk), feel free to submit a pull request! 🙌

## 📌 To-Do & Future Enhancements

- ✅ Support for more complex SQL-like queries
- ✅ Persistent storage instead of in-memory tables
- ✅ Client library for easy integration

## 📜 License

This project is **MIT licensed** – free to use, modify, and contribute!
