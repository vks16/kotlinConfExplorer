import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.css.*
import react.*
import react.dom.*
import styled.*




val AppClient = functionalComponent<RProps> { props ->
    var (watchedVideos, setWatchedVideos) = useState<List<Video>>(listOf())
    var (unwatchedVideos, setUnwatchedVideos) = useState<List<Video>>(listOf())
    var (currentVideo, setCurrentVideo) = useState<Video?>(null)

    useEffect (emptyList()) {
        val mainScope = MainScope()
        mainScope.launch {
            val videos = fetchVideos()
            setUnwatchedVideos(videos)
        }
    }

    h1 {
        +"KotlinConf Explorer"
    }
    styledDiv{
        css{
            position = Position.absolute
            left = 10.px
            top = 50.px
        }
        h3{
            +"Videos to watch"
        }
        child(VideoList::class) {
            attrs.videos = watchedVideos
            attrs.selectedVideo = currentVideo
            attrs.onSelectVideo = {
                video -> setCurrentVideo(video)
            }
        }

        h3{
            +"Videos watched"
        }

        child(VideoList::class) {
            attrs.videos = unwatchedVideos
            attrs.selectedVideo = currentVideo
            attrs.onSelectVideo = {
                    video -> setCurrentVideo(video)
            }
        }

    }

    currentVideo?.let {
        currentVideo -> videoPlayer {
            video = currentVideo
            unwatchedVideo = currentVideo in unwatchedVideos
            onWatchedButtonPressed = {
                if(it in unwatchedVideos) {
                    setUnwatchedVideos(unwatchedVideos - it)
                    setWatchedVideos(watchedVideos + it)
                }else {
                    setUnwatchedVideos(unwatchedVideos + it)
                    setWatchedVideos(watchedVideos - it)
                }
            }
    }
    }
}

external interface Video {
    val id: Int
    val title: String
    val speaker: String
    val videoUrl: String
}

data class KotlinVideo(
    override val id: Int,
    override  val title: String,
    override  val speaker: String,
    override  val videoUrl: String
) : Video


suspend fun fetchVideo(id: Int) : Video {
    val response = window.fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
        .await()
        .json()
        .await()

    return response as Video

}

suspend fun fetchVideos(): List<Video> = coroutineScope {
    (1..25).map{ id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}
