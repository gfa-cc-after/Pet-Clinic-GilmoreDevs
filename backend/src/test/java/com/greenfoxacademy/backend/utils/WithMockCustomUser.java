package com.greenfoxacademy.backend.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * @author Rob Winch
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

  /**
   * The username to be used. The default is john
   *
   * @return
   */
  String username() default "john.doe@gmail.com";

  /**
   * The roles to use. The default is "USER". A
   * {@link org.springframework.security.core.GrantedAuthority} will be created for each
   * value within roles. Each value in roles will automatically be prefixed with
   * "ROLE_". For example, the default will result in "ROLE_USER" being used.
   *
   * @return
   */
  String[] roles() default {"USER"};

  /**
   * The name of the user
   *
   * @return
   */
  String name() default "John Doe";

}