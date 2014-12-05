<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.BookmarksView"
-->

<#list bookmarks as bookmark>
<#if (bookmark.title)??>
   Hi ${bookmark.title}, How are you?
</#if>
</#list>