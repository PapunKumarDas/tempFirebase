package net.infometry.firebaseconnector.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;

import com.google.api.services.storage.Storage.BucketAccessControls.List;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.infometry.firebaseconnector.User.User;
import shapeless.newtype;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
public class FirebaseOperations {
	FileInputStream serviceAccount = null;
	FirebaseOptions options = null;

	ArrayList<String> list = new ArrayList<String>();
	Map<String, String> map = new HashMap<String, String>();

	@MediaType(value = ANY, strict = false)
	public String Create(@Config FirebaseConfiguration configuration, @Connection FirebaseConnection connection,
			String child, String ID, String Name) throws InterruptedException {

		try {
			serviceAccount = new FileInputStream(configuration.getpath());
			options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl(configuration.geturl())
					.build();
		} catch (Exception e) {
			// TODO: handle exception
			return "Error occured..";
		}
		FirebaseApp.initializeApp(Objects.requireNonNull(options));

		User user = new User();

		user.setId(ID);
		user.setName(Name);
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

		ref.child(child).setValue(user,
				(databaseError, databaseReference) -> System.out.println("done writting to firebase"));
		return CreateMessage();
	}

	private String CreateMessage() {
		return "sucessfully created";
	}

	@MediaType(value = ANY, strict = false)
	public String Select(@Config FirebaseConfiguration configuration, @Connection FirebaseConnection connection)
			throws InterruptedException {
		try {
			serviceAccount = new FileInputStream(configuration.getpath());
			options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl(configuration.geturl())
					.build();
		} catch (Exception e) {
			// TODO: handle exception
			return "Error occured..";
		}
		FirebaseApp.initializeApp(Objects.requireNonNull(options));

		DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot datasnapshot) {
				// TODO Auto-generated method stub

				for (DataSnapshot ds : datasnapshot.getChildren()) {
					User value = ds.getValue(User.class);
					list.add(value.getId());
					list.add(value.getName());
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
			}
		});
		Thread.sleep(5000);
		return list.toString();
	}

}
