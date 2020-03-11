select client_id,title,name,surname,(case when transactional = 'false' then 'Loan Balances' else 'Transactional Account Balance' end) as BALANCE_TYPE, TOTAL_BALANCE
from (
        select a.client_id,a.title,a.name,a.surname, transactional, sum(b.display_balance) as TOTAL_BALANCE
            from client a, client_account b, account_type c
            where a.client_id = 1
            and a.client_id=b.client_id
            and b.account_type_code=c.account_type_code
            and b.account_type_code <> 'CFCA'
            group by a.client_id,a.title,a.name,a.surname,transactional
    )  q1
