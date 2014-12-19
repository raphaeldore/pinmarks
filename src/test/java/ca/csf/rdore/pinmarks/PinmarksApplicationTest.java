package ca.csf.rdore.pinmarks;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class PinmarksApplicationTest {
  private final Environment environment = mock(Environment.class);
  private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
  private final PinmarksApplication application = new PinmarksApplication();
  private final PinmarksConfiguration config = new PinmarksConfiguration();
  
  @Before
  public void setup() throws Exception {
      //config.setMyParam("yay");
    
      when(environment.jersey()).thenReturn(jersey);
  }

  @Test
  public void buildsAThingResource() throws Exception {
      // application.run(config, environment);

      //verify(jersey).register(any(ThingResource.class));
  }

}
