<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.TestView"
-->

<html>
<head>
<title>Pinmarks - Bookmarks</title>
<meta charset="utf-8">
<meta name="description" content="Manage your bookmarks!">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/css/bijou.css">
<link rel="stylesheet" href="/css/site.css">
</head>
<body>
<#list bookmarks as item>${item.title}
<div class="bookmark">
	<h4 class="bookmarkTitle">${item.title}</h4>
	<a class="bookmarkURL" href="#">${item.url}</a><br/>
	<p class="bookmarkDescription">
		${item.description}
	</p>
	<ul class="bookmarkTags">
		<li><a href="#">Tag 1</a></li>
		<li><a href="#">Tag 2</a></li>
		<li><a href="#">Tag 3</a></li>
		<li><a href="#">Tag 4</a></li>
	</ul>
	<span>Added ${item.dateAdded}</span>
	<div class="editBookmark">
		<a href="#">Edit</a>&nbsp; <a href="#" onclick="return deleteBookmark(1)">Delete</a>
	</div>
</div>
</#list>

</body>
</html>