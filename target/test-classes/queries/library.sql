SELECT * FROM books;

-- US 01 -1
  -- OPT 1

      select id from users;
      -- getColumnDataAsList --> List --> size --> 10
      -- getColumnDataAsList --> Set  --> size --> 10

  -- OPT 2
    select count(id) from users; -- 5277          --> ACTUAL
    select count(distinct id) from users; -- 5277 --> EXPECTED

-- US 03
    select name from book_categories;
-- join 4 tables to see same data from ui
    select isbn,b.name,author,bc.name,year,full_name
    from users u
    inner join book_borrow bb on u.id=bb.user_id
    inner join books b on bb.book_id = b.id
    inner join book_categories bc on b.book_category_id = bc.id;

-- .....