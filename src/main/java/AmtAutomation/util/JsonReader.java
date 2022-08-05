package AmtAutomation.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class JsonReader {
    public static HashMap<String,String> configReader(String fileName) {
        JSONParser parser = new JSONParser();
        HashMap<String, String> data = new HashMap < String, String >();
        HashMap <String,String> operations = new HashMap <String,String>();
        String pathName = new File("").getAbsolutePath() + "/Config/";
        try {
            Object obj = parser.parse(new FileReader(pathName + fileName));
            JSONObject jsonObject = (JSONObject)obj;
            for (Object key : jsonObject.keySet())
            {
                data.put((String) key, (String) jsonObject.get(key));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }  finally {
            return data;
        }
    }
}
