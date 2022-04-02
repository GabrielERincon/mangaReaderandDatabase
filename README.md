# Android Manga Reader

This is a manga reader for android using MangaDex API

## Architecture

```mermaid
classDiagram
  class MangaChapterAdapter {

    ~ chapters : ArrayList<MangaChapter>
    + MangaChapterAdapter(Context context, int textViewResourceId, ArrayList<MangaChapter> chapters)
    + getCount() : int
    + getView(int position, View convertView, ViewGroup parent) : View
  }
  class MainActivity {

    ~ searchClicked : boolean
    ~ toolbar_visible : boolean
    ~ info_visible : boolean
    # onCreate(Bundle savedInstanceState) : void
    + onClick(View v) : void
  }
  class MangaChapter {

    - data : HashMap<String, Object>
    - attributes : Map<String, String>
    - relationships : List<Map<String, String>>
    - pages : LinkedHashMap<String, byte[]>
    - hash : String
    - scanlationGroup : String
    + MangaChapter(HashMap<String, Object> data)
    + setPageBytes(String page, byte[] bytes) : void
    + getPageBytes(String page) : byte[]
    + getId() : String
    + getType() : String
    + getAttributes() : Map<String, String>
    + setAttributes(Map<String, String> attributes) : void
    + addPage(String page) : void
    + getPages() : LinkedHashMap<String, byte[]>
    + setPages(LinkedHashMap<String, byte[]> pages) : void
    + getHash() : String
    + setHash(String hash) : void
    + getMangaId() : String
    + getChapterId() : String
    + getVolume() : String
    + getChapterNumber() : String
    + getTranslatedLanguage() : String
    + getScanlationGroup() : String
    + getExternalUrl() : String
    + toString() : String
  }
  class ThemeActivity {

    ~ searchClicked : boolean
    ~ toolbar_visible : boolean
    ~ info_visible : boolean
    # onCreate(Bundle savedInstanceState) : void
    - themeList() : void
    + onClick(View v) : void
  }
  class ChapterActivity {

    ~ pageStart : int
    ~ currentPage : int
    ~ maxPage : int
    ~ searchClicked : boolean
    ~ toolbar_visible : boolean
    ~ info_visible : boolean
    ~ topbar_visible : boolean
    ~ usePages : ArrayList<String>
    ~ readingChapter : MangaChapter
    # onCreate(Bundle savedInstanceState) : void
    - chapterReading() : void
    - flipPage() : void
    - changePageText() : void
    + dispatchKeyEvent(KeyEvent event) : boolean
    + onClick(View v) : void
  }
  class CreditsActivity {

    ~ searchClicked : boolean
    ~ toolbar_visible : boolean
    ~ info_visible : boolean
    # onCreate(Bundle savedInstanceState) : void
    + onClick(View v) : void
  }
  class FavouritesActivity {

    - FILE_NAME : String$
    ~ searchClicked : boolean
    ~ toolbar_visible : boolean
    ~ info_visible : boolean
    # onCreate(Bundle savedInstanceState) : void
    + getFavouriteMangas() : ArrayList<Manga>
    + onClick(View v) : void
  }
  class MangaAdapter {

    ~ mangas : ArrayList<Manga>
    + MangaAdapter(Context context, int textViewResourceId, ArrayList<Manga> objects)
    + getCount() : int
    + getView(int position, View convertView, ViewGroup parent) : View
  }
  class SearchActivity {

    ~ searchClicked : boolean
    ~ toolbar_visible : boolean
    ~ info_visible : boolean
    + onCreate(Bundle savedInstanceState) : void
    - handleIntent(Intent intent) : void
    - doSearch(String x) : void
    + onClick(View v) : void
  }
  class MangaCover {

    - data : HashMap<String, Object>
    - attributes : Map<String, String>
    - relationships : List<Map<String, String>>
    - cover : byte
    - cover256 : byte
    - cover512 : byte
    + MangaCover(HashMap<String, Object> data)
    + getId() : String
    + getType() : String
    + getAttributes() : Map<String, String>
    + setAttributes(Map<String, String> attributes) : void
    + getMangaId() : String
    + getDescription() : String
    + getLocale() : String
    + getFileName() : String
    + getFileName(int width) : String
    + setCoverBytes(byte[] coverBytes, int width) : void
    + getCoverBytes() : byte[]
    + getCoverBytes(int width) : byte[]
    + toString() : String
  }
  class MangaDex {

    - responseCode : int
    - raw : String
    - json : JsonObject
    - apiHostname : String
    - apiPort : int
    - dlHostname : String
    - dlPort : int
    - tags : Map<String, String>$
    - authorsCache : Map<String, String>$
    - groupsCache : Map<String, String>$
    + MangaDex()
    + MangaDex(String apiHostname, String dlHostname)
    + MangaDex(String apiHostname, int apiPort, String dlHostname, int dlPort)
    + getTagInfo() : void$
    + getTags() : Map<String, String>$
    + searchByTag(String tagId) : List<Manga>
    + searchManga() : List<Manga>
    + searchManga(String pattern) : List<Manga>
    + searchManga(String pattern, String tagId) : List<Manga>
    + getCoverInfo(List<Manga> mangas) : void
    + streamCover(MangaCover cover) : ReadableByteChannel
    + streamCover(MangaCover cover, int width) : ReadableByteChannel
    + getCoverBytes(MangaCover cover) : byte[]
    + getCoverBytes(MangaCover cover, int width) : byte[]
    + getChapterInfo(Manga manga) : void
    + getPagesInfo(MangaChapter chapter) : void
    + streamPage(MangaChapter chapter, String page) : ReadableByteChannel
    + getPageBytes(MangaChapter chapter, String page) : byte[]
    + translateIdtoString(String id, String call) : String
    + getAuthorsCache() : Map<String, String>$
    + setAuthorsCache(Map<String, String> authorsList) : void$
    + getGroupsCache() : Map<String, String>$
    + setGroupsCache(Map<String, String> groupsList) : void$
  }
  class Keys {
<<enumeration>>
    + RESULT$
    + DATA$
    + LIMIT$
    + OFFSET$
    + TOTAL$
    + CHAPTER$
    - value : Object
    ~ Keys(Object value)
    + getKey() : String
    + getValue() : Object
  }
  class DetailActivity {

    - FILE_NAME : String$
    ~ searchClicked : boolean
    ~ toolbar_visible : boolean
    ~ info_visible : boolean
    ~ saved : boolean
    ~ manga : Manga
    # onCreate(Bundle savedInstanceState) : void
    - chapterSelection() : void
    + addOrRemoveFavourite() : void
    + getFavouriteMangas() : ArrayList<Manga>
    + storeFavouriteMangas(ArrayList<Manga> favMangas) : void
    + onClick(View v) : void
  }
  class Manga {

    - data : HashMap<String, Object>
    - attributes : Map<String, Map<String, Object>>
    - relationships : List<Map<String, String>>
    - covers : List<MangaCover>
    - chapters : List<MangaChapter>
    - author : String
    + Manga(HashMap<String, Object> data)
    + getId() : String
    + getType() : String
    + getTitle() : String
    + getTitle(String locale) : String
    + addCover(MangaCover cover) : void
    + getCovers() : List<MangaCover>
    + addChapter(MangaChapter chapter) : void
    + getChapters() : List<MangaChapter>
    + getAuthor() : String
    + getDate() : String
    + getDescription() : String
    + toString() : String
  }
ChapterActivity --> CreditsActivity
ChapterActivity --> FavouritesActivity
ChapterActivity --> MainActivity
ChapterActivity --> MangaChapter
ChapterActivity --> MangaDex
ChapterActivity --> ThemeActivity
CreditsActivity --> FavouritesActivity
CreditsActivity --> MainActivity
CreditsActivity --> ThemeActivity
DetailActivity --> ChapterActivity
DetailActivity --> CreditsActivity
DetailActivity --> FavouritesActivity
DetailActivity --> MainActivity
DetailActivity --> Manga
DetailActivity --> MangaChapter
DetailActivity --> MangaChapterAdapter
DetailActivity --> MangaCover
DetailActivity --> MangaDex
DetailActivity --> ThemeActivity
FavouritesActivity --> CreditsActivity
FavouritesActivity --> DetailActivity
FavouritesActivity --> MainActivity
FavouritesActivity --> Manga
FavouritesActivity --> MangaAdapter
FavouritesActivity --> MangaCover
FavouritesActivity --> MangaDex
FavouritesActivity --> ThemeActivity
MainActivity --> CreditsActivity
MainActivity --> FavouritesActivity
MainActivity --> ThemeActivity
MangaAdapter --> Manga
MangaAdapter --> MangaCover
MangaChapter --> MangaDex
MangaChapterAdapter --> MangaChapter
MangaDex --> Manga
MangaDex --> MangaChapter
MangaDex --> MangaCover
SearchActivity --> CreditsActivity
SearchActivity --> DetailActivity
SearchActivity --> FavouritesActivity
SearchActivity --> MainActivity
SearchActivity --> Manga
SearchActivity --> MangaAdapter
SearchActivity --> MangaCover
SearchActivity --> MangaDex
SearchActivity --> ThemeActivity
ThemeActivity --> CreditsActivity
ThemeActivity --> FavouritesActivity
ThemeActivity --> MainActivity
```
