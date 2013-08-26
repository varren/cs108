USE c_cs108_psyoung;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<
  
DROP TABLE IF EXISTS metropolises;
 -- remove table if it already exists and start from scratch

CREATE TABLE metropolises (
    metropolis CHAR(64),
    continent CHAR(64),
    population BIGINT
);

INSERT INTO metropolises VALUES
	("Mumbai","Asia",20400000),
    ("New York","North America",21295000),
	("San Francisco","North America",5780000),
	("London","Europe",8580000),
	("Rome","Europe",2715000),
	("Melbourne","Australia",3900000),
	("San Jose","North America",7354555),
	("Rostov-on-Don","Europe",1052000);
