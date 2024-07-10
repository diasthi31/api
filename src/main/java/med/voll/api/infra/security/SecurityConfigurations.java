package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    //DESABILITA O PROCESSO PADRÃO DE LOGIN DO SPRING SECURITY
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs").permitAll(); //DIZ QUE SEMPRE QUE HOUVER UMA REQUISIÇÃO COM ESSE MAPEAMENTO, DEVE ACEITAR SEM VERIFICAR TOKEN
                    req.anyRequest().authenticated(); //QUALQUER OUTRA REQUISIÇÃO, DEVE SER AUTENTICADA
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // CHAMA O FILTRO DE AUTENTICAÇÃO QUE EU CRIEI ANTES DO FILTRO DO SPING
                .build(); //DESATIVA A VERIFICAÇÃO DE CROSS FIRE E TRANSFORMA A AUTENTICAÇÃO EM STATELESS AO INVÉS DE STATEFULL
    }

    //BEAN SERVE PARA EXPORTAR UMA CLASSE PARA O SPRING FAZENDO COM QUE ELE CARREGUE E INJETE ELA EM OUTRAS CLASSES COMO DEPENDENCIA
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //DIZ AO SPRING PARA USAR ESSE ALGORITMO COMO HASH DE SENHA
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}