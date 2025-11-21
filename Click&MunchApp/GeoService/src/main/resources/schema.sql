CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS locations (
     id SERIAL PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     type VARCHAR(50) NOT NULL,
     latitude DOUBLE PRECISION NOT NULL,
     longitude DOUBLE PRECISION NOT NULL,
     geometry geometry(Point, 4326) GENERATED ALWAYS AS (
         ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)
         ) STORED
);
