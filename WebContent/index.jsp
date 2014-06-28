<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后臺管理系統</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="css/login.css" rel="stylesheet" type="text/css" />
<script charset="utf-8" type="text/javascript" src="js/global.js"></script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36372141-1']);
  _gaq.push(['_setDomainName', 'ourstudybuddy.com']);
  _gaq.push(['_setAllowLinker', true]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>

<body>
   <div class="bg">
      <div class="bg1">
        <form action="/ourstudybuddy/system/checkessaylogin.do" method="get"  name="form1" id="form1">
          <table width="658" border="0">
            <tr>
              <td width="254" height="268">&nbsp;</td>
              <td width="314">&nbsp;</td>
              <td width="76">&nbsp;</td>
            </tr>
            <tr>
              <td height="25" align="right">&nbsp;</td>
              <td>&nbsp;
			  <span class="message">
		
			  </span>
			  </td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td height="46" align="right">帳號:</td>
              <td style="padding-left:12px;"><label>
                <input name="login" type="text" id="login" class='contentinput'  style="width:200px;"/>
              </label></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td height="30" align="right">密碼:</td>
              <td style="padding-left:12px;"><input name="pwd" class='contentinput' type="password" id="pwd" style="width:200px;" /></td>
              <td>&nbsp;</td>
            </tr>

            <tr>
              <td height="25" align="right">&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td height="36" align="right">&nbsp;</td>
              <td><label>
                <input type="image" name="imageField" src="images/loginBtn.gif" />
              </label></td>
              <td>&nbsp;</td>
            </tr>
          </table>
        </form>
      </div>
   </div>
   <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1000151987'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s22.cnzz.com/z_stat.php%3Fid%3D1000151987' type='text/javascript'%3E%3C/script%3E"));</script>
</body>
</html>

