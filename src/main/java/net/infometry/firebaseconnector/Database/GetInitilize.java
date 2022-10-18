package net.infometry.firebaseconnector.Database;

import java.io.FileInputStream;
import java.util.Objects;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class GetInitilize
{
	 public FileInputStream serviceAccount = null;
	public FirebaseOptions options = null;
	
	public void initilize() {
    	try {

    		serviceAccount = new FileInputStream("./serviceAccountKey.json");
    		options = new FirebaseOptions.Builder()
    		.setCredentials (GoogleCredentials.fromStream(serviceAccount) )
    		.setDatabaseUrl("https://fir-demo-a6356-default-rtdb.firebaseio.com/")
    		.build();
    		}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	
	}

}
