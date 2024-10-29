package com.votaciones.back.service.util;

import com.github.javafaker.Faker;

public class FakeDataGenerator {

    private static final Faker faker = new Faker();

    public static String generarNombre() {
        return faker.name().firstName();
    }

    public static String generarApellido() {
        return faker.name().lastName();
    }

    public static String generarEmail() {
        return faker.internet().emailAddress();
    }

    public static String generarPasword() {
        return faker.internet().password();
    }
}
