package com.epam.keikom.web.config;

import com.epam.keikom.service.config.ServiceSpringConfig;
import com.epam.keikom.web.dto.UserDTO;
import com.epam.keikom.web.viewresolver.Jaxb2MarshallingXmlViewResolver;
import com.epam.keikom.web.viewresolver.JsonViewResolver;
import com.epam.keikom.web.viewresolver.user.UserExcelViewResolver;
import com.epam.keikom.web.viewresolver.user.UserPdfViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@Import(ServiceSpringConfig.class)
@ComponentScan(basePackages = "com.epam.keikom.web")
public class WebSpringConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
    }

    @Autowired
    ServletContext servletContext;

    @Bean
    public ViewResolver getFreeMarkerViewResolver() {
        final FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths("/", "/WEB-INF/views/ticket", "/WEB-INF/views/user",
                "/WEB-INF/views/event", "/WEB-INF/views/booked");
        return freeMarkerConfigurer;
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        final List<ViewResolver> resolvers = new ArrayList<>();

        resolvers.add(jsonViewResolver());
        resolvers.add(pdfViewResolver());
        resolvers.add(excelViewResolver());
        resolvers.add(getFreeMarkerViewResolver());
        resolvers.add(jaxb2MarshallingXmlViewResolver());

        resolver.setViewResolvers(resolvers);

        return resolver;
    }

    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }

    @Bean
    public ViewResolver pdfViewResolver() {
        return new UserPdfViewResolver();
    }

    @Bean
    public ViewResolver excelViewResolver() {
        return new UserExcelViewResolver();
    }

    @Bean
    public ViewResolver jaxb2MarshallingXmlViewResolver() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(UserDTO.class);
        return new Jaxb2MarshallingXmlViewResolver(marshaller);
    }
}
