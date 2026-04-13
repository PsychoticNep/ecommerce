INSERT INTO products (name, description, price, category, stock_quantity, image_url, status, created_at, updated_at)
VALUES
  ('iPhone 15 Pro', 'Latest Apple smartphone with titanium build', 999.99, 'Electronics', 50, 'https://example.com/iphone15.jpg', 'ACTIVE', NOW(), NOW()),
  ('Samsung 4K TV 55"', '55-inch 4K Ultra HD Smart TV', 799.00, 'Electronics', 20, 'https://example.com/samsung-tv.jpg', 'ACTIVE', NOW(), NOW()),
  ('Nike Air Max 270', 'Comfortable running shoes with air cushioning', 149.99, 'Footwear', 100, 'https://example.com/nikeairmax.jpg', 'ACTIVE', NOW(), NOW()),
  ('Levi''s 501 Jeans', 'Classic straight fit denim jeans', 59.99, 'Clothing', 200, 'https://example.com/levis501.jpg', 'ACTIVE', NOW(), NOW()),
  ('The Pragmatic Programmer', 'Must-read book for software developers', 39.99, 'Books', 75, 'https://example.com/pragprog.jpg', 'ACTIVE', NOW(), NOW()),
  ('Sony WH-1000XM5 Headphones', 'Industry-leading noise canceling headphones', 349.99, 'Electronics', 3, 'ACTIVE', 'https://example.com/sony-headphones.jpg', NOW(), NOW()),
  ('Yoga Mat Pro', 'Non-slip premium yoga mat, 6mm thick', 29.99, 'Sports', 0, 'https://example.com/yogamat.jpg', 'OUT_OF_STOCK', NOW(), NOW()),
  ('Mechanical Keyboard', 'RGB backlit mechanical keyboard with Cherry MX switches', 129.99, 'Electronics', 45, 'https://example.com/mech-keyboard.jpg', 'ACTIVE', NOW(), NOW());
