DELETE FROM excels_imported;

UPDATE contract SET amount_remains = amount_total, periodic_payment = 0, status = NULL;