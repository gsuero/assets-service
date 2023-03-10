-- Creation of assets table
CREATE TABLE IF NOT EXISTS assets (
    id INT NOT NULL,
    name varchar(250) NOT NULL,
    parent INT,
    promoted BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
