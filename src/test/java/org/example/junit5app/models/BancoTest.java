package org.example.junit5app.models;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BancoTest {

    @BeforeAll // se ejecuta una vez durante el test
    static void beforeAll(){
        //static porque no esta asociado a la instancia
        System.out.println("Inicializando el test");
    }

    @AfterAll
    static void afterAll() { //Se usa para cerrar recursos
        System.out.println("Finalizando el test");
    }

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.236632"));

        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta, new BigDecimal("100"));

        assertEquals("1400.236632", cuenta2.getSaldo().toPlainString());
        assertEquals("2600", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.236632"));

        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);

        assertEquals(2, banco.getCuentas().size());
        assertEquals("Banco del estado", cuenta.getBanco().getNombre());
        assertEquals("Andres", banco.getCuentas()
                .stream()
                .filter(c -> c.getPersona().equals("Andres"))
                .findFirst()
                .get()
                .getPersona());
        assertTrue(banco.getCuentas()
                .stream()
                .filter(c -> c.getPersona().equals("Andres"))
                .findFirst()
                .isPresent());
        //o
        assertTrue(banco.getCuentas()
                .stream()
                .anyMatch(c -> c.getPersona().equals("Andres")));
    }

    //Cunado se tienen muchos assert
    @Test
    void testRelacionBancoCuentasAssertAll() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.236632"));

        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);

        assertAll(
                 () -> assertEquals(2, banco.getCuentas().size()),
                () -> assertEquals("Banco del estado", cuenta.getBanco().getNombre()),
                () -> assertEquals("Andres", banco.getCuentas()
                        .stream()
                        .filter(c -> c.getPersona().equals("Andres"))
                        .findFirst()
                        .get()
                        .getPersona()),
                () -> assertTrue(banco.getCuentas()
                        .stream()
                        .filter(c -> c.getPersona().equals("Andres"))
                        .findFirst()
                        .isPresent()),
                () -> assertTrue(banco.getCuentas()
                        .stream()
                        .anyMatch(c -> c.getPersona().equals("Andres")))
        );


        //o

    }
}