<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.AddBookmarkView" -->
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Pinmarks - Add a Bookmark</title>
    <link rel="stylesheet" href="resources/css/bijou.css">
</head>
<body onload="init();">
    <script language="javascript">
    var min_height = 550;

    function init() {
        if (window.outerHeight < min_height) {
            window.resizeTo(700, min_height);
        }
        var url = document.getElementById("url");
        var focus_me = url.value.length > 0 ? 'tags' : 'url';
        document.getElementById(focus_me).focus();
        get_suggested_tags();
    }

    function checkEnter(e) { //e is event object passed from function invocation
        if (e.keyCode == 13) {
            submit();
            return false;
        } else {
            return true;
        }
    }

    function submit() {
        document.forms[0].submit();
    }

    function cancel() {
        window.close();
    }
    </script>
    <form method="post" action="/add">
        <input type="hidden" name="next" value="" />
        <input type="hidden" name="form_token" value="aec67dfa772e74c78d50cd186d893b9ab21ed9eb" />
        <table>
            <tr>
                <td>URL</td>
                <td>
                    <input type="text" autocorrect="off" id="url" name="url" size="70" value="http://placeholder.com" />
                </td>
            </tr>
            <tr>
                <td>title</td>
                <td>
                    <input type="text" autocomplete="off" autocapitalize="off" name="title" size="70" value="Pinmarks" />
                </td>
            </tr>
            <tr>
                <td>description</td>
                <td>
                    <textarea name="description" autocomplete="off" autocapitalize="off" cols=60 rows=4></textarea>
                </td>
            </tr>
            <tr>
                <td style="vertical-align:top">tags</td>
                <td>
                    <input name="tags" autocomplete="off" autocorrect="off" autocapitalize="off" id="tags" size="70" value="" />
                </td>
            </tr>

            <tr id="suggestion_row" style="height:30px;visibility:hidden;">
                <td>suggest</td>
                <td id='suggested'></td>
            </tr>

            <tr>
                <td></td>
                <td>
                    <input type="checkbox" name="private" id="private" checked />
                    <label style="display:inline" for="private">private</label>
                    <input type="checkbox" name="toread" id="toread" />
                    <label style="display:inline" for="toread">read later</label>
                    <br>
                </td>
            </tr>

            <tr>
                <td></td>
                <td>
                	<br />
                    <input type="submit" value="add bookmark" class="button success small" />
                </td>
            </tr>
        </table>
</body>

</html>