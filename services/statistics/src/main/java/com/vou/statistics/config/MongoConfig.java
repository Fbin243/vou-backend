// // package com.vou.statistics.config;

// // import org.springframework.beans.factory.annotation.Value;
// // import org.springframework.context.annotation.Bean;
// // import org.springframework.context.annotation.Configuration;
// // import org.springframework.data.mongodb.core.MongoTemplate;
// // import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
// // import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
// // import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
// // import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

// // @Configuration
// // public class MongoDBConfiguration {

// //     @Value("${spring.data.mongodb.uri}")
// //     private String mongoUri;

// //     @Value("${spring.data.mongodb.database}")
// //     private String databaseName;

// //     @Bean
// //     public MongoTemplate mongoTemplate() {
// //         // Create a factory using the URI and database name separately
// //         SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoUri);

// //         // Set up the converter, optionally remove the _class field
// //         MappingMongoConverter converter = new MappingMongoConverter(factory, new MongoMappingContext());
// //         converter.setTypeMapper(new DefaultMongoTypeMapper(null));

// //         // Create and return the MongoTemplate
// //         return new MongoTemplate(factory, converter);
// //     }
// // }

// package com.vou.statistics.config;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
// import org.springframework.data.mongodb.core.MongoTemplate;


// @Configuration
// public class MongoConfig extends AbstractMongoClientConfiguration {

//     @Override
//     protected String getDatabaseName() {
//         return "vou";
//     }

//     // @Override
//     // @Bean
//     // public MongoClient mongoClient() {
//     //     return MongoClients.create("mongodb+srv://ntbinh243dev:rPNKlCQIypFEDJrF@vou.hvasc.mongodb.net/vou");
//     // }

//     // public @Bean
//     // MongoTemplate mongoTemplate() throws Exception{
//     //     MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
//     //     return mongoTemplate;
//     // }

//     @Override
//     protected boolean autoIndexCreation() {
//         return true;
//     }
// }

// package com.vou.statistics.config;

// import com.mongodb.client.MongoClients;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

// @Configuration
// public class MongoConfig {

//     @Bean
//     public MongoTemplate mongoTemplate() {
//         return new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create("mongodb+srv://ntbinh243dev:rPNKlCQIypFEDJrF@vou.hvasc.mongodb.net/vou"), "vou"));
//     }
// }
