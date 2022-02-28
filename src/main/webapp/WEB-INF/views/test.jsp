<%@ page contentType="text/html;" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>프로시저 호출 테스트</title>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    </head>
    <body>
        <div style="width:100%; height:100%;">
            <form onsubmit="return false" name="apprForm">
                <input type="number" id="apprId" name="apprId" value="" placeholder="감정서 ID"/>
                <button type="button" onclick="javascript:fnGetProcData();">실행</button>
            </form>
            <div id="result"></div>
        </div>
        <script type="text/javascript">


            window.onload = function(e){

                document.getElementById("apprId").addEventListener("keydown", function(e){
                   if(e.key == "Enter"){
                       fnGetProcData();
                   }
                });

            }

            function fnOnEnterKey(e, callback){
                if(e.key == "Enter") {
                    callback();
                }
            }

            function fnGetProcData(){

                const result = document.getElementById("result");

                fetch("/callAppraisal", {
                    method : "POST",
                    headers : {
                        "Content-Type" : "application/json"
                    },
                    body : JSON.stringify({
                        apprId : document.forms[0].apprId.value
                    })
                })
                .then((res) => {
                    result.innerHTML = JSON.stringify("then");
                    console.log(res);
                })
                .catch((err) => {
                    result.innerHTML = JSON.stringify("catch");
                    console.log(err);
                });
            }
        </script>
    </body>
</html>