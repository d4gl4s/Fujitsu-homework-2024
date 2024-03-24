# Fujitsu 2024: Food Delivery Fee Calculator

This application is a sub-functionality of a food delivery application that calculates the delivery fee for food couriers based on regional base fee, vehicle type, and weather conditions. Find the instructions of how to set up the project down below.

## Core Modules

1. **Database:** The application includes a h2 database for storing and manipulating weather and delivery fee calculation rule data.
2. **CronJob for Importing Weather Data:** A configurable scheduled task is implemented to import weather data from the weather portal of the Estonian Environment Agency. The CronJob runs once every hour by default, 15 minutes after a full hour (HH:15:00).

3. **Delivery Fee Calculation Functionality:** The application includes functionality to calculate the delivery fee based on regional base fee, vehicle type, and weather conditions.

4. **REST Interface:** The application provides API endpoints to request the delivery fee based on input parameters and modify business rules for calculating the delivery fee.

## Delivery Fee API 

- **URL:** `/api/calculateDeliveryFee`
- **Method:** `GET`
- **Parameters:**
  - `city` (Enum): The city where the delivery is to be made.
  - `vehicleType` (Enum): The type of vehicle to be used for the delivery.
  - `dateTime` (Optional LocalDateTime): The date and time of the delivery.
- **Description:** Calculates the delivery fee based on the given city, vehicle type, and optional date and time.
- **Returns:** A `Double` value representing the calculated delivery fee or an error message.

## Base Fee Rule API 

### Retrieve All Base Fee Rules

- **URL:** `/api/base-fees`
- **Method:** `GET`
- **Description:** Retrieves all base fee rules.
- **Returns:** A list of `BaseFeeRule` objects.

### Create Base Fee Rule

- **URL:** `/api/base-fees`
- **Method:** `POST`
- **Parameters:**
  - `baseFeeRule` (RequestBody): The base fee rule to be created.
- **Description:** Creates a new base fee rule.
- **Returns:** The created `BaseFeeRule` object.

### Retrieve Base Fee Rule by ID

- **URL:** `/api/base-fees/{id}`
- **Method:** `GET`
- **Parameters:**
  - `id` (PathVariable): The ID of the base fee rule to retrieve.
- **Description:** Retrieves a base fee rule by its ID.
- **Returns:** The retrieved `BaseFeeRule` object.

### Update Base Fee Rule

- **URL:** `/api/base-fees/{id}`
- **Method:** `PUT`
- **Parameters:**
  - `id` (PathVariable): The ID of the base fee rule to update.
  - `baseFeeRule` (RequestBody): The updated base fee rule.
- **Description:** Updates an existing base fee rule. Keeps original rule for queries that need rules that were applied at some time period. Creates a new updated rule.
- **Returns:** The updated `BaseFeeRule` object.

### Delete Base Fee Rule

- **URL:** `/api/base-fees/{id}`
- **Method:** `DELETE`
- **Parameters:**
  - `id` (PathVariable): The ID of the base fee rule to delete.
- **Description:** Deletes a base fee rule by its ID. Keeps original in database but adds a endDate value to it, to specify that rule does not apply anymore. Keeping old rules is necessary to query rules for fee calculations at previous dateTimes.

## Extra Fee Rule API

### Retrieve All Extra Fee Rules

- **URL:** `/api/extra-fees`
- **Method:** `GET`
- **Description:** Retrieves all extra fee rules.
- **Returns:** A list of `ExtraFeeRule` objects.

### Create Extra Fee Rule

- **URL:** `/api/extra-fees`
- **Method:** `POST`
- **Parameters:**
  - `extraFeeRule` (RequestBody): The extra fee rule to be created.
- **Description:** Creates a new extra fee rule.
- **Returns:** The created `ExtraFeeRule` object.

### Retrieve Extra Fee Rule by ID

- **URL:** `/api/extra-fees/{id}`
- **Method:** `GET`
- **Parameters:**
  - `id` (PathVariable): The ID of the extra fee rule to retrieve.
- **Description:** Retrieves an extra fee rule by its ID.
- **Returns:** The retrieved `ExtraFeeRule` object.

### Update Extra Fee Rule

- **URL:** `/api/extra-fees/{id}`
- **Method:** `PUT`
- **Parameters:**
  - `id` (PathVariable): The ID of the extra fee rule to update.
  - `extraFeeRule` (RequestBody): The updated extra fee rule.
- **Description:** Updates an existing extra fee rule. Keeps original rule for queries that need rules that were applied at some time period. Creates a new updated rule.
- **Returns:** The updated `ExtraFeeRule` object.

### Delete Extra Fee Rule

- **URL:** `/api/extra-fees/{id}`
- **Method:** `DELETE`
- **Parameters:**
  - `id` (PathVariable): The ID of the extra fee rule to delete.
- **Description:** Deletes an extra fee rule by its ID. Keeps original in database but adds a endDate value to it, to specify that rule does not apply anymore. Keeping old rules is necessary to query rules for fee calculations at previous dateTimes.
## Setting Up the Project ✨

1. **Cloning the Repository:**
   - Clone the repository to your local machine using Git:
     ```
     git clone https://github.com/d4gl4s/Fujitsu-homework-2024.git
     ```

2. **Database Configuration:**
   - The application uses an H2 in-memory database.
   - The JDBC URL is (`jdbc:h2:mem:test`), the username is (`su`), and the password is empty.

3. **Running the Application:**
   - Once configured, run the Spring Boot application using your IDE or command line.

4. **Accessing the H2 Console:**
   - After running the application, access the H2 console to manage the database.
   - Open a web browser and go to `http://localhost:8080/h2-console`.
   - Enter the JDBC URL (`jdbc:h2:mem:test`), username (`su`), and leave the password field blank, then click Connect.

5. **Configuring CronJob Options:**
   - Adjust the CronJob schedule for importing weather data in `WeatherService.java` as needed.

6. **Testing API Endpoints:**
   - Use tools like Postman to test the provided API endpoints.
   - Refer to the README for details on API endpoints and their usage.


