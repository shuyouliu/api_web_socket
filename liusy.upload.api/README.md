RESTFUL API
upload/uploadStream


文件上传功能

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HTML5的标题</title>
</head>
<body>
<p>HTML5的内容！Hello</p>
<form action="http://localhost:8080/liusy.upload.api-0.0.1-SNAPSHOT/upload/uploadStream" 
		enctype="multipart/form-data" method="post" >
        <input name="file" type="file" />
        <input type="submit" value="上传图片" />
</form>
</body>
</html>

