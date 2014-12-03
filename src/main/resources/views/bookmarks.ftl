<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.BookmarksView"
-->
<#import "masterTemplate.ftl" as layout>
<@layout.layout title="Bookmarks">

<list bookmarks as bookmark>

<#if (bookmark)??>
<div class="bookmark">
    <h4 class="bookmarkTitle"><#if (bookmark.title)??>${bookmark.title}<#else>Bookmark Title</#if></h4>
    <a class="bookmarkURL" href="#"></a><#if (bookmark.url)??>${bookmark.url}<#else>Bookmark URL</#if><br />
    <p class="bookmarkDescription"><#if (bookmark.description)??>${bookmark.description}</#if>
    </p>
    <ul class="bookmarkTags">
        <li><a href="#">Tag 1</a></li>
        <li><a href="#">Tag 2</a></li>
        <li><a href="#">Tag 3</a></li>
        <li><a href="#">Tag 4</a></li>
    </ul>
    <span>Added <#if (bookmark.dateAdded)??>${bookmark.dateAdded}<#else>Date Added</#if></span>
    <div class="editBookmark">
        <a href="#">Edit</a>&nbsp; <a href="#"
            onclick="return deleteBookmark(1)">Delete</a>
    </div>
</div>
</list>
</#if>
<a href="/"><img src="/img/nothing_here.png" style="display: block; margin-left: auto; margin-right: auto;"></img></a>
</@layout.layout>