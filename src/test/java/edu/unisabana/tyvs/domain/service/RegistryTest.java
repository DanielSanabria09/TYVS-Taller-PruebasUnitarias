package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Gender;
import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistryTest {

    private Registry registry;

    @BeforeEach
    void setUp() {
        registry = new Registry();
    }

    // ---- Iteración 1 ----
    @Test
    @DisplayName("Una persona viva y mayor de edad queda registrada")
    void shouldRegisterValidPerson() {
        Person person = new Person("Ana", 1, 30, Gender.FEMALE, true);
        assertEquals(RegisterResult.VALID, registry.registerVoter(person));
    }

    // ---- Iteración 2 ----
    @Test
    @DisplayName("Una persona no viva se rechaza con DEAD")
    void shouldRejectDeadPerson() {
        Person dead = new Person("Carlos", 2, 40, Gender.MALE, false);
        assertEquals(RegisterResult.DEAD, registry.registerVoter(dead));
    }

    // ---- Iteración 3: nulidad ----
    @Test
    @DisplayName("Una persona nula se rechaza con INVALID")
    void shouldReturnInvalidWhenPersonIsNull() {
        assertEquals(RegisterResult.INVALID, registry.registerVoter(null));
    }

    // ---- Iteración 4: id inválido ----
    @Test
    @DisplayName("Un id cero o negativo se rechaza con INVALID")
    void shouldRejectWhenIdIsZeroOrNegative() {
        Person idCero = new Person("Luis", 0, 25, Gender.MALE, true);
        Person idNegativo = new Person("Marta", -5, 25, Gender.FEMALE, true);

        assertEquals(RegisterResult.INVALID, registry.registerVoter(idCero));
        assertEquals(RegisterResult.INVALID, registry.registerVoter(idNegativo));
    }

    // ---- Iteración 5: edad (bordes) ----
    @Test
    @DisplayName("Edad -1 se rechaza con INVALID_AGE (borde inferior)")
    void shouldRejectInvalidAgeBelowZero() {
        Person p = new Person("X", 10, -1, Gender.UNIDENTIFIED, true);
        assertEquals(RegisterResult.INVALID_AGE, registry.registerVoter(p));
    }

    @Test
    @DisplayName("Edad 121 se rechaza con INVALID_AGE (borde superior)")
    void shouldRejectInvalidAgeOver120() {
        Person p = new Person("X", 11, 121, Gender.UNIDENTIFIED, true);
        assertEquals(RegisterResult.INVALID_AGE, registry.registerVoter(p));
    }

    @Test
    @DisplayName("Edad 17 se rechaza con UNDERAGE (borde inferior de adultez)")
    void shouldRejectUnderageAt17() {
        Person p = new Person("Sofia", 12, 17, Gender.FEMALE, true);
        assertEquals(RegisterResult.UNDERAGE, registry.registerVoter(p));
    }

    @Test
    @DisplayName("Edad 18 se acepta como VALID (borde de adultez)")
    void shouldAcceptAdultAt18() {
        Person p = new Person("Andres", 13, 18, Gender.MALE, true);
        assertEquals(RegisterResult.VALID, registry.registerVoter(p));
    }

    @Test
    @DisplayName("Edad 120 se acepta como VALID (borde superior biológico)")
    void shouldAcceptMaxAge120() {
        Person p = new Person("Rosa", 14, 120, Gender.FEMALE, true);
        assertEquals(RegisterResult.VALID, registry.registerVoter(p));
    }

    // ---- Iteración 6: duplicados ----
    @Test
    @DisplayName("El mismo id no puede registrarse dos veces")
    void shouldRejectDuplicatedId() {
        Person primeraVez = new Person("Pedro", 100, 30, Gender.MALE, true);
        Person segundaVez = new Person("Pedro", 100, 30, Gender.MALE, true);

        assertEquals(RegisterResult.VALID, registry.registerVoter(primeraVez));
        assertEquals(RegisterResult.DUPLICATED, registry.registerVoter(segundaVez));
    }
}