package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

public class US04_nisa_StepDefs {
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();
    String globalBookName;

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String string) {
        BrowserUtil.waitFor(2);
        loginPage.login("librarian");
    }


    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        BrowserUtil.waitFor(2);
        bookPage.navigateModule(moduleName);
    }


    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String bookName) {
        globalBookName = bookName;
        BrowserUtil.waitFor(2);
        bookPage.search.sendKeys(bookName);

    }
    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {
        BrowserUtil.waitFor(2);
        bookPage.editBook(globalBookName).click();
    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {
        BrowserUtil.waitFor(2);

        //GET DATA FROM UI
        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualAuthor = bookPage.author.getAttribute("value");
        String actualISBN = bookPage.isbn.getAttribute("value");
        String actualDesc = bookPage.description.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");

        Select select = new Select(bookPage.categoryDropdown);
        String actualCategory = select.getFirstSelectedOption().getText();

        //GET DATA FROM DB


        DB_Util.runQuery("select b.name as bookName, isbn, year, author, b.description, bc.name as categoryName\n" +
                "from books b join book_categories bc\n" +
                "on b.book_category_id = bc.id\n" +
                "where b.name = '" + globalBookName + "'");

        Map<String, String> rowMap = DB_Util.getRowMap(1);
        String expectedBookName = rowMap.get("bookName");
        String expectedISBN = rowMap.get("isbn");
        String expectedYear = rowMap.get("year");
        String expectedDesc = rowMap.get("description");
        String expectedAuthor = rowMap.get("author");
        String expectedCategoryName = rowMap.get("categoryName");



        //COMPARE

        Assert.assertEquals(expectedBookName, actualBookName);
        Assert.assertEquals(expectedISBN, actualISBN);
        Assert.assertEquals(expectedAuthor, actualAuthor);
        Assert.assertEquals(expectedDesc, actualDesc);
        Assert.assertEquals(expectedYear, actualYear);
        Assert.assertEquals(expectedCategoryName, actualCategory);


        DB_Util.destroy();

    }

}
