USE lawncare_service;

-- ******************************************States****************************************************
INSERT INTO STATES (state) VALUES
                               ('AL'), -- Alabama
                               ('AK'), -- Alaska
                               ('AZ'), -- Arizona
                               ('AR'), -- Arkansas
                               ('CA'), -- California
                               ('CO'), -- Colorado
                               ('CT'), -- Connecticut
                               ('DE'), -- Delaware
                               ('FL'), -- Florida
                               ('GA'), -- Georgia
                               ('HI'), -- Hawaii
                               ('ID'), -- Idaho
                               ('IL'), -- Illinois
                               ('IN'), -- Indiana
                               ('IA'), -- Iowa
                               ('KS'), -- Kansas
                               ('KY'), -- Kentucky
                               ('LA'), -- Louisiana
                               ('ME'), -- Maine
                               ('MD'), -- Maryland
                               ('MA'), -- Massachusetts
                               ('MI'), -- Michigan
                               ('MN'), -- Minnesota
                               ('MS'), -- Mississippi
                               ('MO'), -- Missouri
                               ('MT'), -- Montana
                               ('NE'), -- Nebraska
                               ('NV'), -- Nevada
                               ('NH'), -- New Hampshire
                               ('NJ'), -- New Jersey
                               ('NM'), -- New Mexico
                               ('NY'), -- New York
                               ('NC'), -- North Carolina
                               ('ND'), -- North Dakota
                               ('OH'), -- Ohio
                               ('OK'), -- Oklahoma
                               ('OR'), -- Oregon
                               ('PA'), -- Pennsylvania
                               ('RI'), -- Rhode Island
                               ('SC'), -- South Carolina
                               ('SD'), -- South Dakota
                               ('TN'), -- Tennessee
                               ('TX'), -- Texas
                               ('UT'), -- Utah
                               ('VT'), -- Vermont
                               ('VA'), -- Virginia
                               ('WA'), -- Washington
                               ('WV'), -- West Virginia
                               ('WI'), -- Wisconsin
                               ('WY'); -- Wyoming

-- *********************************************Cities*********************************************
INSERT INTO `CITIES` (`city`) VALUES
                                  ('Atlanta'),
                                  ('Augusta'),
                                  ('Columbus'),
                                  ('Conyers'),
                                  ('Covington'),
                                  ('Savannah'),
                                  ('Athens'),
                                  ('Macon'),
                                  ('Roswell'),
                                  ('Albany'),
                                  ('Johns Creek'),
                                  ('Warner Robins'),
                                  ('Alpharetta'),
                                  ('Marietta'),
                                  ('Valdosta'),
                                  ('Smyrna'),
                                  ('Dunwoody'),
                                  ('Rome'),
                                  ('East Point'),
                                  ('Peachtree City'),
                                  ('Gainesville'),
                                  ('Hinesville'),
                                  ('Newnan'),
                                  ('Milton'),
                                  ('Douglasville'),
                                  ('Kennesaw'),
                                  ('LaGrange'),
                                  ('Lawrenceville'),
                                  ('Statesboro'),
                                  ('Tucker'),
                                  ('Duluth'),
                                  ('Stockbridge'),
                                  ('Carrollton'),
                                  ('Woodstock'),
                                  ('Griffin'),
                                  ('Canton'),
                                  ('McDonough'),
                                  ('Acworth'),
                                  ('Cartersville'),
                                  ('Union City'),
                                  ('Decatur'),
                                  ('Pooler'),
                                  ('Sugar Hill'),
                                  ('Forest Park'),
                                  ('Thomasville'),
                                  ('Snellville'),
                                  ('Milledgeville'),
                                  ('St. Marys'),
                                  ('Americus'),
                                  ('Tifton'),
                                  ('Dublin'),
                                  ('Kingsland');


-- *********************************************Treatments*********************************************
INSERT INTO TREATMENTS (treatment_name, treatment_description, price)
VALUES
    ('Treatment # 1', 'PRE-M #1/Spring', 0.00),
    ('Treatment # 2', 'Pre-M #2/Spring', 0.00),
    ('Treatment # 3', 'Urea Post-M', 0.00),
    ('Treatment # 4', 'Fertilizer #1/Post-M', 0.00),
    ('Treatment # 5', 'Fertilizer #2/Post-M', 0.00),
    ('Treatment # 6', 'Pre-M/Fall', 0.00),
    ('Treatment # 7', 'Pre-M #2/Fall', 0.00),
    ('Treatment # 8', 'Lime/Potassium', 0.00);




INSERT INTO ADDITIONAL_SERVICES (treatment_name, treatment_description, price)
VALUES
    ('Aeration', '', 0.00),
    ('Top dressing', '', 0.00),
    ('Soil test', '', 0.00),
    ('Fungicide', '', 0.00),
    ('Insect control', '', 0.00),
    ('Shrub fertilization', '', 0.00),
    ('Shrub fungicide', '', 0.00),
    ('Dethaching', '', 0.00);


-- *********************************************Zipcodes*********************************************
INSERT INTO zipcodes (zipcode, city_id) VALUES (30301, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30302, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30303, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30304, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30305, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30306, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30307, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30308, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30309, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30310, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30311, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30312, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30313, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30314, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30315, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30316, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30317, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30318, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30319, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30321, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30322, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30324, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30325, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30326, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30327, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30329, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30331, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30332, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30334, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30335, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30336, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30342, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30343, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30344, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30348, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30353, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30354, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30355, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30357, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30361, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30363, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30368, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30369, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30370, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30371, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30374, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30375, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30377, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30380, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30384, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30385, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30388, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30392, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30394, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30396, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30398, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31106, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31107, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31119, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31126, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31131, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31193, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31195, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (31196, 1); -- Atlanta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30805, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30808, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30812, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30813, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30814, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30815, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30818, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30830, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30901, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30902, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30903, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30904, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30905, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30906, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30907, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30908, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30909, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30910, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30911, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30912, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30913, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30914, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30915, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30916, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30917, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30918, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30919, 2); -- Augusta
INSERT INTO zipcodes (zipcode, city_id) VALUES (30999, 2); -- Augusta


INSERT INTO units (unit) VALUES
                             ('Feet'),       -- ft
                             ('Inches'),     -- in
                             ('Kilometer'),  -- km
                             ('Meter'),      -- m
                             ('Centimeter'), -- cm
                             ('Millimeter'), -- mm
                             ('Kilogram'),   -- kg
                             ('Gram'),       -- g
                             ('Milligram'),  -- mg
                             ('Kilolitre'),  -- kl
                             ('Litre'),      -- l
                             ('Milliliter'), -- ml
                             ('Centilitre'), -- cl
                             ('Fluid Ounce'),-- fl oz
                             ('Minute'),     -- min
                             ('Hour'),       -- hr
                             ('Second'),     -- sec
                             ('Days'),       -- days
                             ('Week'),       -- week
                             ('Month'),      -- month
                             ('Year'),       -- year
                             ('Kelvin'),     -- K
                             ('Celsius'),    -- °C
                             ('Fahrenheit'), -- °F
                             ('Square Feet'); -- sqft

INSERT INTO `lawncare_service`.`admin`
(`username`,
 `password`,
 `role`,
 `hint`)
VALUES
    ('admin',
     'password',
     'p@ssword',
     'master admin');

commit;
