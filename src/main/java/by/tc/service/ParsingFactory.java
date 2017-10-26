package by.tc.service;

import by.tc.service.implementaion.XmlParsingService;

public final class ParsingFactory {

    private static final ParsingFactory instance = new ParsingFactory();

    private final ParsingService parsingService = new XmlParsingService();

    private ParsingFactory() {
    }

    public ParsingService getParsingService() {
        return parsingService;
    }

    public static ParsingFactory getInstance() {
        return instance;
    }

}
