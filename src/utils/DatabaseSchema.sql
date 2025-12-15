

-- ===========================
-- Create Database
-- ===========================
-- Run this in the "Query Tool" connected to your postgres server (or a superuser)
CREATE DATABASE gym_db;
 
-- ===========================
-- Switch to your database
-- ===========================
-- In pgAdmin 4, just select "gym_db" in the left sidebar, then open Query Tool.
 
-- ===========================
-- Create Tables
-- ===========================
CREATE TABLE IF NOT EXISTS Users (
    userId SERIAL PRIMARY KEY,
    userName TEXT NOT NULL,
    userAddress TEXT,
    userPhoneNumber TEXT,
    userRole TEXT CHECK (userRole IN ('Admin', 'Trainer', 'Member')) NOT NULL,
    passwordHash TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL
);
 
CREATE TABLE IF NOT EXISTS Memberships (
    membershipID SERIAL PRIMARY KEY,
    membershipType TEXT NOT NULL,
    membershipDescription TEXT,
    membershipCost DOUBLE PRECISION NOT NULL,
    memberID INT NOT NULL,
    FOREIGN KEY (memberID) REFERENCES Users(userId) ON DELETE CASCADE
);
 
CREATE TABLE IF NOT EXISTS WorkoutClasses (
    workoutClassID SERIAL PRIMARY KEY,
    workoutClassType TEXT NOT NULL,
    workoutClassDescription TEXT,
    trainerID INT,
    FOREIGN KEY (trainerID) REFERENCES Users(userId) ON DELETE SET NULL
);
 
CREATE TABLE IF NOT EXISTS GymMerch (
    merchID SERIAL PRIMARY KEY,
    merchName TEXT NOT NULL,
    merchType TEXT,
    merchPrice DOUBLE PRECISION NOT NULL,
    quantityInStock INT NOT NULL
);
 
-- ===========================
-- Grant Permissions
-- ===========================
-- Replace <DBUSER> with your app login user
GRANT CONNECT ON DATABASE gym_db TO <DBUSER>;
GRANT USAGE ON SCHEMA public TO <DBUSER>;
 
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO <DBUSER>;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO <DBUSER>;
 
-- Optional: future tables/sequences
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO <DBUSER>;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO <DBUSER>;