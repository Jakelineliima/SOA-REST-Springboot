package ws;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import org.hibernate.jpa.HibernatePersistenceProvider;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@SpringBootApplication(scanBasePackages = {"ws.security","ws.controller"})
@EntityScan(basePackages = {"ws.model"})
@EnableJpaRepositories(basePackages = {"ws.repository"})
@EnableAutoConfiguration
public class Aplicacao {
	
    private static final Logger log = LoggerFactory.getLogger(Aplicacao.class);	

	public static void main(String[] args) {
		//SpringApplication.run(App.class, args);
		HashMap<String, Object> props = new HashMap<>();
		props.put("server.port", 9000);

		new SpringApplicationBuilder()
		    .sources(Aplicacao.class)                
		    .properties(props)
		    .run(args);		
	}
    
    @Bean
    public javax.sql.DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/springrest");
        ds.setUsername("root");
        ds.setPassword("");
        return ds;
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.debug("Carregado");
            
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                log.debug(beanName);
            }
        };
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
 
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getClass().getPackage().getName());
        factory.setDataSource(dataSource());
        factory.setPersistenceProvider(new HibernatePersistenceProvider());
        Properties p = new Properties();
        factory.setJpaProperties(p);
 
        return factory;
    }

}
