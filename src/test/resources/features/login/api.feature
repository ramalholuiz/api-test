Feature: API Testing

  Scenario: Successful Login
    Given the API endpoint is "/login-hard"
    And the request body is
      """
      {
        "username": "admin",
        "password": "password"
      }
      """
    When I send a POST request
    Then the status code is 201
    And the response content type is JSON
    And the "data[0].form[0].cam[0].in[0].gen[0].form_token[0].in_token[0].gen_token[0].refresh[0].bearer[0].refresh_token[0].show.token" is present
#    And the "token" is present

  Scenario: Invalid Username or Password
    Given the API endpoint is "/login-hard"
    And the request body is
      """
      {
        "username": "invalid",
        "password": "invalid"
      }
      """
    When I send a POST request
    Then the status code is 401
    And the error message is "Credenciais inválidas"
#    And the error message is "Invalid credentials provided."

  Scenario: Unauthorized Access
    Given the API endpoint is "/login-hard"
    And the request body is
      """
      {
        "username": "admin",
        "password": "wrongpassword"
      }
      """
    When I send a POST request
    Then the status code is 401
    And the error message is "Credenciais inválidas"
#    And the error message is "Authentication failed."
