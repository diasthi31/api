package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //ANOTAÇÃO GENÉRICA, UTILIZADO PARA O SPRING CARREGAR QUALQUER COISA, SEJA UMA CLASSE OU COMPONENTE GENÉRICO
public class SecurityFilter extends OncePerRequestFilter { //SEMPRE QUE CHEGAR UMA REQUISIÇÃO PASSARÁ POR ESSE FILTER, APENAS UMA VER POR REQUISIÇÃO

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); //VALIDA O TOKEN SE ELE FOR DIFERENTE DE NULO
            var usuario = repository.findByLogin(subject); //BUSCA NO BANCO O USUÁRIO PASSANDO O LOGIN DELE

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); //OBJETO PARA AUTENTICAR O TOKEN

            SecurityContextHolder.getContext().setAuthentication(authentication); //SPRING VERIRICA DE O USUARIO ESTA LOGADO
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization"); //PEGA O CABEÇALHO DA REQUISIÇÃO

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", ""); //REMOVE PREFIXO DO TOKEN
        }

        return null;
    }

}