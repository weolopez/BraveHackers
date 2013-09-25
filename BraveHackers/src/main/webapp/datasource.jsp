<%@ page import="javax.naming.Context" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.sql.DatabaseMetaData" %>
<%
    String jndiName = request.getParameter("jndiName");
    if(jndiName == null || jndiName.isEmpty()) {
        jndiName = "jdbc/mydb";
    }
%>
<html>
<body>
<h1>DataSource demo</h1>

<h2>DataSource declaration</h2>
DataSource is declared in file <code>${WEBAPP_HOME}/WEB-INF/glassfish-resources.xml</code>

<p/>

<h2>Sample of code</h2>
<table border="1">
    <tr>
        <td>
<pre>
Context ctx = new InitialContext();
DataSource ds = (DataSource) ctx.lookup("<%= jndiName %>");
Connection conn = ds.getConnection();
ResultSet rst = stmt.executeQuery("select 1");
while (rst.next()) {
    out.print("resultset result: " + rst.getString(1));
}
rst.close();
stmt.close();
conn.close();
<code>
</code>
</pre>
        </td>
    </tr>
</table>
<p>
        <%
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(jndiName);
        Connection conn = ds.getConnection();
        DatabaseMetaData databaseMetaData = conn.getMetaData();
    %>

<h1>Connection Meta Data</h1>
<table border="1">
    <tr>
        <td>databaseProductName</td>
        <td><%= databaseMetaData.getDatabaseProductName() %>
        </td>
    </tr>
    <tr>
        <td>databaseProductVersion</td>
        <td><%= databaseMetaData.getDatabaseProductVersion() %>
        </td>
    </tr>
    <tr>
        <td>JdbcDriverName</td>
        <td><%= databaseMetaData.getDriverName() %>
        </td>
    </tr>
    <tr>
        <td>JdbcDriverVersion</td>
        <td><%= databaseMetaData.getDriverVersion() %>
        </td>
    </tr>
    <tr>
        <td>JdbcUrl</td>
        <td><%= databaseMetaData.getURL() %>
        </td>
    </tr>
    <tr>
        <td>DefaultTransactionIsolation</td>
        <td><%= databaseMetaData.getDefaultTransactionIsolation() %>
        </td>
    </tr>
    <tr>
        <td>MaxConnections</td>
        <td><%= databaseMetaData.getMaxConnections() %>
        </td>
    </tr>
    <tr>
        <td>ResultSetHoldability</td>
        <td><%= databaseMetaData.getResultSetHoldability() %>
        </td>
    </tr>
    <tr>
        <td>UserName</td>
        <td><%= databaseMetaData.getUserName() %>
        </td>
    </tr>
</table>
<h1>Connection Attributes</h1>
<table border="1">
    <tr>
        <td>AutoCommit</td>
        <td><%= conn.getAutoCommit() %>
        </td>
    </tr>
    <tr>
        <td>Catalog</td>
        <td><%= conn.getCatalog() %>
        </td>
    </tr>
    <tr>
        <td>ClientInfo</td>
        <td><%= conn.getClientInfo() %>
        </td>
    </tr>
    <tr>
        <td>Holdability</td>
        <td><%= conn.getHoldability() %>
        </td>
    </tr>
    <tr>
        <td>Schema</td>
        <td><%= conn.getSchema() %>
        </td>
    </tr>
    <tr>
        <td>TransactionIsolation</td>
        <td><%= conn.getTransactionIsolation() %>
        </td>
    </tr>
</table>
<%
    Statement stmt = conn.createStatement();
    ResultSet rst = stmt.executeQuery("select 1");
    while (rst.next()) {
        out.print("resultset result: " + rst.getString(1));
    }
    rst.close();
    stmt.close();
    conn.close();
%>
</p>
<strong>Success! Your DataSource works!</strong>
</body>
</html>
