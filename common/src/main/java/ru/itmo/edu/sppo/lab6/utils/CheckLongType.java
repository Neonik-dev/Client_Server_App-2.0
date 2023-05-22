package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.exceptions.IncorrectLongTypeExceptions;

public class CheckLongType {
    public static long checkLong(String number) throws IncorrectLongTypeExceptions {
        if (number.split("[^\\d-]").length != 1) {
            throw new IncorrectLongTypeExceptions("Необходимо ввести целое число!");
        }
        boolean sing = number.charAt(0) == '-';
        if (sing && number.length() > 20) {
            throw new IncorrectLongTypeExceptions("Слишком маленькое число");
        } else if (!sing && number.length() > 19) {
            throw new IncorrectLongTypeExceptions("Слишком большое число");
        }

        long result;
        try {
            result = Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new IncorrectLongTypeExceptions("Слишком " + (sing ? "маленькое" : "большое") + " число");
        }
        return result;
    }
}
