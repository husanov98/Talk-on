package uz.mh.talkoncopy.config.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.mh.talkoncopy.dto.response.AppErrorDto;
import uz.mh.talkoncopy.dto.response.DataDto;
import uz.mh.talkoncopy.dto.user.user.UserDetails;
import uz.mh.talkoncopy.entities.user.User;
import uz.mh.talkoncopy.exceptions.user.UserNotFoundException;
import uz.mh.talkoncopy.repositories.user.user.UserRepository;
import uz.mh.talkoncopy.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final ObjectMapper mapper;
    private final UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getServletPath());
//
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/v1/auth/access/token") || request.getServletPath().equals("/api/v1/auth/refresh/token")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());

                DecodedJWT decodedJWT = JwtUtil.getVerifier().verify(token);

                String id = decodedJWT.getSubject();
                User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
                UserDetails userDetails = new UserDetails(user);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (Exception e){
                log.error("Error logging in: {}",e.getMessage());
                response.setHeader("error",e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                DataDto<AppErrorDto> responseDto = new DataDto<>(new AppErrorDto(e.getLocalizedMessage(),request.getRequestURI(),HttpStatus.FORBIDDEN,e.getMessage()));
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                mapper.writeValue(response.getOutputStream(),responseDto);
            }
        }
        filterChain.doFilter(request,response);
    }
}
