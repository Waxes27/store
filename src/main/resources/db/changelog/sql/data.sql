CREATE OR REPLACE PROCEDURE generate_sample_data()
LANGUAGE plpgsql
AS '
DECLARE
    i INT := 1;
    customer_count INT;
    order_count INT;
    min_customer_id INT;
    max_customer_id INT;
    adj1 VARCHAR(50);
    adj2 VARCHAR(50);
    noun VARCHAR(50);
    name_part VARCHAR(50);
    surname VARCHAR(50);
    suffix VARCHAR(10);
    full_name VARCHAR(100);
    description_text VARCHAR(100);
    random_customer_id INT;

    first_names TEXT := ''James,Emma,William,Olivia,Noah,Ava,Liam,Sophia,Benjamin,Isabella,Lucas,Mia,Mason,Charlotte,Elijah,Amelia,Oliver,Evelyn,Jacob,Abigail,Michael,Harper,Alexander,Emily,Ethan,Elizabeth,Daniel,Sofia,Matthew,Ella'';
    last_names TEXT := ''Smith,Johnson,Williams,Brown,Jones,Garcia,Miller,Davis,Rodriguez,Martinez,Hernandez,Lopez,Gonzalez,Wilson,Anderson,Thomas,Taylor,Moore,Jackson,Martin,Lee,Perez,Thompson,White,Harris,Sanchez,Clark,Ramirez,Lewis,Robinson'';
    suffixes TEXT := ''Jr.,Sr.,II,III,PhD,MD,Esq.'';
    adjectives TEXT := ''Handcrafted,Artisanal,Premium,Luxury,Vintage,Modern,Classic,Elegant,Rustic,Minimalist,Innovative,Sustainable,Eco-friendly,Smart,Digital,Wireless,Portable,Compact,Ergonomic,Professional'';
    nouns TEXT := ''Chair,Table,Lamp,Watch,Book,Bag,Shoes,Glasses,Phone,Laptop,Camera,Speaker,Headphones,Keyboard,Mouse,Pen,Notebook,Wallet,Backpack,Jacket'';
BEGIN
    SELECT COUNT(*) INTO customer_count FROM customer;
    SELECT COUNT(*) INTO order_count FROM store_order;
    SELECT MIN(id), MAX(id) INTO min_customer_id, max_customer_id FROM customer;

    IF min_customer_id IS NULL THEN
        min_customer_id := 1;
        max_customer_id := 100;
        customer_count := 0;
    END IF;

    IF order_count IS NULL THEN
        order_count := 0;
    END IF;

    WHILE i <= 100 LOOP
        name_part := TRIM(SPLIT_PART(first_names, '','', FLOOR(1 + RANDOM() * 30)::INT));
        surname := TRIM(SPLIT_PART(last_names, '','', FLOOR(1 + RANDOM() * 30)::INT));

        IF RANDOM() < 0.2 THEN
            suffix := TRIM(SPLIT_PART(suffixes, '','', FLOOR(1 + RANDOM() * 7)::INT));
            full_name := name_part || '' '' || surname || '' '' || suffix;
        ELSE
            full_name := name_part || '' '' || surname;
        END IF;

        INSERT INTO customer (id, name) VALUES (customer_count + i, full_name);
        i := i + 1;
    END LOOP;

    i := 1;
    SELECT MIN(id), MAX(id) INTO min_customer_id, max_customer_id FROM customer;

    WHILE i <= 1000 LOOP
        adj1 := TRIM(SPLIT_PART(adjectives, '','', FLOOR(1 + RANDOM() * 20)::INT));

        IF RANDOM() < 0.5 THEN
            adj2 := TRIM(SPLIT_PART(adjectives, '','', FLOOR(1 + RANDOM() * 20)::INT));
            WHILE adj2 = adj1 LOOP
                adj2 := TRIM(SPLIT_PART(adjectives, '','', FLOOR(1 + RANDOM() * 20)::INT));
            END LOOP;
            description_text := adj1 || '' '' || adj2 || '' '';
        ELSE
            description_text := adj1 || '' '';
        END IF;

        noun := TRIM(SPLIT_PART(nouns, '','', FLOOR(1 + RANDOM() * 20)::INT));
        description_text := description_text || noun;
        random_customer_id := FLOOR(min_customer_id + RANDOM() * (max_customer_id - min_customer_id + 1))::INT;

        INSERT INTO store_order (id, description, customer_id)
        VALUES (order_count + i, description_text, random_customer_id);
        i := i + 1;
    END LOOP;

    RAISE NOTICE ''100 customers and 100 orders generated successfully!'';
END;
';

CALL generate_sample_data();

---

CREATE OR REPLACE PROCEDURE generate_sample_products()
LANGUAGE plpgsql
AS '
DECLARE
    i INT := 1;
    product_count INT;
    order_count INT;
    adj1 VARCHAR(50);
    adj2 VARCHAR(50);
    noun VARCHAR(50);
    description_text VARCHAR(100);

    -- Arrays for product generation
    adjectives TEXT := ''Premium,Luxury,Handcrafted,Artisanal,Vintage,Modern,Classic,Elegant,Rustic,Minimalist,Innovative,Sustainable,Eco-friendly,Smart,Digital,Wireless,Portable,Compact,Ergonomic,Professional,Advanced,Deluxe,Ultimate,Superior,Exclusive'';
    nouns TEXT := ''Chair,Table,Lamp,Watch,Book,Bag,Shoes,Glasses,Phone,Laptop,Camera,Speaker,Headphones,Keyboard,Mouse,Pen,Notebook,Wallet,Backpack,Jacket,Desk,Sofa,Bed,Clock,Vase,Plant,Artwork,Mirror,Rug,Cushion'';
    materials TEXT := ''Wood,Leather,Steel,Aluminum,Glass,Ceramic,Plastic,Silk,Cotton,Wool,Marble,Granite,Bronze,Copper,Brass'';
    colors TEXT := ''Black,White,Red,Blue,Green,Brown,Gray,Silver,Gold,Beige,Navy,Teal,Maroon,Olive,Charcoal'';

    -- For many-to-many relationship
    order_product_count INT;
    random_order_id INT;
    random_product_id INT;
BEGIN
    -- Get current counts
    SELECT COUNT(*) INTO product_count FROM product;
    SELECT COUNT(*) INTO order_count FROM store_order;

    -- If no products exist, set default value
    IF product_count IS NULL THEN
        product_count := 0;
    END IF;

    -- Generate 100 products
    WHILE i <= 100 LOOP
        -- Random product description with variations
        CASE FLOOR(RANDOM() * 4)::INT
            WHEN 0 THEN
                -- Format: Adjective + Noun
                adj1 := TRIM(SPLIT_PART(adjectives, '','', FLOOR(1 + RANDOM() * 25)::INT));
                noun := TRIM(SPLIT_PART(nouns, '','', FLOOR(1 + RANDOM() * 30)::INT));
                description_text := adj1 || '' '' || noun;

            WHEN 1 THEN
                -- Format: Material + Noun
                adj1 := TRIM(SPLIT_PART(materials, '','', FLOOR(1 + RANDOM() * 15)::INT));
                noun := TRIM(SPLIT_PART(nouns, '','', FLOOR(1 + RANDOM() * 30)::INT));
                description_text := adj1 || '' '' || noun;

            WHEN 2 THEN
                -- Format: Color + Material + Noun
                adj1 := TRIM(SPLIT_PART(colors, '','', FLOOR(1 + RANDOM() * 15)::INT));
                adj2 := TRIM(SPLIT_PART(materials, '','', FLOOR(1 + RANDOM() * 15)::INT));
                noun := TRIM(SPLIT_PART(nouns, '','', FLOOR(1 + RANDOM() * 30)::INT));
                description_text := adj1 || '' '' || adj2 || '' '' || noun;

            ELSE
                -- Format: Adjective + Material + Noun
                adj1 := TRIM(SPLIT_PART(adjectives, '','', FLOOR(1 + RANDOM() * 25)::INT));
                adj2 := TRIM(SPLIT_PART(materials, '','', FLOOR(1 + RANDOM() * 15)::INT));
                noun := TRIM(SPLIT_PART(nouns, '','', FLOOR(1 + RANDOM() * 30)::INT));
                description_text := adj1 || '' '' || adj2 || '' '' || noun;
        END CASE;

        -- Insert product
        INSERT INTO product (id, description) VALUES (product_count + i, description_text);

        i := i + 1;
    END LOOP;

    -- Create many-to-many relationships between orders and products
    -- Assuming you have a join table named store_order_products with orders_id and products_id columns
    SELECT COUNT(*) INTO order_product_count FROM store_order_products;
    IF order_product_count IS NULL THEN
        order_product_count := 0;
    END IF;

    -- Generate 200-300 random relationships (2-3 products per order on average)
    i := 1;
    WHILE i <= 250 LOOP
        -- Get random order ID (assuming store_order table exists)
        SELECT id INTO random_order_id FROM store_order
        ORDER BY RANDOM()
        LIMIT 1;

        -- Get random product ID
        SELECT id INTO random_product_id FROM product
        ORDER BY RANDOM()
        LIMIT 1;

        -- Insert relationship if it doesn''t already exist
        INSERT INTO store_order_products (orders_id, products_id)
        SELECT random_order_id, random_product_id
        WHERE NOT EXISTS (
            SELECT 1 FROM store_order_products
            WHERE orders_id = random_order_id AND products_id = random_product_id
        );

        -- Only increment if we actually inserted a row
        IF FOUND THEN
            i := i + 1;
        END IF;
    END LOOP;

    RAISE NOTICE ''100 products generated and relationships created with orders!'';
END;
';

CALL generate_sample_products();

