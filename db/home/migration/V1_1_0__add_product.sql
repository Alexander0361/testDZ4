CREATE TABLE products
(product_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
 menu_name VARCHAR(80) NOT NULL,
 price FLOAT NOT NULL);

INSERT INTO products(menu_name, price) VALUES
('GOJIRA ROLL', 300),
('VIVA LAS VEGAS ROLL',450),
('FUTOMAKI', 700),
('TOOTSY MAKI', 133),
('ZONIE ROLL', 254),
('NUTTY GRILLED SALAD', 1200),
('SASHIMI SALAD', 1114),
('SUNNY TEA', 456),
('COFFEE', 79),
('MINERAL WATER', 50);

