package net.infometry.firebaseconnector.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(FirebaseOperations.class)
@ConnectionProviders(FirebaseConnectionProvider.class)
public class FirebaseConfiguration {

  @Parameter
  private String path;
  @Parameter
  private String url;

  public String getpath(){
    return path;
  }
  public String geturl(){
	    return url;
	  }
}
