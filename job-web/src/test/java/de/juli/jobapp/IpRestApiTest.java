/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package de.juli.jobapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@WebAppConfiguration
@EnableAutoConfiguration
public class IpRestApiTest extends Assert {

    private TestRestTemplate rest = new TestRestTemplate();

    @Autowired
    AbstractRefreshableWebApplicationContext tomcat;

    int port = 8080;
    String baseUri = "localhost";

    @Before
    public void before() {
    }

    @Test
    public void shouldExposeIpApi() throws InterruptedException {
        String ip = rest.getForObject(baseUri + "/ip", String.class);
        assertNotNull(ip);

        String ip2 = rest.getForObject(baseUri + "/ip", String.class);
        assertNotNull(ip2);

        // should not be same as there is a counter in the response
        assertNotSame(ip, ip2);
    }

}
