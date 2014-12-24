<#-- @ftlvariable name=""
type="ca.csf.rdore.pinmarks.views.EditBookmarkView" -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Pinmarks - Edit Bookmark</title>
<link rel="stylesheet" href="/css/bijou.css">
<style>
	#editBookmark {
		padding: 20px;
	}
	#editBookmark label {
		width: 250px;
	}
	#editBookmark label.error, #editBookmark input.submit {
		margin-left: 26px;
		color: red;
	}
</style>
</head>
<body onload="init();">
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
	<script	src="//cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/additional-methods.min.js"></script>
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
		
		function closeWindowAndRefreshParent() {
			opener.location.reload();
			window.close();
		}
	</script>
	<script>
	
	$().ready(function() {
		
		// validate signup form on keyup and submit
		$("#editBookmark")
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
									url : "<#if bookmark??>/bookmark/${bookmark.slug}/edit</#if>",
									data : $('#editBookmark').serialize(),
									success : function(data) {
										closeWindowAndRefreshParent();
									},
								    error: function(XMLHttpRequest, textStatus, errorThrown) { 
								        alert("Error: " + errorThrown); 
								    }   
								});

							}
					});		
	});
	</script>

	<form id="editBookmark">
			<p>
				<label for="url">URL (Required)</label> <br />
				<input type="text" autocorrect="off" id="url" name="url" size="70" value="<#if bookmark.url??>${bookmark.url}</#if>" required /> <br />
			</p>
			<p>
				<label for="title">Title (Required, minimum length: 5)</label><br /> 
				<input type="text"	autocomplete="off" autocapitalize="off" name="title" size="70"	value="<#if bookmark.title??>${bookmark.title}</#if>" minlength="5" required /> <br />
			</p>
			<p>
				<label for="description">Description (optional)</label> <br />
				<textarea id="descriptionTextarea" name="description" autocomplete="off" autocapitalize="off" cols=56 rows=4 value=""><#if bookmark.description??>${bookmark.description}</#if></textarea>
			</p>
			<p>
				<label for="tags">Tags (optional)</label> <br />
				<input name="tags" autocomplete="off" autocorrect="off" autocapitalize="off" id="tags" size="70" value="<#if (bookmark.tags)??><#list bookmark.tags as tag>${tag}<#if tag_has_next>, </#if></#list></#if>" />
			</p>
			<br />
			<p>
				<input type="submit" value="Update Bookmark" class="button success small" />
			</p>
	</form>

</body>

</html>
