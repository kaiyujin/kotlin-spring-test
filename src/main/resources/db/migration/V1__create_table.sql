CREATE TABLE sample
(
    id   int PRIMARY KEY,
    name text NOT NULL
);

create table child
(
    id         int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    parent_id int references sample (id),
    name       text NOT NULL
);
