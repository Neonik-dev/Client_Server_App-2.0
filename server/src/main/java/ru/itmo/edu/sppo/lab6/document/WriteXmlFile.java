package ru.itmo.edu.sppo.lab6.document;

import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.CheckFile;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class WriteXmlFile {
    private static final String FILE_NAME_PROPERTIES = "files.output";
    private static final String fileName;
    private XMLStreamWriter xmlWriter;

    static {
        fileName = new ReadProperties().read(FILE_NAME_PROPERTIES);
    }

    public void writeFile(Printer printer) throws XMLStreamException {
        try {
            CheckFile.checkFileForWrite(fileName);
            xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(new FileWriter(fileName));

            writeContent();
            printer.println("Сохранение в файл прошло успешно");
        } catch (IncorrectDataEntryExceptions e) {
            printer.println(e.getMessage());
        } catch (XMLStreamException | IOException | FactoryConfigurationError ex) {
            printer.println("Произошла непредвиденная ошибка при сохранении коллекции в файл");
        } finally {
            if (xmlWriter != null) {
                xmlWriter.close();
            }
        }
    }

    public void writeContent() throws XMLStreamException {
        // Открываем XML-документ и записываем корневой элемент
        xmlWriter.writeStartDocument("1.0");
        xmlWriter.writeStartElement("ListMusicBand");
        for (MusicBand musicBand : MusicBandCollection.getMusicBandCollection()) {
            writeMusicBand(musicBand);
        }
        xmlWriter.writeEndElement(); // Закрываем корневой элемент
        xmlWriter.writeEndDocument(); // Закрываем XML-документ
        xmlWriter.flush();
    }

    private void writeMusicBand(MusicBand musicBand) throws XMLStreamException {
        xmlWriter.writeStartElement("MusicBand");
        // Заполняем все тэги для MusicBand
        writeXmlTag("id", Integer.toString(musicBand.getId()));
        writeXmlTag("name", musicBand.getName());

        xmlWriter.writeStartElement("coordinates");
        writeXmlTag("x", Double.toString(musicBand.getCoordinates().x()));
        writeXmlTag("y", Long.toString(musicBand.getCoordinates().y()));
        xmlWriter.writeEndElement();

        writeXmlTag("creationDate", musicBand.getCreationDate().toString());

        Optional<Long> participants = musicBand.getSafeNumberOfParticipants();
        if (participants.isEmpty()) {
            xmlWriter.writeEmptyElement("numberOfParticipants");
        } else {
            writeXmlTag("numberOfParticipants", Long.toString(participants.get()));
        }

        writeXmlTag("description", musicBand.getDescription());

        Optional<Date> date = musicBand.getSafeEstablishmentDate();
        if (date.isEmpty()) {
            xmlWriter.writeEmptyElement("establishmentDate");
        } else {
            writeXmlTag("establishmentDate", new SimpleDateFormat("dd.MM.yyyy").format(date.get()));
        }

        writeXmlTag("genre", musicBand.getGenre().toString());

        Optional<String> address = musicBand.getStudio().getAddress();
        xmlWriter.writeStartElement("studio");
        if (address.isEmpty()) {
            xmlWriter.writeEmptyElement("address");
        } else {
            writeXmlTag("address", address.get());
        }
        xmlWriter.writeEndElement();

        xmlWriter.writeEndElement(); // Закрываем тэг MusicBand
    }

    private void writeXmlTag(String nameTag, String value) throws XMLStreamException {
        xmlWriter.writeStartElement(nameTag);
        xmlWriter.writeCharacters(value);
        xmlWriter.writeEndElement();
    }
}
