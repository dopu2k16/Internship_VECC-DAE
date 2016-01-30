<%-- 
    Document   : index
    Created on : 5 Mar, 2013, 1:29:03 PM
    Author     : User
--%>


<%@page import="java.io.FileOutputStream"%>
<%@page import="com.itextpdf.text.Document" %>
<%@page import="com.itextpdf.text.pdf.PdfWriter" %>
<%@page import="com.itextpdf.text.Paragraph"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PAY SLIP</title>
        <script type="text/javascript" src="jquery-1.7.2.min.js"></script>
        <script type="text/javascript">
            idleTime = 0;
            $(document).ready(function () {
                //Increment the idle time counter every minute.
                var idleInterval = setInterval("timerIncrement()", 1000); // 1 second

                //Zero the idle timer on mouse movement.
                $("body").mousedown(function (e) {
                    idleTime = 0;
                });
            })
            function timerIncrement() {
                idleTime = idleTime + 1;
                if (idleTime > 870) { // 29==30 seconds => 870==15 mins
                    window.location = "logged.jsp";
                }
            }
        </script>
        <style type="text/css">
       body
{
background-image:url("payslip2/Commercial_wallpapers_282.jpg");
background-repeat:no-repeat;
overflow: hidden;
} 
root { 
    display: block;
    white-space: nowrap;
}
#pb{
    height: 30px;
}
#part1{
  white-space: nowrap;  
}
#tab1{
    font-family: cursive;
    font: bolder;
    font-weight: 16px;
    margin-left: 330px;
    font-size: xx-large;
    margin-top: 150px;
    color: #660000;
    white-space: nowrap;
}
#tab2{
    font-family: cursive;
    font: bold;
    font-weight: 16px;
    margin-right: auto;
    font-size: large;
    white-space: nowrap;
   }
#part2{
   background-color: lightblue;
   align: center;
   width:40%;
   height: 130px;
   border: blue; 
   border-width: 3px;
   border-style: groove;
   border-width: 2px; 
   font-family: cursive;
   font: bold;
   font-size: large;
   color: blueviolet;
   margin-left: 35%;
   margin-top: 5%;
   white-space: nowrap;
}
#part3{
   
    width:700px;
    margin-left: 165px;
}
#tab4{
    font-family: cursive;
    font: bold;
    font-weight: 16px;
    font-size: large;
    border-color: blueviolet;
    border: 2px;
    margin-top: 0px;
}
#tab3{
    font-family: serif;
    font-size: small;
    border-color: blueviolet;
    border-width: 2px;
    border: 2px;
}
#h10{
    color: blue;
}
.CSSButton {
border: 2px solid #225289;
background: -webkit-gradient( linear, left top, left bottom, color-stop(0.01, #ff9966), color-stop(0.9, #660000) );
background: -moz-linear-gradient( center top, #ff9966 1%, #660000 90% );
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#A9D1E0', endColorstr='#225289');
background-color: #660000;
-moz-box-shadow: inset 0px 1px 2px 0px #FFFFFF;
-webkit-box-shadow: inset 0px 1px  2px 0px #FFFFFF;
box-shadow: inset 0px 1px  2px 0px #FFFFFF;
-moz-border-radius: 5px;
-webkit-border-radius: 5px;
border-radius: 5px;
text-shadow: 1px 1px 2px #000000;
font-weight: bold;
margin: 5px 5px;
padding: 6px 6px;
color: #FFFFFF;
letter-spacing: 1px;
font-family: 'Arial', sans-serif;
font-size: 16px;
width: 73px;
text-transform: capitalize;
text-align: center;
text-decoration: none;
cursor: pointer;
display: inline-block;
}
.CSSButton:hover {
background: -webkit-gradient( linear, left top, left bottom, color-stop(0.01, #225289), color-stop(0.9, #A9D1E0) );
background: -moz-linear-gradient( center top, #225289 1%, #A9D1E0 90% );
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#225289', endColorstr='#A9D1E0');
background-color: #225289;
}
.CSSButton:active {
position: relative;
top: 1px;
left: 0px;
}
.CSSButton1 {
border: 2px solid #B73E7D;
background: -webkit-gradient( linear, left top, left bottom, color-stop(0.01, #FF5DB1), color-stop(0.9, #EF007C) );
background: -moz-linear-gradient( center top, #FF5DB1 1%, #EF007C 90% );
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FF5DB1', endColorstr='#EF007C');
background-color: #FF5DB1;
-moz-box-shadow: inset 0px 1px 2px 0px #FFFFFF;
-webkit-box-shadow: inset 0px 1px  2px 0px #FFFFFF;
box-shadow: inset 0px 1px  2px 0px #FFFFFF;
-moz-border-radius: 5px;
-webkit-border-radius: 5px;
border-radius: 5px;
text-shadow: 1px 1px 30px #000000;
font-weight: bold;
margin: 5px 5px;
padding: 6px 6px;
color: #FFFFFF;
letter-spacing: 1px;
font-family: 'Arial', sans-serif;
font-size: 16px;
width: 73px;
text-transform: capitalize;
text-align: center;
text-decoration: none;
cursor: pointer;
display: inline-block;
}
.CSSButton1:hover {
background: -webkit-gradient( linear, left top, left bottom, color-stop(0.01, #EF007C), color-stop(0.9, #FF5DB1) );
background: -moz-linear-gradient( center top, #EF007C 1%, #FF5DB1 90% );
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#EF007C', endColorstr='#FF5DB1');
background-color: #EF007C;
}
.CSSButton1:active {
position: relative;
top: 1px;
left: 0px;
}
#tab11, #td11, th
{
border:1px;
margin-left: 30%;
border-style: groove;
border-color: #990033;
width: 70%;
background-color: #fcefa1;
}
th
{
background-color: #990033;
color: whitesmoke;
tab-size: 40% 20%;
font-size: large;
font-variant-position: sub;
border-color: #ff0000;
}
#tab13{
   border:1px;
margin-left: 30%;
border-style: groove;
border-color: #990033;
width: 70%;
background-color: #fcefa1; 
}
#table{
border:1px;
margin-left: 30%;
border-style: groove;
border-color: #990033;
width: 70%;
display: none;
background-color: #fcefa1;   
}
        </style>
    </head>        
          <script>
             function mnth(){
        var x = document.form1.getElementById("month");
        var y = document.form1.getElementById("year");
        
        if(x==null ||y==null)
            {
                alert("please fill up the month or year!!");
                window.location="WELCOME.jsp";
                return false;
               }}
        </script>
        <script>
            function pdf1(){
             var radios = document.getElementsByName("pslip");
        for (var i = 0; i < radios.length; i++) {       
          if (radios[i].checked) {
            if(radios[i].value == "yes")
            {
                document.getElementById("table").style.display="none";
                window.location="PaySlipPdfServlet?j=0";
                //window.location="PayPdf?j=0";
                break;
            }
            if(radios[i].value == "no")
            {
                document.getElementById("table").style.display="table";
                break;
            }
        }
    }
            }
        </script>
        <script>
            function pdf2(){
             var radios = document.getElementsByName("pslip");
        for (var i = 0; i < radios.length; i++) {       
          if (radios[i].checked) {
            if(radios[i].value == "yes")
            {
                document.getElementById("table").style.display="none";
                               break;
                           }
            if(radios[i].value == "no")
            {
                document.getElementById("table").style.display="table";
                document.getElementById("tab11").style.display="tab11";
                document.getElementById("but1").style.display="none";
                break;
            }
        }
    }
            }
        </script>
         <script>
            function view(){                
             var x = document.getElementById("month").value;
              var y = document.getElementById("year").value;
             window.location="PaySlipPdfServlet?j=1&month="+x+"&year="+y;
           //  window.location="PayPdf?j=1&month="+x+"&year="+y;
             return true;
                   
        }
  
        </script>
       
        <body background=" http://www.walleo.co/wp-content/uploads/2016/01/windows_10_wallpapers_nature_hd_41xc.jpg"  alt=" ApsS" height="100" width="100"> 
            
    <div id="pb">
        <form name="form1"  method="post">      
            <div id="part1">
                <table id="tab1"><tr><td>WELCOME ! <% String str=session.getAttribute("email").toString();%>
                                                   <%=str.toUpperCase()%> </td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                        <td id="tab4"><center><a href="Welcome.jsp" class="CSSButton1">HOME</a></center></tr></table>
                        <table>.</table><table>.</table><table>.</table></div>
        
        <div id="part3" >
            <table id="tab11"><tr><th colspan="2" align="center"> ENTER YOUR CHOICE HERE </th></tr>
                <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></table>
            
            <table id="tab13" align="center" height="30%">
                 <tr> 
                   <td align="center"><h3 align="center"> PRINT PAYSLIP: </h3></td><td colspan="1" width="100" align="center"><input type="radio" name="pslip" value="yes" onClick="return pdf2();" checked><h3 align="center">CURRENT MONTH</h3></td><td align="center"><input type="radio" name="pslip" value="no" onClick="return pdf1();"><h3 align="center">OTHER MONTH</h3></td>
                <td><center><span class="CSSButton" id="but1" name="print" value="SHOW" onClick="return pdf1();">SHOW</span></center></td>
               </tr> </table></div>
        <div id="part3">
            <table id="tab11"><tr><th colspan="4" align="center" style="display: none;"> OR </th></tr>
                <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></table>
            <table  width="40.8%" height="20%" id ="table" align="center" >
                 <tr>
                    <td align="center"><h3 align="center">ENTER MONTH: </h3></td><td colspan="1" width="100"><select name="month" id="month" type="text">
                        <option value="1" style="width: 100px">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        </select> 
</td>
<td align="center"><h3 align="center">ENTER YEAR: </h3></td><td colspan="1" width="100"><select name="year" id="year" type="text">
                        <option value="2006" style="width: 100px">2006</option>
                        <option value="2007">2007</option>
                        <option value="2008">2008</option>
                        <option value="2009">2009</option>
                        <option value="2010">2010</option>
                        <option value="2011">2011</option>
                        <option value="2012">2012</option>
                        <option value="2013">2013</option>
                        <option value="2014">2014</option>
                        <option value="2015">2015</option>
                        <option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                        <option value="2019">2019</option>
                        <option value="2020">2020</option>
        </select>
</td>
                </tr>
                   <tr>
                       <td colspan="2"><center><span class="CSSButton" name="submit" value="VIEW" onClick="return view();">VIEW</span></center></td>
                       <td colspan="2"><center><input class="CSSButton" type="reset" name="clear" value="CLEAR"/></center></td>
                </tr>
                </table>
        </div>  
                </form>
       </div>  
     </body>
</html>