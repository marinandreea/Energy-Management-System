PGDMP                  
    {            userdb    16.0    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16623    userdb    DATABASE     �   CREATE DATABASE userdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE userdb;
                postgres    false            �            1259    19307    userr    TABLE     L  CREATE TABLE public.userr (
    id integer NOT NULL,
    name character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255),
    CONSTRAINT userr_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'CLIENT'::character varying])::text[])))
);
    DROP TABLE public.userr;
       public         heap    postgres    false            �            1259    19306    userr_id_seq    SEQUENCE     �   CREATE SEQUENCE public.userr_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.userr_id_seq;
       public          postgres    false    216            �           0    0    userr_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.userr_id_seq OWNED BY public.userr.id;
          public          postgres    false    215                       2604    19310    userr id    DEFAULT     d   ALTER TABLE ONLY public.userr ALTER COLUMN id SET DEFAULT nextval('public.userr_id_seq'::regclass);
 7   ALTER TABLE public.userr ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            �          0    19307    userr 
   TABLE DATA           C   COPY public.userr (id, name, password, role, username) FROM stdin;
    public          postgres    false    216          �           0    0    userr_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.userr_id_seq', 1, true);
          public          postgres    false    215                       2606    19315    userr userr_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.userr
    ADD CONSTRAINT userr_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.userr DROP CONSTRAINT userr_pkey;
       public            postgres    false    216            �   d   x�3�t�K)JMMT�M,���T1JT14R�ˬ�2.p���	-N/�J�pJ/�Ou2H��s)K��H��t+�O*+J4.J��tt����L����� �j�     