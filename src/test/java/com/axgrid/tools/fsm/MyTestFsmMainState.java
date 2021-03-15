package com.axgrid.tools.fsm;

import com.axgrid.tools.dto.IFSMState;
import com.axgrid.tools.service.FSMEnter;
import com.axgrid.tools.service.FSMExit;
import com.axgrid.tools.service.FSMState;
import com.axgrid.tools.service.FSMUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FSMState(value = "main", start = true)
public class MyTestFsmMainState implements IFSMState<MyTestFSMContext> {

    @FSMEnter
    public void enter(MyTestFSMContext t) {
        log.info("Enter main {}", t);
    }

    @FSMExit
    public void exit(MyTestFSMContext t) {
        log.info("Exit main {}", t);
    }

    @FSMUpdate
    public void changeState(MyTestFSMContext t, MyTestFSM fsm) {
        t.setIndex(t.getIndex() + 1);
        log.info("Update main {}", t);
        if (t.getIndex() > 5)
            fsm.change(t, "second");
    }


}
