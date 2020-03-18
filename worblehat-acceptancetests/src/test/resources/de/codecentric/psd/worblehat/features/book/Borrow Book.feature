Feature: Borrowing borrowed and available books

  Scenario Outline: Borrowed books cannot be borrowed again

    Given a library, containing a book with isbn "<isbn>"

    When "<user>" borrows the book "<isbn>"

    Then the booklist lists "<user>" as borrower for the book with isbn "<isbn>"
    And I get an error "<message>", when "<user>" tries to borrow the book with isbn "<isbn>" again

    Examples:

      | isbn       | user          | message                       |
      | 0552131075 | user@test.com | The book is already borrowed. |

