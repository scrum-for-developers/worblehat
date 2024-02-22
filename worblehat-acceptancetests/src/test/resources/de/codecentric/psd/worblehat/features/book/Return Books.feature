Feature: Returning - giving back - borrowed books

  Scenario Outline: Returning all books at once

    Given an empty library
    And "<borrower1>" has borrowed books "<isbns1>"
    And "<borrower2>" has borrowed books "<isbns2>"

    When "<borrower1>" returns all books

    Then books "<isbns1>" are not borrowed anymore by borrower "<borrower1>"
    But books "<isbns2>" are still borrowed by borrower "<borrower2>"

    Examples:

      | borrower1       | isbns1                | borrower2       | isbns2                |
      | user1@dings.com | 0321293533            |                 |                       |
      | user1@dings.com | 0321293533            | user2@dings.com | 1234567962            |
      | user1@dings.com | 0321293533 1234567962 |                 |                       |
      | user1@dings.com | 0321293533 1234567962 | user2@dings.com | 7784484156 1126108624 |
