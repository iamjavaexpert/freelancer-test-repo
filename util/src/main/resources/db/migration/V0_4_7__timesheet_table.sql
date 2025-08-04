CREATE TABLE IF NOT EXISTS employee (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS employee_project (
    employee_id BIGINT REFERENCES employee(id),
    project_id BIGINT REFERENCES project(id),
    PRIMARY KEY (employee_id, project_id)
);

CREATE TABLE IF NOT EXISTS time_entry (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT REFERENCES employee(id),
    project_id BIGINT REFERENCES project(id),
    activity VARCHAR(100),
    comment VARCHAR(100),
    entry_date DATE,
    hours_worked DOUBLE PRECISION
);
