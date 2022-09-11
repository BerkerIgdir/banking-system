CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


insert into public.countries(id, code,name,time_zone,currency)
values ('c1df7d01-4bd7-40b6-86da-7e2ffabf37f7', 'TUR','TURKEY','TRT','TRY');
insert into public.countries(id, code,name,time_zone,currency)
values ('f2b2d644-3a08-4acb-ae07-20569f6f2a01', 'GER','GERMANY','CEST','EUR');


INSERT INTO public.accounts(
	id, IBAN, name, surname,country_code,balance)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb41', 'TR320010009999901234567890', 'Turkish', 'User','TR','TUR',100);
INSERT INTO public.accounts(
    id, IBAN, name, surname,country_code,balance)
	VALUES ('d4bf5e29-e619-4b3a-bea9-136f9538126c', 'TR320010009999901234567891', 'Turkish', 'User2','TR','TUR',75);
INSERT INTO public.accounts(
    id, IBAN, name, surname,country_code,balance)
	VALUES ('38a460d6-4f8a-4cb5-8284-5c833ca278fd', 'DE75512108001245126199', 'German', 'User','DE','GER',799);