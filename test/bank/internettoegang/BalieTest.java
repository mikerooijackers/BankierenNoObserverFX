/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import fontys.util.InvalidSessionException;
import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mike
 */
public class BalieTest {
    
    private IBank bank;
    private IBalie balie;
    
    public BalieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws RemoteException {
        this.bank = new Bank("Test");
        this.balie = new Balie(this.bank);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openRekening method, of class Balie.
     * @throws java.rmi.RemoteException
     * @throws fontys.util.InvalidSessionException
     */
    @Test
    public void testOpenRekening() throws RemoteException, InvalidSessionException {
        String br = this.balie.openRekening("", "Tilburg", "geheim");
        Assert.assertNull("Naam mag niet leeg zijn", br);

        br = this.balie.openRekening("Mike", "", "geheim");
        Assert.assertNull("Plaats mag niet leeg zijn", br);

        br = this.balie.openRekening("Mike", "Tilburg", "geh");
        Assert.assertNull("Wachtwoord moet 4 of meer karakers lang zijn", br);

        br = this.balie.openRekening("Mike", "Tilburg", "geheimpje");
        Assert.assertNull("Wachtwoord mag niet meer dan 8 karakters hebben", br);

        br = this.balie.openRekening("Mike", "Tilburg", "geheim");
        Assert.assertEquals("Accountnaam moet 8 karakters lang zijn", br.length(), 8);

        /* creatie van een nieuwe bankrekening; het gegenereerde bankrekeningnummer is
         * identificerend voor de nieuwe bankrekening en heeft een saldo van 0 euro
         */
        IBankiersessie bs = this.balie.logIn(br, "geheim");
        IRekening rek = bs.getRekening();

        Assert.assertEquals("Saldo moet 0,00 euro zijn", rek.getSaldo().getValue(), "0,00");
    }

    /**
     * Test of logIn method, of class Balie.
     * er wordt een sessie opgestart voor het login-account met de naam
     * accountnaam mits het wachtwoord correct is
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testLogIn() throws Exception {
        String br = this.balie.openRekening("Mike", "Tilburg", "geheim");

        IBankiersessie bs = this.balie.logIn(br, "verkeerd");
        Assert.assertNull("Wachtwoord mag niet verkeerd zijn", bs);

        bs = this.balie.logIn("Mike", "geheim");
        Assert.assertNull("Accountnaam mag niet verkeerd zijn", bs);

        bs = this.balie.logIn(br, "geheim");
        Assert.assertNotNull("Bankiersessie moet gecreeerd worden als de gegevens goed waren", bs);
    }
    
}
