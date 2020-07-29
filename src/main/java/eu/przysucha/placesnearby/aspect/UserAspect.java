package eu.przysucha.placesnearby.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(eu.przysucha.placesnearby.aspect.UserSignupAspectAnnotation)")
    void userSignup() {
        logger.info("New user is trying to sign up.");
    }

    @After("@annotation(eu.przysucha.placesnearby.aspect.UserLoginAspectAnnotation)")
    void userLogin() {
        logger.info("User has logged in.");
    }


}
