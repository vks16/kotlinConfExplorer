@file:JsModule("react-youtube-lite")
@file:JsNonModule

import react.*

@JsName("ReactYouTubeLite")
external val reactPlayer: RClass<ReactYoutubeProps>

external interface ReactYoutubeProps: RProps {
    var url: String
    var aspectRatio: String
}