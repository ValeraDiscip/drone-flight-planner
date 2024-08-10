CREATE TABLE "user"
(
    id       SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(30)        NOT NULL UNIQUE,
    password VARCHAR(200)       NOT NULL

);

CREATE TABLE parameter
(
    id              SERIAL PRIMARY KEY         NOT NULL,
    user_id         INT REFERENCES "user" (id) NOT NULL,
    language        VARCHAR(20)                NOT NULL,
    location        VARCHAR(20)                NOT NULL,
    min_temperature DOUBLE PRECISION           NOT NULL,
    max_temperature DOUBLE PRECISION           NOT NULL,
    max_wind_speed  DOUBLE PRECISION           NOT NULL,
    max_wind_gust   DOUBLE PRECISION           NOT NULL,
    max_humidity    DOUBLE PRECISION           NOT NULL,
    max_precip      DOUBLE PRECISION           NOT NULL,
    max_pressure    DOUBLE PRECISION           NOT NULL
);

CREATE TABLE flight
(
    id             SERIAL PRIMARY KEY         NOT NULL,
    user_id        INT REFERENCES "user" (id) NOT NULL,
    time_of_flight TIMESTAMP                  NOT NULL,
    successful     BOOLEAN                    NOT NULL
);

CREATE TABLE weather
(
    id          SERIAL PRIMARY KEY         NOT NULL,
    flight_id   INT REFERENCES flight (id) NOT NULL,
    time        TIMESTAMP                  NOT NULL,
    temperature DOUBLE PRECISION           NOT NULL,
    wind_speed  DOUBLE PRECISION           NOT NULL,
    pressure    DOUBLE PRECISION           NOT NULL,
    humidity    DOUBLE PRECISION           NOT NULL,
    precip      DOUBLE PRECISION           NOT NULL,
    wind_gust   DOUBLE PRECISION           NOT NULL
);

INSERT INTO "user" (id, username, password) VALUES (1, 'TestUser', 1);
INSERT INTO parameter (id, user_id, language, location, min_temperature, max_temperature, max_wind_speed, max_wind_gust, max_humidity, max_precip, max_pressure)
VALUES (1, 1, 'RU', 'Krasnodar', 0, 30, 20, 25, 80, 1, 1000);