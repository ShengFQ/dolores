<a name="top">轻应用JS桥API接口使用说明(本文档仅供内部app开发使用)</a>

API_V1.0
这里案例:oa.com=192.168.16.223:9000/oa
需要引入的js文件到用户界面:
qingjs.js

jquery_lastest.js

##接口返回消息说明
<table  class="table table-bordered table-striped table-condensed">
   <tr>
      <td>返回值字段</td>
      <td>字段类型</td>
      <td>字段说明</td>
   </tr>
   <tr>
      <td>xxx</td>
      <td>string</td>
      <td>返回的消息文本</td>
   </tr>
</table>


##目录
	
## <a NAME="userapp">轻应用</a>
### <a name="userapp_read">读取接口</a>

##### getPersonLoginID() 获取用户登录id
#####url:mobileJsBridge. getPersonLoginID()
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无
返回结果<br/>
text

字段说明<br/>


<table  class="table table-bordered table-striped table-condensed">
   <tr>
      <td>返回值字段</td>
      <td>字段类型</td>
      <td>字段说明</td>
   </tr>
   <tr>
      <td>loginid</td>
      <td>string</td>
      <td>当前登录oa的用户loginid</td>
   </tr>
</table>

##### getPersonInfo() 获取用户姓名
#####url:mobileJsBridge. getPersonInfo()
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无
返回结果<br/>
text

字段说明<br/>


<table>
 
   <tr>
      <td>返回值字段</td>
      <td>字段类型</td>
      <td>字段说明</td>
   </tr>
   <tr>
      <td>username</td>
      <td>string</td>
      <td>当前登录oa的中文用户姓名</td>
   </tr>
</table>

##### getPersonUID() 获取用户ID
#####url:mobileJsBridge. getPersonUID()
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无
返回结果<br/>
text

字段说明<br/>


<table>
   <tr>
      <td>返回值字段</td>
      <td>字段类型</td>
      <td>字段说明</td>
   </tr>
   <tr>
      <td>userid</td>
      <td>string</td>
      <td>当前登录oa的用户ID</td>
   </tr>
</table>


##### getPersonPwd() 获取oa登录用户加密密码
#####url:mobileJsBridge. getPersonPwd() //通过MD5加密的登录用户密码
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无
返回结果<br/>
text

字段说明<br/>

<table>
   <tr>
      <td>返回值字段</td>
      <td>字段类型</td>
      <td>字段说明</td>
   </tr>
   <tr>
      <td>name</td>
      <td>string</td>
      <td>当前登录oa的密码</td>
   </tr>
</table>

##### close() 关闭当前窗口
#####url:mobileJsBridge.close()
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无
返回结果<br/>
无

##### hideTitleBar() 隐藏导航栏
#####url:mobileJsBridge. hideTitleBar()
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无
返回结果<br/>
无

##### showTitleBar() 显示导航栏
#####url:mobileJsBridge. showTitleBar()
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无
返回结果<br/>
无

##### setTitleBar(string title) 设置导航栏标题
#####url:mobileJsBridge. setTitleBar(string title)
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
string title 标题
返回结果<br/>
无

##### showToast(string msg) 弹出短时间显示消息
#####url:mobileJsBridge. showToast(string msg)
支持格式<br/>
javascript<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
string message
返回结果<br/>
无


##### 第三方轻应用服务端获取oa的用户名和加密后密码
#####url:http://lightapp_url?username=xxx&userpwd=yyy&userid=zzz&loginid=aaa
支持格式<br/>
java<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
无

返回结果<br/>
用户姓名:request.getparameter("username");

密码:request.getparameter("userpwd");

用户id:request.getparameter("userid");

用户loginid:request.getparameter("loginid");
<a href="#top">回顶部</a>