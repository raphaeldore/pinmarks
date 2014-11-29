<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.IndexView"
-->
<!DOCTYPE html>
<html>

<head>
<title>Pinmarks</title>
<meta charset="utf-8">
<meta name="description"
	content="A beautiful CSS framework in under 2 KiB">
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
					onclick="PopupCenter('/addBookmark','Add Bookmark','700','350'); return false;"
					title="Add a bookmark to your collection">Add Bookmark</a></li>
			</ul>
		</div>
	</div>

	<div class="container bookmarksSearch">
		<div class="row">
			<div class='span ten'>
				<a class="search-submit-button" href="javascript:void(0)"> <i
					class="fa fa-search"></i>
				</a>
				<div id="searchtext">
					<input type="text" id="s" name="s" value="Search">
				</div>
			</div>
			<div class='span two'>
				<select id="searchBy">
					<option value="tag">..By tag</option>
					<option value="title">..By title</option>
					<option value="description">..By description</option>
					<option value="all">..By All</option>
				</select>
			</div>
		</div>
	</div>

	<div class='container'>
		<div class="row">
			<div class='span eight'>
				<h1>Search Results</h1>
			</div>
			<div class='span four'>
				<h1>Tags</h1>
			</div>
		</div>
		<div class="row">
			<div class="span eight" id="bookmarkSearchResults">
				<div id="lightbox">Testing out the lightbox</div>
				<a href="#" id="opener">Click me</a>
				<div class="bookmark">
					<h4 class="bookmarkTitle">Bookmark Title</h4>
					<a class="bookmarkURL" href="#">http://lipsum.com</a> <br />
					<p class="bookmarkDescription">Bookmark description. Lorem
						ipsum Sit adipisicing quis dolore in et in in anim cillum enim
						occaecat eiusmod sit Duis culpa.</p>
					<ul class="bookmarkTags">
						<li><a href="#">Tag 1</a></li>
						<li><a href="#">Tag 2</a></li>
						<li><a href="#">Tag 3</a></li>
						<li><a href="#">Tag 4</a></li>
					</ul>
					<span>Added {Date}</span>
					<div class="editBookmark">
						<a href="#">Edit</a>&nbsp; <a href="#"
							onclick="return deleteBookmark(1)">Delete</a>
					</div>
				</div>
				<div class="bookmark">
					<h4 class="bookmarkTitle">Bookmark Title</h4>
					<a class="bookmarkURL" href="#">http://lipsum.com</a> <br />
					<p class="bookmarkDescription">Bookmark description. Lorem
						ipsum Sit adipisicing quis dolore in et in in anim cillum enim
						occaecat eiusmod sit Duis culpa.</p>
					<ul class="bookmarkTags">
						<li><a href="#">Tag 1</a></li>
						<li><a href="#">Tag 2</a></li>
						<li><a href="#">Tag 3</a></li>
						<li><a href="#">Tag 4</a></li>
					</ul>
					<span>Added {Date}</span>
					<div class="editBookmark">
						<a href="#">Edit</a>&nbsp; <a href="#"
							onclick="return deleteBookmark(1)">Delete</a>
					</div>
				</div>
				<div class="bookmark">
					<h4 class="bookmarkTitle">Bookmark Title</h4>
					<a class="bookmarkURL" href="#">http://lipsum.com</a> <br />
					<p class="bookmarkDescription">Bookmark description. Lorem
						ipsum Sit adipisicing quis dolore in et in in anim cillum enim
						occaecat eiusmod sit Duis culpa.</p>
					<ul class="bookmarkTags">
						<li><a href="#">Tag 1</a></li>
						<li><a href="#">Tag 2</a></li>
						<li><a href="#">Tag 3</a></li>
						<li><a href="#">Tag 4</a></li>
					</ul>
					<span>Added {Date}</span>
					<div class="editBookmark">
						<a href="#">Edit</a>&nbsp; <a href="#"
							onclick="return deleteBookmark(1)">Delete</a>
					</div>
				</div>
				<div class="bookmark">
					<h4 class="bookmarkTitle">Bookmark Title</h4>
					<a class="bookmarkURL" href="#">http://lipsum.com</a> <br />
					<p class="bookmarkDescription">Bookmark description. Lorem
						ipsum Sit adipisicing quis dolore in et in in anim cillum enim
						occaecat eiusmod sit Duis culpa.</p>
					<ul class="bookmarkTags">
						<li><a href="#">Tag 1</a></li>
						<li><a href="#">Tag 2</a></li>
						<li><a href="#">Tag 3</a></li>
						<li><a href="#">Tag 4</a></li>
					</ul>
					<span>Added {Date}</span>
					<div class="editBookmark">
						<a href="#">Edit</a>&nbsp; <a href="#"
							onclick="return deleteBookmark(1)">Delete</a>
					</div>
				</div>

			</div>
			<div class="span four">Tags....</div>
		</div>
	</div>
	<div class="container">
		<footer>
			&copy; 2014, <a href="http://www.nothingrelevant.org/">Raphaël
				Doré</a>
		</footer>
	</div>
	<script type="text/javascript">
		var windowObjectReference = null; // global variable

		function openAddBookmarkPopup() {
			if (windowObjectReference == null || windowObjectReference.closed)
			/* if the pointer to the window object in memory does not exist
			   or if such pointer exists but the window was closed */

			{
				windowObjectReference = window.open("/addBookmark",
						"AddBookmark",
						"resizable,scrollbars,status,width=700,height=350");
				/* then create it. The new window will be created and
				   will be brought on top of any other window. */
			} else {
				windowObjectReference.focus();
				/* else the window reference must exist and the window
				   is not closed; therefore, we can bring it back on top of any other
				   window with the focus() method. There would be no need to re-create
				   the window or to reload the referenced resource. */
			}
			;

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

				if (windowObjectReference == null
						|| windowObjectReference.closed)
				/* if the pointer to the window object in memory does not exist
				   or if such pointer exists but the window was closed */

				{
					windowObjectReference = window.open(url, title,
							'resizable, scrollbars, status, width=' + w
									+ ', height=' + h + ', top=' + top
									+ ', left=' + left);
					/* then create it. The new window will be created and
					   will be brought on top of any other window. */
				} else {
					windowObjectReference.focus();
				}

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