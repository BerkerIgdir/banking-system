DROP TABLE IF EXISTS public.countries CASCADE;

CREATE TABLE public.countries
(
    id        uuid NOT NULL UNIQUE,
    code character varying COLLATE  pg_catalog."default" UNIQUE NOT NULL,
    version integer default 0,
    name      character varying COLLATE pg_catalog."default",
    time_zone character varying COLLATE pg_catalog."default",
    currency  character varying COLLATE pg_catalog."default",
    CONSTRAINT countries_pkey PRIMARY KEY (id)
) TABLESPACE pg_default;

ALTER TABLE public.countries
    OWNER to postgres;

DROP TABLE IF EXISTS public.accounts CASCADE;

CREATE TABLE public.accounts
(
    id         uuid NOT NULL UNIQUE ,
    version integer default 0,
    IBAN       character varying COLLATE pg_catalog."default" NOT NULL UNIQUE,
    name       character varying COLLATE pg_catalog."default",
    surname    character varying COLLATE pg_catalog."default",
    country_code character varying COLLATE pg_catalog."default" NOT NULL,
    balance numeric(12,4) default 0,
    CONSTRAINT country_fk FOREIGN KEY (country_code)
        REFERENCES public.countries (code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
) TABLESPACE pg_default;

ALTER TABLE public.accounts
    OWNER to postgres;

DROP TABLE IF EXISTS public.transactions CASCADE;

CREATE TABLE public.transactions
(
    id uuid NOT NULL UNIQUE,
    version integer default 0,
    to_account     character varying COLLATE pg_catalog."default" NOT NULL,
    from_account   character varying COLLATE pg_catalog."default" NOT NULL,
    amount NUMERIC(12, 4),
    trans_time TIMESTAMP,
    CONSTRAINT transactions_pk PRIMARY KEY (id),
    CONSTRAINT account_to_fk FOREIGN KEY (to_account)
        REFERENCES public.accounts (IBAN),
    CONSTRAINT account_from_fk FOREIGN KEY (from_account)
        REFERENCES public.accounts (IBAN)
) TABLESPACE pg_default;

ALTER TABLE public.transactions
    OWNER to postgres;

CREATE INDEX "fki_ACC_FK"
    ON public.accounts USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX fki_trans_fk
    ON public.transactions USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;
