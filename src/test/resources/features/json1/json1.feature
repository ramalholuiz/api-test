Feature: Validate JSON1 response

  Scenario: Validate user and interactions in e-commerce system
    Given the API endpoint is "/json_1"
    When I send a GET request
    Then the status code is 200
    And the response content type is JSON
    And the response JSON is valid