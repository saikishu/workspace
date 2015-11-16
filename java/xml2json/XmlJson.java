import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class XmlJson {

	//Some constants - REFER JSON documentation
	public static final int INDENT_FACTOR = 4;

	/**
	 * Program assumes students.xml and buildings.xml files are present at root of the program
	 */
	public static void main(String[] args) {

		String students = "";
		String buildings = "";
		String studentsJSONString = "";
		String buildingsJSONString = "";
		try {
			students = new Scanner(new File("students.xml")).useDelimiter("\\Z").next();
			buildings = new Scanner(new File("buildings.xml")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
            JSONObject studentsJSON  = XML.toJSONObject(students);
            studentsJSONString = studentsJSON.toString(INDENT_FACTOR);
            JSONObject buildingsJSON  = XML.toJSONObject(buildings);
            buildingsJSONString = buildingsJSON.toString(INDENT_FACTOR);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }

        try {
			PrintWriter s = new PrintWriter("students.json");
			s.println(studentsJSONString);
			s.flush();

			PrintWriter b = new PrintWriter("buildings.json");
			b.println(buildingsJSONString);
			b.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
