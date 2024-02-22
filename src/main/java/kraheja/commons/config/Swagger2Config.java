package kraheja.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	@Value("${swagger2.base.package}")
	private String swagger2BasePackage;

	@Value("${swagger2.api.title}")
	private String swagger2ApiTitle;

	@Value("${swagger2.api.description}")
	private String swagger2ApiDescription;

	@Value("${swagger2.contact.name}")
	private String swagger2ContactName;

	@Value("${swagger2.contact.url}")
	private String swagger2ContactUrl;

	@Value("${swagger2.contact.email}")
	private String swagger2ContactEmail;
	
	    @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("kraheja"))
	                .paths(PathSelectors.any())
	                .build().apiInfo(apiEndPointsInfo());
	    }
	    
		private ApiInfo apiEndPointsInfo() {
			return new ApiInfoBuilder().title(this.swagger2ApiTitle)
					.description(this.swagger2ApiDescription)
					.contact(new Contact(this.swagger2ContactName, this.swagger2ContactUrl, this.swagger2ContactEmail))
					.build();
		}
	}