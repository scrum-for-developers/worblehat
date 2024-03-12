Feature: Borrowing borrowed and available books

  Scenario Outline: Borrowed books cannot be borrowed again

    Given a library, containing only books with isbns "<availableIsbn> <alreadyBorrowedIsbns>"
    And "earlybird@worblehat.dw" has borrowed books "<alreadyBorrowedIsbns>" on 2020-02-10

    When "<user>" borrows the books "<toBeBorrowedIsbns>"

    Then the booklist lists "<user>" as borrower only for the books with isbns "<toBeBorrowedIsbns>"
    And "<user>" gets the error "<message>", when trying to borrow the book with one of the "<toBeBorrowedIsbns>" again

    Examples:

      | availableIsbn                    | alreadyBorrowedIsbns | user          | toBeBorrowedIsbns | message                       |
      | 0552131075 0321293533 1234567962 | 123456789X           | user@test.com | 1234567962        | The book is already borrowed. |
