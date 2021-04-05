package com.marlonmarqs.promobv.config;

import java.util.Arrays;

import com.marlonmarqs.promobv.security.OAuth2Util;
import com.marlonmarqs.promobv.security.oauth2.CustomOAuth2UserService;
import com.marlonmarqs.promobv.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.marlonmarqs.promobv.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.marlonmarqs.promobv.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.marlonmarqs.promobv.security.JWTAuthenticationFilter;
import com.marlonmarqs.promobv.security.JWTAuthorizationFilter;
import com.marlonmarqs.promobv.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // permiti inserir preautorizacao de endpoints
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	// instancia
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private OAuth2Util oAuth2Util;
	
	// liberado
		private static final String[] PUBLIC_MATCHERS = {
				"/h2-console/**",
				"/oauth2/**",
		};

		// liberado, so vai poder recuperar os dados
		private static final String[] PUBLIC_MATCHERS_GET = {
				"/promocoes/**",
				"/categorias/**",
				"/usuarios/**",
				"/auth/updatePassword",
				"/auth/check_email"
		};
		
		private static final String[] PUBLIC_MATCHERS_POST = {
				"/auth/new_password/**",
				"/usuarios",
				"/usuarios/picture"
		};

	/*
 	Por padrão, o Spring OAuth2 usa HttpSessionOAuth2AuthorizationRequestRepository para salvar
  	o pedido de autorização. Mas, como nosso serviço é apátrida, não podemos salvá-lo na
  	sessão. Em vez disso, salvaremos a solicitação em um cookie codificado em Base64.
	*/
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

		@Override                //do framework
		protected void configure(HttpSecurity http) throws Exception{

			if (Arrays.asList(env.getActiveProfiles()).contains("test")) { // se nos profiles ativos tiver contido o test, então vai liberar o acesso
				http.headers().frameOptions().disable();
			}

			http.cors().and().csrf().disable(); // ativando o cors e desativando o csrf
			http.authorizeRequests()
			.antMatchers("/",
				"/error",
				"/favicon.ico",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.svg",
				"/**/*.jpg",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js")
			.permitAll()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // meotodo so para pegar dados, não permitindo alterar
			.antMatchers(PUBLIC_MATCHERS).permitAll() // toda autenticação feita em public_matchers é permitida
			.anyRequest().authenticated() // para todo o resto, se exige autenticação
			.and()
			.oauth2Login()
			.authorizationEndpoint()
			.baseUri("/oauth2/authorize")
			.authorizationRequestRepository(cookieAuthorizationRequestRepository())
			.and()
			.redirectionEndpoint()
			.baseUri("/oauth2/callback/*")
			.and()
			.userInfoEndpoint()
			.userService(customOAuth2UserService)
			.and()
			.successHandler(new OAuth2AuthenticationSuccessHandler(cookieAuthorizationRequestRepository(), oAuth2Util, jwtUtil))
			.failureHandler(new OAuth2AuthenticationFailureHandler(cookieAuthorizationRequestRepository()));

			//registrando o filtro                                            
			http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil)); // authenticationManager ja é um metodo disponivel da classe
			http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // para asegura que o back end não vai criar a sessão de usuario
		}
		
		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception{
			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		}

		// permitindo acesso básico de multiplas fontes para todos os caminhos, configurações básicas
		@Bean
		  CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
			configuration.setAllowedMethods(Arrays.asList("POST", "PUT", "DELETE", "GET", "OPTIONS"));
		    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		    source.registerCorsConfiguration("/**", configuration);
		    return source;
		  }
		
		//criptografia de senha
		@Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();
		}
}
