import kotlinx.browser.document
import react.child
import react.dom.*


fun main() {
//    document.bgColor = "blue"
//    document.bgColor = "red"
    render(document.getElementById("root")) {
//        child(App::class) {}

        child(AppClient){}
    }
}

