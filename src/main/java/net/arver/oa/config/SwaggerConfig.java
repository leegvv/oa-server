package net.arver.oa.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置信息.
 * @author leegvv
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * docket配置.
     * @return docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * apiInfo.
     * @return apiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("在线办公系统api文档")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

    /**
     * securitySchemes.
     * @return securitySchemes
     */
    private List<SecurityScheme> securitySchemes() {
        final List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("token", "token", "header"));
        return securitySchemes;
    }

    /**
     * securityContexts.
     * @return securityContexts
     */
    private List<SecurityContext> securityContexts() {
        final List<SecurityContext> securityContexts = new ArrayList<>();
        final SecurityContext securityContext = SecurityContext.builder().securityReferences(defaultAuth()).build();
        securityContexts.add(securityContext);
        return securityContexts;
    }

    /**
     * 默认权限.
     * @return List<SecurityReference>
     */
    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = {scope};
        final List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("token", scopes));
        return securityReferences;
    }



}
