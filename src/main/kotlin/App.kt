import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.css.*
import react.*
import react.dom.*
import styled.css
import styled.styledDiv

external interface AppState: RState {
   var currentVideo: Video?
   var unwatchedVideos: List<Video>
   var watchedVideos: List<Video>
}



@JsExport
class App : RComponent<RProps, AppState>() {
   override fun AppState.init() {
      unwatchedVideos = listOf()
      watchedVideos = listOf()


      val mainScope = MainScope()
      mainScope.launch {
         val videos = fetchVideos()
         setState {
            unwatchedVideos = videos
         }
      }
   }
   override fun RBuilder.render() {
       // typesafe HTML goes here!
      h1 {
         +"KotlinConf Explorer"
      }
      styledDiv{
         css {
            position = Position.absolute
            left = 10.px
            top = 50.px
         }
         h3{
            +"Videos to watch"
         }
         child(VideoList::class){
            attrs.videos = state.unwatchedVideos
            attrs.selectedVideo = state.currentVideo
            attrs.onSelectVideo = { video ->
               setState {
                  currentVideo = video
               }
            }
         }
//         VideoList {
//            videos = unwatchedVideos
//         }


         h3 {
            + "Videos watched"
         }
         child(VideoList::class) {
            attrs.videos = state.watchedVideos
            attrs.selectedVideo = state.currentVideo
            attrs.onSelectVideo = { video ->
               setState {
                  currentVideo = video
               }
            }
         }

      }


      state.currentVideo?.let {
         currentVideo -> videoPlayer {
            video = currentVideo
            unwatchedVideo = currentVideo in state.unwatchedVideos
           onWatchedButtonPressed = {
              if(video in state.unwatchedVideos){
                 setState {
                    unwatchedVideos -= video
                    watchedVideos += video
                 }
              }else {
                 setState{
                    watchedVideos -= video
                    unwatchedVideos += video
                 }
              }
           }
      }
      }
   }
}





