# Consultoria App – Medical Consultation Manager

A desktop application built with **Java**, **JavaFX** and **Scene Builder** to help a private general surgeon manage patients, referring doctors and consultations in one place.

This project was developed as a Computer Science Internal Assessment. The complete documentation (planning, design, development and evaluation) is in the [`Documentation/`](Documentation) folder.

---

## The problem

The client, Dr. Manuel Bazaldua, gives private consultations in two consulting rooms at different hospitals. Before this app:

- His assistant wrote down patient contact info in an agenda.
- Medical histories were filled in by hand on a template.
- Consultations were scheduled in a separate calendar app.

All the information ended up in different places. Finding the details of a consultation was slow and tedious. He also had no easy way to see in which months he had more or fewer appointments, which made it hard to plan vacations and days off.

## The solution

One application that keeps all patient, doctor and consultation information together. It also shows the data as graphs to help with planning.

### Features
- **Log in**: access protected by a username and password
- **Patients**: register, edit, delete and search patients by name
- **Medical data**: personal info, insurance, referring doctor, height and weight (with automatic BMI), diagnosis and prescription
- **Consultations**: schedule consultations with registered patients using a date picker, and search them by patient and date
- **Patient files**: automatic `.txt` file generated with a patient's information
- **Referring doctors**: add and manage the doctors who refer patients
- **Statistics**:
  - Consultation frequency per month
  - Which doctors refer the most patients
- **Data validation**: alerts for missing or invalid input, plus confirmation before any deletion

---

## Technologies

| Tool | Use |
|------|-----|
| Java | Main language (object-oriented design) |
| JavaFX | Graphical user interface |
| Scene Builder | Designing the screens (`.fxml` files) |
| CSV / TXT files | Data storage |

### Algorithms used
- **Binary search**: finding patients by name in large lists (O(log n))
- **Linear search**: finding consultations by two parameters (patient and date)
- **Selection sort**: ordering table views and preparing lists for binary search

---

## Project structure

```
src/
├── application/     # Main.java, FXML screens and CSS
├── controllers/     # One controller per screen
├── additionals/     # Model classes (Patient, Doctor, Consultation, User, lists)
├── images/          # Logos and images
├── txtFiles/        # CSV data files (patients, doctors, consultations)
└── PatientsFiles/   # Generated patient .txt files
Documentation/       # Project documentation (PDF)
```

---

## How to run it

The project was originally built with Java 8 (which included JavaFX). It also runs on newer versions of Java if you add JavaFX separately.

### Requirements
- **JDK 21** (or newer)
- **JavaFX SDK 21**: download it from [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx/), unzip it and rename the folder to `javafx-sdk` in your home folder

### Compile
From the project's root folder:
```bash
javac --module-path ~/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d bin $(find src -name "*.java")
```

### Run
```bash
java --module-path ~/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp bin application.Main
```

> ⚠️ Always run the app from the **project's root folder**. It reads and saves data using relative paths (`src/txtFiles/...`).

> 💡 On macOS, if JavaFX is blocked by security, run:
> `xattr -dr com.apple.quarantine ~/javafx-sdk`

---

## Documentation

| Document | Content |
|----------|---------|
| [Planning](Documentation/Crit_A_Planning.pdf) | Client scenario, rationale and success criteria |
| [Record of Tasks](Documentation/Crit_B_Record_of_tasks.pdf) | Development timeline |
| [Design](Documentation/Crit_B_Design.pdf) | Flowcharts, UML diagrams, screen designs and test plan |
| [Development](Documentation/Crit_C_Development.pdf) | Techniques, algorithms and tools used |
| [Evaluation](Documentation/Crit_E_Evaluation.pdf) | Client feedback and future improvements |
| [Appendix](Documentation/Appendix.pdf) | Client interviews |

---

## Future improvements

Based on the client's feedback:
- Upload medical images (X-rays, CT scans, lab and pathology reports) to each patient's file
- Spanish translation of the interface

---

## Author

**Clarisa Bazaldua**