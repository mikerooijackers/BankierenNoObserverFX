/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike
 */
public class BankTest {
    
    private IBank bank1, bank2;
    private int rekening1, rekening2, rekening3;
    
    public BankTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bank1 = new Bank("test1");
        rekening1 = bank1.openRekening("Mike", "Tilburg");
        rekening2 = bank1.openRekening("Gino", "Tilburg");
        rekening3 = bank1.openRekening("Vincent", "Tilburg");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openRekening method, of class Bank.
     * Als city en Name leeg zijn fout melding geven
     */
    @Test
    public void testOpenRekening() {
        int rTest = bank1.openRekening("Mike", "Tilburg");
        boolean bool = bank1.getRekening(rekening1).getEigenaar().equals(bank1.getRekening(rTest).getEigenaar());
        assertTrue("De Klant objecten (eigenaar) moet hetzelfde zijn", bool);

        int openRekening = bank1.openRekening("", "Tilburg");
        assertEquals("Naam mag niet leeg zijn", openRekening, -1);

        openRekening = bank1.openRekening("Mike", "");
        assertEquals("Woonplaats mag niet leeg zijn", openRekening, -1);
    }

    /**
     * Test of getRekening method, of class Bank.
     */
    @Test
    public void testGetRekening() {
        assertNotNull("Rekeningnummer moet geen null zijn", bank1.getRekening(rekening1));
        assertNull("Rekeningnummer moet null zijn", bank1.getRekening(0));
        assertEquals("Rekening is niet geinitaliseerd",bank1.getRekening(rekening1).getNr(), rekening1);
    }

    /**
     * Test of maakOver method, of class Bank.
     */
    @Test
    public void testMaakOver() throws Exception {
        Money m1 = bank1.getRekening(rekening1).getSaldo();
        Money m2 = bank1.getRekening(rekening2).getSaldo();
        Money mPositief = new Money(1000, Money.EURO);
        Money mNegatief = new Money(-1000, Money.EURO);

        Boolean bool = bank1.maakOver(rekening1, rekening2, mPositief);

        assertEquals("Geld is niet van r1 afgeschreven", bank1.getRekening(rekening1).getSaldo(), Money.sum(m1, mNegatief));
        assertEquals("Geld is niet op r2 gezet", bank1.getRekening(rekening2).getSaldo(), Money.sum(m2, mPositief));
        assertTrue("Geld moet zijn overgemaakt", bool);
    }

    /**
     * Test of getName method, of class Bank.
     */
    @Test
    public void testGetName() {
        assertEquals("Hoort de naam 'test1' te hebben", bank1.getName(), "test1");
    }
    
}
