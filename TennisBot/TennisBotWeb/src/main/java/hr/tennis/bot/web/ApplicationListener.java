package hr.tennis.bot.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener  {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		System.out.println("context destroyed!");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {


		System.out.println("context initialized!");

	}

}
