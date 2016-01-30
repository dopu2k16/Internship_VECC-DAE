<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
    <link rel="stylesheet" href="css/reset.css">

    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
<link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

        <link rel="stylesheet" href="css/style.css">



<title>Login Form</title>
<script type="text/javascript">
function validate()
{
	if(document.getElementById("email").value="" ||document.getElementById("password").value="")
		{
		  alert("UserName or password can't be empty");
		  return false;
		}
	alert("Succesful");
	return true;
}
</script>

</head>

<h3 align="center"><blink><u>VECC LOGIN PAGE</u></blink></h3>

 <body background="http://indico.vecc.gov.in/indico/conferenceDisplay.py/getPic?picId=2&confId=21" alt="showImage" height="100" width="100%">

	<form action="Login" onSubmit="return validate()" method="post">
		<table align="center" border="10" bgcolor="yellow" >
			<tr>
				<th>Enter Login Email Id</th>
				<td><input type="text" id="email" name="email"
					placeholder="Enter username"></input>@vecc.gov.in</td>
			</tr>
			<tr>
				<th>Enter password</th>
				<td><input type="password" id="password" name="password"
					placeholder="Enter password"></input></td>
			</tr>
			
			<tr>
				<th>Enter PERID</th>
				<td><input type="text" id="sperid" name="sperid"
					placeholder="Enter PERID"></input></td>
			</tr>
			
			<tr>
				<th><input type="submit" id="Login" name="Login" value="Login"></th>
				<td><input type="Reset" value="clear"></td>

			</tr>
		</table>
	</form>

</body>
</html>