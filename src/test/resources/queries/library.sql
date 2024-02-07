SELECT * FROM books;

-- US 01 -1
  -- OPT 1
      select id from users;
      -- getColumnDataAsList --> List --> size --> 10
      -- getColumnDataAsList --> Set  --> size --> 10

  -- OPT 2
    select count(id) from users; -- 5277          --> ACTUAL
    select count(distinct id) from users; -- 5277 --> EXPECTED
select b.name, b.isbn, b.year, b.author, bc.name
from books b
         join book_categories bc on b.book_category_id = bc.id
where b.name = 'The Rose';




-- .....