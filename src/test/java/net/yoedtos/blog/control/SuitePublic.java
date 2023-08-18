package net.yoedtos.blog.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({ErrorHandlerIt.class, MediaIt.class, RegisterIt.class, PostIt.class, ProfileIt.class, AuthIt.class})
@RunWith(Suite.class)
public class SuitePublic {
	
}

