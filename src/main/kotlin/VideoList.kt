import kotlinx.browser.window
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*

@JsExport
class VideoList: RComponent<VideoListProps, RState>() {
    override fun RBuilder.render() {
        for (video in props.videos) {
            p {
                key = video.id.toString()
                attrs {
                    onClickFunction = {
                        props.onSelectVideo(video)
                    }
                }
                if(video == props.selectedVideo) {
                    +"▶ "
                }
                +"${video.speaker}: ${video.title}"
            }
        }
    }


}

external interface VideoListProps: RProps {
    var videos: List<Video>
    var selectedVideo: Video?
    var onSelectVideo: (Video) -> Unit
}


//fun alert(video: Video): (Event) -> Unit {
//    window.alert("Clicked $video!")
//}
//fun RBuilder.videoList(handler: VideoListProps.() -> Unit): ReactElement {
//    return child(VideoList::class) {
//        this.attr(handler)
//    }
//}
