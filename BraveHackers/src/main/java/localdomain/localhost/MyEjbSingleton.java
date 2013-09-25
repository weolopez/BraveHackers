/*
 * Copyright 2010-2013, CloudBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package localdomain.localhost;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
@Singleton
public class MyEjbSingleton {
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected final AtomicInteger counter = new AtomicInteger();

    @PostConstruct
    public void postConstruct() {
        logger.info(getClass().getName() + " initialized!");
    }

    public int incrementAndGetCounter() {
        return counter.incrementAndGet();
    }
}
