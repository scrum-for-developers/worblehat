/// <reference types="Cypress" />

beforeEach(() => {
    cy.visit('http://localhost:8080/worblehat/')   
})

describe('worblehat', () => {
it('Can open worblehat', () => {    
    cy.get('#welcome_heading > span:nth-child(1)').should('contain', 'Worblehat Bookmanager')
})

it('Should list all books', () => {
    cy.get('body > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)').click()
    cy.get('.bookList > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1)')
    .should('contain', 'Harry Potter and the Deathly Hallows')

})

it('Should return to main menu from booklist', () => {
    cy.get('body > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)').click()
    cy.get('body > a:nth-child(3)').click()
    cy.get('#welcome_heading > span:nth-child(1)').should('contain', 'Worblehat Bookmanager')
})

it('should accept a valid new book', () => {
    cy.visit('http://localhost:8080/worblehat/insertBooks')
    cy.get('#title').type('Jurassic Park')
    cy.get('#edition').type('1')
    cy.get('#isbn').type('3-426-19290-X')
    cy.get('#author').type('Michael Chrichton')
    cy.get('#yearOfPublication').type('1990')
    cy.get('#addBook').click()
})

})