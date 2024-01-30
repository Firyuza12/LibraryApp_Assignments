
Feature: Books module
  As a librarian, I should be able to add new book into library
    @US06 @ui @db
  Scenario Outline: Verify added book is matching with DB
    Given the "librarian" on the home page
    And the user navigates to "Books" page
    When the librarian click to add book
    And the librarian enter book name "<Book Name>"
    When the librarian enter ISBN "<ISBN>"
    And the librarian enter year "<Year>"
    When the librarian enter author "<Author>"
    And the librarian choose the book category "<Book Category>"
    And the librarian click to save changes
    Then verify "The book has been created" message is displayed
    And verify "<Book Name>" information must match with DB
    Examples:
      | Book Name           | ISBN     | Year | Author       | Book Category        |
      | A silly Cat         | 10112021 | 2011 | Kathy Sierra | Action and Adventure |
      | Tips for studying   | 11112021 | 2016 | Mitch Lacey  | Short Story          |
      | Journey to the West | 11112025 | 1906 | John Lacey   | Drama                |
      | The Sun is sparkle  | 1111261  | 1806 | Mitch Brown  | Fairy Tale           |
      | The Rabbit          | 1115521  | 2009 | Buyer Lana   | Humor                |
      | The Lady            | 11112029 | 2002 | Teddy Baker  | Romance              |