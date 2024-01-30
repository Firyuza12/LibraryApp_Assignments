package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Map;

public class AddBook_Van_StepDefs extends BasePage{

    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    BookPage bookPage = new BookPage();
    String expectedBookName;
    String  expectedIsbn;
    String expectedYear;
    String expectedAuthor;
    String expectedBookCategory;
    String bookName;


    @Given("the {string} on the home page")
    public void the_on_the_home_page(String userType) {
        loginPage.login("librarian");
    }
    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String module) {
        navigateModule("Books");

    }
    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {
    bookPage.addBook.click();
    }
    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String bookName) {
    BrowserUtil.waitForVisibility(bookPage.bookName, 10);
        bookPage.bookName.sendKeys(bookName);
        this.bookName=bookName;

    }
    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String isbn) {
        bookPage.isbn.sendKeys(isbn);


    }
    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String year) {
        bookPage.year.sendKeys(year);


    }
    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String author) {
        bookPage.author.sendKeys(author);


    }
    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String bookCategoryName) {
        BrowserUtil.waitFor(4);
       BrowserUtil.selectOptionDropdown(bookPage.categoryDropdown, bookCategoryName);
       this.expectedBookCategory= bookCategoryName;

    }
    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
        bookPage.saveChanges.click();
    }
    @Then("verify {string} message is displayed")
    public void verify_message_is_displayed(String expectedMsg) {


        Assert.assertTrue(expectedMsg, bookPage.toastMessage.isDisplayed());
    }
    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String nameOfBook) {
       nameOfBook= this.bookName;
       String query=" select  b.name, b.isbn, b.year, b.author, bc.name\n" +
               "    from books b\n" +
               "    left join book_categories bc on b.book_category_id = bc.id where b.name='"+nameOfBook+"'";

        DB_Util.runQuery(query);

        Map<String,String> bookInfo = DB_Util.getRowMap(1);

        String actualBookName = DB_Util.getFirstRowFirstColumn();
        String actualIsbn = bookInfo.get("isbn");
        String actualYear = bookInfo.get("year");
        String actualAuthor = bookInfo.get("author");
        String actualCategoryName = bookInfo.get("name");


        expectedBookName = bookPage.bookName.getAttribute("value");
        expectedIsbn= bookPage.isbn.getAttribute("value");
        expectedYear=bookPage.year.getAttribute("value");
        expectedAuthor = bookPage.author.getAttribute("value");


        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedIsbn,actualIsbn);
        Assert.assertEquals(expectedYear,actualYear);
        Assert.assertEquals(expectedAuthor,actualAuthor);
        Assert.assertEquals(expectedBookCategory,actualCategoryName);



    }

}
