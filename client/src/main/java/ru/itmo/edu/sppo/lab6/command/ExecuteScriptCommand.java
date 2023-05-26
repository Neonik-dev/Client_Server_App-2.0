package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.InputHandler;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.CheckFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScriptCommand implements BaseCommand {
    private static final String NAME = "execute_script";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " file_name -> считывает и выполняет скрипт из указанного файла";
    }

    @Override
    public void execute(String[] args) throws IncorrectDataEntryExceptions {
        try {
            checkArgs(args);
            Scanner keepOldScanner = InputHandler.getScanner();
            new InputHandler().startInputHandler(new Scanner(new BufferedInputStream(new FileInputStream(args[0]))));
            InputHandler.setScanner(keepOldScanner);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 1) {
            throw new IncorrectDataEntryExceptions("Необходимо ввести один аргумент -> file_name");
        }
        CheckFile.checkFileForRead(args[0]);
    }
}
