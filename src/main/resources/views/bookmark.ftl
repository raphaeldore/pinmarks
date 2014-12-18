<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.BookmarkView"
-->
<#import "masterTemplate.ftl" as layout>
<@layout.layout title="Bookmark">

<#if (bookmark)??>
			<div class="bookmark" id="${bookmark.slug}">
				<h4 class="bookmarkTitle">${bookmark.title}</h4>
				<a class="bookmarkURL" href="${bookmark.url}" target="_Blank">${bookmark.url}</a><br />
				<p class="bookmarkDescription">${bookmark.description}</p>

				<#if (bookmark.tags)??>
				<ul class="bookmarkTags">
				<#-- Iterate through the bookmark tags -->
					<#list bookmark.tags as tag> 
					<#if tag != ''>
					<li><a href="/?search=${tag}&searchBy=tag">${tag}&nbsp;</a></li>
					</#if> 
					</#list>
				</ul>
				</#if> <span>Added ${bookmark.dateAdded}</span>
				<div class="editBookmark">
					<a href="#">Edit</a>&nbsp; <a href="#"
						onclick="return deleteBookmarkBySlug('${bookmark.slug}')">Delete</a>
				</div>
			</div>
<#else>
<a href="/"><img src="/img/nothing_here.png" style="display: block; margin-left: auto; margin-right: auto;"></img></a>
</#if>

</@layout.layout>