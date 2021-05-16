package toy.subscribe.domain.dictionary.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.subscribe.common.config.properties.ApiProperties;
import toy.subscribe.domain.dictionary.parser.DocumentParser;
import toy.subscribe.domain.dictionary.parser.Translator;

@SpringBootTest(classes = {ApiProperties.class, DocumentParser.class, Translator.class, DictionaryServiceImpl.class})
class DictionaryServiceImplTest {
    
    @Autowired
    DictionaryService dictionaryService;
    
    @Test
    @Disabled(value = "API_호출_횟수제한")
    void create() {
        dictionaryService.create();
    }
    
}