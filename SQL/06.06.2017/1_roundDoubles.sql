UPDATE contract SET periodic_payment = ROUND(periodic_payment, 3), amount_remains = ROUND(amount_remains, 3);
UPDATE contract SET amount_remains = 0 WHERE amount_remains = -0;
UPDATE contract SET status = NULL;