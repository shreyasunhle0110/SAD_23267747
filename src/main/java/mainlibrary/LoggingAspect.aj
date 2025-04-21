package mainlibrary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public aspect LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    pointcut daoMethods(): execution(* mainlibrary.TransBookDao.*(..));

    before(): daoMethods() {
        logger.info("Entering method: " + thisJoinPoint.getSignature());
    }
}
