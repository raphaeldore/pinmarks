<html>
<body onload="window.open('', '_self', '');">
<a href="javascript:window.close();" onclick="refreshParent()">
Close Me!</a>
<script>
    function refreshParent() {
    	window.opener.location.reload(false);
    }
</script>
</body>
</html>