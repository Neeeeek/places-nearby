package eu.przysucha.placesnearby.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CategoryAspect {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @After("@annotation(eu.przysucha.placesnearby.aspect.CategoryAddAspectAnnotation)")
    void newCategoryAdded() {
        logger.info("New category has been registered.");
    }

}
