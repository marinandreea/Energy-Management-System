PGDMP                  
    {            devicedb    16.0    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16664    devicedb    DATABASE     �   CREATE DATABASE devicedb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE devicedb;
                postgres    false            �            1259    19317    device    TABLE     �   CREATE TABLE public.device (
    id integer NOT NULL,
    max_hourly_energy_consumption double precision,
    user_id_id integer,
    address character varying(255),
    description character varying(255)
);
    DROP TABLE public.device;
       public         heap    postgres    false            �            1259    19316    device_id_seq    SEQUENCE     �   CREATE SEQUENCE public.device_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.device_id_seq;
       public          postgres    false    216            �           0    0    device_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.device_id_seq OWNED BY public.device.id;
          public          postgres    false    215            �            1259    19325    userr    TABLE     7   CREATE TABLE public.userr (
    id integer NOT NULL
);
    DROP TABLE public.userr;
       public         heap    postgres    false                       2604    19320 	   device id    DEFAULT     f   ALTER TABLE ONLY public.device ALTER COLUMN id SET DEFAULT nextval('public.device_id_seq'::regclass);
 8   ALTER TABLE public.device ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    19317    device 
   TABLE DATA           e   COPY public.device (id, max_hourly_energy_consumption, user_id_id, address, description) FROM stdin;
    public          postgres    false    216          �          0    19325    userr 
   TABLE DATA           #   COPY public.userr (id) FROM stdin;
    public          postgres    false    217   !       �           0    0    device_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.device_id_seq', 1, false);
          public          postgres    false    215                        2606    19324    device device_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.device DROP CONSTRAINT device_pkey;
       public            postgres    false    216            "           2606    19329    userr userr_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.userr
    ADD CONSTRAINT userr_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.userr DROP CONSTRAINT userr_pkey;
       public            postgres    false    217            #           2606    19330 "   device fk123ec9pyfvj1o2m7qfst4em2p    FK CONSTRAINT     �   ALTER TABLE ONLY public.device
    ADD CONSTRAINT fk123ec9pyfvj1o2m7qfst4em2p FOREIGN KEY (user_id_id) REFERENCES public.userr(id);
 L   ALTER TABLE ONLY public.device DROP CONSTRAINT fk123ec9pyfvj1o2m7qfst4em2p;
       public          postgres    false    4642    216    217            �      x������ � �      �      x������ � �     