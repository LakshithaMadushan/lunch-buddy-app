## 🚀 Application Setup guide

This repository contains Angular front-end and back-end applications related to **Lunch-Buddy** project.


## 1️⃣ Frontend

### Running the FE app locally

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you
change any of the source files.

> ⚠️ Initially you can see only the original login page, if you want to log in then the back-end app also needs to run properly.

### Build Project

Run `ng build` to build the project locally.
```
The build artifacts will be stored in the `dist/` directory.
```

## 2️⃣ Backend App

> ⚠️ Prerequisites: Install MySQL local server and create a database (`my_db`)

1. Build app:
```
mvn clean install
```

2. Run app:
```
mvn spring-boot:run
```
