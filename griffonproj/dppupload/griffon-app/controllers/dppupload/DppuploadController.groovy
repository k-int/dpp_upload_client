package dppupload

//import javax.swing.filechooser.FileFilter
import javax.swing.JFileChooser

class DppuploadController {
    // these will be injected by Griffon
    def model
    def view
    def running = false;

    def selectBaseDir = { evt = null ->
      def fc = view.baseDirDialog
      if(fc.showOpenDialog() != JFileChooser.APPROVE_OPTION) return //user cancelled
        println "Select ${fc.selectedFile} as base directory"
        model.baseDir = fc.selectedFile
    }

    def start = {
      println "Start"
      running = true;
      if ( ( model.baseDir != null ) && ( model.baseDir.length() > 0 ) ) {
        File dir = new File(model.baseDir)
        if ( dir.exists() && dir.isDirectory() ) {
          println "Cool, ${model.baseDir} exists and is a directory...."
          process(dir)
        }
      }
    }

    def process(dir) {
      // dir.listFiles().each { file ->
      for ( file in dir.listFiles() ) {
        println "Process ${file}"
        if ( file.isDirectory() ) {
          // Recurse
          process(file)
        }
        else {
          // See if the file can be parsed as XML. If so, it's a candidate for upload
          model.resources.add([resource:"${file}", status:"Not processed"])
          view.tm.fireTableRowsInserted(model.resources.size()-1,model.resources.size()-1)
        }

        if ( ! running )
          break;
      }
    }

    def stop = {
      println "Stop"
      running = false;
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
