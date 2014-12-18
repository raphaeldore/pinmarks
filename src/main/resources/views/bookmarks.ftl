<#-- @ftlvariable name=""
type="ca.csf.rdore.pinmarks.views.BookmarksView" --> <#import
"masterTemplate.ftl" as layout> <@layout.layout title="Bookmark"> <#if
bookmarks?has_content> <#-- If the bookmark list is not null or empty,
then iterate thought it--> <#list bookmarks as item>
<div class="bookmark" id="${item.slug}">
	<h4 class="bookmarkTitle">${item.title}</h4>
	<a class="bookmarkURL" href="${item.url}" target="_Blank">${item.url}</a><br />
	<p class="bookmarkDescription">${item.description}</p>

	<#if (item.tags)??>
	<ul class="bookmarkTags">
		<#-- Iterate through the bookmark tags --> <#list item.tags as tag>
		<#if tag != ''>
		<li><a href="/?search=${tag}&amp;searchBy=tag">${tag}<#if
				tag_has_next>&nbsp;</#if></a></li> </#if> </#list>
	</ul>
	</#if> <span>Added ${item.dateAdded}</span>
	<div class="editBookmark">
		<a href="/bookmark/${item.slug}/edit" target="EditBookmark"
			onclick="PopupCenter('/bookmark/${item.slug}/edit','EditBookmark','700','350'); return false;"
			title="Edit bookmark">Edit</a>&nbsp; <a href="#"
			onclick="return deleteBookmarkBySlug('${item.slug}')">Delete</a>
	</div>
</div>
</#list> </#if> </@layout.layout>
