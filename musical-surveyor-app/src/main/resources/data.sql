-- ------------------------------------------------
-- DATA.SQL - DML SCRIPT
-- ------------------------------------------------


INSERT INTO `color`
    (`code`, `created_on`, `created_by`)
    VALUES
    ('rebeccapurple', '2023-07-10T12:00:00', 'SYSTEM'),
    ('salmon',        '2023-07-10T12:00:00', 'SYSTEM'),
    ('coal',          '2023-07-10T12:00:00', 'SYSTEM'),
    ('gold',          '2023-07-10T12:00:00', 'SYSTEM'),
    ('silver',        '2023-07-10T12:00:00', 'SYSTEM'),
    ('#F2F2F2',       '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `artist`
    (`name`, `biography`, `created_on`, `created_by`)
    VALUES
    ('MusicalArtist 001', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 002', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 003', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 004', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 005', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 006', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 007', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 008', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 009', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('MusicalArtist 010 no-songs', NULL, '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `song`
    (`title`, `release_year`, `duration`, `genre`, `artist_id`, `created_on`, `created_by`)
    VALUES
    ('Song 0001', 1990,  157, 'Rock',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0002', 1980,  180, 'Rock',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0003', 1991,  145, 'Rock',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0004', 1992,  175, 'Pop',     (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0005', 1993,  170, 'Pop',     (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0006', 1994,  200, 'Pop',     (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0007', 1995,  190, 'Folk',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0008', 1996,  210, 'Folk',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0009', 1997,  301, 'Balad',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0010', 1997,  310, 'Balad',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0011', 1997, NULL, 'Rock',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0012', 1997, NULL, 'Folk',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0013', 1997, NULL, 'Blues',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0014', 2000, NULL, 'Blues',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0015', 2000, NULL, 'Country', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0016', 2000, NULL, 'Country', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0017', 2000, NULL, 'Rock',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0018', 2000, NULL, 'Rock',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 002'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0019', 2001, NULL, 'Ambient', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 003'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0020', 2001, NULL, 'Ambient', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 003'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0021', 2001, NULL, 'Ambient', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 003'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0022', 2001,  398, 'Dance',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 003'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0023', 2001,  361, 'Dance',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 003'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0024', 2010, NULL, 'House',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 003'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0025', 2010, NULL, 'Rumba',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 004'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0026', 2010, NULL, 'Rumba',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 004'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0027', 2010, NULL, 'Merengue',(SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 004'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0028', 2011, NULL, 'Bachata', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 004'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0029', NULL, NULL, 'Bolero',  (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 004'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0030', 2011, NULL, 'Folk',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 004'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0031', 2013, NULL, 'Indie',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 005'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0032', 2013, NULL, 'Indie',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 005'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0033', 2013, NULL, 'Indie',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 005'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0034', 2013, NULL, 'Indie',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 005'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0035', 1930,  360, 'Classic', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 006'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0036', 1940,  410, 'Classic', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 006'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0037', 1950,  689, 'Classic', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 006'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0038', 1920,  511, 'Classic', (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 006'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0039', 2005, NULL, 'Rock',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 007'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0040', 2005, NULL, 'Folk',    (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 007'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0041', 2005, NULL, 'Salsa',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 007'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0042', 2005, NULL, 'Tango',   (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 007'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0043', 2006, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 008'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0044', 2006, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 008'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0045', NULL, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 005'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0046', NULL, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 001'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0047', NULL, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 009'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0048', NULL, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 009'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0049', NULL, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 003'), '2023-07-10T12:00:00', 'SYSTEM'),
    ('Song 0050', NULL, NULL, NULL,      (SELECT `id` FROM `artist` WHERE `name` = 'MusicalArtist 004'), '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `radio_listener`
    (`name`, `phone`, `email`, `address`, `created_on`, `created_by`)
    VALUES
    ('David Orgaz',         '+34985626361', 'davazor@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Tomás Andujar',       '+34666666666', 'tamason@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Antonio Flórez',      '+01555555555', 'antonio.florez@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('María Montero',       '+34632547890', 'maria.montero@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Joaquín Armengol',    '+34987666441', 'jarmengol@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Pedro Alonso',        '+34981554754', 'pedricoal@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Yolanda Haro',        '+34983666547', 'haroyola@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Jorge Miramón Pol',   '+34685234596', 'jorge.miramon@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Carlota Romero',      '+34654873210', 'carlota.romero@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Jonás Palacios',      '+34985632478', 'palaconas@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Regina Palacios',     '+34985632478', 'palaregin@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Valentina Álvarez',   '+34666874121', 'valenalvz@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Suso Romillo Ruano',  '+34621357456', 'suromiru@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('David Ruano Rebollo', '+34912365400', 'druanoreb@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Mauricio Colmenero',  '+34721564823', 'mauricio.colmenero@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('José María Bermiego', '+34665412890', 'jm.bermiego@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('María José Bermiego', '+34665412890', 'majo.bermiego@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Guadalupe Noriega',   '+01555234120', 'wadanor@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Santos Román Naves',  '+35547889000', 'santrona@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Rosana Matiega',      '+33223455100', 'rosmatig@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Francisca Friol',     '+33552200114', 'paca.friol@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Consuelo Chistera',   '+34687951002', 'chisteconsuel@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Federico Losantos',   '+34965000202', 'losantos.fede@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Luis Mariano Puente', '+34967112233', 'luisma.puente@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Feliciano Caramillo', '+34967001122', 'fele.caramil@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Hugh Grant',          '+44898440020', 'hugh.grant@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Palmira Grant',       '+44898440020', 'palmigrant@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Francisco Tomé Plau', '+35121441122', 'frantome.plau@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Dolores Plá y Grana', '+34938300775', 'lola.gran.pla@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Asunción Nordés',     '+34924824755', 'asun.nordes@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Corina Larson',       '+44897440030', 'corinalar@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Peter Brown Jr.',     '+44897442031', 'peter.brown.jr@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Coralina Alcubiella', '+34984812200', 'coralinal3@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Carolina Pertierra',  '+34820520011', 'pertierra.carol@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Ordoño Pertierra',    '+34820520011', 'pertierra.ordo@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Odón Elorza',         '+34944201030', 'odon.elorza@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Jazmín Revengo Pez',  '+34946291037', 'jazmin.revengo@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Aithamy Blanco Plá',  '+34860521347', 'thamy.blanco@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Carles Blau y Grana', '+34962121289', 'blaugrana.carles@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('John White',          '+44564987132', 'john.white@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Jonás Pitingos',      '+34955565410', 'pitingojos@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Domingo Messi',       '+54953560010', 'messidom@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Roma Barranqueros',   '+57921065004', 'roma.barranqueros@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Diego Surribas',      '+34982003144', 'surribas.diego@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Marta Meirás Pazo',   '+34982003144', 'marta.meiras.pazo@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Adolfo Calvo Sotelo', '+34982110001', 'adolfo.calvoso@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Marta Sotres Luis',   '+34668552301404', 'sotreslu.marta@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Jennifer Carolina',   '+34668552301403', 'jencarro@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('María Fernanda Pío',  '+34762121289', 'piofermaria@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Armando Freire',      '+34762101288', 'freire.armando@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Damien Johnson',      '+44564000245', 'damien.johnson@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Dante Phillips',      '+44564000245', 'dante.phill@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Sonny Pérez',         '+01555210023', 'sonny.perez@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Jamal Saint París',   '+33140000011', 'jamalsant@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Allison McCarty',     '+44555870011', 'allison.mccarty@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Peggy Sue Brady',     '+44505875066', 'peggys.brady@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Virginia Hickman',    '+44500055441', 'virghick@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Leonor de Borbón',    '+34910000001001', 'leonor.borbon@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Teresa Arbeloa',      '+34910500218', 'arbeloate@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Samantha Larrañaga',  '+34913400100', 'samantha.larranaga@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Edurne Marie Louis',  '+33140055033', 'mariedu.lou@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Nadja Rodgers',       '+38044010024', 'nadjarod@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Barbara Putina',      '+07999010024', 'barbraputina@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Noemí Fuentes',       '+34955170000', 'fuentes.noemi@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Marcia McClure',      '+34955870022', 'marcia.mcclure@example.com', NULL, '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `prize`
    (`title`, `description`, `monetary_value`, `created_on`, `created_by`)
    VALUES
    ('Prize 001', NULL, 100.00, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 002', NULL,  75.50, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 003', NULL,  50.50, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 004', NULL,  25.50, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 005', NULL,  10.50, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 006', NULL,  15.50, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 007', NULL,  85.95, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 008', NULL,  70.59, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 009', NULL, 200.00, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Prize 010', NULL, 250.00, '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `survey`
    (`title`, `description`, `status`, `start_date`, `end_date`, `num_max_participants`, `num_survey_responses`, `created_on`, `created_by`)
    VALUES
    ('Survey 001', NULL, 'CLOSED',  '2022-07-15T12:00:00', '2022-07-31T23:59:59', 5, 2, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Survey 002', NULL, 'CLOSED',  '2022-08-01T12:00:00', '2023-08-15T23:59:59', 5, 2, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Survey 003', NULL, 'CLOSED',  '2023-01-01T12:00:00', '2023-01-31T23:59:59', 5, 2, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Survey 004', NULL, 'PENDING', '2023-09-10T12:00:00', '2023-10-31T23:59:59', 5, 2, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Survey 005', NULL, 'PENDING', '2023-09-10T12:00:00', '2023-10-31T23:59:59', 5, 2, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Survey 006', NULL, 'RUNNING', '2023-08-15T12:00:00', '2023-09-30T23:59:59', 5, 2, '2023-07-10T12:00:00', 'SYSTEM'),
    ('Survey 007', NULL, 'RUNNING', '2023-08-15T12:00:00', '2023-09-30T23:59:59', 5, 3, '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `raffle_ticket`
    (`number`, `color_id`)
    VALUES
    ('5s78f9bvyuh7f2jk', 3),
    ('lk57j7nb7j4n27nb', 4),
    ('j56jhnm4n4j5jm8d', 5),
    ('l324jhb45n7nk67j', 3),
    ('k4jhn5kk3mn3j78h', 6),
    ('o3i4u45u5jm6823s', 5),
    ('84km4bn6k3k2nert', 4),
    ('9854h739jn5bj3wq', 4),
    ('1l2jk5h6m5hh3qer', 3),
    ('03k4bn4597q63dfg', 2),
    ('bkd6s0er876em554', 2),
    ('q0w97dn4h5n7m7mm', 1),
    ('kwq4mr5bn453p4mr', 3),
    ('54wqe78sdf21b78s', 4),
    ('78weq5sda4v21wuy', 2),
    ('th191mn19ewuy3d2', 1);



INSERT INTO `survey_participation`
    (`survey_id`, `participant_id`, `participated_at`, `raffle_ticket_id`, `created_on`, `created_by`)
    VALUES
    -- Closed surveys
    -- (if survey responses was sent, has raffle ticket and participated date between survey dates)
    (1,  1, '2022-07-16T13:30:00',    1, '2023-07-10T12:00:00', 'SYSTEM'),
    (1,  8, '2022-07-17T13:30:00',    2, '2023-07-10T12:00:00', 'SYSTEM'),
    (1,  5, '2022-07-18T13:30:00',    3, '2023-07-10T12:00:00', 'SYSTEM'),
    (1,  4, '2022-07-19T13:30:00',    4, '2023-07-10T12:00:00', 'SYSTEM'),
    (1,  9, '2022-07-20T13:30:00',    5, '2023-07-10T12:00:00', 'SYSTEM'),
    (2, 10, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (2, 20, '2022-08-05T13:30:00',    6, '2023-07-10T12:00:00', 'SYSTEM'),
    (2, 15, '2022-08-06T13:30:00',    7, '2023-07-10T12:00:00', 'SYSTEM'),
    (2, 16, '2022-08-07T13:30:00',    8, '2023-07-10T12:00:00', 'SYSTEM'),
    (2, 19, '2022-08-08T13:30:00',    9, '2023-07-10T12:00:00', 'SYSTEM'),
    (3,  2, '2023-01-09T13:30:00',   10, '2023-07-10T12:00:00', 'SYSTEM'),
    (3,  8, '2023-01-10T13:30:00',   11, '2023-07-10T12:00:00', 'SYSTEM'),
    (3,  5, '2023-01-10T13:30:00',   12, '2023-07-10T12:00:00', 'SYSTEM'),
    (3, 14, '2023-01-12T13:30:00',   13, '2023-07-10T12:00:00', 'SYSTEM'),
    -- Pending surveys with already selected participants
    (4,  3, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (4,  6, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (4,  7, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (4, 17, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (4, 21, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    -- Running surveys
    -- (someone has sent it responses, but not allbody before close survey past it end date)
    (6, 30, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (6, 31, '2023-08-16T17:00:00',   14, '2023-07-10T12:00:00', 'SYSTEM'),
    (6, 32, '2023-08-17T14:00:00',   15, '2023-07-10T12:00:00', 'SYSTEM'),
    (6, 20, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (6, 40, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (7, 35, '2023-08-18T18:00:00',   16, '2023-07-10T12:00:00', 'SYSTEM'),
    (7, 42, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (7, 51, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (7, 37, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM'),
    (7, 39, NULL,                  NULL, '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `survey_responsing`
    (`survey_id`, `participant_id`, `song_id`)
    VALUES
    -- Closed surveys
    (1,  1,  1), (1,  1,  2),
    (1,  8,  1), (1,  8,  3),
    (1,  5,  1), (1,  5,  4),
    (1,  4,  2), (1,  4,  5),
    (1,  9, 11), (1,  9, 12),
    (2, 20,  1), (2, 20,  2),
    (2, 15,  5), (2, 15,  6),
    (2, 16, 12), (2, 16, 13),
    (2, 19, 18), (2, 19, 17),
    (3,  2, 35), (3,  2, 37),
    (3,  8,  1), (3,  8,  3),
    (3,  5, 43), (3,  5, 44),
    (3, 14, 40), (3, 14, 41),
    -- Running surveys
    (6, 31, 40), (6, 31, 41),
    (6, 32, 33), (6, 32, 45),
    (7, 35, 43), (7, 35, 44), (7, 35, 48);



INSERT INTO `raffle`
    (`id`, `status`, `resolution_date`, `created_on`, `created_by`)
    VALUES
    -- Closed surveys. Raffled
    (1, 'RESOLVED', '2022-08-14T12:30:00', '2023-07-10T12:00:00', 'SYSTEM'),
    -- Closed surveys. Prizing configured
    (2, 'PENDING',  NULL,                  '2023-07-10T12:00:00', 'SYSTEM');
    -- Closed surveys. Missing raffle
    --(3, 'PENDING',  NULL,                  '2023-07-10T12:00:00', 'SYSTEM');



INSERT INTO `raffle_prize`
    (`raffle_id`, `prize_id`, `winner_ticket_id`, `created_on`, `created_by`)
    VALUES
    (1, 1,    1, '2023-07-10T12:00:00', 'SYSTEM'),
    (1, 5,    2, '2023-07-10T12:00:00', 'SYSTEM'),
    (2, 5, NULL, '2023-07-10T12:00:00', 'SYSTEM');






--SELECT * FROM `color` ORDER BY `id`;
--SELECT * FROM `artist` ORDER BY `id`;
--SELECT * FROM `song` ORDER BY `id`;
--SELECT * FROM `radio_listener` ORDER BY `id`;
--SELECT * FROM `prize` ORDER BY `id`;
--SELECT * FROM `survey` ORDER BY `id`;
--SELECT * FROM `raffle_ticket` ORDER BY `id`;
--SELECT * FROM `survey_participation` ORDER BY `survey_id`, `participated_at`, `participant_id`;
--SELECT * FROM `survey_responsing` ORDER BY `survey_id`, `participant_id`, `song_id`;
--SELECT * FROM `raffle` ORDER BY `id`;
--SELECT * FROM `raffle_prize` ORDER BY `raffle_id`, `prize_id`;
