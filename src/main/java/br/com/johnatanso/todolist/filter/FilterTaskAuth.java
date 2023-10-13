package br.com.johnatanso.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.johnatanso.todolist.user.IUserRepository;
import br.com.johnatanso.todolist.user.UserModel;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {
            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecoded);

            String[] credentials = authString.split(":");

            String email = credentials[0];
            String password = credentials[1];

            UserModel userExists = this.userRepository.findByEmail(email);

            if (userExists == null) {
                response.sendError(401, "Usuário não autorizado");

            } else {

                var passwordVerified = BCrypt.verifyer().verify(password.toCharArray(), userExists.getPassword());

                if (passwordVerified.verified) {
                    request.setAttribute("userId", userExists.get_id());
                    filterChain.doFilter(request, response);

                    return;
                }

                response.sendError(401, "Usuário não autorizado");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
