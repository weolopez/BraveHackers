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

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
@Stateless
public class MyEjbAsync {
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected final Random random = new Random();
    protected final AtomicInteger taskExecutionCounter = new AtomicInteger();

    @Asynchronous
    public Future<String> executeAsyncTask() {
        int taskId = taskExecutionCounter.incrementAndGet();

        int sleepDurationInMillis = 50 + random.nextInt(200);
        try {
            Thread.sleep(sleepDurationInMillis);
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "Exception sleeping", e);
        }

        return new AsyncResult<>("Async task #" + taskId + " took " + sleepDurationInMillis + " ms");
    }

    public int getTaskExecutionCount() {
        return taskExecutionCounter.get();
    }
}
