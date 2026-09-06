package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;

import java.util.HashSet;
import java.util.Set;

public class Registry {

    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 120;
    private static final int ADULT_AGE = 18;

    // Estado: ids ya registrados. Por eso @BeforeEach crea un Registry nuevo
    // en cada prueba (si no, una prueba contamina a la siguiente).
    private final Set<Integer> registrados = new HashSet<>();

    public RegisterResult registerVoter(Person p) {
        if (p == null) {
            return RegisterResult.INVALID; // R1
        }
        if (p.getId() <= 0) {
            return RegisterResult.INVALID; // R2
        }
        if (!p.isAlive()) {
            return RegisterResult.DEAD; // R3
        }
        if (p.getAge() < MIN_AGE || p.getAge() > MAX_AGE) {
            return RegisterResult.INVALID_AGE; // R4
        }
        if (p.getAge() < ADULT_AGE) {
            return RegisterResult.UNDERAGE; // R5
        }
        if (registrados.contains(p.getId())) {
            return RegisterResult.DUPLICATED; // R6
        }
        registrados.add(p.getId());
        return RegisterResult.VALID; // R7
    }
}