package dataProcessingDemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import joinery.DataFrame;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;


public class App 
{	
    public static void main( String[] args ) throws IOException
    {
    	//read text file using Common IO
    	String content = readAsStringFromCommonIO("src/main/resources/data.txt");
		System.out.println( content );
		
		List<String> lines = readAsLinesFromCommonIO("src/main/resources/data.txt");
		System.out.println( lines );
		
		//read CSV file using Common CSV
		List<Person> result = readCSVUsingCommonCSV("src/main/resources/people.csv");
		result.stream().forEach(p -> System.out.println(p.getName() +" | "+ p.getCountry()));
		
		//read CSV files to DataFrame using joinery
		testDataFrameUsingJoinery();
		
		//Perform EDA: summarization
		getSummary("src/main/resources/titanic.csv");
    }
    
    
    //read text file as String using Common IO
  	public static String readAsStringFromCommonIO(String filePath) throws IOException {
  		FileInputStream is = new FileInputStream(filePath);
  		String content = IOUtils.toString(is,StandardCharsets.UTF_8);
  		return content;
  	}
  	
    //read text file as List of Strings using Common IO
  	public static List<String> readAsLinesFromCommonIO(String filePath) throws IOException {
  		FileInputStream is = new FileInputStream(filePath);
  		List<String> lines = IOUtils.readLines(is, StandardCharsets.UTF_8);
  		return lines;
  	}
  	
  	//read CSV file using Common CSV
  	public static List<Person> readCSVUsingCommonCSV(String filePath) throws IOException{
  		//Path pathOfCsv = Paths.get(filePath);
  		BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
  		Iterator<CSVRecord> it = CSVFormat.RFC4180.withHeader().parse(reader).iterator();
//  		CSVFormat csv = CSVFormat.RFC4180.withHeader();
//  		CSVParser parser = csv.parse(reader);
//  		Iterator<CSVRecord> it = parser.iterator();
  		List<Person> result = new ArrayList<>();
  		it.forEachRemaining(rec -> {
  			String name = rec.get("name");
  			String email = rec.get("email");
  			String country = rec.get("country");
  			int salary = Integer.parseInt(rec.get("salary").substring(1).replace(",", ""));
  			Person p = new Person(name, email, country, salary);
  			result.add(p);
  		});
  		return result;
  	}
  	
  	//read CSV files to DataFrame using joinery
  	public static void testDataFrameUsingJoinery() throws IOException{
  		DataFrame<Object> df = DataFrame.readCsv("src/main/resources/people.csv");
  		List<Object> country = df.col("country");
  		System.out.println("list of size " + country.size());
  		System.out.println("list: " + country);
  		
  		df = DataFrame.readCsv("src/main/resources/vgsales.csv");
  		DataFrame<Object> df1 = df.retain("Year", "NA_Sales", "EU_Sales", "JP_Sales")
  				.groupBy(row -> row.get(0)).mean();
  		System.out.println(df1);
  	}
  	
  //Perform EDA: summarization
    public static void getSummary(String filePath) throws IOException {
    	Table titanicData = Table.read().csv(filePath);
    	System.out.println(titanicData.summary());
    	
		List<Column<?>> dataStructure = titanicData.columns();
		System.out.println(dataStructure);
		//table.addColumns(column);
		
//		double[] values = {1, 2, 3, 7, 9.4, 11};
//		DateColumn column = DateColumn.create("name");
//		DoubleColumn column2 = DoubleColumn.create("my numbers", values);
//		System.out.println(column2.print());
		
    }
}
