CREATE TABLE db_api.sch_application.tb_user (
     id TEXT PRIMARY KEY UNIQUE NOT NULL,
     login VARCHAR(100) NOT NULL UNIQUE,
     password VARCHAR(100) NOT NULL,
     role VARCHAR(5) NOT NULL
)