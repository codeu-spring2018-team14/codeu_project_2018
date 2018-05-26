

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a User's Profile info. It is shown on a User's Profile Page */
public class UserProfile {
  private final UUID id;
  private String aboutMe;
  private final Instant creation;

  /**
   * Constructs a new UserProfile.
   *
   * @param id the ID of this User
   * @param aboutMe the aboutMe info of this User
   * @param creation the creation time of this User
   */
  public UserProfile(UUID id, String aboutMe, Instant creation) {
    this.id = id;
    this.aboutMe = aboutMe;
    this.creation = creation;
  }

  /** Returns the ID of this UserProfile. */
  public UUID getId() {
    return id;
  }

  /** Returns the aboutMe info of this User. */
  public String getAboutMe() {
    return aboutMe;
  }

  /** Returns the creation time of this UserProfile. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Sets the AboutMe info of this User. */
  public void setAboutMe(String aboutMe){
  	  this.aboutMe = aboutMe;
  }
}
