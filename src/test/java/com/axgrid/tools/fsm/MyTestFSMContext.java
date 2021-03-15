package com.axgrid.tools.fsm;

import com.axgrid.tools.dto.IFSMContext;
import lombok.Data;

@Data
public class MyTestFSMContext implements IFSMContext {
    String state;
    int index = 0;
}
