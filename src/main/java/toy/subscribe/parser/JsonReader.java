package toy.subscribe.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import toy.subscribe.factory.appendices.Company;

@Slf4j
public class JsonReader {

    public ArrayList<String> readUrls() {
        JSONObject jsonObj = null;
        try {
            FileReader reader = new FileReader(createInputStreamToFile());
            JSONParser parser = new JSONParser();
            jsonObj = (JSONObject) parser.parse(reader);
        } catch (IOException e) {
            log.error("Read not json !");
        } catch (ParseException e) {
            log.error("Json parsing error !");
        }
        if (jsonObj != null) {
            return (ArrayList<String>) jsonObj.get("urls");
        } else {
            return null;
        }
    }

    public ArrayList<Company> readCompanies() {
        ArrayList<Company> companies = null;
        JSONArray jsonCompanies = null;

        try {
            FileReader reader = new FileReader(createInputStreamToFile());
            JSONParser parser = new JSONParser();

            companies = new ArrayList<>();
            JSONObject jsonObj = (JSONObject) parser.parse(reader);
            jsonCompanies = (JSONArray) jsonObj.get("companies");
        } catch (IOException e) {
            log.error("Read not json !");
            return null;
        } catch (ParseException e) {
            log.error("Json parsing error !");
            return null;
        }

        if (jsonCompanies != null) {
            for (int i = 0; i < jsonCompanies.size(); i++) {
                JSONObject o = (JSONObject) jsonCompanies.get(i);
                companies.add(new Company(
                        (String) o.get("key"),
                        (String) o.get("name"),
                        (String) o.get("imgPath"))
                );
            }

            return companies;
        } else {
            return null;
        }
    }

    private static File createInputStreamToFile() throws IOException {
        InputStream inputStream = new ClassPathResource("static/properties/propertiesFactory.json").getInputStream();
        File file = File.createTempFile("propertiesFactory", ".json");
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return file;
    }
    
}
