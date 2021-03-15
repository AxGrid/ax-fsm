package com.axgrid.tools;

import com.axgrid.tools.service.FSMEnter;
import com.axgrid.tools.service.FSMExit;
import com.axgrid.tools.service.FSMState;
import com.axgrid.tools.service.FSMUpdate;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
public final class FSMUtils {

    public static boolean isStartState(Object o) {
        var an = o.getClass().getAnnotation(FSMState.class);
        if (an == null) return false;
        return an.start();
    }

    public static String getStateName(Object o) {
        var an = o.getClass().getAnnotation(FSMState.class);
        if (an == null || an.value().isEmpty())
            return o.getClass().getSimpleName();
        return an.value();
    }

    public static boolean isFSMAnnotation(Method method) {
        return (method.getAnnotation(FSMExit.class) != null ||
                method.getAnnotation(FSMEnter.class) != null ||
                method.getAnnotation(FSMUpdate.class) != null);
    }

    public static boolean isSameType(Class<?> type, Object parameter) {
        if (parameter.getClass() == type) return true;
        return type.isAssignableFrom(parameter.getClass());
    }


    public static Object getParameter(Class<?> clz, List<Object> parameters) {
        return parameters.stream().filter(item -> isSameType(clz, item)).findFirst()
                .orElseThrow(() ->
                    new RuntimeException(String.format("Wrong argument. %s dont have %s", parameters, clz))
                );
    }

    public static boolean isHaveAllArgument(Method method, Class<?> ...params) {
        var paramList = Arrays.asList(params);
        return Arrays.stream(method.getParameterTypes())
                .allMatch(methodParameter -> paramList.stream().anyMatch(item -> {
                    if (log.isTraceEnabled()) log.trace("Check {} == {} is {}", item, methodParameter, item.isAssignableFrom(methodParameter));
                    return item.isAssignableFrom(methodParameter);
                }));
    }

    public static Object[] getArguments(Method method, List<Object> parameters) {
        return Arrays.stream(method.getParameterTypes())
                .map(parameter -> getParameter(parameter, parameters))
                .toArray();
    }
}
