package com.axgrid.tools.service;

import com.axgrid.tools.FSMUtils;
import com.axgrid.tools.dto.FSM;
import com.axgrid.tools.dto.IFSMContext;
import com.axgrid.tools.dto.IFSMState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class FSMService<T extends IFSMContext> implements FSM<T> {

    @Getter
    final Map<String, IFSMState<T>> states;

    @Getter
    final String startStateName;


    private void invokeStateMethod(T context, IFSMState<T> state, Method item) {
        try {
            if (item != null) item.setAccessible(true);
            Object[] callList = FSMUtils.getArguments(item, Arrays.asList(this, context, state));
            if (log.isTraceEnabled()) log.trace("Call {}.{}({})", state.getClass().getTypeName(), item.getName(), callList);
            item.invoke(state, callList);
        }catch (IllegalAccessException | InvocationTargetException e) {
            log.error("FSM enter error in state {} for context {}", FSMUtils.getStateName(state), context, e);
        }
    }

    private void enter(T context, IFSMState<T> state) {
        Arrays.stream(state.getClass().getMethods())
                .filter(item -> item.getAnnotation(FSMEnter.class) != null)
                .sorted(Comparator.comparing((item) -> item.getAnnotation(FSMEnter.class).value()))
                .forEach(item -> invokeStateMethod(context, state, item));
    }

    private void exit(T context, IFSMState<T> state) {
        Arrays.stream(state.getClass().getMethods())
                .filter(item -> item.getAnnotation(FSMExit.class) != null)
                .sorted(Comparator.comparing((item) -> item.getAnnotation(FSMExit.class).value()))
                .forEach(item -> invokeStateMethod(context, state, item));
    }

    private String getCurrentStateName(T context) {
        return context.getState() != null && !context.getState().isEmpty() ? context.getState() : startStateName;
    }

    public void change(T context, String toState) {
        if (!this.states.containsKey(toState)) throw new RuntimeException(String.format("FSM %s State %s not found", this.getClass().getName(), toState));
        if (context.getState() != null) this.exit(context, this.states.get(context.getState()));
        context.setState(toState);
        if (context.getState() != null) this.enter(context, this.states.get(context.getState()));
    }

    public void update(T context) {
        var state = states.get(getCurrentStateName(context));
        if (context.getState() == null || context.getState().isEmpty()) change(context, startStateName);
        Arrays.stream(state.getClass().getMethods())
                .filter(item -> item.getAnnotation(FSMUpdate.class) != null)
                .sorted(Comparator.comparing((item) -> item.getAnnotation(FSMUpdate.class).value()))
                .forEach(item -> invokeStateMethod(context, state, item));
    }

    public FSMService(List<IFSMState<T>> states) {
        this.states = states.stream().collect(Collectors.toMap(FSMUtils::getStateName, item -> item));
        startStateName = FSMUtils.getStateName(this.states.values().stream().filter(FSMUtils::isStartState).findFirst().orElse(states.get(0)));
        //Check all methods is valid
        this.states.values().forEach(item -> {
            Arrays.stream(item.getClass().getMethods()).filter(FSMUtils::isFSMAnnotation)
                    .forEach(method -> {
                        if (!FSMUtils.isHaveAllArgument(method, IFSMState.class, FSM.class, IFSMContext.class))
                            throw new RuntimeException(String.format("FSM Method %s.%s not ready for FSM argument", item.getClass().getName(), method.getName()));
                    });
        });
    }

}
