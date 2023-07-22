package net.yoedtos.blog.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.yoedtos.blog.repository.dao.DaoHandler;

@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {}

    public void contextInitialized(ServletContextEvent e) {
    	DaoHandler.init();
    }

    public void contextDestroyed(ServletContextEvent e) {
    	DaoHandler.shutdown();
    }	
}
