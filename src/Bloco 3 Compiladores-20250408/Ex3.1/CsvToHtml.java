import org.stringtemplate.v4.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvToHtml {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("table.csv"));
        String[] headers = lines.get(0).split(",");

        List<List<String>> rows = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            rows.add(Arrays.asList(values));
        }

        STGroup group = new STGroupFile("Table.stg");
        ST tableTemplate = group.getInstanceOf("table");
        tableTemplate.add("headers", Arrays.asList(headers));
        tableTemplate.add("rows", rows);

        String html = tableTemplate.render();
        System.out.println(html);
    }
}
