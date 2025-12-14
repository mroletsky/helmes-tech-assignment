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

--- FORM DATA ---

-- Root Sectors
INSERT INTO sector (name, path) VALUES
                                    ('Manufacturing', 'manufacturing'),
                                    ('Other', 'other'),
                                    ('Service', 'service');

-- manufacturing/
INSERT INTO sector (name, path) VALUES
                                    ('Construction materials', 'manufacturing.construction_materials'),
                                    ('Electronics and Optics', 'manufacturing.electronics_and_optics'),
                                    ('Food and Beverage', 'manufacturing.food_and_beverage'),
                                    ('Furniture', 'manufacturing.furniture'),
                                    ('Machinery', 'manufacturing.machinery'),
                                    ('Metalworking', 'manufacturing.metalworking'),
                                    ('Plastic and Rubber', 'manufacturing.plastic_and_rubber'),
                                    ('Printing', 'manufacturing.printing'),
                                    ('Textile and Clothing', 'manufacturing.textile_and_clothing'),
                                    ('Wood', 'manufacturing.wood');

-- manufacturing/food_and_beverage/
INSERT INTO sector (name, path) VALUES
                                    ('Bakery & confectionery products', 'manufacturing.food_and_beverage.bakery_and_confectionery_products'),
                                    ('Beverages', 'manufacturing.food_and_beverage.beverages'),
                                    ('Fish & fish products', 'manufacturing.food_and_beverage.fish_and_fish_products'),
                                    ('Meat & meat products', 'manufacturing.food_and_beverage.meat_and_meat_products'),
                                    ('Milk & dairy products', 'manufacturing.food_and_beverage.milk_and_dairy_products'),
                                    ('Other', 'manufacturing.food_and_beverage.other'),
                                    ('Sweets & snack food', 'manufacturing.food_and_beverage.sweets_and_snack_food');

-- manufacturing/furniture/
INSERT INTO sector (name, path) VALUES
                                    ('Bathroom/sauna', 'manufacturing.furniture.bathroom_sauna'),
                                    ('Bedroom', 'manufacturing.furniture.bedroom'),
                                    ('Children’s room', 'manufacturing.furniture.childrens_room'),
                                    ('Kitchen', 'manufacturing.furniture.kitchen'),
                                    ('Living room', 'manufacturing.furniture.living_room'),
                                    ('Office', 'manufacturing.furniture.office'),
                                    ('Other (Furniture)', 'manufacturing.furniture.other'),
                                    ('Outdoor', 'manufacturing.furniture.outdoor'),
                                    ('Project furniture', 'manufacturing.furniture.project_furniture');

-- manufacturing/machinery/
INSERT INTO sector (name, path) VALUES
                                    ('Machinery components', 'manufacturing.machinery.components'),
                                    ('Machinery equipment/tools', 'manufacturing.machinery.equipment_tools'),
                                    ('Manufacture of machinery', 'manufacturing.machinery.manufacture'),
                                    ('Maritime', 'manufacturing.machinery.maritime'),
                                    ('Metal structures', 'manufacturing.machinery.metal_structures'),
                                    ('Other', 'manufacturing.machinery.other'),
                                    ('Repair and maintenance service', 'manufacturing.machinery.repair_and_maintenance');


-- manufacturing/machinery/maritime/
INSERT INTO sector (name, path) VALUES
                                    ('Aluminium and steel workboats', 'manufacturing.machinery.maritime.aluminium_and_steel_workboats'),
                                    ('Boat/Yacht building', 'manufacturing.machinery.maritime.boat_and_yacht_building'),
                                    ('Ship repair and conversion', 'manufacturing.machinery.maritime.ship_repair_and_conversion');

-- manufacturing/metalworking/
INSERT INTO sector (name, path) VALUES
                                    ('Construction of metal structures', 'manufacturing.metalworking.construction_of_metal_structures'),
                                    ('Houses and buildings', 'manufacturing.metalworking.houses_and_buildings'),
                                    ('Metal products', 'manufacturing.metalworking.metal_products'),
                                    ('Metal works', 'manufacturing.metalworking.metal_works');

-- manufacturing/metalworking/metal_works/
INSERT INTO sector (name, path) VALUES
                                    ('CNC-machining', 'manufacturing.metalworking.metal_works.cnc_machining'),
                                    ('Forgings, Fasteners', 'manufacturing.metalworking.metal_works.forgings_and_fasteners'),
                                    ('Gas, Plasma, Laser cutting', 'manufacturing.metalworking.metal_works.cutting'),
                                    ('MIG, TIG, Aluminum welding', 'manufacturing.metalworking.metal_works.welding');

-- manufacturing/plastic_and_rubber/
INSERT INTO sector (name, path) VALUES
                                    ('Packaging', 'manufacturing.plastic_and_rubber.packaging'),
                                    ('Plastic goods', 'manufacturing.plastic_and_rubber.plastic_goods'),
                                    ('Plastic processing technology', 'manufacturing.plastic_and_rubber.processing_technology'),
                                    ('Plastic profiles', 'manufacturing.plastic_and_rubber.plastic_profiles');

-- manufacturing/plastic_and_rubber/processing_technology/
INSERT INTO sector (name, path) VALUES
                                    ('Blowing', 'manufacturing.plastic_and_rubber.processing_technology.blowing'),
                                    ('Moulding', 'manufacturing.plastic_and_rubber.processing_technology.moulding'),
                                    ('Plastics welding and processing', 'manufacturing.plastic_and_rubber.processing_technology.welding_and_processing');

-- manufacturing/printing/
INSERT INTO sector (name, path) VALUES
                                    ('Advertising', 'manufacturing.printing.advertising'),
                                    ('Book/Periodicals printing', 'manufacturing.printing.book_and_periodicals'),
                                    ('Labelling and packaging printing', 'manufacturing.printing.labelling_and_packaging');

-- manufacturing/textile_and_clothing/
INSERT INTO sector (name, path) VALUES
                                    ('Clothing', 'manufacturing.textile_and_clothing.clothing'),
                                    ('Textile', 'manufacturing.textile_and_clothing.textile');

-- manufacturing/wood/
INSERT INTO sector (name, path) VALUES
                                    ('Other (Wood)', 'manufacturing.wood.other'),
                                    ('Wooden building materials', 'manufacturing.wood.building_materials'),
                                    ('Wooden houses', 'manufacturing.wood.houses');

-- other/
INSERT INTO sector (name, path) VALUES
                                    ('Creative industries', 'other.creative_industries'),
                                    ('Energy technology', 'other.energy_technology'),
                                    ('Environment', 'other.environment');

-- service/
INSERT INTO sector (name, path) VALUES
                                    ('Business services', 'service.business_services'),
                                    ('Engineering', 'service.engineering'),
                                    ('Information Technology and Telecommunications', 'service.it_and_telecommunications'),
                                    ('Tourism', 'service.tourism'),
                                    ('Translation services', 'service.translation_services'),
                                    ('Transport and Logistics', 'service.transport_and_logistics');

-- service/it_and_telecommunications/
INSERT INTO sector (name, path) VALUES
                                    ('Data processing, Web portals, E-marketing', 'service.it_and_telecommunications.data_processing'),
                                    ('Programming, Consultancy', 'service.it_and_telecommunications.programming_and_consultancy'),
                                    ('Software, Hardware', 'service.it_and_telecommunications.software_and_hardware'),
                                    ('Telecommunications', 'service.it_and_telecommunications.telecommunications');

-- service/transport_and_logistics/
INSERT INTO sector (name, path) VALUES
                                    ('Air', 'service.transport_and_logistics.air'),
                                    ('Rail', 'service.transport_and_logistics.rail'),
                                    ('Road', 'service.transport_and_logistics.road'),
                                    ('Water', 'service.transport_and_logistics.water');
