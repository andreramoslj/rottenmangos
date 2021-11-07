DROP TABLE IF EXISTS RASPBERRY_INDICATION;
DROP TABLE IF EXISTS RASPBERRY_FILE;

CREATE TABLE RASPBERRY_FILE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  status char(15) NOT NULL,
  records_count integer NOT NULL,
  note varchar(2000) not null,
  created_at timestamp not null
);

CREATE TABLE RASPBERRY_INDICATION (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  id_raspberry_file integer NOT NULL,
  year INT not null,
  title varchar(250) not null,
  studios varchar(250) not null,
  producers varchar(250) not null,
  winner CHAR(10)
);