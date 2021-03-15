package com.axgrid.tools;

import com.axgrid.tools.dto.IFSMContext;
import lombok.Data;

@Data
public class MyFSMContext implements IFSMContext {

    String state;
    int count = 0;

}
