insert into artist (id, uuid, name, email, link1, link2, description, provider, artist_image, role, is_deleted)
values (1, UUID(), 'artist1', 'aaaa@gmail.com', 'https://instagram.com', NULL, 'hello I am artist1', 'google','image.jpg', 'ROLE_USER', false);
insert into artist (id, uuid, name, email, link1, link2, description, provider, artist_image, role, is_deleted)
values (2, UUID(), 'artist2', 'bbbb@gmail.com', 'https://instagram.com', NULL, 'hello I am artist2', 'google','image.jpg', 'ROLE_USER', false);
insert into artist (id, uuid, name, email, link1, link2, description, provider, artist_image, role, is_deleted)
values (3, UUID(), 'artist3', 'cccc@gmail.com', 'https://instagram.com', NULL, 'hello I am artist3', 'google','image.jpg', 'ROLE_USER', false);
insert into follow (id, follower_id, following_id)
values (1, 1, 2);
insert into follow (id, follower_id, following_id)
values (2, 1, 3);
insert into follow (id, follower_id, following_id)
values (3, 2, 1);

-- 1 follows 2, 3 (1's following number:2)
-- 2 follows 1 (1's follower number:1)