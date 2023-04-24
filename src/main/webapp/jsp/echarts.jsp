<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>




<script>
    // //初始化
    // let goeasy = GoEasy.getInstance({
    //     host:"hangzhou.goeasy.io",  //若是新加坡区域：singapore.goeasy.io
    //     appkey:"BC-af84aa957b884013b3dfa8e2b0217c39",
    //     modules:['pubsub']//根据需要，传入‘pubsub’或'im’，或数组方式同时传入
    // });
    // //建立连接
    // goeasy.connect({
    //     onSuccess: function () {  //连接成功
    //         alert("GoEasy connect successfully.") //连接成功
    //     },
    //     onFailed: function (error) { //连接失败
    //         alert("Failed to connect GoEasy, code:"+error.code+ ",error:"+error.content);
    //     },
    //     onProgress:function(attempts) { //连接或自动重连中
    //         alert("GoEasy is connecting", attempts);
    //     }
    // });

    //接收订阅消息
    // var pubsub = goeasy.pubsub;
    // pubsub.subscribe({
    //     channel: "hqxm",//替换为您自己的channel
    //     onMessage: function (message) {
    //         //手动将 字符串类型  转为json类型
    //         var data = JSON.parse(message.content);
    //         //收到消息
    //         alert("Channel:" + message.channel + " content:" + message.content);
    //     },
    //     onSuccess: function () {
    //         alert("Channel订阅成功。");
    //     },
    //     onFailed: function (error) {
    //         alert("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
    //     }
    // });


    </script>


<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="col-lg-10">
    <div id="main" style="width: 600px;height:400px;">
    </div>
</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '男女比列'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1天","7天","30天","1年"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // Ajax异步数据回显
    $.get("${pageContext.request.contextPath}/user/showUsersCount",function (data) {
        console.log(data)
        myChart.setOption({
            series:[
                {
                    name: '男',
                    type: 'bar',
                    data: data.man,
                },{
                    name: '女',
                    type: 'bar',
                    data: data.women,
                }
            ]
        })
    },"json")
</script>