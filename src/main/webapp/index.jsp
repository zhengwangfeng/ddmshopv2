<html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script>
    function f() {
        1
    }

</script>
<body>
<h2>Hello World!</h2>
<form method="post" action="/shop/tcsbClientController.do?notice">
    <input type="file" name="111" value="upload">
    <input type="text" name="userId" value="8a9ad8035c0c5381015c0c7461bc0020">
    <input type="submit" value="1111">
    <img src="/opt/tcsb/userfiles/_thumbs/Images/shop/2018/03/16/20180316173645DReBbNP3.png">
</form>
<input type="button" id="test" value="2222">
</body>

<script>
    $(function () {
        $("#test").click(function () {
//            alert(2);
//            $.ajax({
//                type: "POST",
//                dataType: "json",
//                data: {
//                    "reason": "124124",
//                    "jsonArray": JSON.stringify([{
//                        "orderItemId": "402880256039e046016039e2e5bf0006",
//                        "count": "2"
//                    }])
//                },
//                url: "http://localhost:8080/PCPayController.do?PCFoodRefund",
//                success: function (message) {
//                    alert(message);
//                }, error: function (e) {
//                    //alert(e);
//                }
//            })
               // , {"orderItemId": "402880256039392a01603954798a001b", "count": "2"}
//            $.ajax({
//                type: "POST",
//                dataType:"json",
//                data:{"orderParentId":"","reason":""},
//                url:"http://localhost:8080/PCPayController.do?PCOrderParentRefund",
//                success: function (message) {
//                    alert("成功");
//                },error:function (e) {
//                    //alert(e);
//                }
//            })

//            $.ajax({
//                type: "POST",
//                dataType:"json",
//                data:{"shopId":"8a9ad8035c0c5381015c0c7461db0023","page":"1","rows":"10","token":"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4YTlhZDgwMzVjMGM1MzgxMDE1YzBjNzQ2MWRiMDAyMyIsInN1YiI6IntcInVpZFwiOjEsXCJ1dHlcIjpcInBjdXNlclwifSIsImlhdCI6MTUxNTU1MTExMSwiZXhwIjoxNTE1NTYxMTkxfQ.sMQFmVJFCn95-MeVuEgNqqL9DoEST3y6-U178snTyTM"},
//                url:"http://localhost:8080/rest/pc/PCMemberUserController.do?PCGetShopAllMemberUser",
//                success: function (message) {
//                    alert("成功");
//                },error:function (e) {
//                    //alert(e);
//                }
//            })

                $.ajax({
                    type: "POST",
                    dataType:"json",
                    data:{"content":"啊啊啊啊"},
                    url:"http://localhost:8080/shop/rest/pc/PCSubOrderController.do?PCCreateSubOrderAndSubOrderItems",
                    success: function (message) {
                        alert("成功");
                    },error:function (e) {
                        //alert(e);
                    }
                })

//for(var i=0;i<10000;i++){
    $.ajax({
        type: "POST",
        dataType:"json",
        data:{
            "shopId":"8a9ad8035c0c5381015c0c7461db0023",
            "businessCode":"0"
        },
        url:"http://localhost:8080/rest/socket/send",
        success: function (message) {
        },error:function (e) {
        }
    })
//}


            //www.diandanme.xyz/shop
//            $.ajax({
//                type: "POST",
//                dataType:"json",
//                data:{},
//                url:"http://www.diandanme.xyz/shop/PCUserController.do?checkuser&userName=zhangyang&password=%2BP19JHmYId4=",
//                success: function (message) {
//                    alert("成功");
//                },error:function (e) {
//                    //alert(e);
//                }
//            })
//            $.ajax({
//                type: "POST",
//                dataType:"json",
//                data:{"reservationId":"8a9ccf87606d9cfb01606d9f1fac0006"},
//                url:"http://localhost:8080/appletMessageController.do?sendPCReservationSuccessMessage",
//                success: function (message) {
//                    alert("成功");
//                },error:function (e) {
//
//                }
//            })

        })

//    })
    })

</script>
</html>
