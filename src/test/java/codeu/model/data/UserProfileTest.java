package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class UserProfileTest {

  @Test
  public void testCreate() {
    UUID id = UUID.randomUUID();
    String aboutMe = "test_about_me";
    Instant creation = Instant.now();

    UserProfile user_profile = new UserProfile(id, aboutMe, creation);

    Assert.assertEquals(id, user_profile.getId());
    Assert.assertEquals(aboutMe, user_profile.getAboutMe());
    Assert.assertEquals(creation, user_profile.getCreationTime());
  }
}