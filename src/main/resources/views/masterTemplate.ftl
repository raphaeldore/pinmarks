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
</head>

<body>
	<div class='navbar fixed'>
		<div class='container'>
			<h4 class='pull-left'>Pinmarks</h4>
			<ul class='pull-right'>
				<li><a href='/'>Home</a></li>
				<li><a href='#'>Bookmarks</a></li>
				<li><a href="/addBookmark" target="AddBookmark"
					onclick="PopupCenter('/addBookmark','AddBookmark','700','350'); return false;"
					title="Add a bookmark to your collection">Add Bookmark</a></li>
			</ul>
		</div>
	</div>
	<div class="container">
		<#nested/>
	</div>
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
		var opener = document.getElementById("opener");

		opener.onclick = function() {

			var lightbox = document.getElementById("lightbox"), dimmer = document
					.createElement("div");

			dimmer.style.width = window.innerWidth + 'px';
			dimmer.style.height = window.innerHeight + 'px';
			dimmer.className = 'dimmer';

			dimmer.onclick = function() {
				document.body.removeChild(this);
				lightbox.style.visibility = 'hidden';
			}

			document.body.appendChild(dimmer);

			lightbox.style.visibility = 'visible';
			lightbox.style.top = window.innerHeight / 2 - 50 + 'px';
			lightbox.style.left = window.innerWidth / 2 - 100 + 'px';
			return false;
		}
	</script>
	<script>
		function deleteBookmark(bookmarkID) {
			if (window.confirm("Delete this bookmark?")) {
				console.log("Bookmark deleted");
			}
		}
	</script>
</body>
</html>
</#macro>