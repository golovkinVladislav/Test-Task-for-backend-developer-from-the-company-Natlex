CREATE TABLE sections (
id serial NOT NULL,
name text NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE geologicalclasses (
id serial NOT NULL,
name text NOT NULL,
code text NOT NULL,
section_id int NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (section_id) REFERENCES sections(id));

CREATE TABLE jobs (
id serial NOT NULL,
job_type text NOT NULL,
job_result text NOT NULL,
date_time_create timestamp,
PRIMARY KEY (id)
);