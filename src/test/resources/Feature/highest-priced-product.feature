@highest-price
Feature: Add Highest Priced Product to Cart
  As a user
  I want to add the highest priced product to my cart
  So that I can verify the product selection functionality

  Background:
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should see the Products page

  Scenario: Add highest priced product to cart and verify product name and price
    When I find the product with highest price
    And I add the highest priced product to cart
    Then the highest priced product should be added successfully