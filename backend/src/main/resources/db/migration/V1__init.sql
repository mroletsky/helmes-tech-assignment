CREATE EXTENSION IF NOT EXISTS ltree;

CREATE TABLE sector (
                        id SERIAL PRIMARY KEY,
                        name TEXT NOT NULL,
                        path LTREE NOT NULL UNIQUE,
                        code TEXT,
                        description TEXT,
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE app_user (
                          id SERIAL PRIMARY KEY,
                          username TEXT UNIQUE NOT NULL,
                          agree_to_terms BOOLEAN
);

CREATE TABLE user_sector_selection (
                                       user_id INT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                                       sector_id INT NOT NULL REFERENCES sector(id) ON DELETE CASCADE,
                                       selected_at TIMESTAMPTZ DEFAULT now(),
                                       PRIMARY KEY (user_id, sector_id)
);

CREATE INDEX idx_sector_path_gist ON sector USING GIST (path);
CREATE INDEX idx_sector_path_btree ON sector USING BTREE (path);

-- sample data
INSERT INTO sector (name, path, code) VALUES
                                          ('Manufacturing','manufacturing'::ltree,'MANUF'),
                                          ('Machinery','manufacturing.machinery'::ltree,'MANUF-MACH'),
                                          ('Metalworking','manufacturing.machinery.metalworking'::ltree,'MANUF-MACH-META'),
                                          ('Construction materials','manufacturing.construction_materials'::ltree,'MANUF-CM'),
                                          ('Service','service'::ltree,'SERV'),
                                          ('Other','other'::ltree,'OTHER');
