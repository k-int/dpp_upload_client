package dppupload

//import javax.swing.filechooser.FileFilter
import javax.swing.JFileChooser
import groovyx.net.http.*
import org.springframework.beans.factory.InitializingBean
import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.*
import org.apache.http.entity.mime.*
import org.apache.http.entity.mime.content.*
import java.nio.charset.Charset


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

      def dpp_client = new HTTPBuilder( 'http://testaggregator.openfamilyservices.org.uk/dpp/provider/upload' )
      dpp_client.auth.basic model.dppUser, model.dppPass

      if ( ( model.baseDir != null ) && ( model.baseDir.length() > 0 ) ) {
        File dir = new File(model.baseDir)
        if ( dir.exists() && dir.isDirectory() ) {
          println "Cool, ${model.baseDir} exists and is a directory...."
          process(dir, dpp_client)
        }
      }
    }

    def process(dir, dpp_client) {
      // dir.listFiles().each { file ->
      for ( file in dir.listFiles() ) {
        println "Process ${file}"
        if ( file.isDirectory() ) {
          // Recurse
          process(file, dpp_client)
        }
        else {
          // See if the file can be parsed as XML. If so, it's a candidate for upload
          uploadFile(file, dpp_client)
          model.resources.add([resource:"${file}", status:"Not processed"])
          view.tm.fireTableRowsInserted(model.resources.size()-1,model.resources.size()-1)
        }

        if ( ! running )
          break;
      }
    }

    def uploadFile(file,dpp_client) {
      dppclient.request(POST) {request ->
        requestContentType = 'multipart/form-data'

        // Much help taken from http://evgenyg.wordpress.com/2010/05/01/uploading-files-multipart-post-apache/
        def multipart_entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipart_entity.addPart( "owner", new StringBody( model.dppAuthority, "text/plain", Charset.forName( "UTF-8" )))
        multipart_entity.addPart( "upload", new InputStreamBody( new FileInputStream(file), file.getName()) )

        request.addHeader("Content-length", "${multipart_entity.getContentLength()}")

        request.entity = multipart_entity;

        response.success = { resp ->
          println "response status: ${resp.statusLine}"
          assert resp.statusLine.statusCode == 200
        }
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
