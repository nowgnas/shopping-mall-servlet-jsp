DELIMITER //
CREATE PROCEDURE simple_loop()
BEGIN
    DECLARE counter INT DEFAULT 0;

    WHILE counter < 1000
        DO
        -- Your loop logic here
        -- You can use SELECT, INSERT, UPDATE, DELETE, etc.
        -- Example: SELECT counter;
            INSERT INTO product (category_id, name, description, price, quantity, code)
            VALUES (2,
                    LEFT(CONCAT('item', FLOOR(RAND() * 20)), 20), -- Truncate to 20 characters
                    CONCAT('item description', FLOOR(RAND() * 100)),
                    FLOOR(RAND() * 100000),
                    FLOOR(RAND() * 1000),
                    CONCAT('PRODUCT-', FLOOR(RAND() * 100000))); -- Removed the '20'

            SET counter = counter + 1;
        END WHILE;
END //
DELIMITER ;