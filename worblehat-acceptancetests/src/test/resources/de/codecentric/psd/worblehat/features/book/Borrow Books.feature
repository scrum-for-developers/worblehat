Feature: Borrowing borrowed and available books

    Scenario Outline: Borrowed books cannot be borrowed again

        Given a library, containing only books with isbns "<allIsbn>"

        When "<user>" borrows the books "<borrowedIsbns>"

        Then the booklist lists "<user>" as borrower only for the books with isbns "<borrowedIsbns>"
        And "<user>" gets the error "<message>", when trying to borrow the book with one of the "<borrowedIsbns>" again

        Examples:

            | allIsbn                          | user          | borrowedIsbns | message                       |
            | 0552131075 0321293533 1234567962 | user@test.com | 1234567962    | The book is already borrowed. |

