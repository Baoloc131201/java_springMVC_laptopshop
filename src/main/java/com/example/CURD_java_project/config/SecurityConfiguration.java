package com.example.CURD_java_project.config;

import com.example.CURD_java_project.service.Impl.CustomUserDetailsService;
import com.example.CURD_java_project.service.Impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfiguration  {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Lazy
    private CustomUserDetailsService userDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("baoloc2211v2Test")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER");
//    }

    @Bean
    public UserDetailsService userDetailsService(IUserService userService) {
        return new CustomUserDetailsService(userService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationManager(PasswordEncoder passwordEncoder,
                                                           UserDetailsService userDetailsService){
        DaoAuthenticationProvider authenticationManagerBuilder= new DaoAuthenticationProvider();
        authenticationManagerBuilder.setUserDetailsService(userDetailsService);
        authenticationManagerBuilder.setPasswordEncoder(passwordEncoder);

        return authenticationManagerBuilder;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new CustomSuccessHandlerAuthentication();
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);

        return rememberMeServices;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,DispatcherType.INCLUDE).permitAll()
                        .mvcMatchers("/status","/reset_password/**","/forgot_password","/homepage","/product/**","/login", "/client/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
         )

         .formLogin(form -> form
                .loginPage("/login")
                .failureUrl("/login?error")
//                .defaultSuccessUrl("/homepage")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
         )

         .sessionManagement((sessionManagement) -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                 .invalidSessionUrl("/logout?expired")
                 .maximumSessions(1)
                 .maxSessionsPreventsLogin(false))

         .rememberMe(r -> r.rememberMeServices(rememberMeServices()))

         .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
