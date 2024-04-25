CREATE USER 'wlcs'@'localhost'
IDENTIFIED BY 'wlcs';

CREATE DATABASE lawncare_service;

GRANT CREATE, ALTER, DROP
ON lawncare_service.*
TO 'wlcs'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE
ON lawncare_service.* TO 'wlcs'@'localhost';
