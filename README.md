# Cafeteria Management System

A Java-based program for managing a cafeteria menu system with separate functionalities for administrators and users. 

## Features

### Admin Features
- **Menu Management**
  - Add new food items with complete details (name, origin, allergens, calories, price)
  - Delete existing menu items
  - Search for specific food items
  - View total number of items on menu

- **Reporting System**
  - Generate reports of all item names
  - View countries of origin with item counts
  - Display comprehensive information for all menu items

- **Request Management**
  - View user-submitted food requests
  - Approve requests and add them to the menu
  - Delete processed requests

- **Complaint Monitoring**
  - View all user-submitted complaints

### User Features
- **Ordering System**
  - Order two food items simultaneously
  - View itemized receipt with total price
  - Browse complete menu with nutritional information

- **Menu Inquiry**
  - Search for specific food items
  - View detailed information (origin, allergens, calories, price)

- **Feedback System**
  - Submit complaints
  - Request new menu items with country of origin

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- JUnit 5 (for running tests)
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) 


## Usage


### Admin Workflow

1. Select option 1 for Admin mode
2. Choose from 8 available actions:
   - Add menu item
   - Delete menu item
   - Generate reports
   - Check and approve requests
   - Search for food
   - Check menu size
   - View complaints
   - Exit to main menu

### User Workflow

1. Select option 2 for User mode
2. Choose from 5 available actions:
   - Order two food items
   - Inquire about specific items
   - Submit a complaint
   - Request a new item
   - Exit to main menu


## File Structure

The application creates and manages three text files:

### Menu.txt
Stores all menu items
```
ItemName. Origin: Country, Allergens: List, Calories: XXX kcal, Price: XX.XX SAR.
```

### Request.txt
Stores user requests
```
ItemName. Origin: Country.
```

### Complaint.txt
Stores user complaints
```
Complaint text here
```

## Testing

The project includes comprehensive JUnit 5 tests covering:
- foodNode constructors
- Linked list operations (add, delete, length)
- Search functionality
- Admin request approval
- User operations
- File I/O operations
- Edge cases and error handling

## Technical Details

### Data Structures

**foodNode**
- Custom node class for linked list implementation
- Stores: name, origin, allergen, calories, price, link to next node

**foodList**
- Singly linked list implementation

### Key Algorithms

1. **addTail()**: O(n) - Traverses to end of list to add new node
2. **deleteAtPosition()**: O(n) - Traverses to position and adjusts links
3. **checkAvailability()**: O(n) - Linear search through list
4. **displayOrigins()**: O(n²) - Nested loops for counting unique countries
