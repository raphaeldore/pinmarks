<#-- @ftlvariable name="" type="ca.csf.rdore.pinmarks.views.BookmarkView"
-->
<div class="bookmark">
    <h4 class="bookmarkTitle">${bookmark.title}</h4>
    <a class="bookmarkURL" href="#"></a>${bookmark.url}<br />
    <p class="bookmarkDescription">${bookmark.description}
    </p>
    <ul class="bookmarkTags">
        <li><a href="#">Tag 1</a></li>
        <li><a href="#">Tag 2</a></li>
        <li><a href="#">Tag 3</a></li>
        <li><a href="#">Tag 4</a></li>
    </ul>
    <span>Added ${bookmark.dateAdded}</span>
    <div class="editBookmark">
        <a href="#">Edit</a>&nbsp; <a href="#"
            onclick="return deleteBookmark(1)">Delete</a>
    </div>
</div>