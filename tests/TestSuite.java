package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  JobControllerTest.class,
  ParksSystemTest.class,
  VolunteerControllerTest.class
})

public class TestSuite {

}