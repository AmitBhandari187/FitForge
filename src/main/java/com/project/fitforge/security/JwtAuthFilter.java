package com.project.fitforge.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt=parseJwt(request);
            if (jwt !=null && jwtUtils.validateJwtToken(jwt)){
                System.out.println("Token here: " + jwt);
                String userId=jwtUtils.getUserIdFromToken(jwt);

                //Now we need roles to implement role based access control
                Claims claims=jwtUtils.getAllClaims(jwt);
                List<String> roles=claims.get("roles",List.class);
                System.out.println("Roles :" + roles);

                // Now converting roles to GrantedAuthority because spring takes it as GrantedAuthority
                List<GrantedAuthority> authorities=List.of();
                if (roles !=null){
                    authorities=roles
                            .stream()
                            .map(role->(GrantedAuthority) new SimpleGrantedAuthority(role))
                            .toList();
                }
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userId,null,authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request){
        String jwt=jwtUtils.getJwtFromHeader(request);
        return jwt;
    }
}
