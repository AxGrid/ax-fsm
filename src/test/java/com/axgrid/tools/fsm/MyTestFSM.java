package com.axgrid.tools.fsm;

import com.axgrid.tools.dto.IFSMState;
import com.axgrid.tools.service.FSMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyTestFSM extends FSMService<MyTestFSMContext> {

    @Autowired
    public MyTestFSM(List<IFSMState<MyTestFSMContext>> fsmStates) {
        super(fsmStates);
    }
}
