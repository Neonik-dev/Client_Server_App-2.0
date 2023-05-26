package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicGenre;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;

import java.util.Arrays;
import java.util.Scanner;

public class CreateMusicBand {
    private final ValidationMusicBand musicBandValidation;

    public CreateMusicBand(ValidationMusicBand musicBandValidation) {
        this.musicBandValidation = musicBandValidation;
    }

    public MusicBand create(Scanner inputScanner) throws UnexpectedCommandExceptions {
        MusicBand musicBand = new MusicBand();

        while (true) {
            try {
                System.out.print("Введите название музыкальной группы: ");
                musicBand.setName(
                        musicBandValidation.validateName(inputScanner.nextLine())
                );
                break;
            } catch (IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Введите координаты (в формате: долгота широта): ");
                musicBand.setCoordinates(
                        musicBandValidation.validateCoordinates(inputScanner.nextLine().split(" "))
                );
                break;
            } catch (IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Введите количество участников группы: ");
                musicBand.setNumberOfParticipants(
                        musicBandValidation.validateNumberOfParticipants(inputScanner.nextLine())
                );
                break;
            } catch (IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Опишите группу: ");
                musicBand.setDescription(
                        musicBandValidation.validateDescription(inputScanner.nextLine())
                );
                break;
            } catch (IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Введите дату создания группы (в формате дд.мм.гггг): ");
                musicBand.setEstablishmentDate(
                        musicBandValidation.validateEstablishmentDate(inputScanner.nextLine())
                );
                break;
            } catch (IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.printf(
                        "Выберите из списка музыкальный жанр группы %s: ",
                        Arrays.toString(MusicGenre.values())
                );
                musicBand.setGenre(
                        musicBandValidation.validateGenre(inputScanner.nextLine())
                );
                break;
            } catch (IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Введите адрес студии группы: ");
        musicBand.setStudio(
                musicBandValidation.validateStudio(inputScanner.nextLine())
        );

        return musicBand;
    }
}
