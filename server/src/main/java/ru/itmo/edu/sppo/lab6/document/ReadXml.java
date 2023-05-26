package ru.itmo.edu.sppo.lab6.document;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryInFileExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.CheckFile;
import ru.itmo.edu.sppo.lab6.utils.CreateMusicBandFromFile;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Stack;

@Slf4j
public class ReadXml {
    private static final String ROOT_TAG = "ListMusicBand";
    private static final String ELEMENT_TAG = "MusicBand";
    private boolean flagExit = false;
    private final Stack<String> xmlTagFromFile = new Stack<>();
    private HashMap<String, String> coordinates;
    private HashMap<String, String> dataForMusicBand;

    public ReadXml() {
        clearData();
    }

    public void readFile(String fileName) throws XMLStreamException {
        XMLStreamReader xmlReader = null;

        try {
            CheckFile.checkFileForRead(fileName);
            xmlReader = XMLInputFactory.newInstance()
                    .createXMLStreamReader(fileName, new BufferedInputStream(new FileInputStream(fileName)));
            read(xmlReader);

            System.out.println("Чтение файла прошло успешно");
        } catch (IncorrectDataEntryInFileExceptions | IncorrectDataEntryExceptions | UnexpectedCommandExceptions e) {
            System.out.println(e.getMessage());
            exit();
        } catch (FileNotFoundException e) {
            System.out.println("Файл с таким именем не найден!");
            exit();
        } catch (XMLStreamException | FactoryConfigurationError e) {
            System.out.println("Неверная структура xml файла");
            exit();
        } finally {
            if (xmlReader != null) {
                xmlReader.close();
            }
        }
    }

    public void read(XMLStreamReader xmlReader) throws XMLStreamException, IncorrectDataEntryInFileExceptions,
            IncorrectDataEntryExceptions, UnexpectedCommandExceptions {
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (flagExit && !(xmlReader.getEventType() == xmlReader.END_DOCUMENT)) {
                throw new IncorrectDataEntryInFileExceptions("Должен быть только один корневой тег");
            }

            if (xmlReader.isStartElement()) {
                addTag(xmlReader.getLocalName().trim());
//                    System.out.println(xmlReader.getLocalName().trim());
            } else if (xmlReader.isEndElement()) {
                removeTag(xmlReader.getLocalName());
//                    System.out.println("/" + xmlReader.getLocalName());
            } else if (xmlReader.hasText() && xmlReader.getText().trim().length() > 0) {
                addText(xmlReader.getText());
//                    System.out.println("   " + xmlReader.getText());
            }
        }
    }

    private void exit() {
        clearData();
        System.exit(0);
    }

    private void addText(String text) throws IncorrectDataEntryInFileExceptions {
        String lastTag = xmlTagFromFile.peek();
        if (dataForMusicBand.containsKey(lastTag)) {
            checkMapNullValue(dataForMusicBand, lastTag);
            dataForMusicBand.put(lastTag, text);
        } else if (coordinates.containsKey(lastTag)) {
            checkMapNullValue(coordinates, lastTag);
            coordinates.put(lastTag, text);
        } else {
            checkMapNullValue(dataForMusicBand, lastTag);
            dataForMusicBand.put("studio", text);
        }
    }

    private void checkMapNullValue(HashMap<String, String> map, String tag) throws IncorrectDataEntryInFileExceptions {
        if (map.get(tag) != null) {
            throw new IncorrectDataEntryInFileExceptions("Несколько раз встречается одно и то же поле (" + tag + ")");
        }
    }

    private void removeTag(String tag) throws XMLStreamException, UnexpectedCommandExceptions,
            IncorrectDataEntryExceptions, IncorrectDataEntryInFileExceptions {
        if (xmlTagFromFile.empty() || !xmlTagFromFile.peek().equals(tag)) {
            throw new XMLStreamException();
        }
        if (ELEMENT_TAG.equals(tag)) {
            addMusicBand();
            clearData();
        }
        xmlTagFromFile.pop();
        if (xmlTagFromFile.empty()) {
            flagExit = true;
        }
    }

    private void addTag(String tag) throws IncorrectDataEntryInFileExceptions {
        if ((xmlTagFromFile.empty() && ROOT_TAG.equals(tag))) {
        } else if (xmlTagFromFile.peek().equals("studio")) {
            if (!tag.equals("address")) {
                throw new IncorrectDataEntryInFileExceptions("В файле неверно указана студия");
            }
        } else if (xmlTagFromFile.peek().equals("coordinates")) {
            if (!coordinates.containsKey(tag)) {
                throw new IncorrectDataEntryInFileExceptions("В файле неверно указаны координаты");
            }
        } else if ((xmlTagFromFile.peek().equals(ROOT_TAG) && ELEMENT_TAG.equals((tag)))
                || (dataForMusicBand.containsKey(tag))) {
        } else {
            throw new IncorrectDataEntryInFileExceptions("В файле указан несуществующий тег");
        }
        xmlTagFromFile.push(tag);
    }

    private void addMusicBand() throws UnexpectedCommandExceptions, IncorrectDataEntryExceptions,
            IncorrectDataEntryInFileExceptions {
        MusicBand musicBand = new CreateMusicBandFromFile().createFromFile(dataForMusicBand, coordinates);
        MusicBandCollection.addFromServerFile(musicBand);
    }

    private void clearData() {
        dataForMusicBand = new HashMap<>() {{
            put("id", null);
            put("name", null);
            put("creationDate", null);
            put("numberOfParticipants", null);
            put("coordinates", null);
            put("description", null);
            put("establishmentDate", null);
            put("genre", null);
            put("studio", null);
        }};
        coordinates = new HashMap<>() {{
            put("x", null);
            put("y", null);
        }};
    }
}
