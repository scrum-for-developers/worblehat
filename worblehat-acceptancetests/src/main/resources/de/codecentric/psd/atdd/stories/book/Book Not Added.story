Narrative:
In order to keep a clean database of books
As a librarian
I want to have my entries validated

Scenario:

Given an empty library

When a librarian adds a book with title <title>, author <author>, edition <edition>, year <year> and isbn <isbn>
Then the page contains error message <message>
And The library contains no books

Examples:
 
| isbn       | author           | title     |edition    | year  | message                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| 0XXXXXXXX5 | Terry Pratchett  | Sourcery  | 1         | 1989  | The ISBN is not valid. A correct ISBN 10 number is for example 960-425-059-0. It consists of exactly 10 numbers. It begins with the registration group element which is a 1- to 5-digit number that is valid within a single prefix element (i.e. one of 978 or 979). The registrant element consisting of 1 to 7 digits and the publication element consisting of 1 to 6 digits follow. The last single number, a number between 0 and 9 or an X, is a checksum character or check digit. All groups of numbers can be divided by an -.     |
| 0552131075 | Terry Pratchett  | Sourcery  | X         | 1989  | The book edition is not valid. A correct book edition is not empty and numeric.                                                                                                                                                                                                                                                                                                                                                                                                                                                               |

