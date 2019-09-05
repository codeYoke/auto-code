<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>自动代码生成</title>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //页面加载事件
            $("#save").click(function () {
                //获取form表单的数据
                //表单验证，只做了基本的空表单验证
                var $inputs =  $("form input[type='text']");
                if($($inputs[0]).val() == ''){
                    alert('不能为空！');
                    return;
                }
                console.log($('form').serialize());//序列化参数
                //异步提交
                $.post($('form').attr('action'),$('form').serialize()+"&type=save",function (data) {
                    if (data == 'write something here!'){
                        alert('操作成功')
                        //转发到其他页面
                        //清除form数据
                        $('form').get(0).reset();
                    }
                });
                //回调处理
            });
        });
    </script>
</head>
<body>
<h3>
    <p>自动代码生成</p>
</h3>
<form action="${servletClassName}" method="post">
    <#compress >
        <#list fields as field>
    <div>
       ${field.remarks}: <input type="text" name="${field.columnName}" id="${field.columnName}">
    </div>
        </#list>
    </#compress>
    <div>
        <input type="button" id="save" value="保存">
    </div>
</form>

</body>
</html>