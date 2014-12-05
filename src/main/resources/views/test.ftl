<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.TestView"
-->

<#import "masterTemplate.ftl" as layout>
<@layout.layout title="Bookmark">

<#list bookmarks as item>
<div class="bookmark">
	<h4 class="bookmarkTitle">${item.title}</h4>
	<a class="bookmarkURL" href="#">${item.url}</a><br/>
	<p class="bookmarkDescription">${item.description}</p>
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
</@layout.layout>