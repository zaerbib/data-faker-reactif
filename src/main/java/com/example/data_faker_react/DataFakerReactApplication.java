package com.example.data_faker_react;

import com.example.data_faker_react.utils.config.AppConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver;
import io.mongock.runner.springboot.EnableMongock;
import io.mongock.runner.springboot.MongockSpringboot;
import io.mongock.runner.springboot.base.MongockInitializingBeanRunner;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DataFakerReactApplication {

	private final AppConfig appConfig;

	public DataFakerReactApplication(AppConfig appConfig) {
		this.appConfig = appConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(DataFakerReactApplication.class, args);
	}

	@Bean
	@Qualifier("fakerExecutor")
	public Executor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(500);
		executor.setMaxPoolSize(1000);
		executor.setQueueCapacity(5000);
		executor.setThreadNamePrefix("faker-");
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

	/*@Bean
	public MongockInitializingBeanRunner getBuilder(MongoClient reactiveMongoClient,
													ApplicationContext context) {
		return MongockSpringboot.builder()
				.setDriver(MongoReactiveDriver.withDefaultLock(reactiveMongoClient, "datafakerdb"))
				.addMigrationScanPackage("com.example.data_faker_react.changeDb")
				.setSpringContext(context)
				.setTransactionEnabled(true)
				.buildInitializingBeanRunner();
	}*/

	@Bean
	MongoClient mongoClient() {
		CodecRegistry codecRegistry = fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		return MongoClients.create(MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(getUrl(appConfig)))
				.codecRegistry(codecRegistry)
				.build());
	}

	private String getUrl(AppConfig appConfig) {
		return "mongodb://" + appConfig.getMongodb().getHost() + ":" + appConfig.getMongodb().getPort();
	}
}
