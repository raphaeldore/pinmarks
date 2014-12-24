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
				</select>
			</div>
		</div>
	</div>
</form>

<div class='container'>
	<div class="row">
		<div class='span eight'>
			<h1 style="font-weight: 600;">Home</h1>
			<br />
			<#if bookmarks?has_content>
				<h2>Search Results</h2>
			</#if>
		</div>
		<div class='span four'>
			<h1>Tags</h1>
		</div>
	</div>
	<div class="row">
		<div class="span eight" id="bookmarkSearchResults">
			<#if bookmarks?has_content>
			<#-- If the bookmark list is not null or empty, then iterate thought it-->
			<#list bookmarks as item>
			<div class="bookmark" id="${item.slug}">
				<h4 class="bookmarkTitle">${item.title}</h4>
				<a class="bookmarkURL" href="${item.url}" target="_Blank">${item.url}</a><br />
				<p class="bookmarkDescription">${item.description}</p>

				<#if (item.tags)??>
				<ul class="bookmarkTags">
				<#-- Iterate through the bookmark tags -->
					<#list item.tags as tag> 
						<#if tag != ''>
							<li><a href="/?search=${tag}&amp;searchBy=tag">${tag}<#if tag_has_next>&nbsp;</#if></a></li>
						</#if> 
					</#list>
				</ul>
				</#if> <span>Added ${item.dateAdded}</span>
				<div class="editBookmark">
				<a href="/bookmark/${item.slug}/edit" target="EditBookmark"
					onclick="PopupCenter('/bookmark/${item.slug}/edit','EditBookmark','700','350'); return false;"
					title="Edit bookmark">Edit</a>&nbsp; <a href="javascript:void(0);"
						onclick="return deleteBookmarkBySlug('${item.slug}')">Delete</a>
				</div>
			</div>
			</#list> <#else>
			<h4>Nothing to show here...</h4>
			</#if>

		</div>
		<div class="span four">
			<div id="tagCloud">
				<#list tagStats as tagStat>
					<#assign keys = tagStat?keys>
						<#list keys as key>
							<a href="/?search=${key}&amp;searchBy=tag" rel="${tagStat[key]}">${key}</a>
						</#list>
				</#list>
			</div>
		</div>
	</div>
</div>

<script>
	function insertSearchTermParamInTextBox() {
		var input = $('#searchBox');
		var text = getUrlParameter("search");
		input.val(text);
	}
</script>

<script>
	// User can submit the search box by pressing enter
	$('#textboxId').keydown(function(event) {
		var keypressed = event.keyCode || event.which;
		if (keypressed == 13) {
			$(this).closest('form').submit();
		}
	});
</script>

<script>
	$.fn.tagcloud.defaults = {
		size : {
			start : 20,
			end : 42,
			unit : 'pt'
		},
		color : {
			start : '#cde',
			end : '#f52'
		}
	};

	$(function() {
		$('#tagCloud a').tagcloud();
	});
</script>

</@layout.layout>
