-- Table: event

-- DROP TABLE IF EXISTS event;

CREATE TABLE IF NOT EXISTS event
(
    id SERIAL PRIMARY KEY,
    capacity integer NOT NULL,
    description character varying(255),
    location_id integer NOT NULL,
    organizer_id integer NOT NULL,
    scheduleid integer NOT NULL,
    tags character varying(255),
    title character varying(255),
    type_id integer NOT NULL,
    eventdate date,
    constraint fkfd4uaqlkjhqlk8oo7dshgwd7d foreign key (location_id)
        references locations (locationid) MATCH SIMPLE
        on update no action
        on delete no action,
    constraint fkfillb9i5om6bfso0uhmkytp7s foreign key (type_id)
        references eventtype (eventtypeid) MATCH SIMPLE
        on update no action
        on delete no action
)
TABLESPACE pg_default;


ALTER TABLE IF EXISTS event
    OWNER to postgres;

-- Insert data records
INSERT INTO event (id, capacity, description, location_id, organizer_id, scheduleid, tags, title, type_id, eventdate)
VALUES
    (1, 300, 'A thrilling sports competition with top athletes.', 2, 3, 4, 'Sport', 'Sports Competition', 5, '2023-06-08'),
    (2, 300, 'A musical extravaganza.', 4, 5, 6, 'Music', 'Music Festival', 7, '2023-07-09'),
    (3, 300, 'A thrilling sports competition with top athletes.', 6, 7, 8, 'Food', 'Food Fair', 9, '2023-08-10'),
    (4, 300, 'A grand chess championship.', 8, 1, 2, 'Sport', 'Chess Championship', 3, '2023-09-11'),
    (5, 300, 'A thrilling sports competition with top athletes.', 10, 2, 3, 'Music', 'Rock Concert', 4, '2023-10-12'),
    (6, 300, 'A lively dance festival.', 2, 4, 5, 'Dance', 'Dance Festival', 6, '2023-11-13'),
    (7, 300, 'A thrilling sports competition with top athletes.', 4, 6, 7, 'Art', 'Art Fair', 8, '2023-12-14'),
    (8, 300, 'A carnival with fun rides and games.', 6, 8, 9, 'Carnival', 'Fun Carnival', 1, '2024-01-15'),
    (9, 300, 'A thrilling sports competition with top athletes.', 8, 1, 2, 'Conference', 'Tech Conference', 2, '2024-02-16'),
    (10, 300, 'A gathering of book lovers.', 10, 3, 4, 'Book', 'Book Fair', 3, '2024-03-17'),
    (11, 300, 'A thrilling sports competition with top athletes.', 2, 5, 6, 'Sport', 'Rugby Match', 4, '2024-04-18'),
    (12, 300, 'A food tasting event with various cuisines.', 4, 7, 8, 'Food', 'Food Tasting', 5, '2024-05-19'),
    (13, 300, 'A thrilling sports competition with top athletes.', 6, 9, 10, 'Music', 'Classical Concert', 6, '2024-06-20'),
    (14, 300, 'A comedy show featuring top comedians.', 8, 2, 3, 'Comedy', 'Comedy Show', 7, '2024-07-21'),
    (15, 300, 'A thrilling sports competition with top athletes.', 10, 4, 5, 'Conference', 'Health Conference', 8, '2024-08-22'),
    (16, 300, 'A meet-up for dog lovers.', 2, 6, 7, 'Meetup', 'Dog Lovers Meetup', 9, '2024-09-23'),
    (17, 300, 'A thrilling sports competition with top athletes.', 4, 8, 9, 'Sport', 'Soccer Match', 10, '2024-10-24'),
    (18, 300, 'A thrilling literary discussion.', 6, 1, 2, 'Literature', 'Literary Discussion', 1, '2024-11-25'),
    (19, 300, 'A thrilling sports competition with top athletes.', 8, 3, 4, 'Sport', 'Basketball Match', 2, '2024-12-26'),
    (20, 300, 'An exclusive art exhibition.', 10, 5, 6, 'Art', 'Art Exhibition', 3, '2025-01-27');

CREATE TABLE IF NOT EXISTS eventschedule
(
    eventscheduleid SERIAL,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    description character varying(255),
    constraint eventschedule_pkey PRIMARY KEY (eventscheduleid)
);

ALTER TABLE IF EXISTS eventschedule
    OWNER to postgres;

    -- Insert data records
INSERT INTO eventschedule (eventscheduleid, starttime, endtime, description)
VALUES
  (1, '2023-06-02 18:00:00', '2023-06-02 22:00:00', 'Birthday Party'),
  (2, '2023-07-04 20:00:00', '2023-07-04 23:00:00', 'Music Concert'),
  (3, '2023-08-06 19:00:00', '2023-08-06 21:00:00', 'Book Club Meeting'),
  (4, '2023-09-08 10:00:00', '2023-09-08 18:00:00', 'Technology Conference'),
  (5, '2023-10-10 19:30:00', '2023-10-10 21:30:00', 'Stand-up Comedy Show'),
  (6, '2023-11-12 11:00:00', '2023-11-12 15:00:00', 'Food Festival'),
  (7, '2023-12-14 20:00:00', '2023-12-14 22:00:00', 'Magic Show'),
  (8, '2024-01-16 09:00:00', '2024-01-16 17:00:00', 'Business Workshop'),
  (9, '2024-02-18 20:00:00', '2024-02-18 23:00:00', 'Theatre Play'),
  (10, '2024-03-20 08:00:00', '2024-03-20 16:00:00', 'Photography Exhibition');

CREATE TABLE IF NOT EXISTS eventtype
(
    eventtypeid SERIAL,
    typename character varying(255),
    constraint eventtype_pkey PRIMARY KEY (eventtypeid)
);

ALTER TABLE IF EXISTS eventtype
    OWNER to postgres;

-- Insert data records
INSERT INTO eventtype (eventtypeid, typename)
VALUES
  (1, 'Concert'),
  (2, 'Party'),
  (3, 'Meeting'),
  (4, 'Conference'),
  (5, 'Comedy Show'),
  (6, 'Festival'),
  (7, 'Show'),
  (8, 'Workshop'),
  (9, 'Play'),
  (10, 'Exhibition');
  (11, 'Match');
  (12, 'Meetup');
  (13, 'Festival');
  (14, 'Fair');
  (15, 'Carnival');
  (16, 'Match');
  (17, 'Discussion');
  (18, 'Match');
  (19, 'Exhibition');
  (20, 'Party');

CREATE TABLE IF NOT EXISTS locations
(
    locationid SERIAL,
    address character varying(255),
    latitude double precision,
    longitude double precision,
    constraint locations_pkey PRIMARY KEY (locationid)
);

ALTER TABLE IF EXISTS locations
    OWNER to postgres;

-- Insert data records
INSERT INTO locations (locationid, address, latitude, longitude)
VALUES 
  (1, 'Schönbrunn Palace, Schönbrunner Schloßstraße 47, 1130 Vienna, Austria', 48.1847, 16.3122),
  (2, 'Hofburg Palace, Michaelerkuppel, 1010 Vienna, Austria', 48.2057, 16.3635),
  (3, 'St. Stephen''s Cathedral, Stephansplatz 3, 1010 Vienna, Austria', 48.2083, 16.3731),
  (4, 'Vienna State Opera, Opernring 2, 1010 Vienna, Austria', 48.2033, 16.3687),
  (5, 'Tiergarten Schönbrunn, Maxingstraße 13b, 1130 Vienna, Austria', 48.1827, 16.3145),
  (6, 'Kunsthistorisches Museum Wien, Maria-Theresien-Platz, 1010 Vienna, Austria', 48.2035, 16.3612),
  (7, 'Natural History Museum, Burgring 7, 1010 Vienna, Austria', 48.2054, 16.3609),
  (8, 'Albertina, Albertinaplatz 1, 1010 Vienna, Austria', 48.2042, 16.3685),
  (9, 'Rathaus, Friedrich-Schmidt-Platz 1, 1010 Vienna, Austria', 48.2100, 16.3575),
  (10, 'Hundertwasserhaus, Kegelgasse 36-38, 1030 Vienna, Austria', 48.2014, 16.3942),
  (11, 'Kahlenberg, 1190 Vienna, Austria', 48.2794, 16.3267),
  (12, 'Danube Tower, Donauturmstraße 4, 1220 Vienna, Austria', 48.2450, 16.4200),
  (13, 'Schönbrunn Zoo, Maxingstraße 13b, 1130 Vienna, Austria', 48.1827, 16.3145),
  (14, 'St. Charles''s Church, Karlsplatz 1, 1010 Vienna, Austria', 48.1981, 16.3717),
  (15, 'Hundertwasser Village, Kegelgasse 37-39, 1030 Vienna, Austria', 48.2014, 16.3942),
  (16, 'Prater, 1020 Vienna, Austria', 48.2167, 16.3956),
  (17, 'Belvedere Palace, Prinz Eugen-Straße 27, 1030 Vienna, Austria', 48.1915, 16.3805),
  (18, 'Museum of Art History, Maria-Theresien-Platz 1, 1010 Vienna, Austria', 48.2035, 16.3612),
  (19, 'Stadtpark, Parkring 1, 1010 Vienna, Austria', 48.2025, 16.3793),
  (20, 'Naschmarkt, 1060 Vienna, Austria', 48.2001, 16.3642);


ALTER TABLE event
ADD CONSTRAINT fk_event_schedule
FOREIGN KEY (scheduleid)
REFERENCES eventschedule (eventscheduleid)
ON UPDATE NO ACTION
ON DELETE NO ACTION;

