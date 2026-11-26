package com.brotherselectronics.orderregistration.configs;

import jakarta.validation.Validator;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.BigDecimalRepresentation;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

@Configuration
public class MongoConfig {

    /**
     * Keeps the document format written by previous versions of the app.
     * <ul>
     *   <li>BigDecimal as string: Spring Data MongoDB 5 defaults to Decimal128.</li>
     *   <li>SimpleGrantedAuthority as {@code {role: "X"}}: Spring Security 7 renamed its constructor
     *   parameter, so Spring Data can no longer map the class on its own.</li>
     * </ul>
     */
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return MongoCustomConversions.create(configurer -> {
            configurer.bigDecimal(BigDecimalRepresentation.STRING);
            configurer.registerConverters(List.of(new AuthorityReader(), new AuthorityWriter()));
        });
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @ReadingConverter
    static class AuthorityReader implements Converter<Document, SimpleGrantedAuthority> {
        @Override
        public SimpleGrantedAuthority convert(Document source) {
            return new SimpleGrantedAuthority(source.getString("role"));
        }
    }

    @WritingConverter
    static class AuthorityWriter implements Converter<SimpleGrantedAuthority, Document> {
        @Override
        public Document convert(SimpleGrantedAuthority source) {
            return new Document("role", source.getAuthority());
        }
    }
}
