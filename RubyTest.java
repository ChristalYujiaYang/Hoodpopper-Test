import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class RubyTest {

	static WebDriver driver = new HtmlUnitDriver();

	// Start at the home page for reddit for each test
	@Before
	public void setUp() throws Exception {
		driver.get("http://lit-bayou-7912.herokuapp.com/");
	}

	/**
	 * As a user, I would like to know how the tokenizer goes through and
	 * separates the code I have written into tokens, So that I could improve
	 * performance in a Ruby app
	 * 
	 * @author Yujia Yang
	 *
	 */
	// Given that I am on the main page
	// When I write the code with spaces and click on "Tokenize" button
	// Then I see that it contains corresponding quantity of the word ":on_sp"
	@Test
	public void testTokenizeSpace() {

		// Enter the code

		driver.findElement(By.name("code[code]")).sendKeys("the_best_cat = \"Noogie Cat\"");

		// Look for the "Tokenize" button and click

		driver.findElements(By.name("commit")).get(0).click();

		// Check that there is corresponding quantity of the word ":on_sp"

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			String res = code.getText();
			int count = 0;
			for (int i = 0; i <= res.length() - 6; i++) {
				String sub = res.substring(i, i + 6);
				if (sub.equals(":on_sp"))
					count++;
			}
			assertEquals(count, 2);
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the main page
	// When I write the code with newlines and click on "Tokenize" button
	// Then I see that it contains corresponding quantity of the word ":on_nl"
	@Test
	public void testTokenizeNewlines() {

		// Enter the code

		driver.findElement(By.name("code[code]"))
				.sendKeys("the_best_cat = \"Noogie Cat\"\nputs \"The best cat is: \" + the_best_cat");

		// Look for the "Tokenize" button and click

		driver.findElements(By.name("commit")).get(0).click();

		// Check that there is corresponding quantity of the word ":on_nl"

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			String res = code.getText();
			int count = 0;
			for (int i = 0; i <= res.length() - 6; i++) {
				String sub = res.substring(i, i + 6);
				if (sub.equals(":on_nl"))
					count++;
			}
			assertEquals(count, 1);
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the main page
	// When I write the code with identifiers and click on "Tokenize" button
	// Then I see that it contains corresponding quantity of the word
	// ":on_ident"
	@Test
	public void testTokenizeIdentifiers() {

		// Enter the code

		driver.findElement(By.name("code[code]"))
				.sendKeys("the_best_cat = \"Noogie Cat\"\nputs \"The best cat is: \" + the_best_cat");

		// Look for the "Tokenize" button and click

		driver.findElements(By.name("commit")).get(0).click();

		// Check that there is corresponding quantity of the word ":on_ident"

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			String res = code.getText();
			int count = 0;
			for (int i = 0; i <= res.length() - 9; i++) {
				String sub = res.substring(i, i + 9);
				if (sub.equals(":on_ident"))
					count++;
			}
			assertEquals(count, 3);
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the main page
	// When I write the code with operators and click on "Tokenize" button
	// Then I see that it contains corresponding quantity of the word ":on_op"
	@Test
	public void testTokenizeOperator() {

		// Enter the code

		driver.findElement(By.name("code[code]"))
				.sendKeys("the_best_cat = \"Noogie Cat\"\nputs \"The best cat is: \" + the_best_cat");

		// Look for the "Tokenize" button and click

		driver.findElements(By.name("commit")).get(0).click();

		// Check that there is corresponding quantity of the word ":on_op"

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			String res = code.getText();
			int count = 0;
			for (int i = 0; i <= res.length() - 6; i++) {
				String sub = res.substring(i, i + 6);
				if (sub.equals(":on_op"))
					count++;
			}
			assertEquals(count, 2);
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	/**
	 * As a user, I would like to know how the parser puts the code into an
	 * abstract syntax tree (AST), So that I could improve performance in a Ruby
	 * app
	 * 
	 * @author Yujia Yang
	 *
	 */
	// Given that I am on the main page
	// When I write code with errors and click on "Parse" button
	// Then I see that it shows wrong message
	@Test
	public void testParseError() {

		// Enter the code

		driver.findElement(By.name("code[code]")).sendKeys("a = 5\nb = a - 1\nc = a + (b / ");

		// Look for the "Parse" button and click

		driver.findElements(By.name("commit")).get(1).click();

		// Check that the system shows error message

		try {
			WebElement code = driver.findElement(By.className("dialog"));
			assertEquals(code.getText(), "We're sorry, but something went wrong.");
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the main page
	// When I write the code with space and newlines then click on "Parse"
	// button
	// Then I see that it does not contain any ":op_nl" or ":op_sp"
	@Test
	public void testParseSpace() {

		// Enter the code

		driver.findElement(By.name("code[code]"))
				.sendKeys("the_best_cat = \"Noogie Cat\"\nputs \"The best cat is: \" + the_best_cat");

		// Look for the "Parse" button and click

		driver.findElements(By.name("commit")).get(1).click();

		// Check that there contains space and newlines

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			assertFalse(code.getText().contains(":op_nl"));
			assertFalse(code.getText().contains(":op_sp"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the main page
	// When I write the code with operators then click on "Parse" button
	// Then I see that it contains corresponding quantity of operators
	@Test
	public void testParseOperator() {

		// find the number of operators in the input

		int add = 0;
		int minus = 0;
		int multiply = 0;
		int divide = 0;
		String input = "a = 5\nb = a - 1\nc = a + (b / 2 * 4)";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '+')
				add++;
			if (input.charAt(i) == '-')
				minus++;
			if (input.charAt(i) == '*')
				multiply++;
			if (input.charAt(i) == '/')
				divide++;
		}

		// Enter the code

		driver.findElement(By.name("code[code]")).sendKeys(input);

		// Look for the "Parse" button and click

		driver.findElements(By.name("commit")).get(1).click();

		// Check that there contains corresponding quantity of operators

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			String res = code.getText();
			int add1 = 0, minus1 = 0, multiply1 = 0, divide1 = 0;
			for (int i = 0; i < res.length(); i++) {
				if (res.charAt(i) == '+')
					add1++;
				if (res.charAt(i) == '-')
					minus1++;
				if (res.charAt(i) == '*')
					multiply1++;
				if (res.charAt(i) == '/')
					divide1++;
			}
			assertEquals(add, add1);
			assertEquals(minus, minus1);
			assertEquals(multiply, multiply1);
			assertEquals(divide, divide1);
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the main page
	// When I write the code with the word "puts" then click on "Parse" button
	// Then I see that it contains corresponding quantity of "assign"
	@Test
	public void testParseIdentifier() {

		// Enter the code

		driver.findElement(By.name("code[code]"))
				.sendKeys("the_best_cat = \"Noogie Cat\"\nputs \"The best cat is: \" + the_best_cat");

		// Look for the "Parse" button and click

		driver.findElements(By.name("commit")).get(1).click();

		// Check that there contains corresponding quantity of "assign"

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			String res = code.getText();
			int count = 0;
			for (int i = 0; i <= res.length() - 6; i++) {
				String sub = res.substring(i, i + 6);
				if (sub.equals("assign"))
					count++;
			}
			assertEquals(count, 1);
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	/**
	 * As a user, I would like to know how the compiler goes through the AST and
	 * writes the actual machine-level instructions to an executable, So that I
	 * could improve performance in a Ruby app
	 * 
	 * @author Yujia Yang
	 *
	 */
	// Given that I am on the main page
	// When I write the code with "puts" and click on "Compile" button
	// Then I see that it contains the word "putstring"
	@Test
	public void testCompilePuts() {

		// Enter the code

		driver.findElement(By.name("code[code]"))
				.sendKeys("the_best_cat = \"Noogie Cat\"\nputs \"The best cat is: \" + the_best_cat");

		// Look for the "Compile" button and click

		driver.findElements(By.name("commit")).get(2).click();

		// Check that there contains the word "putstring"

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			assertTrue(code.getText().contains("putstring"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the main page
	// When I write the code with "+","-","*","/" and click on "Compile" button
	// Then I see that it contains the word
	// "opt_plus","opt_minus","opt_mult","opt_div"
	@Test
	public void testCompileOperator() {

		// Enter the code

		driver.findElement(By.name("code[code]")).sendKeys("a = 5\nb = a - 1\nc = a + (b / 2 * 4)");

		// Look for the "Compile" button and click

		driver.findElements(By.name("commit")).get(2).click();

		// Check that there contains the word "opt_plus", "opt_minus",
		// "opt_mult" and "opt_div"

		try {
			WebElement code = driver.findElement(By.tagName("code"));
			assertTrue(code.getText().contains("opt_plus"));
			assertTrue(code.getText().contains("opt_minus"));
			assertTrue(code.getText().contains("opt_mult"));
			assertTrue(code.getText().contains("opt_div"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

}