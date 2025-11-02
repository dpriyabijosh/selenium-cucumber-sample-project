# Simple Selenium Cucumber Framework

A test automation framework for testing the highest priced product functionality on SauceDemo.

## Summary

- Tests adding the **highest priced product** to cart **without using sort functionality**
- Verifies product name and price

## How to Run

```bash
mvn test
```

## Test Result

The test will:
1. Login to SauceDemo
2. Find the highest priced product by checking all products
3. Add it to cart
4. Verify the product details

## Requirements

- Java 11+
- Maven
- Firefox browser