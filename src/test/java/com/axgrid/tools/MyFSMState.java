package com.axgrid.tools;

import com.axgrid.tools.dto.IFSMState;
import com.axgrid.tools.service.FSMEnter;
import com.axgrid.tools.service.FSMState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FSMState(value = "init")
public class MyFSMState implements IFSMState<MyFSMContext> {

    @FSMEnter
    public void enter(MyFSMContext ctx, IFSMState<MyFSMContext> fsm) {
        log.info("Enter ...");
    }


}
