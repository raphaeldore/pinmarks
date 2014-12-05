<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.IndexView"
--> <#import "masterTemplate.ftl" as layout> <@layout.layout
title="Home">
<form method="get" onSubmit="return getSearchByType()" id="searchForm">
	<div class="container bookmarksSearch">

		<div class="row">
			<div class='span ten'>
				<button type="submit" class="search-submit-button">
					<i class="fa fa-search"></i>
				</button>
				<div id="searchtext">
					<input type="text" id="searchBox" name="search"
						placeholder="Search">
				</div>
			</div>
			<div class='span two'>
				<select id="searchBy" name="searchBy">
					<option value="tag">..By tag</option>
					<option value="title">..By title</option>
					<option value="description">..By description</option>
					<option value="all">..By All</option>
				</select>
			</div>
		</div>
	</div>
</form>

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
			<#list bookmarks as item>
			<div class="bookmark">
				<h4 class="bookmarkTitle">${item.title}</h4>
				<a class="bookmarkURL" href="#">${item.url}</a><br />
				<p class="bookmarkDescription">${item.description}</p>
				<ul class="bookmarkTags">
					<li><a href="#">Tag 1</a></li>
					<li><a href="#">Tag 2</a></li>
					<li><a href="#">Tag 3</a></li>
					<li><a href="#">Tag 4</a></li>
				</ul>
				<span>Added ${item.dateAdded}</span>
				<div class="editBookmark">
					<a href="#">Edit</a>&nbsp; <a href="#"
						onclick="return deleteBookmark(1)">Delete</a>
				</div>
			</div>
			</#list>

		</div>
		<div class="span four">Tags....</div>
	</div>
</div>
</@layout.layout>
