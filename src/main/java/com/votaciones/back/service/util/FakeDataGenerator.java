package com.votaciones.back.service.util;

import com.github.javafaker.Faker;

public class FakeDataGenerator {

    private static final Faker faker = new Faker();

    public static final String[] NOMBRES_DE_MUJER = {
            "Ana", "Beatriz", "Camila", "Diana", "Elena",
            "Fernanda", "Gabriela", "Isabel", "Juana", "Karla",
            "Lucía", "María", "Natalia", "Olivia", "Patricia",
            "Rosa", "Sandra", "Teresa", "Valentina", "Yolanda",
            "Adriana", "Alicia", "Alejandra", "Andrea", "Ariana",
            "Blanca", "Carla", "Claudia", "Daniela", "Emilia",
            "Estefanía", "Eva", "Fabiola", "Gloria", "Graciela",
            "Irma", "Ivanna", "Jacqueline", "Jennifer", "Jessica",
            "Julia", "Julieta", "Laura", "Lourdes", "Lorena",
            "Margarita", "Martha", "Monica", "Miranda", "Noelia",
            "Norma", "Pamela", "Paola", "Rebeca", "Regina",
            "Rocío", "Sofía", "Susana", "Tamara", "Verónica",
            "Victoria", "Virginia", "Zaira", "Zulema", "Abril",
            "Carolina", "Consuelo", "Cristina", "Delia", "Esperanza",
            "Fátima", "Florencia", "Guadalupe", "Inés", "Ivette",
            "Jimena", "Lilia", "Liliana", "Luciana", "Maite",
            "Malena", "Mariana", "Marisol", "Miriam", "Norma",
            "Pilar", "Renata", "Romina", "Silvia", "Tatiana",
            "Vanesa", "Violeta", "Yamila", "Yesenia", "Zaida"
    };

    public static final String[] NOMBRES_DE_HOMBRE = {
            "Antonio", "Carlos", "David", "Eduardo", "Francisco",
            "Gabriel", "Héctor", "Ignacio", "Javier", "José",
            "Juan", "Luis", "Manuel", "Marcos", "Miguel",
            "Pedro", "Rafael", "Ricardo", "Roberto", "Samuel",
            "Sergio", "Tomás", "Víctor", "Ángel", "Adrián",
            "Alberto", "Alexis", "Andrés", "Ángel", "Braulio",
            "César", "Cristian", "Daniel", "Diego", "Emilio",
            "Enrique", "Felipe", "Fernando", "Francisco", "Gael",
            "Gonzalo", "Héctor", "Iker", "Iván", "Joaquín",
            "Jorge", "José Luis", "Julio", "Kevin", "Leandro",
            "Leonardo", "Luis Miguel", "Manuel", "Marco", "Mariano",
            "Nicolás", "Óscar", "Pablo", "Raúl", "Ricardo",
            "Rodrigo", "Rubén", "Salvador", "Santiago", "Saúl",
            "Teodoro", "Vicente", "Xavier", "Yago", "Zacarías",
            "Zuley", "Antonio", "Baltasar", "Felipe", "César"
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
