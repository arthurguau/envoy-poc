package com.tinybank.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tinybank.security.configuration.jwt.config.JwtTokenUtil;
import com.tinybank.security.configuration.jwt.exception.AuthenticationException;
import com.tinybank.security.configuration.jwt.model.JwtTokenRequest;
import com.tinybank.security.configuration.jwt.model.JwtTokenResponse;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthenticationController {

    @Value("${jwt.http-request-header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtUserDetailsService;
    
    @GetMapping(value = "${jwt.get-token-uri}")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authString) throws AuthenticationException {
    	
        System.out.println(authString);
        
        String userName = "arthur";
        if (null != authString & authString.length()>0)
            userName = authString.substring(5);  
        
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userName);

        final String token = jwtTokenUtil.generateToken(userDetails);
        log.info(token);
        
        ResponseEntity<JwtTokenResponse> response = ResponseEntity
                .status(HttpStatus.OK)
                .header("Authorization", token)
                .body(new JwtTokenResponse(token));
        
        return response;
    }
    

    @PostMapping(value = "${jwt.get-token-uri}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
        throws AuthenticationException {
        
        //authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        
        //HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("authorization", token);
        
        ResponseEntity<JwtTokenResponse> response = ResponseEntity
                .status(HttpStatus.OK)
                .header("authorization", token)
                .body(new JwtTokenResponse(token));
       
        return response;
        
        // return new ResponseEntity <String> ((new JwtTokenResponse(token)).toString(), responseHeaders, HttpStatus.CREATED);        
    }

    
    @PostMapping(value = "${jwt.refresh-token-uri}")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * 
     * @param username
     * @param password
     */
//    private void authenticate(String username, String password) {
//        Objects.requireNonNull(username);
//        Objects.requireNonNull(password);
//
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new AuthenticationException("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new AuthenticationException("INVALID_CREDENTIALS", e);
//        }
//    }
}
