package acardenas.com.handler;

import acardenas.com.model.ObjectResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RestAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAspect.class);

    @Pointcut("execution(public * acardenas.com.controller.*.*(..))")
    public void allControllers() {}

    private final HttpServletRequest request;

    @Autowired
    public RestAspect(HttpServletRequest request){
        this.request = request;
    }

    @Around("allControllers()")
    public Object maskResponseController(ProceedingJoinPoint joinPoint) throws Throwable {

        long inicio = System.currentTimeMillis();

        Object objResultado = joinPoint.proceed();
        int status;

        if (objResultado instanceof ResponseEntity) {

            ResponseEntity<?> preResultado = (ResponseEntity<?>) objResultado;

            if (!(preResultado.getBody() instanceof InputStreamResource)) {

                status = preResultado.getStatusCodeValue();
                objResultado = preResultado.getBody();

                //resultado final
                ObjectResponse<?> body = ObjectResponse.builder()
                        .path(request.getRequestURI())
                        .status(status)
                        .data(objResultado).build();

                objResultado = ResponseEntity.status(status).body(body);
            }

        }

        long fin = System.currentTimeMillis();

        LOGGER.info("Tiempo de ejecución: {} ms", (fin - inicio));

        return objResultado;
    }
}
