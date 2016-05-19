package com.hantsylabs.restexample.springmvc.test;

import org.junit.Before;
import org.springframework.boot.context.embedded.LocalServerPort;

/**
 *
 * @author hantsy
 */
public class WebIntegrationTestBase extends IntegrationTestBase {

    @LocalServerPort
    protected int port;

    @Before
    public void setup() {
        clearData();
        initData();
    }

}
