-- Sample data for my_db.session
INSERT INTO session (session_id, ended_at, started_at, started_by, picked_location)
VALUES (1, '2024-02-24 10:00:00', '2024-02-24 09:00:00', 'user_1', 'Restaurant 1'),
       (2, null, '2024-02-25 11:00:00', 'user_3', null),
       (3, '2024-02-25 12:30:00', '2024-02-25 12:00:00', 'user_5', 'Restaurant 2');

-- Sample data for my_db.user
INSERT INTO my_db.user (user_id, first_name, last_name, avatar_url, password)
VALUES
    ('user_1',  'John', 'Doe', '/assets/images/avatar/image-01.jpg', 'pwd123'),
    ('user_2',  'Jane', 'Smith', '/assets/images/avatar/image-02.jpg', 'pwd123'),
    ('user_3',  'Alice', 'Johnson', '/assets/images/avatar/image-03.jpg', 'pwd123'),
    ('user_4',  'Kelly', 'Anderson', '/assets/images/avatar/image-04.jpg', 'pwd123'),
    ('user_5',  'Mark', 'Nelson', '/assets/images/avatar/image-05.jpg', 'pwd123'),
    ('user_6',  'Alice', 'Broad', '/assets/images/avatar/image-06.jpg', 'pwd123'),
    ('user_7',  'Jade', 'Broad', '/assets/images/avatar/image-07.jpg', 'pwd123'),
    ('user_8',  'Lakshitha', 'Madushan', '/assets/images/avatar/image-08.jpg', 'pwd123'),
    ('user_9',  'Shane', 'Broad', '/assets/images/avatar/image-09.jpg', 'pwd123'),
    ('user_10',  'Robert', 'Kali', '/assets/images/avatar/image-10.jpg', 'pwd123');

-- -- Sample data for my_db.invitation
INSERT INTO invitation (SESSION_ID, USER_ID, invitation_status, location)
VALUES
    ( 1, 'user_1', 'OWNER', 'Restaurant 1'),
    ( 1, 'user_2', 'EXPIRED', null),
    ( 1, 'user_3', 'ACCEPTED', 'Restaurant 2'),
    ( 2, 'user_3', 'OWNER', 'Restaurant 1' ),
    ( 2, 'user_1', 'PENDING', null),
    ( 2, 'user_2', 'REJECTED', null ),
    ( 2, 'user_4', 'ACCEPTED', 'Restaurant 4'),
    ( 3, 'user_5', 'OWNER', 'Restaurant 1'),
    ( 3, 'user_6', 'ACCEPTED', 'Restaurant 2');
