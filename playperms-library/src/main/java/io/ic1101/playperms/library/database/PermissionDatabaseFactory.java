package io.ic1101.playperms.library.database;

import io.ic1101.playperms.library.config.DatabaseConfig;
import io.ic1101.playperms.library.database.model.Permission;
import io.ic1101.playperms.library.database.model.PermissionGroup;
import io.ic1101.playperms.library.database.model.PermissionUser;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PermissionDatabaseFactory {

  @Getter private SessionFactory sessionFactory;

  public void createSessionFactory(DatabaseConfig databaseConfig) {
    Configuration configuration = new Configuration();

    configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

    String connectionUrl =
        "jdbc:mysql://"
            + databaseConfig.getHost()
            + ":"
            + databaseConfig.getPort()
            + "/"
            + databaseConfig.getDatabase();

    configuration.setProperty("hibernate.connection.url", connectionUrl);

    configuration.setProperty("hibernate.connection.username", databaseConfig.getUsername());
    configuration.setProperty("hibernate.connection.password", databaseConfig.getPassword());

    configuration.setProperty("hibernate.show_sql", String.valueOf(databaseConfig.isShowSql()));
    configuration.setProperty("hibernate.format_sql", String.valueOf(databaseConfig.isFormatSql()));
    configuration.setProperty("hibernate.hbm2ddl.auto", databaseConfig.getHbmDdlAuto());

    configuration.addAnnotatedClass(PermissionGroup.class);
    configuration.addAnnotatedClass(Permission.class);
    configuration.addAnnotatedClass(PermissionUser.class);

    this.sessionFactory = configuration.buildSessionFactory();
  }
}
