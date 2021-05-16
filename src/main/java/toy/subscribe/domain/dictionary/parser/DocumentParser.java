package toy.subscribe.domain.dictionary.parser;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DocumentParser {
    
    public String read(String path) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)))) {
            String read;
            while((read = br.readLine()) != null) {
                sb.append(read).append("\n");
            }
        }
        catch(IOException e) {
            log.error(e.getMessage());
        }
        return sb.toString();
    }
    
    public Set<String> parsing(String html) throws IOException {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("<p>.*</p>");
        Matcher matcher = pattern.matcher(html);
        
        while(matcher.find()) {
            String s = " " + html.substring(matcher.start(), matcher.end())
                                 .replaceAll("<b>", "").replaceAll("</b>", "")
                                 .replaceAll("<a [^<>]*>", "").replaceAll("</a>", "")
                                 .replaceAll("<p>", "").replaceAll("</p>", "")
                                 .replaceAll("<sup [^<>]*>", "").replaceAll("</sup>", "")
                                 .replaceAll("<span [^<>]*>", "").replaceAll("</span>", "")
                                 .replaceAll("<i [^<>]*>>", "").replaceAll("</i>", "")
                                 .replaceAll("<table [^<>]*>>", "").replaceAll("</table>", "")
                                 .replaceAll("<block [^<>]*>>", "").replaceAll("</block>", "")
                                 .replaceAll("<ul [^<>]*>>", "").replaceAll("</ul>", "")
                                 .replaceAll("<li [^<>]*>>", "").replaceAll("</li>", "")
                                 .replaceAll("<div [^<>]*>>", "").replaceAll("</div>", "")
                                 .replaceAll("<h [^<>]*>>", "").replaceAll("</h>", "")
                                 .replaceAll("www\\.", "").replaceAll("http", "")
                                 .replaceAll("\\.com", "").replace(".", " ")
                                 .replaceAll("\\[[^\\[\\]]*\\]", "");
            
            String s1 = splitCamelCase(s);
            if(s1.contains(" ")) {
                String[] strings1 = s1.split(" ");
                for(String s2 : strings1) {
                    sb.append(s2).append(" ");
                }
            }
            else {
                sb.append(s);
            }
        }
        
        String s = sb.toString().trim().toLowerCase()
                     .replaceAll("[^a-zA-Z\\s\\.]", " ")
                     .replaceAll(" +", " ");
        
        InputStream inputStream = getClass()
                .getResourceAsStream("/models/en-token.bin");
        TokenizerModel model = new TokenizerModel(inputStream);
        TokenizerME tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize(s);
        
        Set<String> set = new HashSet<>();
        for(String s1 : tokens) {
            if(s1.length() > 2) {
                set.add(s1);
            }
        }
        return set;
    }
    
    private String splitCamelCase(String s) {
        return s.replaceAll(String.format("%s|%s|%s",
                                          "(?<=[A-Z])(?=[A-Z][a-z])",
                                          "(?<=[^A-Z])(?=[A-Z])",
                                          "(?<=[A-Za-z])(?=[^A-Za-z])"),
                            " ");
    }
    
}
