# StudySync — Smart Study Planner 📚🎒📗

**Plan better. Learn smarter.**

StudySync is a web-based study planning application designed to help students organize academic tasks, manage deadlines, track progress, and recover from missed study sessions through a priority-based smart study plan proposal.

The project combines a Java Spring Boot backend with a responsive HTML, CSS, and JavaScript frontend.

## ✨ Features

### 1. Study Task Management

* Create study tasks with a title, subject, deadline, and priority.
* View all saved tasks.
* Update task details and completion status.
* Mark tasks as completed or undo completion.
* Delete tasks when they are no longer needed.

### 2. Smart Study Plan

* Generate a revised study plan for unfinished tasks.
* Provide a reason for requesting a revised plan, such as missing a study session.
* Prioritize tasks using their priority level and deadline urgency.
* Review the proposed task order before making a decision.
* Accept or reject a proposal through the interface.

**Important:** Accepting or rejecting a proposal currently updates the interface only. It does not save the decision to the database or automatically reschedule existing tasks.

### 3. Progress Dashboard

* View total tasks.
* Track completed and pending tasks.
* Monitor overall completion percentage.
* Identify overdue tasks and approaching deadlines.

### 4. Persistent Storage

StudySync uses an H2 database configured for file-based storage so task records can persist across application restarts.

### 5. Responsive Interface

The frontend uses HTML, CSS, and JavaScript to provide a dashboard that adapts to desktop and mobile screens.

## 🛠️ Technology Stack

| Technology      | Purpose                                    |
| --------------- | ------------------------------------------ |
| Java 21         | Backend programming                        |
| Spring Boot     | Application framework and REST APIs        |
| Spring Data JPA | Database access                            |
| Maven           | Dependency management and build automation |
| H2 Database     | Persistent task storage                    |
| HTML5           | Page structure                             |
| CSS3            | Styling and responsive layout              |
| JavaScript      | Frontend interactions and API requests     |
| Fetch API       | Communication between frontend and backend |
| JUnit 5         | Automated testing                          |

## 🏗️ Application Architecture

```text
┌──────────────────────────────────────┐
│         Student / Web Browser        │
└──────────────────┬───────────────────┘
                   │
                   ▼
┌──────────────────────────────────────┐
│          Frontend Interface           │
│            HTML / CSS / JS            │
│                                      │
│  Task Form · Dashboard · Smart Plan  │
└──────────────────┬───────────────────┘
                   │
                   │ HTTP / JSON
                   ▼
┌──────────────────────────────────────┐
│          Spring Boot Backend          │
│                                      │
│  TaskController                      │
│  StudyPlanController                 │
│  HealthController                    │
│                 │                    │
│                 ▼                    │
│  TaskService                         │
│  StudyPlanService                    │
│                 │                    │
│                 ▼                    │
│  TaskRepository                      │
└──────────────────┬───────────────────┘
                   │
                   ▼
┌──────────────────────────────────────┐
│             H2 Database               │
│          Persistent Task Data         │
└──────────────────────────────────────┘
```

## 🚀 Getting Started

### Prerequisites

Install or configure the following:

* Java Development Kit (JDK) 21
* Git
* A terminal such as PowerShell
* A web browser

The project includes a Maven wrapper, so a separate Maven installation should not be necessary.

### 1. Clone the repository

If the project is hosted on GitHub, clone it using:

```bash
git clone <your-github-repository-url>
cd studysync
```

Replace the placeholder with your actual repository URL.

If you already
