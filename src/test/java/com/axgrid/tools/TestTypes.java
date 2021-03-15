package com.axgrid.tools;


import com.axgrid.tools.dto.FSM;
import com.axgrid.tools.dto.IFSMContext;
import com.axgrid.tools.dto.IFSMState;
import com.axgrid.tools.service.FSMState;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class TestTypes {

    @Test
    public void testSuperTypeOfType() {
        var cls = MyFSMState.class;
        Assert.assertTrue(Arrays.stream(cls.getInterfaces()).anyMatch(item -> item == IFSMState.class));
        Assert.assertTrue(IFSMState.class.isAssignableFrom(cls));
        Assert.assertTrue(IFSMContext.class.isAssignableFrom(MyFSMContext.class));
        Assert.assertTrue(MyFSMContext.class.isAssignableFrom(MyFSMContext.class));
    }

    @Test
    public void testValidState() {
        MyFSMState s = new MyFSMState();
        var method = Arrays.stream(s.getClass().getMethods()).filter(item -> item.getName().equals("enter")).findFirst().orElse(null);
        Assert.assertNotNull(method);
        Assert.assertTrue(FSMUtils.isHaveAllArgument(method, IFSMState.class, FSM.class, IFSMContext.class));
    }

    @Test
    public void testGetParameter() {
        MyFSMContext context = new MyFSMContext();
        Assert.assertTrue(FSMUtils.isSameType(MyFSMContext.class,  context));


        Object o = FSMUtils.getParameter(MyFSMContext.class, Arrays.asList(context, new MyFSMState()));
        Assert.assertNotNull(o);

    }

}
