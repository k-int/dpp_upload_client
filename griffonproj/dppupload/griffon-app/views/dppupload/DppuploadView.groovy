package dppupload

import java.awt.Color
import org.jdesktop.swingx.painter.GlossPainter
import java.awt.BorderLayout
import net.miginfocom.swing.MigLayout
import javax.swing.filechooser.FileFilter
import javax.swing.JFileChooser

gloss = glossPainter(paint: new Color(1f, 1f, 1f, 0.2f), position: GlossPainter.GlossPosition.TOP)
stripes = pinstripePainter(paint: new Color(1f, 1f, 1f, 0.17f), spacing: 5.0)
matte = mattePainter(fillPaint: new Color(51, 51, 51))
compound = compoundPainter(painters: [matte, stripes, gloss])

// Some of this taken from http://griffon.codehaus.org/FileViewer  
openAction = action(closure: controller.selectBaseDir, name:"Select...")
startAction = action(closure: controller.start, name:"Start")
stopAction = action(closure: controller.stop, name:"Stop")

baseDirDialog  = fileChooser(dialogTitle:"Choose a source directory", 
                             id:"selectSourceDirectory", 
                             fileSelectionMode : JFileChooser.DIRECTORIES_ONLY) {
                             //the file filter must show also directories, in order to be able to look into them
                             //fileFilter: [getDescription: {-> "*.xls"}, accept:{file-> file ==~ /.*?\.xls/ || file.isDirectory() }] as FileFilter) {
}


application(title: 'swingx-test', pack: true, locationByPlatform: true,
            iconImage: imageIcon('/griffon-icon-48x48.png').image,
            iconImages: [imageIcon('/griffon-icon-48x48.png').image,
            imageIcon('/griffon-icon-32x32.png').image,
            imageIcon('/griffon-icon-16x16.png').image]) {
    borderLayout()

    jxheader(constraints: BorderLayout.NORTH, title: "Data Provider Portal Simple Upload Utility",
             description: "Data Provider Portal - Simple Upload Utility",
             titleForeground: Color.WHITE,
             descriptionForeground: Color.WHITE,
             // icon: imageIcon("/griffon-icon-48x48.png"),
             preferredSize: [480,80],
             backgroundPainter: compound)

    

    panel(constraints: CENTER, border:emptyBorder(12), layout:new MigLayout('fill',"[][grow]")) {
      label(text:"Base Directory")
      textField(id:'BaseDir',  text: bind{model.baseDir},
                editable:false,
                constraints:"split 2, growx")
      button(constraints:"wrap",action:openAction)
      label(text:"DPP Username")
      textField(id:'DPPUserName',  text: bind{model.dppUser},
                editable:true,
                constraints:"growx, wrap")
      label(text:"DPP Password")
      textField(id:'DPPPassword',  text: bind{model.dppPass},
                editable:true,
                constraints:"growx, wrap")
      label(text:"Authority")
      textField(id:'DPPAuth',  text: bind{model.dppAuthority},
                // columns:20,
                editable:true,
                constraints:"growx, wrap")
      panel(constraints:"span, wrap") {
        button(action:startAction)
        button(action:stopAction)
      }

      sp = splitPane(constraints:"grow, push, span") {
        scrollPane() {
          table {
            tableModel (list:model.resources) {
              propertyColumn(header:'Resource', propertyName:'resource')
              propertyColumn(header:'Status', propertyName:'status')
            }
          }
        }

        tabbedPane() {
          panel(name:'Resource') {
            label('The Resouce')
          }
          panel(name:'Response Detail') {
            label('The Response')
          }
        }
      }

      sp.dividerLocation=200;
    }
  }


// Old view
// application(title: 'dppupload',
  //size: [320,480],
//   pack: true,
  //location: [50,50],
//   locationByPlatform:true,
//   iconImage: imageIcon('/griffon-icon-48x48.png').image,
//   iconImages: [imageIcon('/griffon-icon-48x48.png').image,
//                imageIcon('/griffon-icon-32x32.png').image,
//                imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
//     label('Content Goes Here') // deleteme
// }
