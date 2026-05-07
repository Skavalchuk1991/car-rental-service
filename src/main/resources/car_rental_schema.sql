DROP DATABASE IF EXISTS car_rental;
CREATE DATABASE car_rental;
USE car_rental;

-- Categories (SUV, Sedan, Economy, etc.)
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    price_multiplier DOUBLE NOT NULL DEFAULT 1.0
);

-- Branches (rental locations)
CREATE TABLE branches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    opened_at DATETIME NOT NULL
);

-- Cars
CREATE TABLE cars (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    daily_rate DOUBLE NOT NULL,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    category_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Employees
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    position VARCHAR(50) NOT NULL,
    salary DOUBLE NOT NULL,
    hired_at DATE NOT NULL,
    branch_id BIGINT NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Customers
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    registered_at DATETIME NOT NULL
);

-- Insurance types
CREATE TABLE insurances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    coverage_amount DOUBLE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    valid_until DATETIME NOT NULL
);

-- Rentals
CREATE TABLE rentals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_price DOUBLE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    customer_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    insurance_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (insurance_id) REFERENCES insurances(id) ON DELETE SET NULL ON UPDATE NO ACTION
);

-- Payments (one-to-one with rental)
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    payment_date DATETIME,
    rental_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (rental_id) REFERENCES rentals(id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Reviews
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rating INT NOT NULL,
    comment VARCHAR(500),
    review_date DATETIME NOT NULL,
    customer_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Maintenance records
CREATE TABLE maintenance_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    cost DOUBLE NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    scheduled_at DATETIME NOT NULL,
    car_id BIGINT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE ON UPDATE NO ACTION
);
