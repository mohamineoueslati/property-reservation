package com.moh.property_reservation.common.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@RequiredArgsConstructor
@Scope(value = SCOPE_PROTOTYPE)
@Slf4j
public class ProblemBuilder {

    private String title;
    private int status;
    private List<Error> errors = new ArrayList<>();

    public ProblemBuilder title(final String title) {
        this.title = title;
        return this;
    }

    public ProblemBuilder status(final int status) {
        this.status = status;
        return this;
    }

    public ProblemBuilder errors(List<Error> errors) {
        this.errors = errors;
        return this;
    }

    public Problem build() {
        var problem = new Problem(
                title,
                status,
                errors
        );
        log.info("Faced {}", problem);
        return problem;
    }

}
