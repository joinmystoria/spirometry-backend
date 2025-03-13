-- Drop the table if it exists
DROP TABLE IF EXISTS users CASCADE;

-- Create the users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    email VARCHAR(100) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    phone_number VARCHAR(20),
    address VARCHAR(100),
    birth_date DATE,
    gender VARCHAR(30),
    height DECIMAL(5, 2),
    weight DECIMAL(5, 2),
);