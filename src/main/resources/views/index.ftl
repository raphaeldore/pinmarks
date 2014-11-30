<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.IndexView"
-->
<#import "masterTemplate.ftl" as layout>
<@layout.layout title="Home">
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
</@layout.layout>