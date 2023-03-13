
INSERT INTO assets (id, name, parent, promoted) VALUES (1, 'Asset A',  null, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (2, 'Asset B',  1, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (4, 'Asset D',  2, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (6, 'Asset F',  4, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (7, 'Asset G',  4, false);

INSERT INTO assets (id, name, parent, promoted) VALUES (5, 'Asset E',  2, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (8, 'Asset H',  5, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (9, 'Asset I',  5, false);

INSERT INTO assets (id, name, parent, promoted) VALUES (3, 'Asset C',  1, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (10, 'Asset J',  3, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (11, 'Asset K',  10, false);
INSERT INTO assets (id, name, parent, promoted) VALUES (12, 'Asset L',  10, false);

commit;