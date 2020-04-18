import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;

public class MainClass {

    private static int loopCount = 0;

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();
        try {
            //change absolute path according to the location of saved file
            Object obj = parser.parse(new FileReader("D:\\Eclipse Workspace\\SmartCodersAssignment\\src\\foodyo_output.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject body = (JSONObject) jsonObject.get("body");
            JSONArray jsonArray = (JSONArray)body.get("Recommendations");

            Iterator i = jsonArray.iterator();
            while (i.hasNext()) {
                JSONObject resObj = (JSONObject) i.next();
                String name = (String)resObj.get("RestaurantName");
                System.out.println(name);
                JSONArray menuArray = (JSONArray)resObj.get("menu");

                Iterator i1 = menuArray.iterator();

                while(i1.hasNext()) {
                    JSONObject menuChildObj = (JSONObject) i1.next();
                    if(menuChildObj.get("type").equals("sectionheader")) {
                        JSONArray childArray = (JSONArray)menuChildObj.get("children");
                        Iterator i2 = childArray.iterator();

                        while(i2.hasNext()) {
                            JSONObject childObj = (JSONObject) i2.next();
                            long isSelected = (long)childObj.get("selected");
                            if(isSelected == 1 && childObj.get("type").equals("item")) {
                                System.out.println("-->" + childObj.get("name"));
                                loopCount = 0;
                                JSONArray childrenArray = (JSONArray)childObj.get("children");
                                Iterator i3 = childrenArray.iterator();
                                loopChildren(i3);
                            }
                        }

                    }

                }

            }
        }catch(IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loopChildren(Iterator i) {
        loopCount++;
        while(i.hasNext()) {
            JSONObject child = (JSONObject)i.next();
            long isSelected = (long)child.get("selected");
            if(isSelected == 1){
                for(int j=0;j<loopCount;j++)
                    System.out.print("--");
                System.out.println("-->" + child.get("name"));
                JSONArray childArray = (JSONArray)child.get("children");
                Iterator i1 = childArray.iterator();
                loopChildren(i1);
            }
        }
    }

}
