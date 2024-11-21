package com.votaciones.back.service.util;

import com.github.javafaker.Faker;

public class FakeDataGenerator {

    private static final Faker faker = new Faker();

    public static final String[] NOMBRES_DE_MUJER = {
            "Alicia", "Violeta", "Raquel", "Lorena", "Camila",
            "Sofía", "Valeria", "Florencia", "Paola", "Isabel",
            "Mariana", "Carolina", "Renata", "Beatriz", "Margarita",
            "Carmen", "Elisa", "Bárbara", "Lucía", "Susana",
            "Paola", "Julieta", "Luna", "Marta", "Inés",
            "Fabiola", "Emma", "Laura", "Sara", "Mireya",
            "Claudia", "Alba", "Andrea", "Catalina", "Nerea",
            "María José", "Lidia", "Rocío", "Jimena", "Anaís",
            "Valentina", "Miriam", "Celeste", "Patricia", "Eva",
            "Zoe", "Catalina", "Selena", "Juliana", "Sonia",
            "Elena", "Samantha", "Andrea", "Rita", "Cecilia",
            "Verónica", "Yasmina", "Milena", "Amalia", "Marisol"
    };


    public static final String[] NOMBRES_DE_HOMBRE = {
            "Adrián", "Luis", "Federico", "Oscar", "Ramón",
            "Damián", "Víctor", "Tomás", "Carlos", "Jaime",
            "Ricardo", "Pablo", "Eduardo", "Ángel", "Bernardo",
            "Iker", "Cristian", "Felipe", "Santiago", "Antonio",
            "Javier", "Héctor", "Samuel", "Ignacio", "Julio",
            "Rodrigo", "José", "Fernando", "Martín", "Leandro",
            "Nicolás", "Maximiliano", "Ernesto", "Antonio", "Ricardo",
            "Gustavo", "Manuel", "Raúl", "Marco", "César",
            "Julio", "Felipe", "Iván", "Xavier", "Raúl",
            "David", "Eduardo", "Víctor", "Braulio", "Guillermo",
            "Aitor", "Julian", "Álvaro", "Ismael", "Enrique",
            "Rafael", "Mauricio", "Mario", "José Luis", "Gerardo",
            "Felipe", "Matías", "Félix", "Leandro", "Yago",
            "Omar", "Adolfo", "Tobías", "Mauro", "Ricardo"
    };


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
