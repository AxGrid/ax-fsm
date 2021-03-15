package com.axgrid.tools;

import com.axgrid.tools.fsm.MyTestFSM;
import com.axgrid.tools.fsm.MyTestFSMContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Import(TestApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "debug=true")
@Slf4j
public class TestFSM {

    @Autowired
    MyTestFSM fsm;

    @Test
    public void testFsmCreated() {
        Assert.assertNotNull(fsm);
        Assert.assertEquals(fsm.getStartStateName(), "main");
        Assert.assertEquals(fsm.getStates().size(), 2);
    }

    @Test
    public void testFsmUpdate() {
        Assert.assertNotNull(fsm);
        MyTestFSMContext ctx = new MyTestFSMContext();
        fsm.update(ctx);
        fsm.update(ctx);
        fsm.update(ctx);
        fsm.update(ctx);
        fsm.update(ctx);
        fsm.update(ctx);
    }
}
