--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2022-01-08 21:52:02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3065 (class 1262 OID 16395)
-- Name: chess; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE chess WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Polish_Poland.1250';


ALTER DATABASE chess OWNER TO postgres;

\connect chess

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 16570)
-- Name: comments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comments (
    comment_id bigint NOT NULL,
    content character varying NOT NULL,
    created_at date NOT NULL,
    author_email character varying NOT NULL,
    profile_id bigint NOT NULL
);


ALTER TABLE public.comments OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16626)
-- Name: friends; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.friends (
    friend_id bigint NOT NULL,
    user_first character varying NOT NULL,
    user_second character varying NOT NULL,
    status boolean NOT NULL,
    sender character varying
);


ALTER TABLE public.friends OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16648)
-- Name: games; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.games (
    game_code character varying NOT NULL,
    started boolean NOT NULL,
    host character varying NOT NULL,
    player character varying,
    accepted boolean NOT NULL,
    state character varying NOT NULL,
    ended boolean NOT NULL,
    winner character varying,
    current_turn character varying,
    tournament_id bigint,
    round_id bigint
);


ALTER TABLE public.games OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16578)
-- Name: profiles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profiles (
    id integer NOT NULL,
    user_email character varying NOT NULL
);


ALTER TABLE public.profiles OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16404)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16707)
-- Name: rounds; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rounds (
    round_id bigint NOT NULL,
    round_number integer NOT NULL,
    tournament_id bigint
);


ALTER TABLE public.rounds OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16668)
-- Name: tournaments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tournaments (
    tournament_id bigint NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    winner character varying,
    title character varying NOT NULL,
    max_players integer NOT NULL
);


ALTER TABLE public.tournaments OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16396)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    email character varying NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16412)
-- Name: users_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_roles (
    user_id character varying NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.users_roles OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16677)
-- Name: users_tournaments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_tournaments (
    user_id character varying NOT NULL,
    tournament_id bigint NOT NULL
);


ALTER TABLE public.users_tournaments OWNER TO postgres;

--
-- TOC entry 2900 (class 2606 OID 16577)
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (comment_id);


--
-- TOC entry 2907 (class 2606 OID 16646)
-- Name: friends friends_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friends
    ADD CONSTRAINT friends_pk PRIMARY KEY (friend_id);


--
-- TOC entry 2910 (class 2606 OID 16656)
-- Name: games games_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_pk PRIMARY KEY (game_code);


--
-- TOC entry 2902 (class 2606 OID 16587)
-- Name: profiles profile_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profiles
    ADD CONSTRAINT profile_pk PRIMARY KEY (id);


--
-- TOC entry 2896 (class 2606 OID 16411)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2917 (class 2606 OID 16711)
-- Name: rounds rounds_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rounds
    ADD CONSTRAINT rounds_pk PRIMARY KEY (round_id);


--
-- TOC entry 2912 (class 2606 OID 16675)
-- Name: tournaments tournaments_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tournaments
    ADD CONSTRAINT tournaments_pk PRIMARY KEY (tournament_id);


--
-- TOC entry 2894 (class 2606 OID 16403)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (email);


--
-- TOC entry 2898 (class 2606 OID 16429)
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 2915 (class 2606 OID 16696)
-- Name: users_tournaments users_tournaments_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tournaments
    ADD CONSTRAINT users_tournaments_pk PRIMARY KEY (tournament_id, user_id);


--
-- TOC entry 2905 (class 1259 OID 16642)
-- Name: friends_friend_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX friends_friend_id_uindex ON public.friends USING btree (friend_id);


--
-- TOC entry 2908 (class 1259 OID 16654)
-- Name: games_game_code_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX games_game_code_uindex ON public.games USING btree (game_code);


--
-- TOC entry 2903 (class 1259 OID 16584)
-- Name: profile_profile_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX profile_profile_id_uindex ON public.profiles USING btree (id);


--
-- TOC entry 2904 (class 1259 OID 16585)
-- Name: profile_user_email_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX profile_user_email_uindex ON public.profiles USING btree (user_email);


--
-- TOC entry 2918 (class 1259 OID 16717)
-- Name: rounds_round_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX rounds_round_id_uindex ON public.rounds USING btree (round_id);


--
-- TOC entry 2913 (class 1259 OID 16676)
-- Name: tournaments_tournament_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX tournaments_tournament_id_uindex ON public.tournaments USING btree (tournament_id);


--
-- TOC entry 2921 (class 2606 OID 16632)
-- Name: friends friends_users_email_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friends
    ADD CONSTRAINT friends_users_email_fk FOREIGN KEY (user_first) REFERENCES public.users(email);


--
-- TOC entry 2922 (class 2606 OID 16637)
-- Name: friends friends_users_email_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friends
    ADD CONSTRAINT friends_users_email_fk_2 FOREIGN KEY (user_second) REFERENCES public.users(email);


--
-- TOC entry 2926 (class 2606 OID 16718)
-- Name: games games_rounds_round_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_rounds_round_id_fk FOREIGN KEY (round_id) REFERENCES public.rounds(round_id);


--
-- TOC entry 2925 (class 2606 OID 16702)
-- Name: games games_tournaments_tournament_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_tournaments_tournament_id_fk FOREIGN KEY (tournament_id) REFERENCES public.tournaments(tournament_id);


--
-- TOC entry 2923 (class 2606 OID 16658)
-- Name: games games_users_email_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_users_email_fk FOREIGN KEY (host) REFERENCES public.users(email);


--
-- TOC entry 2924 (class 2606 OID 16663)
-- Name: games games_users_email_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_users_email_fk_2 FOREIGN KEY (player) REFERENCES public.users(email);


--
-- TOC entry 2920 (class 2606 OID 16423)
-- Name: users_roles role_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES public.roles(id) NOT VALID;


--
-- TOC entry 2929 (class 2606 OID 16712)
-- Name: rounds rounds_tournaments_tournament_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rounds
    ADD CONSTRAINT rounds_tournaments_tournament_id_fk FOREIGN KEY (tournament_id) REFERENCES public.tournaments(tournament_id);


--
-- TOC entry 2919 (class 2606 OID 16418)
-- Name: users_roles user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public.users(email);


--
-- TOC entry 2928 (class 2606 OID 16697)
-- Name: users_tournaments users_tournaments_tournaments_tournament_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tournaments
    ADD CONSTRAINT users_tournaments_tournaments_tournament_id_fk FOREIGN KEY (tournament_id) REFERENCES public.tournaments(tournament_id);


--
-- TOC entry 2927 (class 2606 OID 16685)
-- Name: users_tournaments users_tournaments_users_email_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tournaments
    ADD CONSTRAINT users_tournaments_users_email_fk FOREIGN KEY (user_id) REFERENCES public.users(email);


-- Completed on 2022-01-08 21:52:03

--
-- PostgreSQL database dump complete
--

