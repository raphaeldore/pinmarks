<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.BookmarksView"
-->

<h1>Hello</h1>

<#list bookmarks as bookmark>
   Hi ${bookmark.title}, How are you?
</#list>

<#if bookmarks ??>
<#list bookmarks as bookmark>
   Hi ${bookmark.title}, How are you?
</#list>
</#if>

