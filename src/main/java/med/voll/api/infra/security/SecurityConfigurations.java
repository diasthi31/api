package med.voll.api.infra.security;

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

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    //DESABILITA O PROCESSO PADRÃO DE LOGIN DO SPRING SECURITY
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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