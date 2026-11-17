# 🥪 Sandwich Java Edition (JavaFX OOP System)

An interactive custom sandwich builder and inventory management system inspired by `onesandwich.com/builder/`, built with **Java (OpenJDK 25)**, **JavaFX**, **Scene Builder**, and **Maven**.

---

## 📋 Project Overview & OOP Architecture

This project follows the **Model-View-Controller (MVC)** architectural pattern and satisfies all core Object-Oriented Programming principles:

- **3-Level Inheritance Hierarchy:** `MenuItem` (Base Abstract Class) $\rightarrow$ `Ingredient` (Intermediate Abstract Class) $\rightarrow$ Concrete Subclasses (`Bread`, `Protein`, `Vegetable`, `Sauce`, `Cheese`, etc.) & `PremiumIngredient` (Abstract Class).
- **3 Separate Abstract Classes:** `MenuItem`, `Ingredient`, and `PremiumIngredient` enforce shared contracts and abstract behaviors (`calculatePrice()`, `getCategory()`, `getSummary()`).
- **Encapsulation & Validation:** Private fields with strict validation inside setters and constructors (preventing negative prices, empty names, or invalid stock counts).
- **Polymorphism:** Dynamic price calculation, stock deduction, and order receipt formatting across mixed ingredient subclasses stored inside an `ArrayList<Ingredient>`.

---

## 🗂️ Repository Structure

```text
SandwichJavaEditionFX/
├── src/main/java/
│   ├── app/
│   │   ├── Main.java                 # Application entry point & primary stage loader
│   │   ├── WelcomeController.java    # Welcome screen navigation
│   │   ├── BuilderController.java    # Interactive sandwich customization & pricing
│   │   └── InventoryController.java  # Stock tracking & ingredient availability
│   └── model/
│       ├── MenuItem.java             # Level 1 Abstract Class
│       ├── Ingredient.java           # Level 2 Abstract Class
│       ├── PremiumIngredient.java    # 3rd Abstract Class (for specialty/extra items)
│       └── [Subclasses].java         # Level 3 Concrete Ingredient classes
├── src/main/resources/
│   ├── app/
│   │   ├── welcome-view.fxml         # Landing screen UI
│   │   ├── builder-view.fxml         # Sandwich builder UI
│   │   ├── inventory-view.fxml       # Inventory management UI
│   │   └── style.css                 # Custom JavaFX styling
│   └── images/                       # Ingredient icons & custom wallpapers
└── pom.xml                           # Maven & JavaFX dependency configuration
