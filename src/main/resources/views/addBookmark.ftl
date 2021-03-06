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
	<script src="/js/jquery-1.9.1.min.js"></script>
	<script src="http://cdn.jsdelivr.net/jquery.validation/1.13.1/jquery.validate.min.js"></script>
	<script	src="/js/jquery.validate.additional-methods.min.js"></script>
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
		
		function getUrlParameter(sParam) {
			var sPageURL = window.location.search.substring(1);
			var sURLVariables = sPageURL.split('&');
			for (var i = 0; i < sURLVariables.length; i++) {
				var sParameterName = sURLVariables[i].split('=');
				if (sParameterName[0] == sParam) {
					return sParameterName[1];
				}
			}
		}
		
		function closeWindowAndRefreshParent() {
			opener.location.reload();
			window.close();
		}
	</script>
	<script>
	
	$().ready(function() {
		
		$(function () {
			if(getUrlParameter('url') != null)
			{
				$('#url').val(decodeURIComponent(getUrlParameter('url')));
			}
			if(getUrlParameter('title') != null)
			{
				$('#title').val(decodeURIComponent(getUrlParameter('title')));
			}
			
			if(getUrlParameter('description') != null)
			{
				$('#description').val(decodeURIComponent(getUrlParameter('description')));
			}
			
			});
		
		// validate signup form on keyup and submit
		$("#addBookmark")
			.validate(
					{
						rules : {
							url : {
								required: true,
								url: true
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
									url : "/bookmark/add",
									data : $('#addBookmark').serialize(),
									success : function(data) {
										//alert("hello");
										if($('#title').val(decodeURIComponent(getUrlParameter('title'))) == null)
										{
											closeWindowAndRefreshParent();
										} else {
											window.close();
										}
									},
								    error: function(XMLHttpRequest, textStatus, errorThrown) { 
								        alert("Error: " + errorThrown); 
								    }   
								});

							}
					});
	});
	</script>
	
	<form id="addBookmark">
			<p>
				<label for="url">URL (Required)</label> <br />
				<input type="text" autocorrect="off" id="url" name="url" size="70" value="" required /> <br />
			</p>
			<p>
				<label for="title">Title (Required, minimum length: 5)</label><br /> 
				<input type="text"	autocomplete="off" autocapitalize="off" name="title" id="title" size="70"	value="" minlength="5" required /> <br />
			</p>
			<p>
			
				<label for="description">Description (optional)</label> <br />
				<textarea name="description" id="description" autocomplete="off" autocapitalize="off" cols=56 rows=4 /></textarea>
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