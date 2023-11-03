#Author: Priya S
Feature: See all integration validation

Scenario Outline: Validate whether the user is able to launch the LT integrations link in new tab and url is correct

Given user is navigated to lambda test application with "<URL>"
When clicked on the see all integrations link
Then user should be navigated to new tab
Then verify URL is same as the expected url with "<IntegrationsURL>"

Examples:
|URL|IntegrationsURL|
|https://www.lambdatest.com|https://www.lambdatest.com/integrations|