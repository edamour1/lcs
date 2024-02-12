USE lawncare_service;
SELECT CONCAT('DROP TABLE IF EXISTS ', table_name, ';') 
FROM information_schema.tables 
WHERE table_schema = 'lawncare_service';