package webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import dto.Acknowledgement;


@Path("/authService")
public class AuthService {
	
	
	@GET
	@Path("/auth")
	@Produces("application/json")
	public Acknowledgement auth(@QueryParam("username" ) String username, @QueryParam("password" ) String password) {
		System.out.print("username---------" +username);
		System.out.print("password---------" +password);
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(true);
		if (password!=null && password.equals("1234"))
		{
			ack.setSuccess(false);
			System.out.print("auth failed");
		}
		return ack;
		
	}
	
	
	
	
}
