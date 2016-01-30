package model.bs;
import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;


/**
*
* @author Mitodru Niyogi
*/
public class TestLdap {
	public static boolean Test(String email, String password) {
		boolean b=false;
		
		//String url = "ldap://10.200.3.13:389/o=vecc";
		String url = "ldap://mail.vecc.gov.in:389/o=vecc";
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid="+ email +",o=vecc");
		//env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		env.put(Context.SECURITY_CREDENTIALS, password);
		//env.put(Context.SECURITY_CREDENTIALS, "secret");

		
		try {
			DirContext ctx = new InitialDirContext(env);
			b=true;
			System.out.println("connected");
			System.out.println(ctx.getEnvironment());
			
			// do something useful with the context...

			ctx.close();

		} catch (AuthenticationNotSupportedException ex) {
			System.out.println("The authentication is not supported by the server");
		} catch (AuthenticationException ex) {
			System.out.println("incorrect password or username");
		} catch (NamingException ex) {
			System.out.println("error when trying to create the context");
		}
	return b;
	}
		
}
