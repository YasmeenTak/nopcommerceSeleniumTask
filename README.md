# Nopcommerce Selenium Task

<a href="https://selenium.dev"><img src="https://selenium.dev/images/selenium_logo_square_green.png" width="180" alt="Selenium"/></a>

[![Java workflow](https://github.com/SeleniumHQ/selenium/workflows/JavaScript%20workflow/badge.svg)](https://github.com/SeleniumHQ/selenium/actions?query=workflow%3A%22JavaScript+workflow%22)

## Requirements

- The latest version of the [Java 11 OpenJDK](https://openjdk.java.net/)
- `java` and `jar` on the PATH (make sure you use `java` executable from JDK but not JRE).
  - To test this, try running the command `javac`. This command won't exist if you only have the JRE
    installed. If you're met with a list of command-line options, you're referencing the JDK properly.

### Chrome Web Driver

If you plan to compile the
[IE driver](https://github.com/SeleniumHQ/selenium/wiki/chromeDriver),
you also need:

- [Visual Studio 2008](https://www.visualstudio.com/)
- 32 and 64-bit cross compilers

## The Scenario

- Navigate to https://admin-demo.nopcommerce.com/
- Login with the default admin user
- Navigate to products page from the left nav.
- Add a new product in Basic Model (Fill all fields in the Product Info, Price, and Inventory sections).
- Navigate to the Discount section from the left nav.
- Add a new discout assinged to the previously added product applied from 31/12/2021 to 28/2/2022.
- Write all possible assertions for the previous scenario.
