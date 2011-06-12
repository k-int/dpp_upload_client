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
        println "Select ${fc.selectedFile} as base directory"
        model.baseDir = fc.selectedFile
    }

    def start = {
      println "Start"
      if ( ( model.baseDir != null ) && ( model.baseDir.length() > 0 ) ) {
        File dir = new File(model.baseDir)
        if ( dir.exists() && dir.isDirectory() ) {
          println "Cool, ${model.baseDir} exists and is a directory...."
          process(dir)
        }
      }
    }

    def process(dir) {
      dir.listFiles().each { file ->
        println "Process ${file}"
      }
    }

    def stop = {
      println "Stop"
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
