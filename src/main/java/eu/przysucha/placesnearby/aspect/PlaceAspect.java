package eu.przysucha.placesnearby.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PlaceAspect {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(eu.przysucha.placesnearby.aspect.UserSignupAspectAnnotation)")
    void userSignup() {
        logger.info("New user is trying to sign up.");
    }

    @After("@annotation(eu.przysucha.placesnearby.aspect.PlaceAddToFavListAspectAnnotation)")
    void placeAddToFavouriteList() {
        logger.info("New place has been added to someone's favourite list.");
    }

    @Before("@annotation(eu.przysucha.placesnearby.aspect.PlaceListGetFromApiAspectAnnotation)")
    void getDataFromApi() {
        logger.info("Getting data from external service.");
    }


}
