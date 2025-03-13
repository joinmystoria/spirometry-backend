-- Drop the table if it exists
DROP TABLE IF EXISTS spirometry_tests CASCADE;

-- Create the spirometry_tests table
CREATE TABLE spirometry_tests (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    fev1 DOUBLE PRECISION NOT NULL,  -- Forced Expiratory Volume in 1 second
    fvc DOUBLE PRECISION NOT NULL,   -- Forced Vital Capacity
    fev1_fvc_ratio DOUBLE PRECISION NOT NULL, -- Ratio of FEV1 to FVC (kept this for DB consistency)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
