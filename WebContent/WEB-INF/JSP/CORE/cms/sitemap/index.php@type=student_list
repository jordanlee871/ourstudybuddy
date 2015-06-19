<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后臺管理系統</title>
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
<div class="menu3 "><a href="index.php?type=student_edit&backType=student_list">注册</a></div>
<div class="menu3 selected"><a href="index.php?type=student_list">学生管理</a></div>
<div class="menu3 "><a href="index.php?type=appointment_list">预约记录</a></div>
<div class="menu3 "><a href="index.php?type=appointment_edit">预约</a></div>
<div class="menu3 menu_search_box_div">
	<input id="fuzzy_search_box"/>
</div>
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
                    	margin-top:100px;text-align:center;">我们正在努力加载^_^，不过貌似网速有点慢#_#，麻烦稍等一下</div></div>
					<div class="workplace" id="Main">
					 
						
<div class="workplace_body" >
<div class="topleft"></div>
<div class="topright"></div>
<div class="buttomleft"></div>
<div class="buttomright"></div>
<div class="panelHeader">学生资料</div>
<div class="panelsubmenu">
<div style="text-align:right;padding-right:20px;">
		<a href="index.php?type=student_edit&backType=student_list">增加学生</a>
	</div>
</div>
<table width="100%" border="0" cellspacing="0" style='color:#4F5D66;'>
	<br/>
    <tr>
    			<td style="padding-left:5px;" height="25" align="center"><span class="title">旺旺</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">QQ</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">手机</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">邮箱</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">注册时间</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title"></span></td>
   	    </tr>
    <tr height='15'>
    	<form action="index.php" method="post">
        	<input type="hidden" value="student_list" name="type"/>
			<td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="wanwan" 
            	value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="qq" 
            value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="phone" 
            value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="email" 
            value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="date"
             value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input value="搜索" type="submit"/><br/>
            
        </form>
    </tr>
	<!-- <tr height='15'><td colspan='5'></td></tr>-->
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">fanbinzj</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">13629262402</td>
        <td style="padding-left:5px;" height="25" align="center">1056226976@qq.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-07 16:14:43 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2522">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">tb5057012</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">18649756862</td>
        <td style="padding-left:5px;" height="25" align="center">407540884@qq.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-07 14:26:15 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2521">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">没有萌的嗨</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">15071277035</td>
        <td style="padding-left:5px;" height="25" align="center">evonnep@163.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-07 11:38:25 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2520">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">小queen妮</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">13771903956</td>
        <td style="padding-left:5px;" height="25" align="center">306763472@qq.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-07 11:37:55 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2519">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">时光浅1120</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">13301172514</td>
        <td style="padding-left:5px;" height="25" align="center">1025588079@qq.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-06 22:56:35 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2518">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">hey_girl0601</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">18712785621</td>
        <td style="padding-left:5px;" height="25" align="center">rachel.ryq@gmail.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-06 21:51:33 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2517">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">liruiyan1464</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">18682954801</td>
        <td style="padding-left:5px;" height="25" align="center">851405059@qq.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-06 20:52:10 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2516">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">lotus096</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">13826531859</td>
        <td style="padding-left:5px;" height="25" align="center">1094915388@qq.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-06 18:31:37 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2515">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">danlovesxin</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">18797415448</td>
        <td style="padding-left:5px;" height="25" align="center">275021080@qq.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-06 14:36:41 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2514">详情</a></td>
    </tr>
    
    	<tr>
		<td style="padding-left:5px;" height="25" align="center">amberwx佳</td>
        <td style="padding-left:5px;" height="25" align="center"></td>
        <td style="padding-left:5px;" height="25" align="center">13735553446</td>
        <td style="padding-left:5px;" height="25" align="center">amber_wxj@163.com</td>
        <td style="padding-left:5px;" height="25" align="center">2014-09-06 14:04:27 </td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=student_edit&backType=student_list&editId=2513">详情</a></td>
    </tr>
    	<!--<tr>
    	<td style="padding-left:5px;" height="25">Essay3</td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25"></td>
		<td style="padding-left:5px;" height="25"></td>
		<td style="padding-left:5px;" height="25"><a href="">修改</a><a href="">删除</a></td>
    </tr>
	<tr>
    	<td style="padding-left:5px;" height="25">Essay2</td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25">（老师正在批改，请稍等……）</td>
		<td style="padding-left:5px;" height="25"></td>
		<td style="padding-left:5px;" height="25"></td>
    </tr>
	<tr>
    	<td style="padding-left:5px;" height="25">Essay1</td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25"><a href="">点击下载</a></td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25"></td>
    </tr>-->
</table>
<table width="100%" border="0" cellspacing="0">
	<tr colspan="4" height="10"></tr>
    <tr>
      <td width="220" height="30" align='left'></td>
      <td width="" class="title" colspan='3'><a href='#'>首頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>上一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=index.php?type=student_list&page=2>下一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=index.php?type=student_list&page=247>末頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <select onchange="javascript:submitback('index.php?type=student_list&page='+ this.options[this.selectedIndex].value)">
	<option value=1 selected>1</option>
	<option value=2>2</option>
	<option value=3>3</option>
	<option value=4>4</option>
	<option value=5>5</option>
	<option value=6>6</option>
	<option value=7>7</option>
	<option value=8>8</option>
	<option value=9>9</option>
	<option value=10>10</option>
	<option value=11>11</option>
	<option value=12>12</option>
	<option value=13>13</option>
	<option value=14>14</option>
	<option value=15>15</option>
	<option value=16>16</option>
	<option value=17>17</option>
	<option value=18>18</option>
	<option value=19>19</option>
	<option value=20>20</option>
	<option value=21>21</option>
	<option value=22>22</option>
	<option value=23>23</option>
	<option value=24>24</option>
	<option value=25>25</option>
	<option value=26>26</option>
	<option value=27>27</option>
	<option value=28>28</option>
	<option value=29>29</option>
	<option value=30>30</option>
	<option value=31>31</option>
	<option value=32>32</option>
	<option value=33>33</option>
	<option value=34>34</option>
	<option value=35>35</option>
	<option value=36>36</option>
	<option value=37>37</option>
	<option value=38>38</option>
	<option value=39>39</option>
	<option value=40>40</option>
	<option value=41>41</option>
	<option value=42>42</option>
	<option value=43>43</option>
	<option value=44>44</option>
	<option value=45>45</option>
	<option value=46>46</option>
	<option value=47>47</option>
	<option value=48>48</option>
	<option value=49>49</option>
	<option value=50>50</option>
	<option value=51>51</option>
	<option value=52>52</option>
	<option value=53>53</option>
	<option value=54>54</option>
	<option value=55>55</option>
	<option value=56>56</option>
	<option value=57>57</option>
	<option value=58>58</option>
	<option value=59>59</option>
	<option value=60>60</option>
	<option value=61>61</option>
	<option value=62>62</option>
	<option value=63>63</option>
	<option value=64>64</option>
	<option value=65>65</option>
	<option value=66>66</option>
	<option value=67>67</option>
	<option value=68>68</option>
	<option value=69>69</option>
	<option value=70>70</option>
	<option value=71>71</option>
	<option value=72>72</option>
	<option value=73>73</option>
	<option value=74>74</option>
	<option value=75>75</option>
	<option value=76>76</option>
	<option value=77>77</option>
	<option value=78>78</option>
	<option value=79>79</option>
	<option value=80>80</option>
	<option value=81>81</option>
	<option value=82>82</option>
	<option value=83>83</option>
	<option value=84>84</option>
	<option value=85>85</option>
	<option value=86>86</option>
	<option value=87>87</option>
	<option value=88>88</option>
	<option value=89>89</option>
	<option value=90>90</option>
	<option value=91>91</option>
	<option value=92>92</option>
	<option value=93>93</option>
	<option value=94>94</option>
	<option value=95>95</option>
	<option value=96>96</option>
	<option value=97>97</option>
	<option value=98>98</option>
	<option value=99>99</option>
	<option value=100>100</option>
	<option value=101>101</option>
	<option value=102>102</option>
	<option value=103>103</option>
	<option value=104>104</option>
	<option value=105>105</option>
	<option value=106>106</option>
	<option value=107>107</option>
	<option value=108>108</option>
	<option value=109>109</option>
	<option value=110>110</option>
	<option value=111>111</option>
	<option value=112>112</option>
	<option value=113>113</option>
	<option value=114>114</option>
	<option value=115>115</option>
	<option value=116>116</option>
	<option value=117>117</option>
	<option value=118>118</option>
	<option value=119>119</option>
	<option value=120>120</option>
	<option value=121>121</option>
	<option value=122>122</option>
	<option value=123>123</option>
	<option value=124>124</option>
	<option value=125>125</option>
	<option value=126>126</option>
	<option value=127>127</option>
	<option value=128>128</option>
	<option value=129>129</option>
	<option value=130>130</option>
	<option value=131>131</option>
	<option value=132>132</option>
	<option value=133>133</option>
	<option value=134>134</option>
	<option value=135>135</option>
	<option value=136>136</option>
	<option value=137>137</option>
	<option value=138>138</option>
	<option value=139>139</option>
	<option value=140>140</option>
	<option value=141>141</option>
	<option value=142>142</option>
	<option value=143>143</option>
	<option value=144>144</option>
	<option value=145>145</option>
	<option value=146>146</option>
	<option value=147>147</option>
	<option value=148>148</option>
	<option value=149>149</option>
	<option value=150>150</option>
	<option value=151>151</option>
	<option value=152>152</option>
	<option value=153>153</option>
	<option value=154>154</option>
	<option value=155>155</option>
	<option value=156>156</option>
	<option value=157>157</option>
	<option value=158>158</option>
	<option value=159>159</option>
	<option value=160>160</option>
	<option value=161>161</option>
	<option value=162>162</option>
	<option value=163>163</option>
	<option value=164>164</option>
	<option value=165>165</option>
	<option value=166>166</option>
	<option value=167>167</option>
	<option value=168>168</option>
	<option value=169>169</option>
	<option value=170>170</option>
	<option value=171>171</option>
	<option value=172>172</option>
	<option value=173>173</option>
	<option value=174>174</option>
	<option value=175>175</option>
	<option value=176>176</option>
	<option value=177>177</option>
	<option value=178>178</option>
	<option value=179>179</option>
	<option value=180>180</option>
	<option value=181>181</option>
	<option value=182>182</option>
	<option value=183>183</option>
	<option value=184>184</option>
	<option value=185>185</option>
	<option value=186>186</option>
	<option value=187>187</option>
	<option value=188>188</option>
	<option value=189>189</option>
	<option value=190>190</option>
	<option value=191>191</option>
	<option value=192>192</option>
	<option value=193>193</option>
	<option value=194>194</option>
	<option value=195>195</option>
	<option value=196>196</option>
	<option value=197>197</option>
	<option value=198>198</option>
	<option value=199>199</option>
	<option value=200>200</option>
	<option value=201>201</option>
	<option value=202>202</option>
	<option value=203>203</option>
	<option value=204>204</option>
	<option value=205>205</option>
	<option value=206>206</option>
	<option value=207>207</option>
	<option value=208>208</option>
	<option value=209>209</option>
	<option value=210>210</option>
	<option value=211>211</option>
	<option value=212>212</option>
	<option value=213>213</option>
	<option value=214>214</option>
	<option value=215>215</option>
	<option value=216>216</option>
	<option value=217>217</option>
	<option value=218>218</option>
	<option value=219>219</option>
	<option value=220>220</option>
	<option value=221>221</option>
	<option value=222>222</option>
	<option value=223>223</option>
	<option value=224>224</option>
	<option value=225>225</option>
	<option value=226>226</option>
	<option value=227>227</option>
	<option value=228>228</option>
	<option value=229>229</option>
	<option value=230>230</option>
	<option value=231>231</option>
	<option value=232>232</option>
	<option value=233>233</option>
	<option value=234>234</option>
	<option value=235>235</option>
	<option value=236>236</option>
	<option value=237>237</option>
	<option value=238>238</option>
	<option value=239>239</option>
	<option value=240>240</option>
	<option value=241>241</option>
	<option value=242>242</option>
	<option value=243>243</option>
	<option value=244>244</option>
	<option value=245>245</option>
	<option value=246>246</option>
	<option value=247>247</option>
</select> &nbsp;&nbsp;總共有&nbsp;247&nbsp;頁 &nbsp;&nbsp;每頁10&nbsp;條記錄</td>
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
