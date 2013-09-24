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

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
@WebServlet(value = "/status")
public class MyEjbStatusServlet extends HttpServlet {
    protected final Logger logger = Logger.getLogger(getClass().getName());
    @Resource(lookup = "jdbc/mydb")
    private DataSource dataSource;
    @EJB
    private MyEjbTimer myEjbTimer;
    @EJB
    private MyEjbSingleton myEjbSingleton;
    @EJB
    private MyEjbAsync myEjbAsync;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("Servlet " + getServletName() + " initialized!");
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();

        writer.println("# EJB Status Page#");

        writer.println();
        writer.println();
        writer.println("## EJB ##");
        writer.println();
        writer.println("SINGLETON:\t" + myEjbSingleton.getClass().getSimpleName() + "#incrementAndGetCounter: " + myEjbSingleton.incrementAndGetCounter());
        writer.println("TIMER:\t" + myEjbTimer.getClass().getSimpleName() + "#tickCount: " + myEjbTimer.getTickCount());
        Future<String> futureAsyncTaskResult = myEjbAsync.executeAsyncTask();
        writer.println("ASYNC:\t" + myEjbAsync.getClass().getSimpleName() + "#executeAsyncTask: " + futureAsyncTaskResult + " ...");
        writer.flush();
        try {
            writer.println("\t... " + futureAsyncTaskResult.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(writer);
        }

        writer.println();
        writer.println();
        writer.println("## DATA SOURCE ##");
        writer.println();
        writer.println("Datasource: " + dataSource);
        writer.flush();

    }
}
