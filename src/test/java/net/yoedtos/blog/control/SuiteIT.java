package net.yoedtos.blog.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({SuitePublic.class, SuiteRestrict.class})
@RunWith(Suite.class)
public class SuiteIT {
	
}
