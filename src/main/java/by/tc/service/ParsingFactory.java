package by.tc.service;

import by.tc.service.implementaion.XmlParsingService;

public final class ParsingFactory {

    private static final ParsingFactory instance = new ParsingFactory();

    private final ParsingService parsingFactory = new XmlParsingService();

    private ParsingFactory() {
    }

    public ParsingService getParsingFactory() {
        return parsingFactory;
    }

    public static ParsingFactory getInstance() {
        return instance;
    }

}
