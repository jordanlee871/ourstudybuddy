<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>åèºç®¡çç³»çµ±</title>
<link href="../css/index.css" rel="stylesheet" type="text/css" />
<link href="../css/mains.css" rel="stylesheet" type="text/css" />
<script charset="utf-8" type="text/javascript" src="../js/jquery.min.js"></script>
<script charset="utf-8" type="text/javascript" src="../kindeditor/kindeditor.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/jquery.form.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/DatePicker/WdatePicker.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/global.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/jquery.contextmenu.js"></script>
<link href="../css/mycss.css" rel="stylesheet" type="text/css" />
<link href="../css/jquery.css" rel="stylesheet" type="text/css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js" type="text/
javascript"></script>
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css"/>
<script src="../js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
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
<style type="text/css">
	.do_appointment_div{
		border: 1px solid #000000;
		height: 400px;
		margin-left: 200px;
		margin-top: 100px;
		overflow: hidden;
		position: absolute;
		width: 600px;
	}
	.do_appointment_a{
		color:#3A66FF;
		cursor:pointer;
	}
</style>
</head>
<body class="index">
	<img src="../images/logo.gif"  class="logo"/>
	<div class="adminBox">歡迎你,TA-Wayne(助教)<a href="../logout.php"><img src="../images/logoutbtn.gif" border="0"  class="logout"/></a></div>
	<div class="menu">
					<a href="index.php?ac=cms" class='select' id='menu'>
						<div class='tableft'></div>
						<div class='tabcenter'>CMS</div>
						<div class='tabright'></div>
					</a>
			
					<a href="index.php?ac=set" class='' id='menu'>
						<div class='tableft'></div>
						<div class='tabcenter'>Setting</div>
						<div class='tabright'></div>
					</a>
	
	
	</div>
	<div class="menubar"></div>
	<div class="requestTime"></div>
	<div class="wrap1">
		<table width="100%" border="0" class="layout" >
		  <tr>
			<td height="445" valign="top" class="leftlayout">
				  <div class="menuheader">控制面板</div>				  
				  <div class="menu1" id='leftmenu'>	
						

<div class="menu2">
<a href="#">助教操作</a>
<div class="menu3 "><a href="index.php?type=student_list">学生管理</a></div>
<div class="menu3 "><a href="index.php?type=appointment_list">学生预约</a></div>
<div class="menu3 selected"><a href="index.php?type=appointment_edit">作文预约</a></div>
<div class="menu3 "><a href="index.php?type=teacher_edit">老师修改数量限制</a></div>
<div class="menu3 "><a 
	href="index.php?type=cancel_appointment_list">批量删除预约</a></div>
<div class="menu3 "><a 
	href="index.php?type=charge_s_edit">批量充值</a></div>
<div class="menu3 "><a href="index.php?type=export_edit">导出xls文件</a></div>
<div class="menu3 "><a href="index.php?type=assistant_edit">配置</a></div>
</div>



				  </div>
				  <div class="menufooter"></div>
			</td>
			
			<td valign="top" class="rightlayout">
				<div style="position:relative;">
					<div id="fishbox" style="position:absolute;z-index:1000;display:none;background-color:#ffffff;padding-bottom:10px;width:100%;">
						<div class="workplace_body" >
						<div class="topleft"></div>
						<div class="topright"></div>
						<div class="buttomleft"></div>
						<div class="buttomright"></div>
						<div class="panelHeader" ><span id="fishboxTitle"></span><a href="javascript:closeFishbox();" style="float:right;">Close</a></div>
						<div id="fishboxContent" style="background-color:#ffffff;width:100%;"></div>
						</div>
					</div>
                    <div id="alert_box">
                    	<span class="close_btn">X</span>
                        <div class="text"></div>
                    </div>
					<div id="mask" style="
                    	display: none;  
                        position: absolute;  
                        top: 0%;  
                        left: 0%;  
                        width: 100%;  
                        height: 100%;  
                        background-color: black;  
                        z-index:1001;  
                        -moz-opacity: 0.7;  
                        opacity:.70;  
                        filter: alpha(opacity=70);
                    "><div style="color:#999999;font-size:25px;margin-left:auto;margin-right:auto;
                    	margin-top:100px;text-align:center;">æä»¬æ­£å¨åªåå è½½^_^ï¼ä¸è¿è²ä¼¼ç½éæç¹æ¢#_#ï¼éº»ç¦ç¨ç­ä¸ä¸</div></div>
					<div class="workplace" id="Main">
					 
						ï»¿
<div class="workplace_body" >
<div class="topleft"></div>
<div class="topright"></div>
<div class="buttomleft"></div>
<div class="buttomright"></div>
<div class="panelHeader"></div>
<div class="panelsubmenu">
<div style="text-align:right;padding-right:20px;">
		<a href="index.php?type=qiangxing_edit">å¼ºè¡é¢çº¦</a>
	</div>
</div>

<!--<div class="do_appointment_div">
	<iframe src="" width="600" height="400"></iframe>
</div>-->
<table width="100%" border="1" cellpadding="1" cellspacing="0" class="appointment_table" style='color:#4F5D66;'>
	<br/>
    <tr>
        </tr>
        	<tr>
        	<td style="padding-left:5px;" height="25" align="center"></td>
                        	<td style="padding-left:5px;" height="25" align="center"><span style="font-size:13px;font-weight:bold;">Aimee</span></td>
                        	<td style="padding-left:5px;" height="25" align="center"><span style="font-size:13px;font-weight:bold;">Lea</span></td>
                        	<td style="padding-left:5px;" height="25" align="center"><span style="font-size:13px;font-weight:bold;">Marion</span></td>
                        	<td style="padding-left:5px;" height="25" align="center"><span style="font-size:13px;font-weight:bold;">Bev</span></td>
                        	<td style="padding-left:5px;" height="25" align="center"><span style="font-size:13px;font-weight:bold;">Jamie(ä¸­æ)</span></td>
                    </tr>
        	<tr>
            <!--<td style="padding-left:5px;" height="25" align="center" rowspan="3">Jamie(ä¸­æ)</td>-->
           
            <td style="padding-left:5px;" height="25" align="center">
            	<span style="font-size:13px;font-weight:bold;">ä»å¤©</span>
                <br/>
				2014-05-24                <br/>
                ææå­            </td>
                        	<td style="padding-left:5px;" height="25" align="center">ä¼åä¸­</td>
                        	<td style="padding-left:5px;" height="25" align="center">ä¼åä¸­</td>
                        	<td style="padding-left:5px;" height="25" align="center">ä¼åä¸­</td>
                        	<td style="padding-left:5px;" height="25" align="center">ä¼åä¸­</td>
                        	<td style="padding-left:5px;" height="25" align="center">çº¦æ»¡</td>
                    </tr>
        	<tr>
            <!--<td style="padding-left:5px;" height="25" align="center" rowspan="3">Jamie(ä¸­æ)</td>-->
           
            <td style="padding-left:5px;" height="25" align="center">
            	<span style="font-size:13px;font-weight:bold;">æå¤©</span>
                <br/>
				2014-05-25                <br/>
                æææ¥            </td>
                        	<td style="padding-left:5px;" height="25" align="center">çº¦æ»¡</td>
                        	<td style="padding-left:5px;" height="25" align="center">çº¦æ»¡</td>
                        	<td style="padding-left:5px;" height="25" align="center"><a href='index.php?type=make_appointment_edit&teacher_id=8&date=2014-05-25'>é¢çº¦</a><br>ï¼æå1,2ç¯ï¼</td>
                        	<td style="padding-left:5px;" height="25" align="center">ä¼åä¸­</td>
                        	<td style="padding-left:5px;" height="25" align="center"><a href='index.php?type=make_appointment_edit&teacher_id=11&date=2014-05-25'>é¢çº¦</a></td>
                    </tr>
        	<tr>
            <!--<td style="padding-left:5px;" height="25" align="center" rowspan="3">Jamie(ä¸­æ)</td>-->
           
            <td style="padding-left:5px;" height="25" align="center">
            	<span style="font-size:13px;font-weight:bold;">åå¤©</span>
                <br/>
				2014-05-26                <br/>
                ææä¸            </td>
                        	<td style="padding-left:5px;" height="25" align="center"><a href='index.php?type=make_appointment_edit&teacher_id=1&date=2014-05-26'>é¢çº¦</a></td>
                        	<td style="padding-left:5px;" height="25" align="center">çº¦æ»¡</td>
                        	<td style="padding-left:5px;" height="25" align="center"><a href='index.php?type=make_appointment_edit&teacher_id=8&date=2014-05-26'>é¢çº¦</a></td>
                        	<td style="padding-left:5px;" height="25" align="center">ä¼åä¸­</td>
                        	<td style="padding-left:5px;" height="25" align="center"><a href='index.php?type=make_appointment_edit&teacher_id=11&date=2014-05-26'>é¢çº¦</a></td>
                    </tr>
        <!--<tr style="height:20px;"><td colspan="2"></td></tr>-->
   
   
</table>
<table width="100%" border="0" cellspacing="0">
	<tr colspan="4" height="10"></tr>
    <tr>
      <td width="220" height="30" align='left'></td>
      <td width="" class="title" colspan='3'><a href=?page=1>é¦é </a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=?page=-1>ä¸ä¸é </a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>ä¸ä¸é </a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>æ«é </a>&nbsp;&nbsp;&nbsp;&nbsp; <select onchange="javascript:submitback('?page='+ this.options[this.selectedIndex].value)">
</select> &nbsp;&nbsp;ç¸½å±æ&nbsp;&nbsp;é  &nbsp;&nbsp;æ¯é &nbsp;æ¢è¨é</td>
	</tr>
	<tr colspan="4" height="10"></tr>
</table>
</div>					 </div>
				 </div>
		 	   </td>
			</tr>
  		</table>
 	</div>
</body>

</html>
