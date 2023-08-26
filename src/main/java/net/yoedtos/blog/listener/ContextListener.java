package net.yoedtos.blog.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.yoedtos.blog.repository.dao.DaoHandler;
import net.yoedtos.blog.search.SearchEngine;

@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {}

    public void contextInitialized(ServletContextEvent e) {
    	DaoHandler.init();
    	DaoHandler.createAdminUser();
    	DaoHandler.createDemoData();
    	SearchEngine.createIndex(DaoHandler.getEntityManager());
    }

    public void contextDestroyed(ServletContextEvent e) {
    	DaoHandler.shutdown();
    }
}
