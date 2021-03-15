package com.axgrid.tools.dto;

public interface FSM<T extends IFSMContext> {
    void change(T context, String toState);
    void update(T context);
}
