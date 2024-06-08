package bg.softuni.cardealerexercise.util;

import jakarta.xml.bind.JAXBException;

public interface XmlParser {

    <E> E fromFile(Class<E> clazz,String filePath) throws JAXBException;

    <E> void exportToFile(Class<E>clazz,E object,String path) throws JAXBException;
}
