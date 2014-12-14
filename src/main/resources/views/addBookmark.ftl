<#-- @ftlvariable name=""
type="ca.csf.rdore.pinmarks.views.AddBookmarkView" -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Pinmarks - Add a Bookmark</title>
<link rel="stylesheet" href="/css/bijou.css">
<style>
	#addBookmark {
		padding: 20px;
	}
	#addBookmark label {
		width: 250px;
	}
	#addBookmark label.error, #addBookmark input.submit {
		margin-left: 26px;
		color: red;
	}
</style>
</head>
<body onload="init();">
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="http://cdn.jsdelivr.net/jquery.validation/1.13.1/jquery.validate.min.js"></script>
	<script language="javascript">
		var min_height = 500;

		function init() {
			if (window.outerHeight < min_height) {
				window.resizeTo(600, min_height);
			}
			var url = document.getElementById("url");
			var focus_me = url.value.length > 0 ? 'tags' : 'url';
			document.getElementById(focus_me).focus();
		}

		function checkEnter(e) { //e is event object passed from function invocation
			if (e.keyCode == 13) {
				submit();
				return false;
			} else {
				return true;
			}
		}

		function cancel() {
			window.close();
		}

		function closeSelf() {
			document.forms[0].submit();
			window.close();
		}
		function reloadParent() {
			if (document.getElementById('url').value != null
					&& document.getElementById('title').value != null) {
				window.opener.location.reload(false);
			}

		}

		function closeWindowAndRefreshParent() {
			opener.location.reload();
			window.close();
		}
	</script>
	<script>
	
	$.validator.addMethod(
	        "regex",
	        function(value, element, regexp) {
	            var re = new RegExp(regexp);
	            return this.optional(element) || re.test(value);
	        },
	        "Please check your input."
	);
		
	$().ready(function() {
		
		// validate signup form on keyup and submit
		$("#addBookmark")
			.validate(
					{
						rules : {
							url : {
								regex: /((([A-Za-z]{3,9}:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)/
							},
							title : {
								required : true,
								minlength : 5
							}
						},
						messages : {
							url : "You must enter a valid url",
							title : {
								required : "Please enter a title",
								minlength : "Your title must consist of at least 5 characters"
							}
						},
						submitHandler : 
							function(form) {
								$.ajax({
									type : "POST",
									url : "/addBookmark",
									data : $('#addBookmark').serialize(),
									success : function(data) {
										//alert("hello");
										closeWindowAndRefreshParent();
									}
								});

							}
					});
	});
	</script>

	<form id="addBookmark">
			<p>
				<label for="cname">URL (Required)</label> <br />
				<input type="text" autocorrect="off" id="url" name="url" size="70" value="" required /> <br />
			</p>
			<p>
				<label for="title">Title (Required, minimum length: 5)</label><br /> 
				<input type="text"	autocomplete="off" autocapitalize="off" name="title" size="70"	value="" minlength="5" required /> <br />
			</p>
			<p>
				<label for="description">Description (optional)</label> <br />
				<textarea name="description" autocomplete="off" autocapitalize="off" cols=56 rows=4 /></textarea>
			</p>
			<p>
				<label for="tags">Tags (optional)</label> <br />
				<input name="tags" autocomplete="off" autocorrect="off" autocapitalize="off" id="tags" size="70" value="" />
			</p>
			<br />
			<p>
				<input type="submit" value="Add Bookmark" class="button success small" />
			</p>
	</form>

</body>

</html>