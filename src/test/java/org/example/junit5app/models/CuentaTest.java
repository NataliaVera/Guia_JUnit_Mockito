package org.example.junit5app.models;

import org.example.junit5app.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class CuentaTest {

    Cuenta cuenta;

    @BeforeEach //antes de ejecutarse cada metodo
    void initMethodTest(){
        cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        System.out.println("Iniciando el método");
    }

    @AfterEach //despues de ejecutarse cada metodo
    void tearDown() {
        System.out.println("Finalizando método");
    }

    @Test
    @DisplayName("Probando nombre de la cuenta")
    void testNombreCuenta() {
//        cuenta.setPersona("Andres");
        String esperado = "Andres";
        String real = cuenta.getPersona();
        assertNotNull(real, "La cuenta no puede ser nula");
        assertEquals(esperado, real, "El nombre de la cuenta no es el esperado");
        assertEquals("Andres", real, "Nombre de la cuenta esperada debe ser igual a la real");
    }

    @Test
    @DisplayName("Probando saldo de la cuenta")
    void testSaldoCuenta() {
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @Disabled //Deshabilita una prueba
    @DisplayName("Testeando referencias que sean iguales con el método equals")
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.3336"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.3336"));

        assertNotEquals(cuenta2, cuenta); //por referencia
        assertEquals(cuenta2, cuenta); //por instancia
    }

    @Test
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteCuentaException() {
        //Lanzar una excepcion
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente.";
        assertEquals(esperado, actual);
    }

    @Test
    @DisplayName("Test saldo cuenta DEV ")
    void testSaldoCuentaDev() {
        //Solo se va a ejecutar cuando este en el ambiente desarrollo {
        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumeFalse(isDev);
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @RepeatedTest(2) //Cantidad de veces que se va a repetir un test
    void testDebitoCuenta2() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }
}