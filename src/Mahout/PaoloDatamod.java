package Mahout;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

public class PaoloDatamod extends FileDataModel {

	/**
	 * 
	 */
	MemoryIDMigrator mem=null;
	private static final long serialVersionUID = 1L;

	public PaoloDatamod(File dataFile) throws IOException {
		super(dataFile);
		// TODO Auto-generated constructor stub
	}

	@Override
		protected long readUserIDFromString(String stringID) {
		    long result = mem.toLongID(stringID); 
		    mem.storeMapping(result, stringID);
		    return result;
		}
	
}


