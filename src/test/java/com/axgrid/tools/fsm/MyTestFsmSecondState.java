package com.axgrid.tools.fsm;

import com.axgrid.tools.dto.IFSMContext;
import com.axgrid.tools.dto.IFSMState;
import com.axgrid.tools.service.FSMEnter;
import com.axgrid.tools.service.FSMState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FSMState(value = "second")
public class MyTestFsmSecondState implements IFSMState<MyTestFSMContext> {

    @FSMEnter(-1)
    public void enter(MyTestFSMContext t) {
        log.info("Enter second {}", t);
    }

    @FSMEnter(2)
    public void enter2(IFSMContext t) {
        log.info("Enter second2 {}", t);
    }
}
