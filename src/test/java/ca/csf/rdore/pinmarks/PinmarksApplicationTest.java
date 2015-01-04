package ca.csf.rdore.pinmarks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;

import org.junit.Before;

public class PinmarksApplicationTest {
  private final Environment environment = mock(Environment.class);
  private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
  
  @Before
  public void setup() throws Exception {
      when(environment.jersey()).thenReturn(jersey);
  }

}
