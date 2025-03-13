-- Drop the table if it exists
DROP TABLE IF EXISTS doctors;

-- Create the doctors table
CREATE TABLE doctors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    profile_image TEXT,
    available_status BOOLEAN NOT NULL DEFAULT TRUE
);

-- Insert a default test doctor
INSERT INTO doctors (name, specialization, profile_image, available_status)
VALUES ('Dr. Jane Smith', 'Cardiologist', 'https://example.com/profile_images/jane_smith.jpg', TRUE);
