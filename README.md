🎬 **AMRO – Android Movie App (MVP)**

This project is an Android application built as part of the AMRO (Advanced Movie Recommendation Organisation) MVP assignment.

The goal of this app is to provide users with an intuitive way to explore trending movies, view detailed movie information, and demonstrate a scalable, maintainable Android architecture that can support future growth.

This README will evolve as features are implemented.

--------------------------------------------------------------------------------------

**Product Scope (MVP)**

The MVP focuses on the following core features:

 1. Displays this week’s top 100 trending movies.
 
 2. Filter and sort of these movies

Each movie shows:
Title,
Image,
Genre

Users can:

-> Filter on genre

-> Sort by:
    Popularity (default),
    Title and 
    Release date

-> Toggle ascending / descending order with the sort

-> Movie Details

Selecting a movie navigates to a detail screen showing:
Title,
Tagline,
Poster image,
Genres,
Description,
Vote average & vote count,
Budget & revenue,
Runtime,
Release date,
Status &
Link to IMDb

-----------------------------------------------------------------------

**Architecture**

The app is built using Clean Architecture principles with MVVM, aiming for:

---Clear separation of concerns

---Testability

---Scalability for future features and teams

High-level structure:

data/      → API, repositories
domain/    → Business models & use cases
ui/        → Compose UI, ViewModels, navigation


State is managed using Kotlin Coroutines + Flow.

----------------------------------------------------------------------

**Tech Stack**

Language: Kotlin

UI: Jetpack Compose (Material 3)

Architecture: MVVM + Clean Architecture

Networking: Retrofit + OkHttp

Image loading: TBD

Dependency Injection: Hilt

Async: Coroutines & Flow

Testing:

Unit tests (JUnit, MockK)

UI tests (Compose testing)

------------------------------------------------------------------------------

**API**

This project uses The Movie Database (TMDB) API:

Trending movies

Movie genres

Movie details

Images

API keys , if any, are not committed to the repository and should be provided locally.

API Key Setup

Add the following to your local.properties file:

TMDB_API_KEY=your_api_key_here

The key is injected via Gradle build configuration.

-----------------------------------------------------------------------------

**Getting Started**

Clone the repository

Open the project in Android Studio

Ensure you are using:

JDK 17

A recent stable Android Studio version

Add your API key to local.properties

Build & run the app

-------------------------------------------------------------------------

**Testing Strategy (Initial)**

Testing is considered a first-class concern:

ViewModels and business logic are covered with unit tests

UI behavior is verified using Compose UI tests

The test setup is designed to grow alongside the app

Test coverage will increase as features are completed.

------------------------------------------------------------------------

**Future Considerations**

The architecture is intentionally designed to support:

Offline caching (e.g. Room)

Multiple API sources

Feature-based modules

Additional features such as:

Actor info

User profiles

Streaming

Social Media

---------------------------------------------------------------------------

👤 Author

Meghana Shetty
Senior Android Developer
Assignment project for AMRO 
