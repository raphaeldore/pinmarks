<#macro layout title="defaultTitle">
<!DOCTYPE html>
<html>

<head>
<title>Pinmarks - ${title}</title>
<meta charset="utf-8">
<meta name="description" content="Manage your bookmarks!">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/css/bijou.css">
<link rel="stylesheet" href="/css/site.css">
<script src="/js/modernizr-2.6.2.min.js"></script>
<script src="/js/jquery-1.9.1.min.js"></script>
<script src="/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/js/jquery.tagcloud.js"></script>
</head>

<body onload="insertSearchTermParamInTextBox();">
	<div class='navbar fixed'>
		<div class='container'>
			<h4 class='pull-left'>Pinmarks</h4>
			<ul class='pull-right'>
				<li><a href='/'>Home</a></li>
				<li><a href='/bookmarks'>Bookmarks</a></li>
				<li><a href="/bookmark/add" target="AddBookmark"
					onclick="PopupCenter('/bookmark/add','AddBookmark','700','350'); return false;"
					title="Add a bookmark to your collection">Add Bookmark</a></li>
			</ul>
		</div>
	</div>
	<div class="container"><#nested/></div>
	<div class="container">
		<footer>
			&copy; 2014, <a href="http://www.nothingrelevant.org/">Raphaël
				Doré</a>
		</footer>
	</div>
	<script type="text/javascript">
		var windowObjectReference = null; // global variable

		/* Based on:
		 * https://developer.mozilla.org/en-US/docs/Web/API/Window.open#Best_practices
		 * http://stackoverflow.com/questions/4068373/center-a-popup-window-on-screen
		 */
		function PopupCenter(url, title, w, h) {
			// Fixes dual-screen position                         Most browsers      Firefox
			var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft
					: screen.left;
			var dualScreenTop = window.screenTop != undefined ? window.screenTop
					: screen.top;

			width = window.innerWidth ? window.innerWidth
					: document.documentElement.clientWidth ? document.documentElement.clientWidth
							: screen.width;
			height = window.innerHeight ? window.innerHeight
					: document.documentElement.clientHeight ? document.documentElement.clientHeight
							: screen.height;

			var left = ((width / 2) - (w / 2)) + dualScreenLeft;
			var top = ((height / 2) - (h / 2)) + dualScreenTop;

			if (windowObjectReference == null || windowObjectReference.closed)
			/* if the pointer to the window object in memory does not exist
			   or if such pointer exists but the window was closed */

			{
				windowObjectReference = window.open(url, title,
						'resizable, scrollbars, status, width=' + w
								+ ', height=' + h + ', top=' + top + ', left='
								+ left);
				/* then create it. The new window will be created and
				   will be brought on top of any other window. */
			} else {
				windowObjectReference.focus();
			}
		}
	</script>
	<script>
		function deleteBookmarkBySlug(bookmarkSlug) {
			if (window.confirm("Delete this bookmark?")) {
				var formData = "bookmarkSlug=" + bookmarkSlug;
				$.ajax({
					url : "/delete",
					type : "DELETE",
					data : formData,
					success : function(data, textStatus, jqXHR) {
						console.log("Server response: " + textStatus);
						console.log("Removing div...");
						$('#' + bookmarkSlug).hide('slow', function(){ $('#' + bookmarkSlug).remove(); });
						// $('#' + bookmarkSlug).fadeOut(300, function(){ $(this).remove();});
						console.log("..done");
					},
					error : function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
					}
				});
			}
		}
	</script>
</body>
</html>
</#macro>
