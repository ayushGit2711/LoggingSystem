# Logging System

## Usage

This project is a pluggable **logging system library** that developers can integrate into their applications for:

- ✅ Debugging
- ✅ Log recording
- ✅ Log querying

---

## 🧩 Problem Statement

Develop a **logging system** packaged as a **Java library** that supports:

- Real-time and crash-safe log ingestion
- Flexible querying capabilities
- Pluggable data storage
- Cross-service traceability and middleware-level integrations

---

## ✅ Functional Requirements

1. **Log Ingestion**
    - Support adding and appending logs from the application.

2. **Query Support**
    - `SELECT (Range)`: Retrieve logs within a specific time or level range
    - `FIND`: Search for specific messages or patterns in logs

3. **Custom Datastore Integration**
    - Allow users to plug in their own datastore implementation for storing logs.

4. **Crash-safe Logging**
    - Ensure that logs are written even during unexpected application crashes.

---

## 📌 Non-Functional Requirements

### 🔹 Log Levels Support

Support multiple standard log levels:
INFO, DEBUG, WARN, ERROR


### 🔹 High Reliability & Responsiveness

- The logger must **never fail** the host application
- Must be **highly responsive and performant**

### 🔹 Modular & Extensible Design

- Architected for **flexibility and extensibility**
- Easy addition of new features/modules

---

## ⚙️ Optional Advanced Features

- **Auto-Flushing**:  
  Automatically flush logs to disk when the size exceeds `2GB`.

- **Log Retention Policy**:  
  Auto-deletion of logs older than `x` days.

---

## 🔗 Cross-Service Traceability

- Support **trace IDs** or **context propagation** to track logs across distributed systems/microservices

---

## 🌐 HTTP Middleware Logging Support

- Can be integrated as middleware to intercept and log all HTTP requests and responses

---

## 📐 Design Document

- https://docs.google.com/document/d/1DIxjvrLIUerLPz3nQ_1lNeaNoczanp1WCuPeMbasnHY/edit?usp=sharing



