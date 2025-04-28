# Udemy Learning Platform

## Overview
This project implements a learning platform similar to Udemy, allowing users to browse, purchase, and complete courses. The platform supports different account types, course categories, and provides functionality for course management and completion tracking.

## Features

### Accounts
- **Account Types**:
  - **Standard Account**: Basic account with standard discount.
  - **Educational Account**: Special account for students with potential for additional discounts based on academic performance.
  - **Business Account**: Corporate account with category-specific permissions and higher discounts.

### Courses
- **Course Management**:
  - Browse courses by name, keyword, or category
  - Course purchase
  - Resource completion tracking
  - Course completion with grading

### Platform Functionality
- **Course Discovery**:
  - Find courses by exact name
  - Search courses by keyword
  - Filter courses by category
  - Find longest course
  - Find cheapest course by category

## Project Structure

### Main Components

#### Interfaces
- `LearningPlatform`: Core interface defining platform operations
- `Account`: Interface for user account operations
- `Completable`: Interface for tracking completion status
- `Purchasable`: Interface for purchasable items

#### Account Types
- `AccountBase`: Abstract base class implementing core account functionality
- `StandartAccount`: Standard user account
- `EducationalAccount`: Educational account with performance-based discounts
- `BusinessAccount`: Business account with category restrictions

#### Course Components
- `Course`: Main course class implementing Completable and Purchasable
- `Resource`: Course component with completion tracking
- `Category`: Enum defining course categories

#### Duration Management
- `CourseDuration`: Record for course duration (hours, minutes)
- `ResourceDuration`: Record for resource duration (minutes)

#### Exceptions
- Custom exceptions for various error cases (insufficient balance, course not found, etc.)

## Project Structure in Detail

```
src/bg/sofia/uni/fmi/mjt/udemy/
│
├── LearningPlatform.java      # Core interface defining platform operations
├── Udemy.java                 # Implementation of the LearningPlatform interface
├── MainDemo.java              # Demo application showing platform usage
│
├── account/                   # Account-related classes
│   ├── Account.java           # Interface defining account operations
│   ├── AccountBase.java       # Abstract base class for all account types
│   ├── BusinessAccount.java   # Implementation for business users
│   ├── EducationalAccount.java # Implementation for educational institutions
│   ├── StandartAccount.java   # Implementation for standard users
│   │
│   └── type/                  # Account type definitions
│       └── AccountType.java   # Enum defining account types and discounts
│
├── course/                    # Course-related classes
│   ├── Category.java          # Enum defining course categories
│   ├── Completable.java       # Interface for tracking completion
│   ├── Course.java            # Main course implementation
│   ├── Purchasable.java       # Interface for purchasable items
│   ├── Resource.java          # Course resource implementation
│   │
│   └── duration/              # Time tracking classes
│       ├── CourseDuration.java  # Record for course duration
│       └── ResourceDuration.java # Record for resource duration
│
└── exception/                 # Custom exceptions
    ├── AccountNotFoundException.java
    ├── CourseAlreadyPurchasedException.java
    ├── CourseNotCompletedException.java
    ├── CourseNotFoundException.java
    ├── CourseNotPurchasedException.java
    ├── InsufficientBalanceException.java
    ├── MaxCourseCapacityReachedException.java
    └── ResourceNotFoundException.java
```

## Key Design Patterns

1. **Factory Method Pattern**: Used in `CourseDuration.of()` to create course durations
2. **Composite Pattern**: Resources are part of Courses
3. **Strategy Pattern**: Different account types implement different discount strategies
4. **Template Method Pattern**: `AccountBase` provides template methods for account operations

## Exception Handling

The platform implements robust exception handling with custom exceptions:
- `AccountNotFoundException`
- `CourseAlreadyPurchasedException`
- `CourseNotCompletedException`
- `CourseNotFoundException`
- `CourseNotPurchasedException`
- `InsufficientBalanceException`
- `MaxCourseCapacityReachedException`
- `ResourceNotFoundException`

## Notes on Implementation

- The platform supports up to 100 courses per account
- Educational accounts can earn discounts by maintaining high grades
- Business accounts are restricted to specific course categories
- Course completion is tracked at the resource level
- Course pricing includes account-specific discounts
