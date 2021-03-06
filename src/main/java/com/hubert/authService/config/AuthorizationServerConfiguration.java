package com.hubert.authService.config;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    private SecurityProperties security;
    private final UserDetailsService userDetailsService;

    private JwtAccessTokenConverter jwtAccessTokenConverter;
    private TokenStore tokenStore;

    public AuthorizationServerConfiguration(final DataSource dataSource, final PasswordEncoder passwordEncoder,
                                            final AuthenticationManager authenticationManager, final SecurityProperties securityProperties,
                                            final UserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.security = securityProperties;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public TokenStore tokenStore() {
        if (tokenStore == null) {
            tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        }
        return tokenStore;
    }

    @Bean
    public DefaultTokenServices tokenServices(final TokenStore tokenStore,
                                              final ClientDetailsService clientDetailsService) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(this.authenticationManager);
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        if (jwtAccessTokenConverter != null) {
            return jwtAccessTokenConverter;
        }

        //KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));

        jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter());
        return jwtAccessTokenConverter;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.dataSource).withClient("platform").accessTokenValiditySeconds(30)				
	        .authorizedGrantTypes("authorization_code", "refresh_token", "client_credentials", "password")
			.scopes("message.read", "message.write")
			.secret("{noop}secret")
			.redirectUris("http://localhost:8080/authResource/security/authorized");
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = 
          new KeyStoreKeyFactory(new ClassPathResource("anansemind.jks"), "anansemind".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("anansemind"));
        //PublicKey pubKey = keyStoreKeyFactory.getKeyPair("anansemind", "anansemind".toCharArray()).getPublic();
        return converter;
    }
    
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(this.userDetailsService)
                .tokenStore(tokenStore());
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.passwordEncoder(this.passwordEncoder).tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

	/*
	 * private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties,
	 * KeyStoreKeyFactory keyStoreKeyFactory) {
	 * 
	 * return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(),
	 * jwtProperties.getKeyPairPassword().toCharArray()); }
	 * 
	 * private KeyStoreKeyFactory
	 * keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
	 * 
	 * return new KeyStoreKeyFactory(jwtProperties.getKeyStore(),
	 * jwtProperties.getKeyStorePassword().toCharArray()); }
	 */
}
