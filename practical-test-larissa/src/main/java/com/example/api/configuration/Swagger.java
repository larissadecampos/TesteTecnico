package com.example.api.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class Swagger {

		@Bean
		public OpenAPI  springCustomerOpenAPI() {
			
	        return new OpenAPI()
					.info(new Info()
						.title("Desafio Técnico")
						.description("Prova prática")
						.version("v0.0.1")
					.license(new License()
						.name("exemplo")
						.url("com.exemple.api"))
					.contact(new Contact()
						.name("Larissa de Campos")
						.url("https://github.com/larissadecampos")
						.email("larissadecampos2@gmail.com")))
					.externalDocs(new ExternalDocumentation()
						.description("Github")
						.url("https://github.com/allanalves92/practical-test.git"));
		}

		@Bean
		public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

			return openApi -> {
				openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

					ApiResponses apiResponses = operation.getResponses();


					apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
					apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
					apiResponses.addApiResponse("204", createApiResponse("Objeto Excluido!"));
					apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
					apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
					apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
					apiResponses.addApiResponse("500", createApiResponse("Erro na Aplição!"));

				}));
			};
		}

	   
		private ApiResponse createApiResponse(String message) {

			return new ApiResponse().description(message);

		}
		
	}
	

