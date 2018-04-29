package codeu.model.store.basic;

import codeu.model.data.UserProfile;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class UserProfileStore {

  /** Singleton instance of UserStore. */
  private static UserProfileStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static UserProfileStore getInstance() {
    if (instance == null) {
      instance = new UserProfileStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static UserProfileStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new UserProfileStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Users. */
  private List<UserProfile> userProfiles;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private UserProfileStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    userProfiles = new ArrayList<>();
  }

  /** Load a set of randomly-generated Message objects. */
  public boolean loadTestData() {
    boolean loaded = false;
    try {
      userProfiles.addAll(DefaultDataStore.getInstance().getAllUserProfiles());
      loaded = true;
    } catch (Exception e) {
      loaded = false;
      System.out.println("ERROR: Unable to establish initial store (userProfiles).");
    }
    return loaded;
  }


  /**
   * Access the User object with the given UUID.
   *
   * @return null if the UUID does not match any existing User.
   */
  public UserProfile getUserProfile(UUID id) {
    for (UserProfile userProfile : userProfiles) {
      if (userProfile.getId().equals(id)) {
        return userProfile;
      }
    }
    return null;
  }

  /** Add a new user to the current set of users known to the application. */
  public void addUserProfile(UserProfile userProfile) {
    userProfiles.add(userProfile);
    persistentStorageAgent.writeThrough(userProfile);
  }

 
  /**
   * Sets the List of Users stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUserProfiles(List<UserProfile> userProfiles) {
    this.userProfiles = userProfiles;
  }
}
