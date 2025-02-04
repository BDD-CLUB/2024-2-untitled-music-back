insert into artist (id, uuid, name, email, link1, link2, description, provider, artist_image, role, is_deleted)
values (1, UUID(), 'artist1', 'aaaa@gmail.com', 'https://instagram.com', NULL, 'hello I am artist1', 'google', 'image.jpg', 'ROLE_USER', false);
insert into album (`id`, `uuid`, `title`, `description`, `art_image`, `release_date`, `artist_id`, `is_deleted`)
values (1, UUID(), 'album1', 'This is a description of the album', 'image.jpg', '2025-01-01', 1, false);
insert into album_like (`id`, `uuid`, `artist_id`, `album_id`)
values (1, UUID(), 1, 1);
insert into album_comment (`id`, `uuid`, `comment`, `artist_id`, `album_id`, `is_deleted`)
values (1, UUID(), 'awesome song!', 1, 1, false);
insert into track (`id`, `uuid`, `title`, `lyric`, `track_url`, `duration`, `album_id`, `is_deleted`)
values (1, UUID(), 'title', 'lyric', 's3.com', 360, 1, false);
