package util;

import org.hibernate.*;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.net.URI;

public class HibernateUtil {
	public static final SessionFactory sessionFactory;

	static {
		try {
			Configuration configuration = new Configuration().configure();
            final String DATABASE_URL = System.getenv("DATABASE_URL");
            if (DATABASE_URL != null && !DATABASE_URL.isEmpty()) {

                URI dbUri = new URI(DATABASE_URL);
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                System.out.println(dbUrl);
                configuration.setProperty("hibernate.connection.username", username);
                configuration.setProperty("hibernate.connection.password", password);
                configuration.setProperty("hibernate.connection.url", dbUrl);
                configuration.setProperty("connection.username", username);
                configuration.setProperty("connection.password", password);
                configuration.setProperty("connection.url", dbUrl);

                configuration.getProperties().list(System.out);
            }

			ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
			registry.applySettings(configuration.getProperties());
			ServiceRegistry serviceRegistry = registry.buildServiceRegistry();

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	// public static final ThreadLocal session = new ThreadLocal();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}