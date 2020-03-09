select c.client_id,b.surname,d.description,c.display_balance from (SELECT c.client_id, surname,  max(display_balance) as balance
FROM client_account ca
LEFT JOIN client c ON ca.client_id = c.client_id
where ca.currency_code = 'ZAR'
and ca.display_balance > 0
GROUP BY c.client_id,surname
ORDER BY c.client_id) a, client b, client_account c,account_type d
where a.client_id=b.client_id
and b.client_id = c.client_id
and a.balance = c.display_balance
and c.account_type_code=d.account_type_code
order by c.display_balance desc;
