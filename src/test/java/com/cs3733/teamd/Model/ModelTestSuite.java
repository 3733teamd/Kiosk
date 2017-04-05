package com.cs3733.teamd.Model;

/**
 * Created by sdmichelini on 4/4/17.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        NodeTest.class,
        PathfinderTest.class,
        ProfessionalTest.class,
        TagTest.class,
        UserTest.class
})

public class ModelTestSuite {
}
