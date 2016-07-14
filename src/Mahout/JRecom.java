package Mahout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class JRecom {

	private GenericUserBasedRecommender recommender = null;
	private MemoryIDMigrator thing2long = new MemoryIDMigrator();
	private static String DATA_FILE_NAME = "Dataset.csv";
	private static DataModel dataModel;

	@PostConstruct
	public void initRecommender() {

		try {
			List<CSVRecord> records;

			Map<Long,List<BooleanPreference>> preferecesOfUsers = new HashMap<Long,List<BooleanPreference>>();

			// a CSV parser for reading the file
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withQuote(null);
			//CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(DATA_FILE_NAME), "UTF-8"));
			@SuppressWarnings("resource")
			CSVParser parser = new CSVParser(new FileReader(DATA_FILE_NAME),csvFileFormat);
				List<BooleanPreference> userPrefList;
records=parser.getRecords();
String person;
String likeName;
for(CSVRecord rec : records){
	person=rec.get(0);
	likeName=rec.get(1);
	long userLong = thing2long.toLongID(person);
	thing2long.storeMapping(userLong, person);
	long itemLong = thing2long.toLongID(likeName);
	thing2long.storeMapping(itemLong, likeName);
		
	if((userPrefList = preferecesOfUsers.get(userLong)) == null) {
		userPrefList = new ArrayList<BooleanPreference>();
		preferecesOfUsers.put(userLong, userPrefList);
	}
	userPrefList.add(new BooleanPreference(userLong, itemLong));
}
FastByIDMap<PreferenceArray> preferecesOfUsersFastMap = new FastByIDMap<PreferenceArray>();
for(Entry<Long, List<BooleanPreference>> entry : preferecesOfUsers.entrySet()) {
	preferecesOfUsersFastMap.put(entry.getKey(), new GenericUserPreferenceArray(entry.getValue()));
}

			dataModel = new GenericDataModel(preferecesOfUsersFastMap);	
			UserSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
			recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> recommendThings(String personName) throws TasteException {
		ArrayList<String> recommendations = new ArrayList<String>();
		try {
			List<RecommendedItem> items = recommender.recommend(thing2long.toLongID(personName), 20);
			for(RecommendedItem item : items) {
				recommendations.add(thing2long.toStringID(item.getItemID()));
			}
		} catch (TasteException e) {
			throw e;
		}
		String[] result=recommendations.toArray(new String[recommendations.size()]);
		System.out.println(result.length);
		PrintStream outFile;
		try {
			outFile = new PrintStream(new File("UserRecommendations.txt"));
			outFile.println("Utente piu' simile a te:"+ this.thing2long.toStringID(this.recommender.mostSimilarUserIDs(this.thing2long.toLongID(personName),1)[0]));
					for (String pippo : result) {
outFile.println(pippo);
}
	outFile.close();				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return recommendations;
	}

}