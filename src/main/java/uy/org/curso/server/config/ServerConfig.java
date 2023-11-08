package uy.org.curso.server.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ServerConfig {

	/**
	 * Defino el filterchain para que valide el acceso solo a usuarios autenticados
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	        return http
	        		.authorizeHttpRequests(authorizeRequests ->
	        			authorizeRequests.anyRequest().authenticated()
	        		)
	        		.formLogin(Customizer.withDefaults())
	        		.build();
	}
	
	/**
	 * Construyo un UserDetailsService en memoria con 2 usuarios
	 * @param encoder
	 * @return
	 */
	@Bean
	UserDetailsService userDetailsService(PasswordEncoder encoder) {
	      List<UserDetails> userList = new ArrayList<>();
	      
	      userList.add(new User("usuario", encoder.encode("usuario"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
	      userList.add(new User("admin", encoder.encode("admin"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
	      
	      return new InMemoryUserDetailsManager(userList);
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
    }	
}
