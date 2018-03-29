<a name="top">API接口使用说明(本文档仅供内部app开发使用)</a>

API_V1.0
这里案例:oa.com=192.168.16.223:9000/oa
进入http://192.168.16.223:9000/oa/mobile/... 使用移动端访问服务器端接口

为了验证用户登录权限,每个api接口访问的http header区都要附加token参数,该参数由oa服务器登录后给出
##接口返回消息说明
| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| status | int | 接口返回0:失败 1:成功 |
| msg | string | 错误详细信息  |
| code | int | 错误代码 0:默认 |

##目录
<a href="#userapp"> <h3> 1.轻应用</h3> </a>
<a href="#userapp_read">读接口</a>

	  返回用户的轻应用列表
	  根据轻应用id请求图标
	
	  
<a href="#worklog"><h3>2.工作日志</h3></a>	<a href="#worklog_read"> 读接口 </a>

	 获取当前用户的个人工作日志列	 
	 获取工作日志详细信息
    日志汇总查询
    获取默认的共享人员
<a href="#worklog_write">写接口</a> 
	
	 保存当前默认的共享人员
	 暂存或发布或重新发布工作日志
	 删除工作日志
	 删除工作日志明细
	 
<a href="#meetings"><h3>3.会议管理</h3></a>
<a href="#meetings_read">读接口</a>

	获取当前用户待参加的
	获取待参加会议详细信息
	待审核会议查询
	历史会议查询,能够搜索已经审核通过或不需要审核的会议概要
	获取历史会议详细信息
	获取历史会议详细信息的附件下载
	我发起的会议查询
	

<a href="#meetings_write">写接口</a>

	待参加会议处理
	发起会议-基础数据
	发起会议-保存
	会议审核保存
<a href="#bulletins"> <h3> 4.通知公告</h3> </a>
<a href="#bulletins_read">读接口</a>

	  返回用户的轻应用列表
	  根据轻应用id请求图标
	  	
## <a NAME="userapp">轻应用</a>
### <a name="userapp_read">读取接口</a>
#####/userapp/userapplist?type=1 or 2 返回用户的轻应用列表
#####url:http://oa.com/mobile/userapp/userapplist?type=1 or 2 
支持格式<br/>
JSON<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|type|int|轻应用类别 1:我的应用 2:生产数据|必填|

http header:token<br/>
返回结果 json示例

```

{
    "rspHeader":{
        "status":1,
        "msg":"获取列表操作成功 ！",
        "code":0
    },
    "rspBody":{
        "count":2,
        "linkType":"我的应用",
        "linktreeLinkVOs":[
            {
                "name":"移动轻应用",
                "id":1010,
                "link":"http://192.168.16.223:9000/demo_xt_js_bridge.jsp",
                "serverfile":"/linktree/link\2\2016061600341524827359",
                "fileext":"image/x-png",
                "filename":null,
                "createdate":1465357312483,
                "modifydate":1465369173547,
                "pos":1,
                "filesize":5194,
                "inheritance":1,
                "thumbnail_pic":"/mobile/userapp/queryimage?id=1010"
            },
            {
                "name":"我的移动应用4",
                "id":1014,
                "link":"http://192.168.16.223:9000/demo_xt_js_bridge.jsp",
                "serverfile":"/linktree/link\2\2016061600709411019170",
                "fileext":"image/x-png",
                "filename":null,
                "createdate":1465369781100,
                "modifydate":1465369781100,
                "pos":3,
                "filesize":5194,
                "inheritance":0,
                 "thumbnail_pic":"/mobile/userapp/queryimage?id=1014"
            }
        ]}
}

```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| count | int | 轻应用条目数量 |
| linkType | string | 轻应用类目名称 |
| name | string | 轻应用名称 |
| id | int | 轻应用ID  |
| link | string | 轻应用目标链接 |
| thumbnail_pic | string | 轻应用图标链接 需要全路径 |
| serverfile | string | 待定 |
| fileext | string | 轻应用图片格式 |
| filename | string | 待定 |

#####/userapp/queryimage?id=xxxx 根据轻应用id请求图标
#####url:http://oa.com/mobile/userapp/queryimage?id=1
支持格式<br/>
.ipeg,.jpg image/jpeg<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
http header:token

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|id|int|轻应用ID|必填|
返回结果<br/>
图片的二进制字节流，格式为经过BASE64编码的字符串



##<a name="worklog">工作日志</a> 
###<a name="worklog_read">读取接口</a>
#####/worklog/user_worklog 获取当前用户的个人工作日志列表

URL:http://oa.com/mobile/worklog/user_worklog

支持分页
是

支持格式
JSON

HTTP请求方式
GET

是否需要登录
是<br/>

请求参数<br/>

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|logDay\_s_ge|string|起始日期 例:'1970-1-1'|选填|
|logDay\_s_le|string|结束日期 例:'1970-1-1'|选填|
|ec_p|int|分页-当前页码|选填|
|ec_crd|int|分页-每页记录数|选填|
返回结果<br/>
JSON示例<br/>

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "count": 2,
    "list": [
      {
        "id": 2002,
        "status": 1,
        "user": "user2",
        "title": "2016-06-28--user2--工作日志",
        "opinion": null,
        "detail": null,
        "remindDate": 1467201600107,
        "createDate": 1467094629003,
        "logDay": "2016-06-28",
        "isremind": 1,
        "remindContent": "完成既定工作",
        "hasMX":true,
        "hasAttachs":false,
        "hasReadUser":false
      },
      {
        "id": 1002,
        "status": 1,
        "user": "user2",
        "title": "2016-06-23--user2--工作日志",
        "opinion": null,
        "detail": null,
        "remindDate": 1466760600837,
        "createDate": 1466670768823,
        "logDay": "2016-06-23",
        "isremind": 0,
        "remindContent": "",
        "hasMX":true,
        "hasAttachs":false,
        "hasReadUser":false
      }
    ]
  }
}


```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int | ID |
| status | int | 发布状态 1:已发布 0:暂存  |
| user | string | 发布人姓名 |
| title | string | 日志标题 |
| opinion | string | 领导批示 |
| detail | string | 空白|
| createDate | date | 日志创建日期(真实日期)  |
| logDay | string | 日志记录日期(日志补发日期) |
| isremind | int | 是否加入任务 1:加入 0:不加入(默认) |
| remindDate | date | 任务提醒日期 |
| remindContent | string | 任务内容 |
| hasMX | boolean | 是否有明细 |
| hasAttachs | boolean | 是否有附件 |
| hasReadUser | boolean | 是否有阅读记录 |

##### <a name="worklog_user_worklog_detail">/worklog/user_worklog_detail?id=xxx 获取工作日志详细信息</a>
URL:http://oa.com/mobile/worklog/user_worklog_detail?id=xxx

支持格式:
JSON

HTTP请求方式:GET

是否需要登录:是

请求参数:

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|id|int|工作日志id|必填|

返回结果

JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": { 
          "id": 1002,
        "status": 1,
        "user": "user2",
        "title": null,
        "opinion": null,
        "detail": null,
        "remindDate": 1466760600837,
        "createDate": 1466670768823,
        "logDay": "2016-06-23",
        "isremind": 0,
        "remindContent": "",
        "worklogMxes": [
          {
            "id": 2002,
            "content": "第一条工作日志",
            "share": 1,
            "workHour": 4,
            "finish": 10,
            "assistUser": "王"
          },
          {
            "id": 2003,
            "content": "第二条工作日志",
            "share": 3,
            "workHour": 3,
            "finish": 10,
            "assistUser": "刘"
          },
          {
            "id": 2004,
            "content": "第三条工作日志",
            "share": 2,
            "workHour": 2,
            "finish": 10,
            "assistUser": "陈"
          }
        ],
        "worklogAttachs": [
          {
            "id": 1,
            "serverfile": "/worklog\\4\\1002\\2016061800239217118114",
            "fileext": "text/html",
            "filename": "Eula.txt",
            "filesize": 7005,
            "createDate": 1467081561710
          },
          {
            "id": 2,
            "serverfile": "/worklog\\4\\1002\\2016061800239217589152",
            "fileext": "application/octet-stream",
            "filename": "code21.png",
            "filesize": 5194,
            "createDate": 1467081561757
          }
        ],
        "worklogReadusers": [
          {
            "id": 1002,
            "user": "user1",
            "createdate": 1467081725323,
            "readCount": 1,
            "isNew": 0
          },
          {
            "id": 1003,
            "user": "用户3",
            "createdate": 1467081767990,
            "readCount": 1,
            "isNew": 0
          }
        ],
        "worklogShareUsers": [
      {
        "userName": "user1",
        "uid": 3
      },
      {
        "userName": "用户3",
        "uid": 5
      }
    ],
        "hasMX": true,
        "hasAttachs": true,
        "hasShareUser": true,
        "hasReadUser": true
      }
}


```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int | ID |
| status | int | 发布状态 1:已发布 0:暂存  |
| user | string | 发布人姓名 |
| title | string | 日志标题 |
| opinion | string | 领导批示 |
| detail | string | 任务内容 |
| remindDate | date | 任务提醒日期 |
| createDate | date | 日志创建日期(真实日期)  |
| logDay | string | 日志记录日期(日志补发日期) |
| isremind | int | 是否加入任务 1:加入 0:不加入(默认) |
| hasMX | boolean | 是否有明细日志 |
| hasAttachs | boolean | 是否有附件 |
| hasReadUser | boolean | 是否有阅读记录 |
| hasShareUser | boolean | 是否有默认共享人员 |
|worklogShareUsers|Object |日志分享人员|
| worklogMxes | Object | 日志明细条目 |
| worklogAttachs | Object | 日志附件条目 |
| worklogReadusers | Object | 日志阅读记录 |

worklogMxes

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int | ID |
| content | string | 内容 |
| share | int | 共享范围(1公开 2共享 3个人)  |
| workHour | double | 工时 |
| finish | int | 进度 |
| assistUser | string | 配合人姓名 |

worklogAttachs

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int | ID |
| serverfile | int | 服务器存储文件地址(访问需要url path) |
| fileext | int | 文件扩展名  |
| filename | string | 文件全名 |
| filesize | string | 文件大小 |
| createDate | string | 创建日期 |

worklogReadusers

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int | ID |
| user | string | 阅读者 |
| createdate | date | 最后一次阅读时间  |
| readCount | int | 阅读次数 |
| isNew | int | 是否第一次阅读 |

worklogShareUsers

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| uid | int | ID |
| userName | string | 分享者 |


##### /worklog/public_worklog 日志汇总查询
URL:http://oa.com/mobile/worklog/public_worklog
支持分页:
是

支持格式:JSON

HTTP请求方式:GET

是否需要登录:是

请求参数:

|名称|类型|说明|必填|
|:-----:|:-----|:-----|:-----|
|logDay_s_ge|string|日志记录日期起始|选填|
|logDay_s_le|string|日志记录日期结束|选填|
|userXXname_s_like|string|用户名|选填|
|ec_p|int|分页-当前页码|选填|
|ec_crd|int|分页-每页记录数|选填|
|t|int|类别|必填 0-所有 1-公开日志 2-共享日志 3-我的个人 4-我关注的人|

返回结果:
JSON 示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "count": 2,
    "list": [
      {
        "id": 2010,
        "status": 1,
        "user": "user2",
        "title": "2016-06-30--user2--工作日志",
        "opinion": null,
        "createDate": 1467297604043,
        "isremind": 0,
        "remindDate": 1467365400043,
        "detail": null,
        "logDay": "2016-06-30",
        "remindContent": "",
        "hasMX": true,
        "hasAttachs": false,
        "hasReadUser": false
      },
      {
        "id": 2005,
        "status": 1,
        "user": "user2",
        "title": "2016-06-29--user2--工作日志",
        "opinion": null,
        "createDate": 1467221600583,
        "isremind": 0,
        "remindDate": 1467279000600,
        "detail": null,
        "logDay": "2016-06-29",
        "remindContent": "",
        "hasMX": true,
        "hasAttachs": false,
        "hasReadUser": true
      }
    ]
  }
}
```
字段说明(与获取个人工作日志列表一致):

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int | ID |
| status | int | 发布状态 1:已发布 0:暂存  |
| user | string | 发布人姓名 |
| title | string | 日志标题 |
| opinion | string | 领导批示 |
| detail | string | 空白|
| createDate | date | 日志创建日期(真实日期)  |
| logDay | string | 日志记录日期(日志补发日期) |
| isremind | int | 是否加入任务 1:加入 0:不加入(默认) |
| remindDate | date | 任务提醒日期 |
| remindContent | string | 任务内容 |
| hasMX | boolean | 是否有明细 |
| hasAttachs | boolean | 是否有附件 |
| hasReadUser | boolean | 是否有阅读记录 |

##### /worklog/get_worklog_default_shareuser 获取默认的共享人员
URL:http://oa.com/mobile/worklog/get_worklog_default_shareuser

支持格式:JSON

HTTP请求方式:GET

是否需要登录:是

返回结果:
JSON 示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "count": 2,
    "list": [
      {       
        "name": "user1",
        "id": 3
      },
      {
        "name": "用户3",
        "id": 5
      }
    ]
  }
}
```
字段说明(与获取个人工作日志列表一致):

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| name | string | 默认共享用户用户名 |
| id | int | 默认共享用户id |

<a href="#top">回顶部</a>


###<a name="worklog_write">写入接口</a>

##### /worklog/save_worklog_default_shareuser 保存当前默认的共享人员
URL:http://oa.com/mobile/worklog/save_worklog_default_shareuser

支持格式:JSON

HTTP请求方式:POST

是否需要登录:是

参数说明:
|名称|类型|说明|必填|
|:-----:|:-----|:-----|:-----|
|userids|string|用户id数组 例如:2,3,4|必填|

返回结果:
JSON 示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "设置默认共享用户成功",
    "code": 0
  },
  "rspBody": ""
}

```


##### /worklog/user_worklog_save  暂存或发布或重新发布工作日志
支持格式:JSON

HTTP请求方式:POST

是否需要登录:是

请求参数:JSON格式

JSON示例

```
{"id":0,"isNew":0,"logDay":"2016-07-06","title":"aaaa","status":1,"userids":"3,5","content":"新消息1","finish":11,"assistUser":"ba","workHour":23,"share":2}
```

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|isNew|int|新建还是修改 0/1 说明:0代表新建工作日志 1表示修改工作日志| 必填|
|id|string|工作日志id|修改日志时必填|
|logDay|string|工作日志发布日期|必填|
|title|string|标题|选填|
|status|int|发布状态 0暂存 1发布|必填|
|userids|string|共享用户id数组 e:1,2,3|选填|
|mxid|int|日志明细id|修改工作日志明细时必填|
|content|string|日志明细内容|必填|
|assistUser|string|协助人|选填|
|finish|int|进度|选填|
|workHour|int|工时|选填|
|share|int|共享范围|必填  可选值 1公开 2共享 3个人 |
返回结果

JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "日志发布成功 ！",
    "code": 0
  },
  "rspBody": { 
          "id": 1002,
        "status": 1,
        "user": "user2",
        "title": null,
        "opinion": null,
        "detail": null,
        "remindDate": 1466760600837,
        "createDate": 1466670768823,
        "logDay": "2016-06-23",
        "isremind": 0,
        "remindContent": "",
        "worklogMxes": [
          {
            "id": 2002,
            "content": "第一条工作日志",
            "share": 1,
            "workHour": 4,
            "finish": 10,
            "assistUser": "王"
          },
          {
            "id": 2003,
            "content": "第二条工作日志",
            "share": 3,
            "workHour": 3,
            "finish": 10,
            "assistUser": "刘"
          },
          {
            "id": 2004,
            "content": "第三条工作日志",
            "share": 2,
            "workHour": 2,
            "finish": 10,
            "assistUser": "陈"
          }
        ],
        "worklogAttachs": [],
        "worklogReadusers": [
          {
            "id": 1002,
            "user": "user1",
            "createdate": 1467081725323,
            "readCount": 1,
            "isNew": 0
          },
          {
            "id": 1003,
            "user": "用户3",
            "createdate": 1467081767990,
            "readCount": 1,
            "isNew": 0
          }
        ],
        "hasMX": true,
        "hasAttachs": false,
        "hasReadUser": true
      }
}

```
代码返回

|状态码|错误代码|错误信息|说明|
|:-----:|:----|:-----|:---|
|0|rspHeader.code:1|rspHeader.msg:您没有输入任何数据，请重新输入||
|0|rspHeader.code:0|rspHeader.msg:日志发布成功||
|0|rspHeader.code:0|rspHeader.msg:日志暂存成功||

##### /worklog/user_worklog_delete  删除工作日志
支持格式:JSON

HTTP请求方式:POST

是否需要登录:是

请求参数:JSON格式

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|ids|string|工作日志编号id数组 e:111,222| 必填|

返回结果

jSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "成功删除3条日志 ！",
    "code": 0
  },
  "rspBody": { }
  
  }
```
代码返回

|状态码|错误代码|错误信息|说明|
|:-----:|:----|:-----|:---|
|0|rspHeader.code:1|rspHeader.msg:日志编号不能为空||
|0|rspHeader.code:2|rspHeader.msg:用户无权删除||
|0|rspHeader.code:3|rspHeader.msg:未知异常||

##### /worklog/user_worklog_detail_delete  删除工作日志明细
支持格式:JSON

HTTP请求方式:POST

是否需要登录:是

请求参数:JSON格式

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|mx_ids|string|工作日志明细编号id数组 e:111,222| 必填|

返回结果

jSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "成功删除3条日志明细 ！",
    "code": 0
  },
  "rspBody": { }
  
  }
```
代码返回

|状态码|错误代码|错误信息|说明|
|:-----:|:----|:-----|:---|
|0|rspHeader.code:1|rspHeader.msg:日志明细编号不能为空||
|0|rspHeader.code:2|rspHeader.msg:用户无权删除||
|0|rspHeader.code:3|rspHeader.msg:未知异常||

----------
<a href="#top">回顶部</a>

##<a name="meetings">会议管理</a>
###<a name="meetings_read">读取接口</a>
#####/meetings/user_wait_meeting 获取当前用户待参加的会议

URL:http://oa.com/mobile/meetings/user_wait_meeting

支持分页
是

支持格式
JSON

HTTP请求方式
GET

是否需要登录
是<br/>

请求参数<br/>

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|ec_p|int|分页-当前页码|选填|
|ec_crd|int|分页-每页记录数|选填|

返回结果<br/>
JSON示例<br/>

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "count": 1,
    "meetingVOs": [
      {
        "name": "2016-7-11 会议1",
        "startDay": "16-7-11 13:00",
        "endDay": "16-7-11 14:00",
        "applyUser": "user2",
        "rooms": "会议室1",
        "hasAttachs": false,
        "hasSummarys": false,
        "atCount":"3",
        "sendstatu":"已发送",
        "attendstatu":"参加",
        "meetingtype":"部门会议"
        "hasMeetingUser": true,
        "meetingstatu":"未关闭",
        "emceeUser":"user2"
      }
    ]
  }
}


```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| name | string| 会议名称 |
| startDay | string | 起始日期   |
| endDay | string | 结束日期  |
| rooms | string | 会议室  |
| applyUser | string | 会议发起人 |
| emceeUser | string | 会议主持人 |
| atCount | string | 会议人数 |
| meetingstatu | string | 会议状态 可选值:已关闭 未关闭 |
| meetingtype | string | 会议类型 可选值:部门会议/销售会议 |
| sendstatu | string | 会议通知发送状态 可选值:未通知 已通知 未确定 空 |
| attendstatu | string | 我的参与状态 确定参与 拒绝参与 未确定  空|
| hasAttachs | boolean | 是否有附件  |
| hasSummarys | boolean | 是否有描述 |
| hasMeetingUser | boolean | 是否有参与人  |
<a href="#top">回顶部</a>

##### /meetings/user_wait_meeting_detail 获取待参加会议详细信息
URL:http://oa.com/mobile/meetings/user_wait_meeting_detail

支持格式:
JSON

HTTP请求方式:GET

是否需要登录:是

请求参数:

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|meetingId|int|会议id|必填|

返回结果

JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "",
    "code": 0
  },
  "rspBody": {
    "meeting": {
      "content": "111111",
      "createdate": "2016-08-15 12:19:40",
      "aduitflag": 3,
      "meetingUsers": [
        {
          "id": 4229,
          "reason": null,
          "user": "超级用户",
          "responsionstatu": "未确定",
          "responsionsdate": ""
        },
        {
          "id": 4230,
          "reason": null,
          "user": "user1",
          "responsionstatu": "未确定",
          "responsionsdate": ""
        },
        {
          "id": 4231,
          "reason": null,
          "user": "user2",
          "responsionstatu": "确定参与",
          "responsionsdate": "2016-08-15 14:45:24"
        },
        {
          "id": 4232,
          "reason": null,
          "user": "用户3",
          "responsionstatu": "未确定",
          "responsionsdate": ""
        }
      ],
      "meetingSummarys": [],
      "isPubMeeting": 1,
      "serverCode": "5!4!",
      "noticetype": null,
      "summarycontent": null,
      "summarydate": "",
      "meetingAttachs": [
        {
          "id": 9,
          "type": 1,
          "userId": 4,
          "serverfile": "/meeting/attach\\2016-08\\2016082280245243418896\\2016082280245243605156",
          "fileext": "application/octet-stream",
          "filename": "Eula.txt",
          "createdate": 1471243524340,
          "filesize": 7005,
          "filestream": null
        },
        {
          "id": 10,
          "type": 1,
          "userId": 4,
          "serverfile": "/meeting/attach\\2016-08\\2016082280245243619356\\2016082280245243613919",
          "fileext": "application/octet-stream",
          "filename": "good.txt",
          "createdate": 1471243524360,
          "filesize": 8,
          "filestream": null
        }
      ],
      "meetingCheckerUser": null,
      "meetingServer": [
        {
          "name": "aa",
          "id": 4,
          "status": 1
        },
        {
          "name": "bb",
          "id": 5,
          "status": 1
        }
      ],
      "userBySummaryuser": "user2",
      "deptByTransactdept": "测试公司",
      "aduitvalue": "不需要审核",
      "name": "2016-8-15 user2 文件上传",
      "id": 3091,
      "starthour": "15",
      "meetingstatu": "未关闭",
      "startDay": "2016-08-15 15:00:00",
      "endDay": "2016-08-15 16:00:00",
      "meetingtype": "公司会议",
      "atCount": "",
      "applyUser": "user2",
      "endhour": "16",
      "sendstatu": "已通知",
      "emceeUser": "",
      "rooms": "会议室1",
      "hasAttachs": true,
      "hasSummarys": false,
      "hasMeetingUser": true,
      "attendstatu": null,
      "startminute": "00",
      "endminute": "00"
    }
  }
}

```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| it | int | ID |
| content | string |  会议内容 |
| createdate | string|  创建日期 |
| aduitflag | int |  审核状态标识 可选值:0 1 2 3 9 |
|aduitvalue|string|审核状态值 可选值:0待审核 1审核通过 2审核不通过 3不需要审核 9暂存|
|isPubMeeting |int |是否公开 可选值:1公开 0不公开  |
|meetingstatu | string|  会议状态 可选值:已关闭 未关闭 |
| sendstatu| string|会议通知发送状态 可选值:未通知 已通知 未确定   |
|summarycontent |string | 纪要内容  |
| summarydate |string | 纪要日期  |
|serverCode | string|会议服务   |
|meetingCheckerUser |string |会议室审核人   |
|userBySummaryuser | string|会议纪要人   |
|deptByTransactdept | string| 会议部门  |
|name | string| 会议名称  |
|applyUser | string| 会议申请人  |
| emceeUser | string| 会议主持人  |
|meetingtype | string| 会议类型  |
|atCount | string|会议人数   |
|rooms | string|会议室名称   |
|hasAttachs | boolean| 是否有附件  |
|hasSummarys | boolean | 是否有纪要  |
|hasMeetingUser | boolean|是否有参与者   |
|startDay | string|开始时间   |
|endDay | string|结束时间   |
| meetingUsers | [] |会议参与人   |
| meetingSummarys| []|  会议纪要列表 |
|meetingAttachs | []|会议附件   |

meetingUsers

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int |  ID |
| reason|string | 不参加的原因  |
| user|string |  参与人姓名 |
| responsionstatu|string |  可选值:确定参与/拒绝参与/未确定 |
|responsionsdate | string|填写日期   |


meetingSummarys

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int |  ID |
| status| string| 可选值:待审核/已审核  |
| auditUser| string|审核人   |
|recorder| string 	 |会议纪要人   |
| meetingSummary| string|纪要内容   |
| auditFlag|string |是否需要审核  可选值:不需要审核 需要审核   |
| recordTime|string |记录时间   |
| isShowForRecorder| int| 是否反馈给纪要人 0:不需要  |

meetingAttachs

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int |  ID |
| type|int| 类型 默认为1  |
| userId|int| 创建人id  |
| createdate| date|创建日期   |
| serverfile| string|存在于服务器上的文件路径 |
| filename|string |文件名   |
| fileext|string | 媒体类型 例如:application/octet-stream |
| filesize| int|  文件字节数|
| filestream| byte[]|  文件字节|

meetingServer

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int | 会议服务 ID |
| name|string| 会议服务名称  |
| status|int| 会议服务状态 1正常 0已删除  |

##### /meetings/user_aduit_meetings 待审核会议查询
url : http://oa.com/mobile/meetings/user_aduit_meetings

支持格式

HTTP请求方式
GET

是否需要登录
是

请求参数

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|aduitStatus|string|waitaudit:待审核 pass:审核通过 reject:审核不通过|必填|
|ec_p|int|分页-当前页码|选填|
|ec_crd|int|分页-每页记录数|选填|
返回结果
JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "count": 1,
    "meetingVOs": [
      {
        "name": "2016-7-11 会议1",
        "startDay": "16-7-11 13:00",
        "endDay": "16-7-11 14:00",
        "applyUser": "user2",
        "rooms": "会议室1",
        "hasAttachs": false,
        "hasSummarys": false,
        "atCount":"3",
        "sendstatu":"已发送",
        "attendstatu":"参加",
        "meetingtype":"部门会议"
        "hasMeetingUser": true,
        "meetingstatu":"未关闭"
      }
    ]
  }
}


```

参数说明
详见 待参加会议列表 api

##### /meetings/public_meetings 历史会议查询,能够搜索已经审核通过或不需要审核的会议概要
URL:http://oa.com/mobile/meetings/public_meetings

支持分页:
是

支持格式:JSON

HTTP请求方式:GET

是否需要登录:是

请求参数:

|名称|类型|说明|必填|
|:-----:|:-----|:-----|:-----|
|search_name_s_like|string|会议名称|选填|
|search_startday_t_eq|string|举办日期|选填|
|search_meetingUsersXXresponsionstatu_s_eq|string|是否已参加 1:参加 0:不参加 9:没答复|选填|
|ec_crd|int|分页-每页记录数|选填|
|ec_p|int|分页-当前页码|选填|
|ec_crd|int|分页-每页记录数|选填|



返回结果<br/>
JSON示例<br/>

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "count": 1,
    "meetingVOs": [
      {
        "name": "2016-7-11 会议1",
        "startDay": "16-7-11 13:00",
        "endDay": "16-7-11 14:00",
        "applyUser": "user2",
        "rooms": "会议室1",
        "hasAttachs": false,
        "hasSummarys": false,
        "atCount":"3",
        "meetingtype":"部门会议"
        "hasMeetingUser": true,
        "meetingstatu":"未关闭"
      }
    ]
  }
}


```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| name | string| 会议名称 |
| startDay | string | 起始日期   |
| endDay | string | 结束日期  |
| rooms | string | 会议室  |
| applyUser | string | 会议发起人 |
| atCount | string | 会议人数 |
| meetingstatu | string | 会议状态 可选值:已关闭 未关闭 |
| meetingtype | string | 会议类型 可选值:部门会议/销售会议 |
| hasAttachs | boolean | 是否有附件  |
| hasSummarys | boolean | 是否有描述 |
| hasMeetingUser | boolean | 是否有参与人  |

##### /meetings/user_apply_meetings 我发起的会议查询
URL:http://oa.com/mobile/meetings/user_apply_meetings

支持分页:
是

支持格式:JSON

HTTP请求方式:GET

是否需要登录:是

请求参数:

|名称|类型|说明|必填|
|:-----:|:-----|:-----|:-----|
|search_name_s_like|string|会议名称|选填|
|search_startday_t_eq|string|举办日期|选填|
|ec_crd|int|分页-每页记录数|选填|
|ec_p|int|分页-当前页码|选填|


返回结果<br/>
JSON示例<br/>

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "count": 1,
    "meetingVOs": [
      {
        "name": "2016-7-11 会议1",
        "startDay": "16-7-11 13:00",
        "endDay": "16-7-11 14:00",
        "applyUser": "user2",
        "rooms": "会议室1",
        "hasAttachs": false,
        "hasSummarys": false,
        "atCount":"3",
        "meetingtype":"部门会议"
        "hasMeetingUser": true,
        "meetingstatu":"未关闭"
      }
    ]
  }
}


```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| name | string| 会议名称 |
| startDay | string | 起始日期   |
| endDay | string | 结束日期  |
| rooms | string | 会议室  |
| applyUser | string | 会议发起人 |
| atCount | string | 会议人数 |
| meetingstatu | string | 会议状态 可选值:已关闭 未关闭 |
| meetingtype | string | 会议类型 可选值:部门会议/销售会议 |
| hasAttachs | boolean | 是否有附件  |
| hasSummarys | boolean | 是否有描述 |
| hasMeetingUser | boolean | 是否有参与人  |

<a href="#top">回顶部</a>
##### /meetings/user_history_meeting_detail 获取历史会议详细信息 (与会议详情相同)
URL:http://oa.com/mobile/meetings/user_history_meeting_detail

支持格式:
JSON

HTTP请求方式:GET

是否需要登录:是

请求参数:

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|meetingId|int|会议id|必填|

返回结果

JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "",
    "code": 0
  },
  "rspBody": {
    "meeting": {
      "content": "111111",
      "createdate": "2016-08-15 12:19:40",
      "aduitflag": 3,
      "meetingUsers": [
        {
          "id": 4229,
          "reason": null,
          "user": "超级用户",
          "responsionstatu": "未确定",
          "responsionsdate": ""
        },
        {
          "id": 4230,
          "reason": null,
          "user": "user1",
          "responsionstatu": "未确定",
          "responsionsdate": ""
        },
        {
          "id": 4231,
          "reason": null,
          "user": "user2",
          "responsionstatu": "确定参与",
          "responsionsdate": "2016-08-15 14:45:24"
        },
        {
          "id": 4232,
          "reason": null,
          "user": "用户3",
          "responsionstatu": "未确定",
          "responsionsdate": ""
        }
      ],
      "meetingSummarys": [],
      "isPubMeeting": 1,
      "serverCode": "5!4!",
      "noticetype": null,
      "summarycontent": null,
      "summarydate": "",
      "meetingAttachs": [
        {
          "id": 9,
          "type": 1,
          "userId": 4,
          "serverfile": "/meeting/attach\\2016-08\\2016082280245243418896\\2016082280245243605156",
          "fileext": "application/octet-stream",
          "filename": "Eula.txt",
          "createdate": 1471243524340,
          "filesize": 7005,
          "filestream": null
        },
        {
          "id": 10,
          "type": 1,
          "userId": 4,
          "serverfile": "/meeting/attach\\2016-08\\2016082280245243619356\\2016082280245243613919",
          "fileext": "application/octet-stream",
          "filename": "good.txt",
          "createdate": 1471243524360,
          "filesize": 8,
          "filestream": null
        }
      ],
      "meetingCheckerUser": null,
      "meetingServer": [
        {
          "name": "aa",
          "id": 4,
          "status": 1
        },
        {
          "name": "bb",
          "id": 5,
          "status": 1
        }
      ],
      "userBySummaryuser": "user2",
      "deptByTransactdept": "测试公司",
      "aduitvalue": "不需要审核",
      "name": "2016-8-15 user2 文件上传",
      "id": 3091,
      "starthour": "15",
      "meetingstatu": "未关闭",
      "startDay": "2016-08-15 15:00:00",
      "endDay": "2016-08-15 16:00:00",
      "meetingtype": "公司会议",
      "atCount": "",
      "applyUser": "user2",
      "endhour": "16",
      "sendstatu": "已通知",
      "emceeUser": "",
      "rooms": "会议室1",
      "hasAttachs": true,
      "hasSummarys": false,
      "hasMeetingUser": true,
      "attendstatu": null,
      "startminute": "00",
      "endminute": "00"
    }
  }
}



```

字段说明<br/>


| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| it | int | ID |
| content | string |  会议内容 |
| createdate | date|  创建日期 |
| aduitflag | int |  审核状态标识 可选值:0 1 2 3 9 |
|aduitvalue|string|审核状态值 可选值:0待审核 1审核通过 2审核不通过 3不需要审核 9暂存|
|isPubMeeting |int |是否公开 可选值:1公开 0不公开  |
|userByEmceeuserId| string|主持人   |
|meetingstatu | string|  会议状态 可选值:已关闭 未关闭 |
| sendstatu| string|会议通知发送状态 可选值:未通知 已通知 未确定   |
|summarycontent |string | 纪要内容  |
|meetingAttachs | []|会议附件   |
|serverCode | string|会议服务   |
|meetingCheckerUser |string |会议室审核人   |
|userBySummaryuser | string|会议纪要人   |
|deptByTransactdept | string| 会议部门  |
|name | string| 会议名称  |
|applyUser | string| 会议申请人  |
|meetingtype | string| 会议类型  |
|atCount | string|会议人数   |
|rooms | string|会议室名称   |
|hasAttachs | boolean| 是否有附件  |
|hasSummarys | boolean | 是否有纪要  |
|hasMeetingUser | boolean|是否有参与者   |
|startDay | date|开始时间   |
|endDay | date|结束时间   |
| meetingUsers | [] |会议参与人   |
| meetingSummarys| []|  会议纪要列表 |
| meetingServer| []|  会议服务列表 |

meetingUsers

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id | int |  ID |
| reason|string | 不参加的原因  |
| user|string |  参与人姓名 |
| responsionstatu|string |  可选值:确定参与/拒绝参与/未确定 |
|responsionsdate | date|填写日期   |


meetingSummarys

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int |  ID |
| status| string| 可选值:待审核/已审核  |
| auditUser| string|审核人   |
|recorder| string 	 |会议纪要人   |
| meetingSummary| string|纪要内容   |
| auditFlag|string |是否需要审核  可选值:不需要审核 需要审核   |
| recordTime|date |记录时间   |
| isShowForRecorder| int| 是否反馈给纪要人 0:不需要  |

meetingAttachs

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int |  ID |
| type|int| 类型 默认为1  |
| userId|int| 创建人id  |
| createdate| date|创建日期   |
| serverfile| string|存在于服务器上的文件路径 |
| filename|string |文件名   |
| fileext|string | 媒体类型 例如:application/octet-stream |
| filesize| int|  文件字节数|
| filestream| byte[]|  文件字节|

meetingServer

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int | 会议服务 ID |
| name|string| 会议服务名称  |
| status|int| 会议服务状态 1正常 0已删除  |

##### /meetings/attachment_download 获取会议详细信息的附件下载

URL:http://oa.com/mobile/meetings/attachment_download

支持格式:
application/json

HTTP请求方式:GET

是否需要登录:是

请求参数:

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|fid|int|attachid|必填|

返回结果

JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
   
  }
}


```

字段说明<br/>

meetingAttachs

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int |  ID |
| type|int| 类型 默认为1  |
| userId|int| 创建人id  |
| createdate| date|创建日期   |
| serverfile| string|存在于服务器上的文件路径 |
| filename|string |文件名   |
| fileext|string | 媒体类型 例如:application/octet-stream |
| filesize| int|  文件字节数|
| filestream| byte[]|  文件字节|

<a href="#top">回顶部</a>
###<a name="meetings_write">写入接口</a>
##### /meetings/wait_meeting_responsion 待参加会议处理

url : http://oa.com/mobile/meetings/wait_meeting_responsion
支持格式
JSON

HTTP请求方式
POST

是否需要登录
是

请求参数

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|meetingId|string|会议  id|必填|
|responsionstatu|string|1确定参加 0无法参加|必填|
|reason|string|理由|不参加时必填|

返回结果
JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "确定参加 ！",
    "code": 0
  },
  "rspBody": {
   
  }
}


```

故障说明

|应答状态|故障代码|消息说明|
|:---:|:---|:---|
|1|1|无法参与会议需要填写原因|
|1|0|确定参加|
|1|0|无法参加|
|1|1|会议被删除|



##### /meetings/user_meeting_room_application 发起会议-基础数据

url : http://oa.com/mobile/meetings/user_meeting_room_application

支持格式
JSON

HTTP请求方式
GET

是否需要登录
是

请求参数
无


返回结果
JSON示例

```
{
  "rspBody": {
    "meetingCategory": [
      "部门会议",
      "高层会议",
      "公司会议",
      "基层会议",
      "例会",
      "全员大会",
      "业务员会议",
      "中层干部会议",
      "专项会议",
      "其它"
    ],
    "roomSer": {
      "1002": [
        {
          "name": "bb",
          "id": 5,
          "status": 1
        },
        {
          "name": "vvv",
          "id": 6,
          "status": 1
        },
        {
          "name": "vvvv",
          "id": 7,
          "status": 1
        },
        {
          "name": "aa",
          "id": 4,
          "status": 1
        }
      ],
      "1003": [
        {
          "name": "aa",
          "id": 4,
          "status": 1
        }
      ],
      "1004": [
        {
          "name": "aa",
          "id": 4,
          "status": 1
        }
      ],
      "1005": [
        {
          "name": "aa",
          "id": 4,
          "status": 1
        }
      ]
    },
    "nowDate": 1470293010261,
    "meetingRooms": [
      {
        "name": "会议室1",
        "id": 1002,
        "image": null,
        "deptId": 2,
        "aduitUserIds": "超级用户",
        "isAduit": 0,
        "remark": ""
      },
      {
        "name": "会议室2",
        "id": 1003,
        "image": null,
        "deptId": 2,
        "aduitUserIds": "超级用户",
        "isAduit": 1,
        "remark": ""
      },
      {
        "name": "会议室3",
        "id": 1004,
        "image": null,
        "deptId": 2,
        "aduitUserIds": "超级用户",
        "isAduit": 1,
        "remark": ""
      },
      {
        "name": "会议室4",
        "id": 1005,
        "image": null,
        "deptId": 2,
        "aduitUserIds": "超级用户",
        "isAduit": 1,
        "remark": ""
      }
    ]
  },
  "rspHeader": {
    "msg": "",
    "status": 1,
    "code": 0
  }
}

```
参数说明

#####会议室
meetingRooms

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| id |int |  会议室编号ID |
| name| string| 会议室名称  |
| deptId| string|会议室所属部门编号  |
|image| string 	 |  |
| isAduit| string|是否需要审核 1:需要 0:不需要 |
| aduitUserIds |string |审核人ID |

#####会议类型
meetingCategory

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| meetingCategory |string[] |  会议类型 |


#####会议室服务(一个房间可以有多个服务)
roomSer 字典类型 

key=meetingroomId 会议室编号value=[meetingServer,meetingServer]

meetingServer

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int | meetingserver ID |
| name| string| 服务名称  |
| status| string|是否可用 1:可用 0:不可用  |
<a href="#top">回顶部</a>

##### /meetings/user_meeting_save 发起会议-保存

url : http://oa.com/mobile/meetings/user_meeting_save

支持格式
JSON

HTTP请求方式
POST

是否需要登录
是

请求参数

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|name|string|会议名称|必填|
|meetingtype|string|会议类型|选填|
|startDay|string|起始时间 例:2016-07-21 11:06|必填|
|endDay|string|结束时间 例:2016-07-21 11:06|必填|
|atCount|string|会议人数|选填|
|roomid|string|会议室ID|必填|
|attendsUserIds|string|参与人员id数组 例:1,2,3|必填|
|userByEmceeuserId|string|主持人id|选填|
|userBySummaryuserId|string|纪要人id|必填|
|content|string|会议内容|选填|
|isSMS|string|是否短信发送通知 0不发送 1发送|选填|
|servercode|string|会议服务id字符串数组 例如:"4,5"|选填|

提交媒体类型

Content－type：application/json
Accept:application/json

JSON示例

```
{"name":"信息","meetingtype":"部门会议","startDay":"2016-07-20 18:00","endDay":"2016-07-20 19:00","atCount":"3","roomid":"1","attendsUserIds":"3,4,5","userByEmceeuserId":"3","userBySummaryuserId":"2","content":"记得开"}

```

返回结果
JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "通知参与者参加会议成功",
    "code": 0
  },
  "rspBody": {
   
  }
}


```
参数说明
该API只能提交会议申请,不能暂存.<br/>
正常返回结果为:通知参与者参加会议/申请会议室等待审核.

故障说明

|应答状态|故障代码|消息说明|
|:---:|:---|:---|
|1|1|会议室已经存在预约|
|1|0|通知参与者参加会议|
|1|0|申请会议室等待审核|



##### /meetings/aduit_meeting_responsion 会议审核保存
url : http://oa.com/mobile/meetings/aduit_meeting_responsion

支持格式


HTTP请求方式
POST

是否需要登录
是

请求参数

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|meetingId|string|会议  id|必填|
|memo|string|审核结果备注|选填|
|auditStatus|string|pass:审核通过  reject:审核不通过|必填|

返回结果
JSON示例

```
{
  "rspHeader": {
    "status": 1,
    "msg": "审核同意",
    "code": 0
  },
  "rspBody": ""
}

```
参数说明


## <a NAME="bulletins">通知公告</a>
### <a name="bulletins_read">读取接口</a>
#####/bulletins/getBulletinsList?key_title=xxx 获取通知公告信息列表
#####url:http://oa.com/mobile/bulletins/getBulletinsList?key_title=xxx

支持格式<br/>
JSON<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>

|名称|类型|说明|是否必填|
|:---:|:---|:---|:----|
|s|string|分页起始页|必填|
|c|string|每页记录数|必填|
|key_title|string|通知公告标题|选填|

http header:token

返回结果 json示例

```
{
  "rspBody": {
    "count": 224,
    "bulletinsVOs": [
      {
        "id": 395,
        "read": false,
        "publisher": "办公室公告员",
        "title": "关于关注“健康江门”微信公众号的函",
        "startDate": "2017-01-09 15:50",
        "readCount": 35,
        "attachCount": 1
      }
    ]
  },
  "rspHeader": {
    "msg": "获取公告信息成功！",
    "status": 1,
    "code": 0
  }
}

```

字段说明
bulletinsVOs

| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
|id |int | 通知公告编号 ID |
| read| boolean| 是否已读 true:已读 false:未读  |
| publisher| string|公告发布者  |
| title| string|标题  |
| startDate| string|发布时间 |
| readCount| int|公告阅读数 |
| attachCount| int|附件数量  |

## <a NAME="message">未读消息</a>
### <a name="message_read">读取接口</a>
#####message/getCount 获取未读的消息数
#####url:http://oa.com/mobile/message/getCount

支持格式<br/>
JSON<br/>
HTTP请求方式<br/>
GET<br/>
是否需要登录<br/>
是<br/>
请求参数<br/>
http header:token<br/>
返回结果 json示例
```
{
  "rspHeader": {
    "status": 1,
    "msg": "获取消息列表操作成功 ！",
    "code": 0
  },
  "rspBody": {
    "messageCount": 0,
    "workflowCount": 86,
    "docexCount": 0,
    "bulletinCount": 0,
    "workflowCount2": 3,
    "waitmeetingcount": 0
  }
}
```
字段说明<br/>
| 返回值字段 | 字段类型 | 字段说明 |
|:-------------:|:-------------|:---------|
| messageCount |int | 消息未读数 |
| workflowCount | int | 公文处理未读数  |
| workflowCount2 | int |事项处理未读数	  |
| docexCount |int | 文件传阅未读数 |
| bulletinCount | int | 通知公告未读数 |
| waitmeetingcount | int | 待参加会议未读数 |
<a href="#top">回顶部</a>




<a href="#top">回顶部</a>