package net.yoedtos.blog.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({ErrorHandlerIt.class, RegisterIt.class, ProfileIt.class, AuthIt.class})
@RunWith(Suite.class)
public class SuitePublic {
	
}

