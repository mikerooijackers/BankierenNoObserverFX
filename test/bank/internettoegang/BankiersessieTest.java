/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import fontys.util.InvalidSessionException;
import java.rmi.RemoteException;
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
public class BankiersessieTest {

    private IBank b1;
    private int r1, r2;
    private IBankiersessie bs;

    public BankiersessieTest() throws RemoteException {
        b1 = new Bank("Test1");
        r1 = b1.openRekening("Mike", "Tilburg");
        r2 = b1.openRekening("Vincent", "Tilburg");

        bs = new Bankiersessie(r1, b1);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isGeldig method, of class Bankiersessie.
     *
     * @throws java.rmi.RemoteException
     * @throws fontys.util.InvalidSessionException
     * @returns true als de laatste aanroep van getRekening of maakOver voor
     * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
     * communicatiestoornis in de tussentijd is opgetreden, anders false
     */
    @Test
    public void testIsGeldig() throws RemoteException, InvalidSessionException {
        bs = new Bankiersessie(r1, b1);
        bs.getRekening();

        boolean bool = bs.isGeldig();
        assertTrue("Fout; Geldigheidsduur is nog niet verlopen", bool);
    }

    /**
     * Test of maakOver method, of class Bankiersessie.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testMaakOver() throws Exception {
        Money s1 = b1.getRekening(r1).getSaldo();
        Money s2 = b1.getRekening(r2).getSaldo();
        Money mPositief = new Money(1000, Money.EURO);
        Money mNegatief = new Money(-1000, Money.EURO);

        IBankiersessie bs = new Bankiersessie(r1, b1);
        boolean bool = bs.maakOver(r2, mPositief);

        assertEquals("Geld is niet van r1 afgeschreven", b1.getRekening(r1).getSaldo(), Money.sum(s1, mNegatief));
        assertEquals("Geld is niet op r2 gezet", b1.getRekening(r2).getSaldo(), Money.sum(s2, mPositief));
        assertTrue("Geld moet zijn overgemaakt", bool);
    }

    /**
     * Test of getRekening method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetRekening() throws Exception {
        // testen: zet Bankiersessie max geldigheid op 600
        Thread.sleep(1000);
        bs.getRekening();
        fail("Sessie is niet meer geldig");
    }

}
