package toy.subscribe.domain.board.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentParserTest {
    
    @ParameterizedTest
    @DisplayName("Document_파싱")
    @MethodSource("whereDocuments")
    public void parsingDocumentationFromHTMLFile(String path) throws Exception {
        // when
        DocumentParser parser = new DocumentParser();
        String html = parser.read(path);
        Set<String> set = parser.parsing(html);
        
        // then
        assertThat(set).isNotNull();
        System.out.println("size = " + set.size());
        System.out.println(set);
    }
    
    // given
    private static Stream<Arguments> whereDocuments() {
        return Stream.of(
                Arguments.of("src/test/resources/springboot_document.html"),
                Arguments.of("src/test/resources/springsecurity_document.html")
                        );
    }
    
}
