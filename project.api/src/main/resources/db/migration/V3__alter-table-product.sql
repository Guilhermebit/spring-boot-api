ALTER TABLE db_api.sch_application.product ADD active BOOLEAN;
UPDATE db_api.sch_application.product SET active = true;
