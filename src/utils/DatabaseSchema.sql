CREATE DATABASE <DBNAME>;

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
    FOREIGN KEY (memberID) REFERENCES Users(userId)
);

CREATE TABLE IF NOT EXISTS WorkoutClasses (
    workoutClassID SERIAL PRIMARY KEY,
    workoutClassType TEXT NOT NULL,
    workoutClassDescription TEXT,
    trainerID INT,
    FOREIGN KEY (trainerID) REFERENCES Users(userId)
);

CREATE TABLE IF NOT EXISTS GymMerch (
    merchID SERIAL PRIMARY KEY,
    merchName TEXT NOT NULL,
    merchType TEXT,
    merchPrice DOUBLE PRECISION NOT NULL,
    quantityInStock INT NOT NULL
);