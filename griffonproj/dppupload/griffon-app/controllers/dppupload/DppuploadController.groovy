package dppupload

//import javax.swing.filechooser.FileFilter
import javax.swing.JFileChooser

class DppuploadController {
    // these will be injected by Griffon
    def model
    def view



    def selectBaseDir = { evt = null ->
      def fc = view.baseDirDialog
      if(fc.showOpenDialog() != JFileChooser.APPROVE_OPTION) return //user cancelled
        model.baseDir = fc.selectedFile
    }

    // void mvcGroupInit(Map args) {
    //    // this method is called after model and view are injected
    // }

    // void mvcGroupDestroy() {
    //    // this method is called when the group is destroyed
    // }

    /*
    def action = { evt = null ->
    }
    */
}
